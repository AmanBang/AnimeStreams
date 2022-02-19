package com.searchit.animestreams;

public class SAnime {

    private String Name;
    private String imgUrl;

    public SAnime(String name, String imgUrl, String number, String url, String des) {
        Name = name;
        this.imgUrl = imgUrl;
        Number = number;
        this.url = url;
        Des = des;
    }

    public SAnime() {

    }

    public void setName(String name) {
        Name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String Number;

    public String getName() {
        return Name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNumber() {
        return Number;
    }

    public String getUrl() {
        return url;
    }

    private String url;

    private String Des;

    public void setDes(String des) {
        Des = des;
    }

    public String getDes() {
        return Des;
    }

}
