package com.imslabs.watc.model;

import com.imslabs.watc.model.enums.Source;
import lombok.Data;

import java.net.URL;
import java.time.LocalDate;

@Data
public class Movie {
    private String title;
    private Source source;
    private LocalDate releaseDate;
    private URL posterUrl;
    private Boolean hasExtrasDuringCredits;
    private Boolean hasExtraAfterCredits;
}
