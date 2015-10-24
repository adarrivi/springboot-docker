package io.payworks.event.sourcing.client;

import io.payworks.event.sourcing.client.dto.GwcEventDto;
import io.payworks.event.sourcing.model.detail.RegisterTxSessionEventDetail;

import java.util.Optional;
import java.util.UUID;

import static io.payworks.event.sourcing.model.domain.GwcEventType.REGISTER_TX_SESSION;
import static io.payworks.event.sourcing.test.builder.TestEventDetailBuilder.createRegisterTxSessionEventDetail;

public class TestGwcEventDtoBuilder {

    public static GwcEventDto<RegisterTxSessionEventDetail> createNewRegisterTxEvent() {
        String requestId = UUID.randomUUID().toString();
        return new GwcEventDto<>(requestId, REGISTER_TX_SESSION, Optional.of(createRegisterTxSessionEventDetail(requestId)));
    }

    public static GwcEventDto<RegisterTxSessionEventDetail> updateRegisterTxEvent(GwcEventDto<RegisterTxSessionEventDetail> event) {
        String transactionId = UUID.randomUUID().toString();
        RegisterTxSessionEventDetail detail = createRegisterTxSessionEventDetail(transactionId);
        event.setDetails(Optional.of(detail));
        return event;
    }
}
