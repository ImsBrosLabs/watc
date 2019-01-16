package com.mohamedibrihen.watc.service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.utils.Consts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO LOGGING
@Service
public class MovieService {

    private static final String AFTERCREDITS_XPATH_ELEMENT_QUERY = "//div[contains(@class, 'td-module-thumb')]";
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private static final int LIMIT_VALUE = 3;

    private final WebClient client;

    public MovieService(WebClient client) {
        this.client = client;
    }

    public Optional<Movie> getMovie(String title) {
        Movie movie = null;

        // TODO check if the website is online before querying.

        // Disable JS + CSS for a faster page loading.
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = Consts.AFTER_CREDITS_SEARCH_URL_TEMPLATE + URLEncoder.encode(formatMovieTitle(title), String.valueOf(StandardCharsets.UTF_8));
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> elements = page.getByXPath(AFTERCREDITS_XPATH_ELEMENT_QUERY);

            List<DomElement> domElements = elements.stream()
                    .limit(LIMIT_VALUE)
                    .filter(htmlElement -> Objects.equals(htmlElement.getFirstElementChild().getAttribute("title"), "Deadpool (2016)*"))
                    .collect(Collectors.toList());

            /* TODO :
             * - Check that there is an element
             * - If so, get the href+link attributes
             */

            System.out.println();

        } catch (IOException | FailingHttpStatusCodeException e) {
            LOGGER.error("Error while fetching data from source.", e);
        }
        return Optional.ofNullable(movie);
    }

    // TODO move to an utils class ?

    /**
     * Removes accents and all the special characters a part of alpha numeric characters, spaces and hyphens.
     *
     * @param title The user entry.
     * @return The formatted title.
     */
    public String formatMovieTitle(final String title) {
        return StringUtils.isNotBlank(title) ? StringUtils.stripAccents(title).replaceAll("[^a-zA-Z0-9 -]", "") : title;
    }

    /**
     * <p>
     * Compares the attribute with the preformated user input.
     * <p>
     * Example : <code>@code{<a href="http://aftercredits.com/2014/05/godzilla-2014/" rel="bookmark" title="Godzilla (2014)">Godzilla (2014)</a>}</code>
     * <p>
     * This funtion will compare the title attribute value "Godzilla (2014)" with the user input let's say "godzilla" (returns true in this case).
     *
     * @param attrValue The title attribute value.
     * @param userInput user input.
     * @return True if the two titles match, false instead.
     */
    public boolean compareTitle(final String attrValue, final String userInput) {

        // Substracting the title from the attribute value.
        String subTitle = StringUtils.substringBefore(attrValue.toLowerCase(), " (");

        return StringUtils.equals(subTitle, userInput.toLowerCase());
    }
}
