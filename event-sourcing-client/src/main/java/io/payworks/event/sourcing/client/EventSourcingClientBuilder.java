package io.payworks.event.sourcing.client;

import io.payworks.event.sourcing.client.internal.DynamoDbEventSourcingClient;
import io.payworks.event.sourcing.client.internal.MockedEventSourcingClient;

public class EventSourcingClientBuilder {

    private String dynamoDbEndPoint;
    private String schema;
    private String awsAccessKey;
    private String awsSecretKey;
    private int asyncClientMaxThreads = 5;
    private int asyncThreadTimeout = 1000;

    public static EventSourcingClient buildMockedClient() {
        return new MockedEventSourcingClient();
    }


    private EventSourcingClientBuilder() {
    }

    public static EventSourcingClientBuilder newDynamoDbClient(String dynamoDbEndPoint, String schema, String awsAccessKey, String awsSecretKey) {
        EventSourcingClientBuilder builder = new EventSourcingClientBuilder();
        builder.dynamoDbEndPoint = dynamoDbEndPoint;
        builder.schema = schema;
        builder.awsAccessKey = awsAccessKey;
        builder.awsSecretKey = awsSecretKey;
        return builder;
    }

    public EventSourcingClientBuilder setAsyncClientMaxThreads(int asyncClientMaxThreads) {
        this.asyncClientMaxThreads = asyncClientMaxThreads;
        return this;
    }

    public EventSourcingClientBuilder setAsyncThreadTimeout(int asyncThreadTimeout) {
        this.asyncThreadTimeout = asyncThreadTimeout;
        return this;
    }

    public EventSourcingClient build() {
        DynamoDbEventSourcingClient client = new DynamoDbEventSourcingClient(dynamoDbEndPoint, schema, awsAccessKey, awsSecretKey, asyncClientMaxThreads, asyncThreadTimeout);
        client.initialize();
        return client;
    }
}
