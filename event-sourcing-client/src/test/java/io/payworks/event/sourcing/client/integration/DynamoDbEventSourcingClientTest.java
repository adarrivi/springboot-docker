package io.payworks.event.sourcing.client.integration;

import io.payworks.event.sourcing.client.EventSourcingClient;
import io.payworks.event.sourcing.client.EventSourcingClientBuilder;
import io.payworks.event.sourcing.client.dto.GwcEventDto;
import io.payworks.event.sourcing.model.detail.RegisterTxSessionEventDetail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rx.Observable;

import static io.payworks.event.sourcing.client.TestGwcEventDtoBuilder.createNewRegisterTxEvent;
import static io.payworks.event.sourcing.client.TestGwcEventDtoBuilder.updateRegisterTxEvent;

public class DynamoDbEventSourcingClientTest {

    private EventSourcingClient victim;

    @BeforeClass
    public void setUpClient() {
        String dockerHost = System.getenv("dynamodb.endpoint");
        if (dockerHost == null) {
            dockerHost = "http://localhost:8000";
        }
        victim = EventSourcingClientBuilder.newDynamoDbClient(dockerHost, "", "", "")
                .setAsyncThreadTimeout(5000)
                .build();
    }

    @Test
    public void logNewRequestEventAndUpdate() throws InterruptedException {
        GwcEventDto<RegisterTxSessionEventDetail> event = createNewRegisterTxEvent();
        victim.logNewRequestEvent(event);
        event = updateRegisterTxEvent(event);
        Observable<String> stringObservable = victim.updateRequestEvent(event);
        //Wait until the log has been executed
        stringObservable.toBlocking().single();
    }

}
