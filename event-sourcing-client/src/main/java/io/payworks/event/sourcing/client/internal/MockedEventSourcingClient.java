package io.payworks.event.sourcing.client.internal;

import io.payworks.event.sourcing.client.EventSourcingClient;
import io.payworks.event.sourcing.client.dto.GwcEventDto;
import rx.Observable;

public class MockedEventSourcingClient implements EventSourcingClient {

    @Override
    public Observable<String> logNewRequestEvent(final GwcEventDto event) {
        return Observable.empty();
    }

    @Override
    public Observable<String> updateRequestEvent(final GwcEventDto event) {
        return Observable.empty();
    }
}
