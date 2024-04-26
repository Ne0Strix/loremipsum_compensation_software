package at.loremipsum.books.entities;

import at.loremipsum.books.exceptions.InvalidDataException;

public enum Genre {
    FICTION("Fiction"),
    NONFICTION("Non-Fiction"),
    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    SCIENCE_FICTION("Science Fiction"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    WESTERN("Western"),
    DYSTOPIAN("Dystopian"),
    CONTEMPORARY("Contemporary"),
    CRIME("Crime"),
    ADVENTURE("Adventure"),
    HISTORY("History"),
    SELF_HELP("Self Help"),
    HEALTH("Health"),
    TRAVEL("Travel"),
    CHILDREN("Children's"),
    RELIGION("Religion"),
    SCIENCE("Science"),
    HUMOR("Humor"),
    COOKBOOKS("Cookbooks"),
    BIOGRAPHY("Biography"),
    AUTOBIOGRAPHY("Autobiography"),
    YOUNG_ADULT("Young Adult"),
    PARANORMAL("Paranormal"),
    ART("Art"),
    PSYCHOLOGY("Psychology"),
    GRAPHIC_NOVEL("Graphic Novel"),
    POETRY("Poetry");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public static Genre fromString(String name) {
        for (Genre genre : Genre.values()) {
            if (genre.name().equalsIgnoreCase(name)) {
                return genre;
            }
        }
        throw new InvalidDataException("Invalid genre: " + name);
    }

    public String getDisplayName() {
        return displayName;
    }
}