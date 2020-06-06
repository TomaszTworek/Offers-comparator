package pl.endproject.offerscomparator.infrastructure.apiAllegro.repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.endproject.offerscomparator.infrastructure.apiAllegro.model.Item;
import pl.endproject.offerscomparator.infrastructure.apiAllegro.properties.ApiAllegroProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ItemRepositoryImpl implements ItemRepository {

    private List<Item> listOfAllegroItems;

    public ItemRepositoryImpl() {
        this.listOfAllegroItems = new ArrayList<>();
    }

    private List<Item> getListOfAllegroItems() {
        return listOfAllegroItems;
    }

    private void setListOfAllegroItems(Document allegroPage) {
        Elements elementsFromAllegroPage = allegroPage.select("._9c44d_2H7Kt");
        listOfAllegroItems.clear();
        for (Element singleElementFromAllegroPage : elementsFromAllegroPage
        ) {
            listOfAllegroItems.add(createItem(singleElementFromAllegroPage));
        }
    }

    private Item createItem(Element singleElementFromAllegroPage) {
        Item item = Item.builder()
                .title(getTitle(singleElementFromAllegroPage))
                .price(getPrice(singleElementFromAllegroPage))
                .priceWithShipping(getPriceWithShipping(singleElementFromAllegroPage))
                .condition(getCondition(singleElementFromAllegroPage))
                .url(getUrlPath(singleElementFromAllegroPage))
                .imageUrl(getImageUrlPath(singleElementFromAllegroPage))
                .build();
        return item;
    }


    private Double getPriceWithShipping(Element singleElementFromAllegroPage) {
        try {
            return Double.parseDouble(singleElementFromAllegroPage.selectFirst("._9c44d_1xKGX i").text().replace(" zł", "").replace(",", ".").replace(" ", "").replace("darmowa", String.valueOf(getPrice(singleElementFromAllegroPage))));
        } catch (NullPointerException ex) {
            return null;
        }
    }

    private String getCondition(Element singleElementFromAllegroPage) {
        try {
            return singleElementFromAllegroPage.selectFirst("._9c44d_1AacC dd").text();
        } catch (NullPointerException ex) {
            return "Brak danych";
        }
    }

    private String getImageUrlPath(Element singleElementFromAllegroPage) {
        if (singleElementFromAllegroPage.selectFirst("._9c44d_1MOYf img").attr("data-src").isBlank()) {
            return singleElementFromAllegroPage.selectFirst("._9c44d_1MOYf img").attr("src");
        } else {
            return singleElementFromAllegroPage.selectFirst("._9c44d_1MOYf img").attr("data-src");
        }
    }

    private String getUrlPath(Element singleElementFromAllegroPage) {
        return singleElementFromAllegroPage.selectFirst("._9c44d_LUA1k a").attr("href");
    }

    private Double getPrice(Element singleElementFromAllegroPage) {
        return Double.parseDouble(singleElementFromAllegroPage.selectFirst("._9c44d_1zemI").text().replace(" zł", "").replace(",", ".").replace(" ", ""));
    }

    private String getTitle(Element singleElementFromAllegroPage) {
        return singleElementFromAllegroPage.selectFirst("._9c44d_LUA1k a").text();
    }

    @Override
    public List<Item> findByPhrase(String phrase) {

        try {
            if (phrase == "") {
                phrase = "*";
            }
            Document allegroPage = Jsoup.connect(ApiAllegroProperties.getAllegroEndpoint() + phrase.replace(" ", "%20")).get();
            setListOfAllegroItems(allegroPage);
            return getListOfAllegroItems();
        } catch (NullPointerException | IOException e) {
            e.getStackTrace();
            e.getMessage();
        }
        return new ArrayList<>();
    }


}

