package pl.endproject.offerscomparator.infrastructure.apiAllegroLokalnie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.endproject.offerscomparator.domain.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LokalnieAapterTest {
    private LokalnieAdapter adapter;

    @Before
    public void setUp(){
        adapter=new LokalnieAdapter();
    }

    @Test
    public void shouldMapLokalnieProductDtoToProduct(){
        //given
        String phrase ="sienkiewicz potop";

        //when
        List<Product> productsList = adapter.findByPhrase(phrase);

        //then
        Assert.assertFalse(productsList.isEmpty());
        Assert.assertEquals(5, productsList.size());
        Assert.assertEquals("Henryk Sienkiewicz - Potop t. 1-3", productsList.get(0).getName());
        Assert.assertEquals(Double.valueOf(1.99),productsList.get(0).getPrice());
        Assert.assertEquals("Potop Henryka Sienkiewicza Powieść i Film 1977", productsList.get(4).getName());
        Assert.assertEquals(Double.valueOf(15.00),productsList.get(4).getPrice());

    }

    @Test
    public void shouldDisplayCorrectData(){
        //given
        String phrase = "farba do drewna";

        //when
        List<Product> productsList = adapter.findByPhrase(phrase);

        //then
        Assert.assertFalse(productsList.isEmpty());
        Assert.assertEquals(2, productsList.size());

        //display data

        for (int i = 0; i <productsList.size(); i++) {
            System.out.println("Titles: " + productsList.get(i).getName() + "\n");
            System.out.println("Prices: " + productsList.get(i).getPrice() + "\n");
            System.out.println("Image urls: " + productsList.get(i).getImageUrl() + "\n");
            System.out.println("Links: " + productsList.get(i).getUrl());

        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenStringIsEmpty(){
        //given
        String phrase ="";

        //when
        List<Product> productsList = adapter.findByPhrase(phrase);

    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenStringIsUnrecognised(){
        //given
        String phrase ="kkjwbka";
        String message = "Brak wyników";


        //when
        try {
            List<Product> byPhrase = adapter.findByPhrase(phrase);
        } catch (RuntimeException e) {
            //then
            assertEquals(message, e.getMessage());
            throw e;
        }


    }

}
