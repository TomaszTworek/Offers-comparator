package pl.endproject.offerscomparator.infrastructure.apiAllegroLokalnie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.Source;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LokalnieProductDto {
    private String name;
    private String price;
    private String url;
    private String imageUrl;
    private final Source source = Source.ALLEGRO_LOKALNIE;

    public Product toDomain() {
        return Product.builder()
                .name(this.name)
                .price(convertPrice(this.price))
                .imageUrl(this.imageUrl)
                .url(this.url)
                .source(this.source)
                .build();
    }

    private Double convertPrice(String s) {
        return Double.parseDouble(s.replace(",", ".")
                .replace("zł", ""));
    }
}
