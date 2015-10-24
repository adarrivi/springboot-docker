package io.payworks.event.sourcing.client.internal.service;


import io.payworks.event.sourcing.client.dto.GwcEventDto;

public interface GwcRequestEventService {
    void logNewRequestEvent(GwcEventDto<?> dtoRq);

    void updateRequestEvent(GwcEventDto<?> dtoRs);
}
