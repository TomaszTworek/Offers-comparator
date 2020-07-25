package pl.endproject.offerscomparator.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.endproject.offerscomparator.domain.Product;
import pl.endproject.offerscomparator.domain.ProductService;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.Phrase;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.Reader;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.ReaderConfig;
import pl.endproject.offerscomparator.infrastructure.autocompleteFeature.SuggestionsWrapper;
import pl.endproject.offerscomparator.infrastructure.memoryCash.MemoryCash;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private ProductService productService;
    private final ReaderConfig readerConfig;
    private Reader reader;
    private List<Product> products;

    @Autowired
    private MemoryCash memoryCash;

    public ProductController(ProductService productService, ReaderConfig readerConfig, Reader reader) {
        this.productService = productService;
        this.readerConfig = readerConfig;
        this.reader = reader;
    }

    @GetMapping("/")
    public String main() {
        return "redirect:/offers";
    }

    @GetMapping("/offers")
    public String getOffers(Model model, @RequestParam(value = "userSearch", required = false, defaultValue = "") String userSearch, HttpSession session, HttpServletResponse response) {
        session.setAttribute("products", null);

        if (!userSearch.isBlank()) {

            products = productService.findForPhraseAsync(userSearch);

            if (products.isEmpty()) {
                return "no-results";
            }
            model.addAttribute("products", products);



            /* Dodawanie do Listy */
            List<List<Product>> cashProducts = memoryCash.getCashProducts();
            cashProducts.add(products);
            model.addAttribute("id", memoryCash.getId());








            /* Version with cookies*/
//            session.setAttribute("products", products);


//            Cookie newCookie = new Cookie("myCookie", "1");
//            newCookie.setMaxAge(30000);
//            response.addCookie(newCookie);
        }
        return "getAll";
    }

    private void benchmark(String userSearch) {
        long synchronizedStart = System.currentTimeMillis();
        productService.findForPhrase(userSearch);
        System.out.println("Synchronized action: ");
        System.out.println(System.currentTimeMillis() - synchronizedStart);

        long asynchronizedStart = System.currentTimeMillis();
        productService.findForPhraseAsync(userSearch);
        System.out.println("Asynchronized action: ");
        System.out.println(System.currentTimeMillis() - asynchronizedStart);
    }

    @PostMapping("/findProducts")
    public String findOffers(@RequestParam String userSearch) throws UnsupportedEncodingException {
        String encodedUserSearch = URLEncoder.encode(userSearch, StandardCharsets.UTF_8.toString());

        return "redirect:/offers?userSearch=" + encodedUserSearch;
    }


    @RequestMapping(value = "/suggestion", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public SuggestionsWrapper autocompleteSuggestions(@RequestParam("userSearch") String userSearch) {
        ArrayList<Phrase> suggestions = new ArrayList<>();
        SuggestionsWrapper sw = new SuggestionsWrapper();
        reader = readerConfig.readerFromFile();

        List<String> wordsFromReader = reader.getWords(userSearch);
        for (String productName : wordsFromReader) {
            suggestions.add(new Phrase(productName));

            sw.setSuggestions(suggestions);
        }
        return sw;
    }

}
