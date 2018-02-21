package com.scrappy.scrapper.html.czytampl;

import com.scrappy.scrapper.common.Book;
import com.scrappy.scrapper.html.HtmlScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CzytamplScrapper implements HtmlScrapper {
    @Override
    public List<Book> scrap() {
        List<Book> books = new ArrayList<>();
        List<String> urlsToScrap = findPromotionUrls();

        urlsToScrap.forEach(url -> {
            try {
                final Document doc = Jsoup.connect(url).get();
                final Elements elements = doc.getElementsByClass("col-small-info");
                elements.forEach(e -> books.add(Book.builder()
                        .setAuthor(retrieveAuthorFrom(e))
                        .setBookstore(retrieveBookstore())
                        .setDiscountPrice(retrieveDiscountPriceFrom(e))
                        .setListPrice(retrieveListPriceFrom(e))
                        .setIsbn(retrieveIsbnFrom(e))
                        .setTitle(retrieveTitleFrom(e))
                        .setUrl(retrieveUrlFrom(e))
                        .build()));
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        });

        return books;
    }

    public List<Book> scrapFromFile(File file) throws IOException {
        List<Book> books = new ArrayList<>();
        final Document doc = Jsoup.parse(file, "UTF-8", "");
        final Elements elements = doc.getElementsByClass("col-small-info");
        elements.forEach(e -> books.add(Book.builder()
                .setAuthor(retrieveAuthorFrom(e))
                .setBookstore(retrieveBookstore())
                .setDiscountPrice(retrieveDiscountPriceFrom(e))
                .setListPrice(retrieveListPriceFrom(e))
                .setIsbn(retrieveIsbnFrom(e))
                .setTitle(retrieveTitleFrom(e))
                .setUrl(retrieveUrlFrom(e))
                .build()));
        return books;
    }

    private String retrieveBookstore() {
        return "Czytam.pl";
    }

    private String retrieveIsbnFrom(Element element) {
        return retrieveIsbnFrom(retrieveUrlFrom(element));
    }

    private String retrieveUrlFrom(Element element) {
        return "https://czytam.pl" + element.getElementsByTag("a").attr("href");
    }

    private BigDecimal retrieveDiscountPriceFrom(Element element) {
        return retrieveListPriceFrom(element.getElementsByClass("product-price").text(), true);
    }

    private BigDecimal retrieveListPriceFrom(Element element) {
        return retrieveListPriceFrom(element.getElementsByClass("product-price").text(), false);
    }

    private String retrieveTitleFrom(Element element) {
        return element.getElementsByClass("product-title").text();
    }

    private String retrieveAuthorFrom(Element element) {
        return element.getElementsByClass("product-author").text();
    }

    private List<String> findPromotionUrls() {
        List<String> links = new ArrayList<>();
        try {
            final Document doc = Jsoup.connect("https://czytam.pl/promocje,1.html").get();
            final String text = doc.getElementsByClass("show-for-medium-up")
                    .tagName("a")
                    .text()
                    .split(" ")[21];
            final int pagesCount = Integer.parseInt(text);
            for (int i = 1; i <= pagesCount; i++) {
                links.add("https://czytam.pl/promocje," + i + ".html");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

    private BigDecimal retrieveListPriceFrom(String pattern, boolean discount) {
        String price;
        if (pattern.isEmpty()) {
            return BigDecimal.ZERO;
        }
        if (discount) {
            price = pattern.split(" ")[2];
        } else {
            price = pattern.split(" ")[0];
        }
        price = price.replaceAll(",", ".");
        return BigDecimal.valueOf(Double.parseDouble(price));
    }

    private String retrieveIsbnFrom(String url) {
        try {
            final Document doc = Jsoup.connect(url).get();
            final String regex = "\\d{13}";
            final Elements elements = doc.getElementsMatchingOwnText(regex);
            String result = elements.get(0).toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(result);
            matcher.find();
            return matcher.group();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
