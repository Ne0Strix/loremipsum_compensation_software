package at.loremipsum.books.entities;

import at.loremipsum.books.exceptions.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    ENGLISH("English"),
    GERMAN("German"),
    CHINESE("Chinese"),
    CROATIAN("Croatian"),
    FRENCH("French"),
    ITALIAN("Italian"),
    NORWEGIAN("Norwegian"),
    PERSIAN("Persian"),
    RUSSIAN("Russian"),
    SANSKRIT("Sanskrit"),
    SPANISH("Spanish"),
    SWEDISH("Swedish"),
    TURKISH("Turkish"),
    CZECH("Czech");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (language.getDisplayName().equals(code)) {
                return language;
            }
        }
        throw new InvalidDataException("No matching language for [" + code + "]");
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }
}
