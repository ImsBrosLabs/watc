package com.imslabs.watc.endpoint;

import com.imslabs.watc.exceptions.MovieNotFoundException;
import com.imslabs.watc.model.Movie;
import com.imslabs.watc.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MainEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainEndpoint.class);
    private final MovieService movieService;

    public MainEndpoint(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Gets a movie's data from available sources.
     *
     * @param title the movie title.
     * @return a {@link Movie} object.
     */
    @GetMapping("/movie/{title}")
    public Movie retrieveMovie(@PathVariable String title) {

        Optional<Movie> movie = movieService.getMovie(title);

        if (!movie.isPresent()) {
            throw new MovieNotFoundException("title : " + title);
        }

        return movie.get();
    }
}