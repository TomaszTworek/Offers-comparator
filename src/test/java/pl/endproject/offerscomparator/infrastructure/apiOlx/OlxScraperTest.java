package pl.endproject.offerscomparator.infrastructure.apiOlx;

import org.junit.Test;
import java.util.List;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;

public class OlxScraperTest {

    @Test
    public void shouldDisplayCertainStringPatternAfterSearchingForProducts() {
        //given
        String searchingItem = "Potop";
        String patternString = ".*::.*::.*::.*";

        //when
        List<String> result = OlxScraper.searchFor(searchingItem);
        Pattern pattern = Pattern.compile(patternString);

        //then
        assertThat(pattern.matcher(result.get(0)).matches()).isEqualTo(true);
    }

    @Test
    public void displayResultOfScraperItems() {
        String searchingItem = "krzesło";

        List<String> result = OlxScraper.searchFor(searchingItem);

        for (String product:result) {
            System.out.println(product);
        }
    }
}
