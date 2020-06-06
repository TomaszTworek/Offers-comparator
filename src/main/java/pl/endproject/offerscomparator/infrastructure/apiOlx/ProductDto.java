package pl.endproject.offerscomparator.infrastructure.apiOlx;

import lombok.Builder;
import lombok.Data;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.Source;

@Data
@Builder
public class ProductDto {
    private String name;
    private String price;
    private String url;
    private String imageUrl;
    private final Source source = Source.OLX;

    public Product toDomain() {
        return Product.builder()
                .name(this.name)
                .price(this.convertPrice(this.price))
                .url(this.url)
                .imageUrl(this.imageUrl)
                .source(this.source)
                .build();
    }

    private Double convertPrice(String price) {
        String editedPrice = price.toLowerCase().trim();
        Double finalPrice;
        if (checkIfStringContainsNumbers(editedPrice)) {
            finalPrice = convertStringToPrice(editedPrice);
        } else if (editedPrice.equals("za darmo")) {
            finalPrice = 0.0;
        } else {
            finalPrice = null;
        }
        return finalPrice;
    }

    private double convertStringToPrice(String editedPrice) {
        return Double.parseDouble(editedPrice.replace(",", ".").replaceAll("[^0-9.]+", " "));
    }

    private boolean checkIfStringContainsNumbers(String editedPrice) {
        return editedPrice.matches(".*\\d+.*");
    }
}
