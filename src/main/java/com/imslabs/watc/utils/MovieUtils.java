package com.imslabs.watc.utils;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.imslabs.watc.model.Movie;
import org.apache.commons.lang3.StringUtils;

import java.rmi.UnexpectedException;

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
     * @param stack The text containing the title.
     * @return The title.
     */
    public static String extractTitleNew(String stack) {
        return extractTitle(stack, true);
    }

    /**
     * Extracts title from the older template text containing it.
     * @param stack The text containing the title.
     * @return The title.
     */
    public static String extractTitleOld(String stack) {
        return extractTitle(stack, false);
    }

    /**
     * Extracts title from the given template text containing it.
     *
     * @param stack          The text containing the title.
     * @param latestTemplate True if the movie's template is new, false otherwise.
     * @return The title.
     */
    private static String extractTitle(String stack, boolean latestTemplate) {
        if (latestTemplate) {
            // FIXME Implement
            return null;
        } else {
            // The non extracted title is usually something like "TITLE: Memento"
            return StringUtils.substringAfter(stack, ":");
        }
    }
}
