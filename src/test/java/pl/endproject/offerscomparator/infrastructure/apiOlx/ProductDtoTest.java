package pl.endproject.offerscomparator.infrastructure.apiOlx;

import org.junit.Before;
import org.junit.Test;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.Source;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ProductDtoTest {

    private ProductDto productDtoOne;
    private ProductDto productDtoTwo;
    private ProductDto productDtoThree;
    private ProductDto productDtoFour;
    private ProductDto productDtoFive;
    private Product product;

    @Before
    public void setUp() {
        productDtoOne = ProductDto.builder()
                .name("Test1")
                .price("2 zł")
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .build();
        productDtoTwo = ProductDto.builder()
                .name("Test1")
                .price("")
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .build();
        productDtoThree = ProductDto.builder()
                .name("Test1")
                .price("do negocjacji")
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .build();
        productDtoFour = ProductDto.builder()
                .name("Test1")
                .price("za darmo")
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .build();
        productDtoFive = ProductDto.builder()
                .name("Test1")
                .price("zamienię")
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .build();
    }

    @Test
    public void shouldConvertProductDtoToDomainProductWhenPriceIsGiven() {
        //given
        product = Product.builder()
                .name("Test1")
                .price(2.0)
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .source(Source.OLX)
                .build();

        //when
        Product resultProduct = productDtoOne.toDomain();

        //then
        assertThat(resultProduct).isEqualTo(product);
    }

    @Test
    public void shouldConvertProductDtoToDomainProductWhenPriceIsNotGiven() {
        //given
        product = Product.builder()
                .name("Test1")
                .price(null)
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .source(Source.OLX)
                .build();

        //when
        Product resultProduct = productDtoTwo.toDomain();

        //then
        assertThat(resultProduct).isEqualTo(product);
    }

    @Test
    public void shouldConvertProductDtoToDomainProductWhenPriceIsToNegotiate() {
        //given
        product = Product.builder()
                .name("Test1")
                .price(null)
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .source(Source.OLX)
                .build();

        //when
        Product resultProduct = productDtoThree.toDomain();

        //then
        assertThat(resultProduct).isEqualTo(product);
    }

    @Test
    public void shouldConvertProductDtoToDomainProductWhenPriceIsForFree() {
        //given
        product = Product.builder()
                .name("Test1")
                .price(0.0)
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .source(Source.OLX)
                .build();

        //when
        Product resultProduct = productDtoFour.toDomain();

        //then
        assertThat(resultProduct).isEqualTo(product);
    }

    @Test
    public void shouldConvertProductDtoToDomainProductWhenPriceIsChangeForSomething() {
        //given
        product = Product.builder()
                .name("Test1")
                .price(null)
                .imageUrl("https://sdsae.pl")
                .url("https://sdaq.pl")
                .source(Source.OLX)
                .build();

        //when
        Product resultProduct = productDtoFive.toDomain();

        //then
        assertThat(resultProduct).isEqualTo(product);
    }

}
