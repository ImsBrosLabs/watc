package com.mohamedibrihen.watc.endpoint;

import com.mohamedibrihen.watc.exceptions.MovieNotFoundException;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MainEndpoint {

    private final MovieService movieService;

    public MainEndpoint(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Gets a movie's data from available sources.
     *
     * @param title the movie title. (TODO search assistance or leave it to the client ?)
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