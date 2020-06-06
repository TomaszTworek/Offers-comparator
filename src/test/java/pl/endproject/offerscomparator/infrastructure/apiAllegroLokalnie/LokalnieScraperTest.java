package pl.endproject.offerscomparator.infrastructure.apiAllegroLokalnie;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LokalnieScraperTest {

    @Test
    public void shouldDisplayProductsInfoForGivenPhrase(){

        //given
        String phrase = " sienkiewicz potop";

        //when
        List<String> itemsFromScraper = LokalnieScraper.finder(phrase,5);

        //then
        Assert.assertFalse(itemsFromScraper.isEmpty());
        Assert.assertEquals(5, itemsFromScraper.size());
        Assert.assertTrue(itemsFromScraper.get(0).contains("Henryk Sienkiewicz - Potop t. 1-3"));

        // Display items
        for (String s: itemsFromScraper
             ) {
            System.out.println(s);

        }


    }
}
