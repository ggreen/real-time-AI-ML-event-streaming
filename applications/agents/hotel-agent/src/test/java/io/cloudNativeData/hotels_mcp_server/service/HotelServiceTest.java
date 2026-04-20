package io.cloudNativeData.hotels_mcp_server.service;

import io.cloudNativeData.ai.hotel.domain.Hotel;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

    private HotelService subject;
    @Mock
    private HotelRecommendationService recommendationsService;
    private static final Hotel hotel = JavaBeanGeneratorCreator.of(Hotel.class).create();

    @BeforeEach
    void setUp() {
        subject = new HotelService(recommendationsService);
    }

    @Test
    void recommendation() {

        List<Hotel> expected = List.of(hotel);
        when(recommendationsService.recommend(anyString())).thenReturn(expected);

        var actual = subject.recommendHotels("relaxation");

        assertThat(actual).isEqualTo(expected);
    }
}