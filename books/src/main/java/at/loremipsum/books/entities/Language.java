package at.loremipsum.books.entities;

import at.loremipsum.books.exceptions.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Languages and their corresponding ISO 639 Set 2/B three-letter code.
 */
public enum Language {
    CHINESE("Chinese", "chi"),
    CROATIAN("Croatian", "hrv"),
    CZECH("Czech", "cze"),
    ENGLISH("English", "eng"),
    FRENCH("French", "fre"),
    GERMAN("German", "ger"),
    ITALIAN("Italian", "ita"),
    NORWEGIAN("Norwegian", "nor"),
    PERSIAN("Persian", "per"),
    RUSSIAN("Russian", "rus"),
    SANSKRIT("Sanskrit", "san"),
    SPANISH("Spanish", "spa"),
    SWEDISH("Swedish", "swe"),
    TURKISH("Turkish", "tur");

    private final String displayName;
    private final String code;

    /**
     * Creates a new Language enum instance.
     *
     * @param displayName The human-readable display name of the language.
     * @param code        The ISO 639 Set 2/B conform three-letter code for the language.
     */
    Language(String displayName, String code) {
        this.displayName = displayName;
        this.code = code;
    }

    /**
     * @param code The ISO 639 Set 2/B conform three-letter code for the language.
     * @return
     */
    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (language.code.equals(code)) {
                return language;
            }
        }
        throw new InvalidDataException("No matching language for [" + code + "]");
    }

    /**
     * @return The human-readable display-name of a language.
     */
    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }
}
