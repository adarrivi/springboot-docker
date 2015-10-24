package io.payworks.event.sourcing.client.internal.service;

import io.payworks.event.sourcing.client.dto.GwcEventDto;
import rx.Observable;

public interface AsyncEventSourcingService {

    Observable<String> executeSilentAsyncAction(Runnable action, GwcEventDto event);

}
