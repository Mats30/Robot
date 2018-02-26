package com.scrappy.scrapper.postsender;

import com.scrappy.scrapper.html.api.HtmlScrapper;
import com.scrappy.scrapper.html.model.Book;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * It sends the scraped data to the database.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

public class PostSender {
    private final RestTemplate template;

    public PostSender(RestTemplate template) {
        this.template = template;
    }

    public void send(HtmlScrapper scrapper) {
        scrapper.scrap().forEach(scrappedBook -> {
            Book book = new EntityConverter().convertScrappedToEntity(scrappedBook);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<com.scrappy.scrapper.html.model.Book> entity = new HttpEntity<>(book, headers);
            template.exchange("http://localhost:8080/database-1.0-SNAPSHOT/books/save", HttpMethod.POST, entity, String.class);
        });
    }
}
