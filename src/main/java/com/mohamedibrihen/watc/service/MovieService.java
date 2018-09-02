package com.mohamedibrihen.watc.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.mohamedibrihen.watc.model.Movie;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    public Optional<Movie> getMovie(String title) {
        Movie movie = null;

        // Logic here !
        WebClient client = new WebClient();

        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        return Optional.ofNullable(movie);
    }
}
