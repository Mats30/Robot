package com.scrappy.scrapper.html;

import java.io.IOException;

public class App {
  public static void main(String[] args) throws IOException {
    NiedzielaScrapper scrapper = new NiedzielaScrapper();
    System.out.println(scrapper.scrap());
  }
}
