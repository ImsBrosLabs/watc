package com.mohamedibrihen.watc.utils;

public final class Consts {

    private static final String SEARCH_QUERY_STRING= "?s=";
    private static final String AFTER_CREDITS_URL = "http://aftercredits.com/";
    // TODO : use a different user agent to bypass the limitation ?
    private static final String MEDIA_STINGER_URL = "http://www.mediastinger.com/";

    public static final int LIMIT_VALUE = 3;

    public static final String AFTER_CREDITS_SEARCH_URL_TEMPLATE = AFTER_CREDITS_URL + SEARCH_QUERY_STRING;
    public static final String MEDIA_STINGER_SEARCH_URL_TEMPLATE = MEDIA_STINGER_URL + SEARCH_QUERY_STRING;

    public static final String AFTERCREDITS_SEARCH_XPATH_ELEMENT_QUERY = "//div[contains(@class, 'td-module-thumb')]";



    /**
     * To prevent the caller from constructing an object from this class.
     */
    private Consts(){
        throw new AssertionError();
    }
}
