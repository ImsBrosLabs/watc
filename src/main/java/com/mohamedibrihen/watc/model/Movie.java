package com.mohamedibrihen.watc.model;

import com.mohamedibrihen.watc.model.enums.Source;
import lombok.Data;

@Data
public class Movie {
    private Integer id;
    private Source source;
    private String title;
    private Boolean hasExtrasDuringCredits;
    private Boolean hasStinger;
}
