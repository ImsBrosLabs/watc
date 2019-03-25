package com.mohamedibrihen.watc.service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.utils.MovieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import static com.mohamedibrihen.watc.utils.Consts.AFTERCREDITS_SEARCH_XPATH_ELEMENT_QUERY;
import static com.mohamedibrihen.watc.utils.Consts.AFTER_CREDITS_SEARCH_URL_TEMPLATE;

// TODO LOGGING
@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private final WebClient client;

    public MovieService(WebClient client) {
        this.client = client;
    }

    public Optional<Movie> getMovie(final String title) {
        Movie movie = null;

        // TODO check if the website is online before querying.

        // Disable JS + CSS for a faster page loading.
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {

            String formattedTitle = MovieUtils.formatMovieTitle(title);

            String searchUrl = AFTER_CREDITS_SEARCH_URL_TEMPLATE
                    + URLEncoder.encode(formattedTitle, String.valueOf(StandardCharsets.UTF_8));
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> elements = page.getByXPath(AFTERCREDITS_SEARCH_XPATH_ELEMENT_QUERY);

            // element > firstChild > attributs
            Optional<HtmlElement> domElement = elements.stream()
                    .filter(htmlElement -> MovieUtils.compareTitle(
                            htmlElement.getFirstElementChild().getAttribute("title"),
                            formattedTitle))
                    .findFirst();

            if (domElement.isPresent()) {
                movie = MovieUtils.extractFromDomElement(domElement.get());
            }
            
        } catch (IOException e) {
            LOGGER.error("Error while fetching data from source.", e);

        } catch (FailingHttpStatusCodeException ignored) {
        } // To prevent weird 502 http error (result of FailingHttpStatusCodeException catching)
        return Optional.ofNullable(movie);
    }
}
