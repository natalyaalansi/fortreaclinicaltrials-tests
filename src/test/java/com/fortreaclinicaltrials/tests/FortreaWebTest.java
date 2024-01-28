package com.fortreaclinicaltrials.tests;

import com.fortreaclinicaltrials.data.Language;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class FortreaWebTest extends TestBase {

    @ParameterizedTest(name = "The transition to {0} version should be made after selecting it on the main page")
    @EnumSource(Language.class)
    @Tags({
            @Tag("WEB"),
            @Tag("SANITY"),
            @Tag("MAJOR"),
    })
    void fortreaShouldSwitchToSelectedLanguage(Language language) {
        open("");
        $("[class^='button-grouped']").find(byText(language.button)).click();
        $$("#location-area a").findBy(text(language.name())).shouldNotBe(disabled);
        webdriver().shouldHave(url(language.expectedURL));
    }


    @MethodSource
    @ParameterizedTest(name = "{0} version should contain the corresponding phone number and menu items")
    @Tags({
            @Tag("WEB"),
            @Tag("SANITY"),
            @Tag("MAJOR"),
    })
    void menuItemsAndPhoneShouldMatchLanguageVersion(String relativeURL, String phone, List<String> expectedItems) {
        open(relativeURL);
        $("a[href^='tel']").shouldHave(text(phone));
        $$("#mega-menu-container a").filter(visible).shouldHave(texts(expectedItems));
    }


    @CsvSource(value = {
            "Dallas, Texas | 2",
            "Daytona Beach, Florida | 5"
    }, delimiter = '|')
    @ParameterizedTest(name = "The list of clinical studies should match Location filter - {0}")
    @Tags({
            @Tag("WEB"),
            @Tag("SANITY"),
            @Tag("MAJOR"),
    })
    void searchShouldWorkWithLocationFilter(String cityAndState, int tableRows) {
        open("/en-us/clinical-research/browse-studies");
        $("#edit-field-location-target-id").selectOption(cityAndState);
        $("[value='Apply Filters']").click();
        $$("tbody tr").filterBy(text(cityAndState)).shouldHave(size(tableRows));

    }


    static Stream<Arguments> menuItemsAndPhoneShouldMatchLanguageVersion() {
        return Stream.of(
                Arguments.of(
                        "en-us",
                        "1-866-429-3700",
                        List.of("Clinical Research", "Locations", "About Us", "BROWSE TRIALS")
                ),
                Arguments.of(
                        "en-gb",
                        "0113 394 5200",
                        List.of("Clinical Research", "Our Clinic", "About Us", "Contact", "BROWSE STUDIES")
                )
        );
    }


}
