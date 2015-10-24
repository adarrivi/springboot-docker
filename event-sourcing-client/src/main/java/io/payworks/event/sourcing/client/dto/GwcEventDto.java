package io.payworks.event.sourcing.client.dto;

import io.payworks.event.sourcing.model.detail.GenericEventDetail;
import io.payworks.event.sourcing.model.domain.GwcEventType;

import java.util.Optional;

public class GwcEventDto<T extends GenericEventDetail> {

    private String uuid;
    private GwcEventType type;
    private Optional<T> details;

    public GwcEventDto(String uuid, GwcEventType type, Optional<T> details) {
        this.uuid = uuid;
        this.type = type;
        this.details = details;
    }

    public String getUuid() {
        return uuid;
    }

    public GwcEventType getType() {
        return type;
    }

    public Optional<T> getDetails() {
        return details;
    }

    public void setDetails(Optional<T> details) {
        this.details = details;
    }
}
