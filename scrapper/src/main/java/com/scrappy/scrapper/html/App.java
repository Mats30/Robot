package com.scrappy.scrapper.html;

import com.scrappy.scrapper.html.swiatksiazki.SwiatKsiazkiScrapper;

import java.io.IOException;

public class App {
  public static void main(String[] args) throws IOException {
    SwiatKsiazkiScrapper scrapper = new SwiatKsiazkiScrapper();
    System.out.println(scrapper.scrap());
  }
}
