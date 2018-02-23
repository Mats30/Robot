package com.scrappy.scrapper.html;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.scrappy.scrapper.html.core.aksiazka.AksiazkaScrapper;
import com.scrappy.scrapper.html.core.czytampl.CzytamplScrapper;
import com.scrappy.scrapper.html.core.niedziela.NiedzielaScrapper;
import com.scrappy.scrapper.html.core.swiatksiazki.SwiatKsiazkiScrapper;
import com.scrappy.scrapper.postsender.PostSender;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

/**
 * It launches the scrapers.
 *
 * @version 1.0-SNAPSHOT
 * @since 2018-02-20
 */

public class App {
    public static void main(String[] args) {

        ThreadFactory scrappersThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Scrapper-thread-%d")
                .build();

        ScheduledExecutorService executor = Executors
                .newScheduledThreadPool(4, scrappersThreadFactory);

        executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new NiedzielaScrapper()), 0, 24, TimeUnit.HOURS);
        executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new AksiazkaScrapper()), 0, 24, TimeUnit.HOURS);
        executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new CzytamplScrapper()), 0, 24, TimeUnit.HOURS);
        executor.scheduleAtFixedRate(() -> new PostSender(new RestTemplate()).send(new SwiatKsiazkiScrapper()), 0, 24, TimeUnit.HOURS);
    }
}
