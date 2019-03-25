package com.mohamedibrihen.watc.services;

import com.mohamedibrihen.watc.WatcApplication;
import com.mohamedibrihen.watc.service.MovieService;
import com.mohamedibrihen.watc.utils.MovieUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
    public void extractTitleTest() {
        String attrValue = "Lion (2016)*";

        String result = MovieUtils.extractTitle(attrValue);
        assertThat(result).isEqualTo(LION_TITLE);
        
    }

    @Test
    public void extractReleaseDateTest() {
        String attrValue = "Fight Club (1999)*";

        String result = MovieUtils.extractReleaseYear(attrValue);

        assertThat(result).isEqualTo("1999");
    }
    
    /* PRIVATE METHODS */
    private void doformatMovieTitleAssertion(String actualTitle, String expectedTitle) {
        String result = MovieUtils.formatMovieTitle(actualTitle);
        assertThat(result).isEqualTo(expectedTitle);
    }
}
