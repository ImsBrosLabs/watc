package com.imslabs.watc.service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.imslabs.watc.model.Movie;
import com.imslabs.watc.model.enums.Source;
import com.imslabs.watc.utils.Consts;
import com.imslabs.watc.utils.MovieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static com.imslabs.watc.utils.Consts.*;

// TODO LOGGING
@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private final WebClient client;

    public MovieService( WebClient client) {
        this.client = client;
    }

    public Optional<Movie> getMovie(String title) {
        Movie movie = null;

        // TODO check if the website is online before querying.

        try {

            String formatedTitle = formatMovieTitle(title);

            String searchUrl = AFTER_CREDITS_SEARCH_URL_TEMPLATE
                    + URLEncoder.encode(formatedTitle, String.valueOf(StandardCharsets.UTF_8));
            HtmlPage searchResultsPage = client.getPage(searchUrl);

            List<HtmlElement> moviesElements = searchResultsPage.getByXPath(AFTERCREDITS_SEARCH_XPATH_ELEMENT_QUERY);

            // element > firstChild > attributs
            Optional<HtmlElement> movieElement = moviesElements.stream()
                    .filter(htmlElement -> compareTitle(
                            htmlElement.getFirstElementChild().getAttribute("title"),
                            formatedTitle))
                    .findFirst();

            if (movieElement.isPresent()) {

                DomElement firstElementChild = movieElement.get().getFirstElementChild();

                // This will get the movie and optionally the year of release.
                String retrievedTitle = firstElementChild.getAttribute("title");

                // This will get the link to the movie's page that contains several information
                // among others, the after/during credits scenes
                String moviePageUrl = firstElementChild.getAttribute("href");

                // Building the movie object.
                movie = new Movie();
                movie.setSource(Source.AFTER_CREDITS);

                // Title
                if (retrievedTitle != null) {
                    movie.setTitle(extractTitle(retrievedTitle));
                    // TODO extract the year too
                }

                if (StringUtils.isNotEmpty(moviePageUrl)) {
                    HtmlPage moviePage = client.getPage(moviePageUrl);

                    if (isLatestTemplate(moviePage)) {

                    } else {

                    }
                    // Getting the during credit extra
                    // "old template"
                    List<HtmlElement> duringCreditElementResults = moviePage.getByXPath(Consts.OLD_TEMPLATE_EXTRAS_DURING_CREDIT_QUERY);
                    if (duringCreditElementResults.isEmpty()) {
                        // "new template"
                        duringCreditElementResults = moviePage.getByXPath(NEW_TEMPLATE_EXTRAS_DURING_CREDIT_QUERY);
                     }

                    HtmlElement duringCreditElementResult = duringCreditElementResults.get(0);
                    movie.setHasExtrasDuringCredits(!StringUtils.equals(StringUtils.lowerCase(duringCreditElementResult.getTextContent()), "no"));

                    // Getting the after credit extra
                    // "old tempalte"
                    List<HtmlElement> afterCreditElementResults = moviePage.getByXPath(OLD_TEMPLATE_EXTRAS_AFTER_CREDIT_QUERY);
                    if (afterCreditElementResults.isEmpty()) {
                        // "new template"
                        afterCreditElementResults = moviePage.getByXPath(NEW_TEMPLATE_EXTRAS_AFTER_CREDIT_QUERY);
                    }

                    HtmlElement afterCreditElementResult = afterCreditElementResults.get(0);
                    movie.setHasExtraAfterCredits(!StringUtils.equals(StringUtils.lowerCase(afterCreditElementResult.getTextContent()), "no"));

                }
            }
        } catch (IOException e) {
            LOGGER.error("Error while fetching data from source.", e);

        } catch (FailingHttpStatusCodeException ignored) {
        } // To prevent weird 502 http error (result of FailingHttpStatusCodeException catching)
        return Optional.ofNullable(movie);
    }

    // TODO move to an utils class ?
    /**
     * Removes accents and all the special characters a part of alpha numeric characters, spaces and
     * hyphens.
     *
     * @param title The user entry.
     * @return The formatted title.
     */
    public String formatMovieTitle(final String title) {
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
    public boolean compareTitle(final String attrValue, final String userInput) {

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
    public String extractTitle(String attrValue) {
        return StringUtils.substringBefore(attrValue.toLowerCase(), " (");
    }

    /**
     * Extracts the movie release date from the given string
     * 
     * @param attrValue A string that eventually contains the movie title.
     * @return
     */
    public String extractReleaseYear(String attrValue) {
        return StringUtils
                .substringBefore(StringUtils.substringAfter(attrValue.toLowerCase(), " ("), ")");
    }

    /**
     * Checks if the movie page template is the latest.
     *
     * @param moviePage The html movie page object.
     * @return True if it's the latest template false if it's the old one.
     */
    public boolean isLatestTemplate(HtmlPage moviePage) {
        return !moviePage
                .getByXPath(
                        MovieUtils.buildContainsQueryNew(Consts.NEW_TEMPLATE_DURING_CREDITS_TEXT))
                .isEmpty();
    }
}
