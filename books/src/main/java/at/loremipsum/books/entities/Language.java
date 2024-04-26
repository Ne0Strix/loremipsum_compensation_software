package at.loremipsum.books.entities;

import at.loremipsum.books.exceptions.InvalidDataException;

public enum Language {
    ENGLISH("en"),
    GERMAN("de"),
    CHINESE("zh"),
    CROATIAN("hr"),
    FRENCH("fr"),
    ITALIAN("it"),
    NORWEGIAN("no"),
    PERSIAN("fa"),
    RUSSIAN("ru"),
    SANSKRIT("sa"),
    SPANISH("es"),
    SWEDISH("sv"),
    TURKISH("tr"),
    CZECH("cs");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (language.getCode().equals(code)) {
                return language;
            }
        }
        throw new InvalidDataException("No matching language for [" + code + "]");
    }

    public String getCode() {
        return this.code;
    }
}
