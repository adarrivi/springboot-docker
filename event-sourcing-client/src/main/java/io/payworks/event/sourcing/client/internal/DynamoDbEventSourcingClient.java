package io.payworks.event.sourcing.client.internal;

import io.payworks.event.sourcing.client.EventSourcingClient;
import io.payworks.event.sourcing.client.dto.GwcEventDto;
import io.payworks.event.sourcing.client.internal.config.DynamoDbClientConfig;
import io.payworks.event.sourcing.client.internal.service.AsyncEventSourcingService;
import io.payworks.event.sourcing.client.internal.service.GwcRequestEventService;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rx.Observable;

import java.util.Properties;

public class DynamoDbEventSourcingClient implements EventSourcingClient {

    private String dynamoDbEndPoint;
    private String schema;
    private String awsAccessKey;
    private String awsSecretKey;
    private int asyncClientMaxThreads;
    private int asyncThreadTimeout;

    private AsyncEventSourcingService asyncEventSourcingClient;
    private GwcRequestEventService gwcRequestEventService;

    public DynamoDbEventSourcingClient(String dynamoDbEndPoint, String schema, String awsAccessKey, String awsSecretKey, int asyncClientMaxThreads, int asyncThreadTimeout) {
        this.dynamoDbEndPoint = dynamoDbEndPoint;
        this.schema = schema;
        this.awsAccessKey = awsAccessKey;
        this.awsSecretKey = awsSecretKey;
        this.asyncClientMaxThreads = asyncClientMaxThreads;
        this.asyncThreadTimeout = asyncThreadTimeout;
    }

    public void initialize() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.put("dynamodb.endpoint", dynamoDbEndPoint);
        properties.put("dynamodb.schema", schema);
        properties.put("dynamodb.accessKey", awsAccessKey);
        properties.put("dynamodb.accessSecretKey", awsSecretKey);
        properties.put("dynamodb.asyncClientMaxThreads", String.valueOf(asyncClientMaxThreads));
        properties.put("dynamodb.asyncThreadTimeout", String.valueOf(asyncThreadTimeout));
        propConfig.setProperties(properties);
        applicationContext.addBeanFactoryPostProcessor(propConfig);
        applicationContext.register(DynamoDbClientConfig.class);
        applicationContext.refresh();
        asyncEventSourcingClient = applicationContext.getBean(AsyncEventSourcingService.class);
        gwcRequestEventService = applicationContext.getBean(GwcRequestEventService.class);
        applicationContext.registerShutdownHook();
    }

    @Override
    public Observable<String> logNewRequestEvent(final GwcEventDto event) {
        return asyncEventSourcingClient.executeSilentAsyncAction(() -> gwcRequestEventService.logNewRequestEvent(event), event);

    }

    @Override
    public Observable<String> updateRequestEvent(final GwcEventDto event) {
        return asyncEventSourcingClient.executeSilentAsyncAction(() -> gwcRequestEventService.updateRequestEvent(event), event);
    }
}
