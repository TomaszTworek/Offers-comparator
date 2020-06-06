package pl.endproject.offerscomparator.infrastructure.apiOlx;

import org.springframework.stereotype.Component;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.port.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OlxAdapter implements ProductRepository {

    @Override
    public List<Product> findByPhrase(String phrase) {
        if (phrase == null || phrase.trim().isEmpty()) {
            throw new RuntimeException("Phrase cannot be empty");
        }
        List<ProductDto> productDtos = getProductsFromScraper(phrase);
        return mapToDomain(productDtos);
    }

    private List<ProductDto> getProductsFromScraper(String phrase) {
        List<String> scrapedProducts = OlxScraper.searchFor(phrase);
        List<ProductDto> dtosProducts = new ArrayList<>();
        for (String productFromScraper : scrapedProducts) {
            ProductDto newProduct = convertStringProductFromScraperToProductDto(productFromScraper);
            dtosProducts.add(newProduct);
        }
        return dtosProducts;
    }

    private ProductDto convertStringProductFromScraperToProductDto(String productFromScraper) {
        String[] splitElements = productFromScraper.trim().split("::");
        return ProductDto.builder()
                .name(splitElements[0])
                .price(splitElements[1])
                .imageUrl(splitElements[2])
                .url(splitElements[3])
                .build();
    }

    private List<Product> mapToDomain(List<ProductDto> productDtos) {
        return productDtos.stream()
                .map(ProductDto::toDomain)
                .collect(Collectors.toList());
    }


}
