package com.example.movieinfoservice.interfaces.rest;

import com.example.movieinfoservice.domain.entity.MovieInfo;
import com.example.movieinfoservice.interfaces.rest.dto.MovieInfoResource;
import com.example.movieinfoservice.application.service.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest
class ErrorHandlerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private MovieInfoService service;
    @SpyBean
    @SuppressWarnings("unused")
    private MovieInfoDtoConverter converter;

    @Test
    void shouldValidateMovieInfoDtoWhenAdding() {


        var invalidMovieInfo = new MovieInfo(null,
                "",
                -9999,
                List.of("", "Connie Nielsen"),
                LocalDate.of(2021, 4, 13));

        var invalidMovieInfoDto = new MovieInfoResource(null,
                "",
                -9999,
                List.of("", "Connie Nielsen"),
                LocalDate.of(2021, 4, 13));

        when(service.addMovieInfo(invalidMovieInfo)).thenReturn(null);
        client.post()
                .uri("/v1/movieinfos")
                .bodyValue(invalidMovieInfoDto)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .value(s -> assertThat(s).isEqualTo("must be greater than 0,must not be blank,must not be blank"));

    }
}