package com.imslabs.watc.service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.imslabs.watc.model.Movie;
import com.imslabs.watc.model.enums.Source;
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

import static com.imslabs.watc.utils.Consts.AFTERCREDITS_SEARCH_XPATH_ELEMENT_QUERY;
import static com.imslabs.watc.utils.Consts.AFTER_CREDITS_SEARCH_URL_TEMPLATE;

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
                    .filter(htmlElement -> MovieUtils.compareTitle(
                            htmlElement.getFirstElementChild().getAttribute("title"),
                            formatedTitle))
                    .findFirst();

            if (movieElement.isPresent()) {

                DomElement firstElementChild = movieElement.get().getFirstElementChild();

                // This will get the movie and optionally the year of release.
                String retrievedTitle = firstElementChild.getAttribute("title");

                // This will get the link to the movie's page that containes several informations
                // among others, the after/during credits scenes
                String moviePageUrl = firstElementChild.getAttribute("href");

                // Building the movie object.
                movie = new Movie();
                movie.setSource(Source.AFTER_CREDITS); // Forced in this first version of the API

                if (StringUtils.isNotEmpty(moviePageUrl)) {
                    HtmlPage moviePage = client.getPage(moviePageUrl);


                    List<HtmlElement> movieInformationElement = moviePage.getByXPath("//span[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'title:')]");
                    if (!movieInformationElement.isEmpty()) {
                        // newer movie info template (retrieving all the movie info here)
                        String movieInformationStr = movieInformationElement.get(0).getTextContent();
                        movie.setTitle(MovieUtils.extractTitleNew(movieInformationStr));

                    } else {
                        // Old template (retrieve the elements one by one)
                        HtmlElement titleElement = (HtmlElement) moviePage.getByXPath("//p[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'title:')]").get(0);
                        movie.setTitle(MovieUtils.extractTitleOld(titleElement.getTextContent()));
                    }


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

}
