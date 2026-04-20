package io.cloudNativeData.ai.hotel.domain;

import lombok.Builder;

@Builder
public record Hotel(String id, String name,String description, String locationAddress, String country) {
}
