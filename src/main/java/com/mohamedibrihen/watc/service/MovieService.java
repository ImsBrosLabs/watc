package com.mohamedibrihen.watc.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.utils.Consts;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Optional;

// TODO use logger
@Service
public class MovieService {

    public Optional<Movie> getMovie(String title) {
        Movie movie = null;

        // Logic here !
        // TODO check if the website is online before querying.

        WebClient client = new WebClient();

        // Disable JS + CSS for a faster page loading.
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            String searchUrl = Consts.AFTER_CREDITS_SEARCH_URL_TEMPLATE + URLEncoder.encode(title, "UTF-8");
            HtmlPage page = client.getPage(searchUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(movie);
    }
}
