package pl.endproject.offerscomparator.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.ProductService;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.Phrase;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.Reader;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.ReaderConfig;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.SuggestionsWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private final ReaderConfig readerConfig;

    public ProductController(ProductService productService, ReaderConfig readerConfig) {
        this.productService = productService;
        this.readerConfig = readerConfig;
    }

    @GetMapping("/offers")
    public String getOffers(Model model, @RequestParam(value = "userSearch", required = false, defaultValue = "") String userSearch) {
        if (!userSearch.isBlank()) {
            List<Product> products = productService.findForPhrase(userSearch);
            if (products.isEmpty() ) {
                return "no-results";
            }
            model.addAttribute("products", products);
        }
        return "getAll";
    }

    @PostMapping("/findProducts")
    public String findOffers(@RequestParam String userSearch) throws UnsupportedEncodingException {
        String encodedUserSearch = URLEncoder.encode(userSearch, StandardCharsets.UTF_8.toString());
        return "redirect:/offers?userSearch=" + encodedUserSearch;
    }

    @RequestMapping(value = "/suggestion", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SuggestionsWrapper autocompleteSuggestions(@RequestParam("userSearch") String userSearch) throws IOException {
        ArrayList<Phrase> suggestions = new ArrayList<>();
        SuggestionsWrapper sw = new SuggestionsWrapper();
        Reader reader = readerConfig.readerFromFile();

        List<String> wordsFromReader = reader.getWords(userSearch);
        for (String productName : wordsFromReader) {
            suggestions.add(new Phrase(productName));

            sw.setSuggestions(suggestions);
        }
        return sw;
    }

}
