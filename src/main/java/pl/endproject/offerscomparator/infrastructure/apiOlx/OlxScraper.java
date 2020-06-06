package pl.endproject.offerscomparator.infrastructure.apiOlx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
class OlxScraper {

    private static String searchingUrl;

    public static List<String> searchFor(String searchItem) {

        List<String> foundItems = new ArrayList<>();
        searchingUrl = "https://www.olx.pl/oferty/q-" + searchItem + "/?view=galleryWide";

        try {
            Document page = Jsoup.connect(searchingUrl).userAgent("Jsoup Scraper").get();
            foundItems = connectElements(getNames(page), getPrices(page), getImage(page), getProductUrl(page));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong url");
        }
        return foundItems;
    }

    private static List<String> connectElements(Elements names, Elements prices, Elements imageUrls, Elements productUrl) {
        List<String> foundElements = new ArrayList<>();
        Integer minValue = Collections.min(List.of(names.size(), prices.size(), imageUrls.size(), productUrl.size()));
        for (int i = 0; i < minValue; i++) {
            foundElements.add(names.get(i).text()
                    + "::" + prices.get(i).text()
                    + "::" + imageUrls.get(i).absUrl("src")
                    + "::" + productUrl.get(i).attr("href"));
        }
        return foundElements;
    }

    private static Elements getNames(Document page) {
        return page.select("div.rel >#gallerywide >.wrap >.inner>.normal>.link");
    }

    private static Elements getPrices(Document page) {
        return page.select("div.rel> #gallerywide >.wrap >.price");
    }

    private static Elements getImage(Document page) {
        return page.select("#gallerywide>.wrap> .mheight >.thumb>img");
    }

    private static Elements getProductUrl(Document page) {
        return page.select("#gallerywide>.wrap> .mheight >.thumb");
    }


}
