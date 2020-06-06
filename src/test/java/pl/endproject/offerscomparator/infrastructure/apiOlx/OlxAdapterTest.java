package pl.endproject.offerscomparator.infrastructure.apiOlx;

import org.junit.Before;
import org.junit.Test;
import pl.endproject.offerscomparator.domain.Product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.Assert.*;

public class OlxAdapterTest {
    private OlxAdapter olxAdapter;

    @Before
    public void setUp() {
        olxAdapter = new OlxAdapter();
    }

    @Test
    public void shouldCreateNewProductsWhenWePassRandomPhrase() {
        //given
        String searchProduct = "potop";

        //when
        List<Product> resultProducts = olxAdapter.findByPhrase(searchProduct);

        //then
        assertThat(resultProducts.get(0).getId()).isNull();
        assertThat(resultProducts.get(0).getName()).isNotNull();
        assertThat(resultProducts.get(0).getImageUrl()).isNotNull();
        assertThat(resultProducts.get(0).getUrl()).isNotNull();
        assertThat(resultProducts.get(0).getSource()).isNotNull();
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWithSpecificMessageWhenPassEmptyPhrase() {
        //given
        String searchProduct = "";
        String message = "Phrase cannot be empty";

        //when
        try {
            olxAdapter.findByPhrase(searchProduct);
        } catch (RuntimeException re) {
            //then
            assertEquals(message, re.getMessage());
            throw re;
        }
        //orElse
        fail("RuntimeException did not throw!");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWithSpecificMessageWhenPassSomeSpacesAsInput() {
        //given
        String searchProduct = "      ";
        String message = "Phrase cannot be empty";

        //when
        try {
            olxAdapter.findByPhrase(searchProduct);
        } catch (RuntimeException re) {
            //then
            assertEquals(message, re.getMessage());
            throw re;
        }
        //orElse
        fail("RuntimeException did not throw!");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWithSpecificMessageWhenPhraseIsNull() {
        //given
        String searchProduct = null;
        String message = "Phrase cannot be empty";

        //when
        try {
            olxAdapter.findByPhrase(searchProduct);
        } catch (RuntimeException re) {
            //then
            assertEquals(message, re.getMessage());
            throw re;
        }
        //orElse
        fail("RuntimeException did not throw!");
    }

    @Test
    public void displayProductsCreatedFromScraper() {
        //given
        String searchProduct = "krzesło";

        //when
        List<Product> resultProducts = olxAdapter.findByPhrase(searchProduct);

        for (Product p : resultProducts) {
            System.out.println(p);
        }
    }
}
