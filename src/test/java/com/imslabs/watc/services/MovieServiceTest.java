package com.imslabs.watc.services;

import com.imslabs.watc.WatcApplication;
import com.imslabs.watc.service.MovieService;
import com.imslabs.watc.utils.MovieUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WatcApplication.class})
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    // Everything is expected in lowercase, the responsability of capitalizing is left for the front.
    private static final String DEADPOOL_TITLE = "deadpool";
    private static final String FIGHT_CLUB_TITLE = "Fight Club";
    private static final String TBTGTU_TITLE = "the bad the good and the ugly";
    private static final String CARNAVAL_FI_DACHRA_TITLE = "carnaval fi dachra";
    private static final String LION_TITLE = "lion";
    private static final String NEW_TEMPLATE_TEXT = "Title: Deadpool 2\nRating: R\nDirected by: David Leitch\nWritten by: Rhett Reese, Paul Wernick, and Ryan Reynolds\nStarring: Ryan Reynolds, Josh Brolin, Morena Baccarin, Julian Dennison, Zazie Beetz, T.J. Miller, Brianna Hildebrand, and Jack Kesy\nRelease Date: 5/18/2018\nRunning Time: 119 minutes";
    private static final String OLD_TEMPLATE_TITLE_TEXT = "TITLE: Memento";
    private static final String OLD_TEMPLATE_RELEASE_DATE_TEXT = "RELEASE DATE: 3/16/2001";


    @Test
    public void formatMovieTitleTest() {

        doformatMovieTitleAssertion("déàdpöôl", DEADPOOL_TITLE);
        doformatMovieTitleAssertion("d_ead.po?ol*/*$", DEADPOOL_TITLE);
        doformatMovieTitleAssertion("thè bàd the *good /and &thé (ugly", TBTGTU_TITLE);
    }

    @Test
    public void compareTitleTest() {
        String attrValue = "Deadpool (2016)*";

        assertThat(MovieUtils.compareTitle(attrValue, DEADPOOL_TITLE)).isTrue();

        attrValue = "Fight Club (1999)";
        assertThat(MovieUtils.compareTitle(attrValue, FIGHT_CLUB_TITLE)).isTrue();

        attrValue = "carnaval fi dachra";
        assertThat(MovieUtils.compareTitle(attrValue, CARNAVAL_FI_DACHRA_TITLE)).isTrue();

    }

    @Test
    public void extractTitleNewTest() {
        assertThat(MovieUtils.extractTitleNew(NEW_TEMPLATE_TEXT)).isEqualToIgnoringCase("deadpool 2");
    }

    @Test
    public void extractTitleOldTest() {
        assertThat(MovieUtils.extractTitleOld(OLD_TEMPLATE_TITLE_TEXT)).isEqualTo("Memento");
    }

    @Test
    public void extractReleaseDateNewTest() {
        LocalDate actualReleaseDate = MovieUtils.extractReleaseDateNew(NEW_TEMPLATE_TEXT);
        assertThat(actualReleaseDate).isEqualTo(LocalDate.of(2018, 05, 18));
    }

    @Test
    public void extractReleaseDateOldTest() {
        LocalDate actualReleaseDate = MovieUtils.extractReleaseDateOld(OLD_TEMPLATE_RELEASE_DATE_TEXT);
        assertThat(actualReleaseDate).isEqualTo(LocalDate.of(2001, 3, 16));
    }

    /* PRIVATE METHODS */
    private void doformatMovieTitleAssertion(String actualTitle, String expectedTitle) {
        String result = MovieUtils.formatMovieTitle(actualTitle);
        assertThat(result).isEqualTo(expectedTitle);
    }
}
