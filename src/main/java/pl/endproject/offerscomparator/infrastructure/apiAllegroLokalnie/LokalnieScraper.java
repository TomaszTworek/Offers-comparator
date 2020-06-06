package pl.endproject.offerscomparator.infrastructure.apiAllegroLokalnie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LokalnieScraper {
    private final static String URL = "https://allegrolokalnie.pl";



    public static List<String> finder(String phrase, Integer count) {
        List<String> productsList = new ArrayList<>();
        String url = URL + "/oferty/?phrase=" + phrase.replace(" ","+") + "&city=&sort=price-asc&price_from=&price_to=&sort=price-asc&page=1&page=1";

        if (phrase.isEmpty()){
            throw new RuntimeException("Pole wyszukiwanie nie może zostać puste!");
        }

        try {
            Document page = Jsoup.connect(url).userAgent("Jsoup Scraper").get();
            productsList = connectElements(getTitles(page), getPrices(page), getImageUrl(page), getProductUrl(page), count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (productsList.isEmpty()){
            throw new RuntimeException("Brak wyników");
        }
        return productsList;
    }




    private static List<String> connectElements(Elements titles, Elements prices, Elements imageUrls, Elements productUrl, Integer size) {

        List<String> foundElements = new ArrayList<>();

        if (titles.size()<size){
            size=titles.size();
        }

        for (int i = 0; i < size; i++) {
            foundElements.add(titles.get(i).text()
                    + "::" + prices.get(i).text()
                    + "::" + imageUrls.get(i).absUrl("src")
                    + "::" + " " +  URL +  productUrl.get(i).attr("href"));
        }
        return foundElements;
    }



    private static Elements getTitles(Document doc) {
        return doc.select("#app-root > form > main > div > div.layout__main > div > div.listing > div.listing__body > div > div > div > a > div.offer-card__body > div.offer-card__body-header > h3");
    }

    private static Elements getPrices(Document doc) {
        return doc.select("#app-root > form > main > div > div.layout__main > div > div.listing > div.listing__body > div > div > div > a > div.offer-card__body > div.offer-card__price > span");
    }

    private static Elements getImageUrl(Document doc) {
        return doc.select("#app-root > form > main > div > div.layout__main > div > div.listing > div.listing__body > div > div > div > a > div.offer-card__image > div > img");
    }

    private static Elements getProductUrl(Document doc) {
        return doc.select("#app-root > form > main > div > div.layout__main > div > div.listing > div.listing__body > div > div > div > a");
    }


}
