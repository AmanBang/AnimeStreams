package com.searchit.animestreams;

import java.util.ArrayList;

public abstract class Scraper {
    public abstract ArrayList<Quality> getQualityUrls();
    public abstract String getHost();
}
