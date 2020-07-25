package pl.endproject.offerscomparator.subdomains.autocompleteFeature;

import java.util.ArrayList;
import java.util.List;


public class Reader {
    private List<String> words;

    public Reader(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }


    public List<String> getWords(String searchStr) {

        List<String> wordsFromReader = this.getWords();
        List<String> suggestions = new ArrayList<>();

        for (String s : wordsFromReader) {
            if (s.startsWith(searchStr)) {
                suggestions.add(s);
            }
            if (suggestions.size() > 3) {
                break;
            }
        }
        return suggestions;
    }


}
