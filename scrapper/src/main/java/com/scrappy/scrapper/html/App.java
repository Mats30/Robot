package com.scrappy.scrapper.html;

public class App {
  public static void main(String[] args) {
    HtmlScrapper scrapper = new AksiazkaScrapper();
    scrapper.scrap().forEach(System.out::println);
  }
}
