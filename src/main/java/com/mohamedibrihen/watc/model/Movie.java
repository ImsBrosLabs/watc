package com.mohamedibrihen.watc.model;

import lombok.Data;

@Data
public class Movie {
    private Integer id;
    private String title;
    private Boolean hasExtrasDuringCredits;
    private Boolean hasStinger;
}
