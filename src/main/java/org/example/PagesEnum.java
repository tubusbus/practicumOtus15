package org.example;

public enum PagesEnum {
    MAIN_PAGE("https://otus.ru/");
    private String url;

    PagesEnum(String url) {
        this.url = url;
    }

    public String getValue (){
        return this.url;
    }
}
