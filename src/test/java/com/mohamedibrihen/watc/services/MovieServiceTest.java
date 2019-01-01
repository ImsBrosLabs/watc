package com.mohamedibrihen.watc.services;

import com.mohamedibrihen.watc.WatcApplication;
import com.mohamedibrihen.watc.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
@ContextConfiguration(classes = {WatcApplication.class})
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    private static final String EXPECTED_DEADPOOL_TITLE = "deadpool";

    @Test
    public void formatMovieTitleTest() {

        doAssertion("déàdpöôl");
        doAssertion("d_ead.po?ol*/*$");
    }

    private void doAssertion(String title) {
        String result = movieService.formatMovieTitle(title);
        assertThat(result).isEqualTo(EXPECTED_DEADPOOL_TITLE);
    }
}
