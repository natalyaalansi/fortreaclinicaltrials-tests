package com.fortreaclinicaltrials.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://www.fortreaclinicaltrials.com/";
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "chrome";
        Configuration.timeout = 3000;
    }
}