package com.scrappy.scrapper.html;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.scrappy.scrapper.api.google.GoogleBooksProvider;
import com.scrappy.scrapper.html.core.aksiazka.AksiazkaScrapper;
import com.scrappy.scrapper.html.core.czytampl.CzytamplScrapper;
import com.scrappy.scrapper.html.core.niedziela.NiedzielaScrapper;
import com.scrappy.scrapper.html.core.pwn.PwnScrapper;
import com.scrappy.scrapper.html.core.swiatksiazki.SwiatKsiazkiScrapper;
import com.scrappy.scrapper.postsender.PostSender;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class App {
  public static void main(String[] args) {

    ThreadFactory scrappersThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("Scrapper-thread-%d")
            .build();

    ScheduledExecutorService executor = Executors
            .newScheduledThreadPool(6, scrappersThreadFactory);

    executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new NiedzielaScrapper()), 0, 24, TimeUnit.HOURS);
    executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new AksiazkaScrapper()), 0, 24, TimeUnit.HOURS);
    executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new CzytamplScrapper()), 0, 24, TimeUnit.HOURS);
    executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new SwiatKsiazkiScrapper()), 0, 24, TimeUnit.HOURS);
    executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new PwnScrapper()), 0, 24, TimeUnit.HOURS);
    executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new GoogleBooksProvider()), 0, 24, TimeUnit.HOURS);
  }
}
