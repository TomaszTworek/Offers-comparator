package pl.endproject.offerscomparator.infrastructure.apiOlx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
class OlxScraper {


    private static String searchingUrl;


    public static List<String> searchFor(String searchItem, Integer numberOfItems) {

        List<String> foundItems = new ArrayList<>();
        searchingUrl = "https://www.olx.pl/oferty/q-" + searchItem + "/?search[order]=filter_float_price%3Aasc&view=galleryWide";

        try {
            Document page = Jsoup.connect(searchingUrl).userAgent("Jsoup Scraper").get();
            foundItems = connectElements(getNames(page), getPrices(page), getImage(page), getProductUrl(page), numberOfItems);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong url");
        }
        return foundItems;
    }


    private static List<String> connectElements(Elements names, Elements prices, Elements imageUrls, Elements productUrl, Integer size) {

        List<String> foundElements = new ArrayList<>();
        for (int i = 0; i < size; i++) {
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