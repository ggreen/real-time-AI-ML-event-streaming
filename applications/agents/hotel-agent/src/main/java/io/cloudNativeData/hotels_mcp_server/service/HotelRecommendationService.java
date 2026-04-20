package io.cloudNativeData.hotels_mcp_server.service;

import io.cloudNativeData.ai.hotel.domain.Hotel;

import java.util.List;

public interface HotelRecommendationService {
    List<Hotel> recommend(String wishList);
}
