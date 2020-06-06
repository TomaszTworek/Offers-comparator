package pl.endproject.offerscomparator.infrastructure.apiAllegroLokalnie;

import org.springframework.stereotype.Component;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.port.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LokalnieAdapter implements ProductRepository {

    @Override
    public List<Product> findByPhrase(String phrase) {
        List<LokalnieProductDto> dto = adaptItemsFromScraper(phrase);
        return toDomain(dto);
    }

    public List<LokalnieProductDto> adaptItemsFromScraper(String phrase) {
        List<String> listFromScraper = LokalnieScraper.finder(phrase, 5);
        List<LokalnieProductDto> dtos = new ArrayList<>();
        List<String> parts;


        for (String itemFromScraper : listFromScraper) {
            LokalnieProductDto product = new LokalnieProductDto();
            parts = Arrays.asList(itemFromScraper.trim().split("::"));
            product.setName(parts.get(0));
            product.setPrice(parts.get(1));
            product.setImageUrl(parts.get(2));
            product.setUrl(parts.get(3));
            dtos.add(product);

        }
        return dtos;
    }

    private List<Product> toDomain(List<LokalnieProductDto> dto) {
        return dto.stream()
                .map(LokalnieProductDto::toDomain)
                .collect(Collectors.toList());
    }

}
