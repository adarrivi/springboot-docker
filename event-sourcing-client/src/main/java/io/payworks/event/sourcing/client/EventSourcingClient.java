package io.payworks.event.sourcing.client;

import io.payworks.event.sourcing.client.dto.GwcEventDto;
import rx.Observable;

public interface EventSourcingClient {

    Observable<String> logNewRequestEvent(GwcEventDto dtoRq);

    Observable<String> updateRequestEvent(GwcEventDto dtoRs);

}
