package com.fortreaclinicaltrials.data;

public enum Language {
    US("United States", "https://www.fortreaclinicaltrials.com/en-us"),
    UK("United Kingdom", "https://www.fortreaclinicaltrials.com/en-gb");

    public final String button;
    public final String expectedURL;

    Language(String button, String expectedURL) {
        this.button = button;
        this.expectedURL = expectedURL;
    }


}
