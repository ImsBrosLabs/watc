package com.mohamedibrihen.watc.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.utils.Consts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);

    private final WebClient client;

    public MovieService(WebClient client) {
        this.client = client;
    }

    public Optional<Movie> getMovie(String title) {
        Movie movie = null;

        // TODO check if the website is online before querying.

        title = formatMovieTitle(title);

        // Disable JS + CSS for a faster page loading.
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = Consts.AFTER_CREDITS_SEARCH_URL_TEMPLATE + URLEncoder.encode(title, String.valueOf(StandardCharsets.UTF_8));
            HtmlPage page = client.getPage(searchUrl);


            List<HtmlElement> elements = page.getByXPath("//div[contains(@class, 'td-module-thumb')]");

            // TODO Filter by the right matching title then get the link and movie name (use Streams API).
            // x = deadpool -> x (year)optional<*>
            List<HtmlElement> htmlElements = elements.stream()
                    .filter(e -> e.getFirstElementChild().getAttributesMap().containsKey("title") && e.getFirstElementChild().getAttributesMap().containsKey("href"))
                    .collect(Collectors.toList());
            
            // TODO get the href+title attribut

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(movie);
    }

    // TODO move to a utils class ?
    /**
     * Removes accents and all the special characters a part of alpha numeric characters, spaces and hyphens.
     *
     * @param title The user entry.
     * @return The formatted title.
     */
    public String formatMovieTitle(String title) {
        String result = null;
        if (StringUtils.isNotBlank(title)) {
            result = StringUtils.stripAccents(title);
            result = result.replaceAll("[^a-zA-Z0-9 -]", "");
        }

        return result;
    }
}
