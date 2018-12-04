package com.mohamedibrihen.watc.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.utils.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

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

        // Disable JS + CSS for a faster page loading.
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = Consts.AFTER_CREDITS_SEARCH_URL_TEMPLATE + URLEncoder.encode(title, String.valueOf(StandardCharsets.UTF_8));
            HtmlPage page = client.getPage(searchUrl);


            List<HtmlElement> elements = page.getByXPath("//div[contains(@class, 'td-module-thumb')]");

            // TODO Filter by the right matching title then get the link ant movie name (use Streams API).
            for (HtmlElement element : elements) {
                DomElement domElement = element.getFirstElementChild();
                if (domElement != null) {

                }
            }
            // TODO get the href+title attribut


        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(movie);
    }
}
