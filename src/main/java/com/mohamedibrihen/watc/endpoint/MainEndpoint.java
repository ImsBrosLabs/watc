package com.mohamedibrihen.watc.endpoint;

import com.mohamedibrihen.watc.exceptions.MovieNotFoundException;
import com.mohamedibrihen.watc.model.Movie;
import com.mohamedibrihen.watc.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MainEndpoint {
    @Autowired
    MovieService movieService;

    /**
     * Gets a movie's data from
     * @param title
     * @return
     */
    @GetMapping("/movie/{title}")
    public Movie retrieveMovie(@PathVariable String title) {

        Optional<Movie> movie = movieService.getMovie(title);

        if(!movie.isPresent()) {
            throw new MovieNotFoundException("title : " + title);
        }

        return movie.get();
    }
}