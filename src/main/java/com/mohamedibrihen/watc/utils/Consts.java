package com.mohamedibrihen.watc.utils;

public final class Consts {

    public static final String SEARCH_QUERY_STRING= "?s=";
    public static final String AFTER_CREDITS_URL = "http://aftercredits.com/";
    public static final String MEDIA_STINGER_URL = "http://www.mediastinger.com/";

    public static final String AFTER_CREDITS_SEARCH_URL = AFTER_CREDITS_URL + SEARCH_QUERY_STRING;
    public static final String MEDIA_STINGER_SEARCH_URL = MEDIA_STINGER_URL + SEARCH_QUERY_STRING;


    /**
     * To prevent the caller from constructing an object from this class.
     */
    private Consts(){
        throw new AssertionError();
    }
}
