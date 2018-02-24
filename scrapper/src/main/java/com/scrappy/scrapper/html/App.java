package com.scrappy.scrapper.html;

import com.scrappy.scrapper.html.api.HtmlScrapper;
import com.scrappy.scrapper.html.core.aksiazka.AksiazkaScrapper;
import com.scrappy.scrapper.html.core.czytampl.CzytamplScrapper;
import com.scrappy.scrapper.html.core.niedziela.NiedzielaScrapper;
import com.scrappy.scrapper.html.core.swiatksiazki.SwiatKsiazkiScrapper;

import static java.lang.System.out;

public class App {
  public static void main(String[] args) {
        
        /*
        Dummy use cases.
         */
    
    HtmlScrapper aksiazkaScrapper = new AksiazkaScrapper();
    out.println(aksiazkaScrapper.scrap());
    
    HtmlScrapper czytamplScrapper = new CzytamplScrapper();
    out.println(czytamplScrapper.scrap());
    
    HtmlScrapper niedzielaScrapper = new NiedzielaScrapper();
    out.println(niedzielaScrapper.scrap());
    
    HtmlScrapper swiatKsiazkiScrapper = new SwiatKsiazkiScrapper();
    out.println(swiatKsiazkiScrapper.scrap());
    
  }
}
