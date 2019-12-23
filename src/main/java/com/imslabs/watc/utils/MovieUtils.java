package com.imslabs.watc.utils;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.imslabs.watc.model.Movie;
import org.apache.commons.lang3.StringUtils;

public class MovieUtils {
    
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
     * Extracts the movie title from the given string.
     * 
     * @param attrValue A string that eventually contains the movie title.
     * @return the extracted movie title.
     */
    public static String extractTitle(final String attrValue) {
        return StringUtils.substringBefore(attrValue.toLowerCase(), " (");
    }

    /**
     * Extracts the movie release date from the given string
     * 
     * @param attrValue A string that eventually contains the movie title.
     * @return The release date.
     */
    public static String extractReleaseYear(final String attrValue) {
        return StringUtils
                .substringBefore(StringUtils.substringAfter(attrValue.toLowerCase(), " ("), ")");
    }

    /**
     * Extracts movie's information from a given {@link DomElement} object.
     * 
     * @param domElement The DOM element from which the information will be extracted.
     * @return {@link Movie} filled with the extracted information.
     */
    public static Movie extractFromDomElement(final HtmlElement domElement) {
        
        Movie result = null;

        DomElement firstElementChild = domElement.getFirstElementChild();

        // This will get the movie and optionally the year of release.
        String retrievedTitle = firstElementChild.getAttribute("title");

        // This will get the link to the movie's page that containes several informations
        // among others, the after/during credits scenesà
        String retrievedHref = firstElementChild.getAttribute("href");

        
        if (retrievedTitle != null) {
            result = new Movie();
            result.setTitle(extractTitle(retrievedTitle));
        }

        if (StringUtils.isNotEmpty(retrievedHref)) {
            // TODO 2 possible ways to get the stringers :
            // 1st : get the article id from the shortlink rel ( example "<link
            // rel="shortlink" href="http://aftercredits.com/?p=54059">") or from the
            // article id which is mayebe a better approach.

            // 2nd : Some entries don't follow a certain template. I suppose the website
            // didn't use any before -> no possible xpath :-( ( like for the movies memento, titanic, fight club...
            // (and any other "old" movie ?)). A first approach is to search for "During Credits?" and "After Credits?" with the 

            // Serenity
            // //*[@id="post-54099"]/div[2]/div/div/div[1]/div/div[1]/p[6]/strong/span[2]
            // //*[@id="post-54099"]/div[2]/div/div/div[1]/div/div[1]/p[7]/strong/span
            
            // Glass
            //*[@id="post-54059"]/div[2]/div/div/div[1]/div/div[1]/p[6]/strong/span[2]
            //*[@id="post-54059"]/div[2]/div/div/div[1]/div/div[1]/p[7]/strong/span

            // Fight Club
            //*[@id="post-22380"]/div[2]/div/div/div[1]/div/div[1]/p[10]/strong
            //*[@id="post-22380"]/div[2]/div/div/div[1]/div/div[1]/p[11]/strong

            // Titanic
            //*[@id="post-10772"]/div[2]/div/div/div[1]/div/div[1]/p[10]/strong
            //*[@id="post-10772"]/div[2]/div/div/div[1]/div/div[1]/p[11]/strong

            // Memento
            //*[@id="post-6214"]/div[2]/div/div/div[1]/div/div[1]/p[9]/strong
            //*[@id="post-6214"]/div[2]/div/div/div[1]/div/div[1]/p[10]/strong
        }
        return result;
    }
}
