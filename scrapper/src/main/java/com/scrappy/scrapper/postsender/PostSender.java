package com.scrappy.scrapper.postsender;

import com.scrappy.scrapper.api.google.GoogleBookAPI;
import com.scrappy.scrapper.html.api.HtmlScrapper;
import com.scrappy.scrapper.html.model.Book;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * It sends the scraped data to the database.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

public class PostSender {
  private static final Logger logger = Logger.getLogger(PostSender.class.getName());
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
      template.exchange("http://147.135.210.145:8080/database-1.0-RC/books/save", HttpMethod.POST, entity, String.class);
    });
  }

  public void send(GoogleBookAPI googleBookAPI) {
    try {
      googleBookAPI.query().forEach(scrappedBook -> {
        Book book = new EntityConverter().convertScrappedToEntity(scrappedBook);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Book> entity = new HttpEntity<>(book, headers);
        template.exchange("http://localhost:8080/books/save", HttpMethod.POST, entity, String.class);
      });
    } catch (Exception e) {
      logger.warning(e.getMessage());
    }
  }
}
