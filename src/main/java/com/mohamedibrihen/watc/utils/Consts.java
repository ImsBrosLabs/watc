package com.mohamedibrihen.watc.utils;

public final class Consts {

    private static final String SEARCH_QUERY_STRING= "?s=";
    private static final String AFTER_CREDITS_URL = "http://aftercredits.com/";
    private static final String MEDIA_STINGER_URL = "http://www.mediastinger.com/";

    public static final String AFTER_CREDITS_SEARCH_URL_TEMPLATE = AFTER_CREDITS_URL + SEARCH_QUERY_STRING;
    public static final String MEDIA_STINGER_SEARCH_URL_TEMPLATE = MEDIA_STINGER_URL + SEARCH_QUERY_STRING;


    /**
     * To prevent the caller from constructing an object from this class.
     */
    private Consts(){
        throw new AssertionError();
    }
}
