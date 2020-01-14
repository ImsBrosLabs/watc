package com.imslabs.watc.utils;

// TODO externalize properties ?
public final class Consts {

    public static final String SEARCH_QUERY_STRING= "?s=";
    public static final String AFTER_CREDITS_URL = "http://aftercredits.com/";
    public static final String GET_FIRST_CHILD_SUB_QUERY = "/*[1]";
    public static final String STRIPPED_CHARS = " \t\u00A0\u1680\u180e\u2000\u200a\u202f\u205f\u3000";

    public static final String AFTER_CREDITS_SEARCH_URL_TEMPLATE = AFTER_CREDITS_URL + SEARCH_QUERY_STRING;

    public static final String AFTERCREDITS_SEARCH_XPATH_ELEMENT_QUERY = "//div[contains(@class, 'td-module-thumb')]";

    public static final String TITLE_TEXT = "TITLE:";
    public static final String RELEASE_DATE_TEXT = "RELEASE DATE:";
    public static final String NEW_TEMPLATE_DURING_CREDITS_TEXT = "Are There Any Extras During The Credits?";
    public static final String OLD_TEMPLATE_DURING_CREDITS_TEXT = "During Credits?";
    public static final String NEW_TEMPLATE_AFTER_CREDITS_TEXT = "Are There Any Extras After The Credits?";
    public static final String OLD_TEMPLATE_AFTER_CREDITS_TEXT = "After Credits?";

    // Movie information queries
    public static final String OLD_TEMPLATE_EXTRAS_DURING_CREDIT_QUERY = "//*[contains(text(), 'During Credits?')]/*[1]";

    public static final String NEW_TEMPLATE_EXTRAS_AFTER_CREDIT_QUERY = "//*[contains(text(), 'Are There Any Extras After The Credits?')]/*[1]";
    public static final String OLD_TEMPLATE_EXTRAS_AFTER_CREDIT_QUERY = "//*[contains(text(), 'After Credits?')]/*[1]";




    /**
     * To prevent the caller from constructing an object from this class.
     */
    private Consts(){
        throw new AssertionError();
    }
}
