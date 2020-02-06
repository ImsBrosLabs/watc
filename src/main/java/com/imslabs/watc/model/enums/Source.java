package com.imslabs.watc.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Source {

    /**
     * http://www.mediastinger.com/
     *
     * NOT USED because doesn't like much "programatical" queries.
     */
    MEDIA_STINGER("http://www.mediastinger.com/"),

    /**
     * http://www.aftercredits.com/
     *
     * Works fine. It's the main source of data retrieving so far.
     */
    AFTER_CREDITS("http://www.aftercredits.com/");

    public final String url;

    Source(String url) {
        this.url = url;
    }
}
