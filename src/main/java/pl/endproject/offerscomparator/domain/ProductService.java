package pl.endproject.offerscomparator.domain;

import org.springframework.stereotype.Service;
import pl.endproject.offerscomparator.domain.port.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    private List<ProductRepository> productRepository;

    public ProductService(List<ProductRepository> productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findForPhrase(String phrase) {
        return productRepository.stream()
                .map(repository -> repository.findByPhrase(phrase))
                .flatMap(products -> products.stream())
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());

    }
}
