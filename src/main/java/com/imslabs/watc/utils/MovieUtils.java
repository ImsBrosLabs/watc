package com.imslabs.watc.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MovieUtils {

    /**
     * Removes accents and all the special characters a part of alpha numeric characters, spaces and
     * hyphens.
     *
     * @param title The user entry.
     * @return The formatted title.
     */
    public static String formatUserInput(final String title) {
        return StringUtils.isNotBlank(title)
                ? StringUtils.stripAccents(title).replaceAll("[^a-zA-Z0-9 -]", "")
                : title;
    }

    /**
     * <p>
     * Compares the attribute with the preformated user input.
     * <p>
     * Example :
     * <code>@code{<a href="http://aftercredits.com/2014/05/godzilla-2014/" rel="bookmark" title=
     * "Godzilla (2014)">Godzilla (2014)</a>}</code>
     * <p>
     * This funtion will compare the title attribute value "Godzilla (2014)" with the user input
     * let's say "godzilla" (returns true in this case).
     *
     * @param userInput user input.
     * @param attrValue The title attribute value.
     * @return True if the two titles match, false instead.
     */
    public static boolean compareTitle(final String attrValue, final String userInput) {

        // Substracting the title from the attribute value.
        String subTitle = StringUtils.substringBefore(attrValue.toLowerCase(), " (");

        return StringUtils.equals(subTitle, userInput.toLowerCase());
    }

    /**
     * Extracts title from the newer template text containing it.
     * @param hayStack The text containing the title.
     * @return The title.
     */
    public static String extractTitleNew(String hayStack) {
        return extractTitle(hayStack, true);
    }

    /**
     * Extracts title² from the older template text containing it.
     * @param hayStack The text containing the title.
     * @return The title.
     */
    public static String extractTitleOld(String hayStack) {
        return extractTitle(hayStack, false);
    }

    /**
     * Extracts the release date from the newer template text containing it.
     * @param hayStack The text containing the title.
     * @return The release date.
     */
    public static LocalDate extractReleaseDateNew(String hayStack) {
        return extractReleaseDate(hayStack, true);
    }

    /**
     * Extracts the release date from the older template text containing it.
     * @param hayStack The text containing the title.
     * @return The release date.
     */
    public static LocalDate extractReleaseDateOld(String hayStack) {
        return extractReleaseDate(hayStack, false);
    }

    /**
     * Removes accents and all the special characters a part of alpha numeric characters, spaces and
     * hyphens.
     *
     * @param title The user entry.
     * @return The formatted title.
     */
    public static String formatMovieTitle(final String title) {
        return StringUtils.isNotBlank(title)
                ? StringUtils.stripAccents(title).replaceAll("[^a-zA-Z0-9 -]", "")
                : title;
    }

    /* PRIVATE METHODS */

    /**
     * Extracts title from the given template text containing it.
     *
     * @param hayStack          The text containing the title.
     * @param latestTemplate True if the movie's template is new, false otherwise.
     * @return The title.
     */
    private static String extractTitle(String hayStack, boolean latestTemplate) {
        return extractField(hayStack, latestTemplate ? "Title:" : "TITLE:", latestTemplate);
    }

    /**
     * Extracts the release date from the given template text containing it.
     *
     * @param hayStack The text containing the title.
     * @param latestTemplate True if the movie's template is new, false otherwise.
     * @return The release date of the movie.
     */
    private static LocalDate extractReleaseDate(String hayStack, boolean latestTemplate) {
        String releaseDateInString = extractField(hayStack, latestTemplate ? "Release Date:" : "RELEASE DATE:", latestTemplate);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/dd/yyyy");

        return LocalDate.parse(releaseDateInString, dtf);
    }

    /**
     * A more generic method that extract a field by its label (title, release date, starring,...).
     *
     * @param hayStack          The text containing the field.
     * @param label          the label of the fue.
     * @param latestTemplate True if the movie's template is new, false otherwise.
     * @return The field's value.
     */
    private static String extractField(String hayStack, String label, boolean latestTemplate) {
        return latestTemplate ? StringUtils.strip(StringUtils.substringBetween(hayStack, label, "\n"), Consts.STRIPPED_CHARS) :
                StringUtils.strip(StringUtils.substringAfter(hayStack, label), Consts.STRIPPED_CHARS);
    }
}
