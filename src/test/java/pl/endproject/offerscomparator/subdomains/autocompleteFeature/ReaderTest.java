package pl.endproject.offerscomparator.subdomains.autocompleteFeature;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReaderConfig.class)
public class ReaderTest {
    private ReaderConfig readerConfig;

    @Before
    public void setUp() {
        readerConfig = new ReaderConfig();
    }

    @Test
    public void shouldReadAllWordsFromTextFile() {

        //given
        String word = "krzesło";
        String szklanka = "szklanka";
        String abdykacyjny = "abdykacyjny";
        String żyzność = "żyzność";

        //when
        List<String> words = readerConfig.getWords();

        //then
        Assert.assertTrue(words.contains(word));
        Assert.assertTrue(words.contains(szklanka));
        Assert.assertTrue(words.contains(abdykacyjny));
        Assert.assertTrue(words.contains(żyzność));

    }

    @Test
    public void shouldReadFromProgramMemory() {

        //given
        Reader reader = readerConfig.readerFromFile();
        String word = "krzesło";
        String szklanka = "szklanka";
        String abdykacyjny = "abdykacyjny";
        String żyzność = "żyzność";

        //when
        List<String> wordsFromReader = reader.getWords();


        //then
        Assert.assertTrue(wordsFromReader.contains(word));
        Assert.assertTrue(wordsFromReader.contains(szklanka));
        Assert.assertTrue(wordsFromReader.contains(abdykacyjny));
        Assert.assertTrue(wordsFromReader.contains(żyzność));

        /* ** */

        for (int i = 0; i < 5; i++) {
            System.out.println(wordsFromReader.get(i));

        }
    }


}
