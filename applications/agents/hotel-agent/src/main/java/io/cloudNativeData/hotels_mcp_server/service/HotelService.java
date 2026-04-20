package io.cloudNativeData.hotels_mcp_server.service;

import io.cloudNativeData.ai.hotel.domain.Hotel;
import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRecommendationService recommendationService;

    public List<Hotel> recommendHotels(String wishList){
        return recommendationService.recommend(wishList);

    }

}
