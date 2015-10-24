package io.payworks.event.sourcing.client.internal.config;

import com.netflix.hystrix.*;
import io.payworks.event.sourcing.model.config.DynamoDbRepositoryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@Import(DynamoDbRepositoryConfig.class)
@ComponentScan(basePackages = {"io.payworks.event.sourcing.client.internal.service"})
public class DynamoDbClientConfig {

    @Value("${dynamodb.asyncClientMaxThreads}")
    private int asyncClientMaxThreads;
    @Value("${dynamodb.asyncThreadTimeout}")
    private int asyncThreadTimeout;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ExecutorService asyncExecutor() {
        return Executors.newFixedThreadPool(asyncClientMaxThreads);
    }

    @Bean
    public ScheduledExecutorService cancellerExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public HystrixCommand.Setter silentAsyncCommandSetter() {
        HystrixCommand.Setter setter = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GwcEventSourcing"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("LogEvent"));
        // For more info about the configuration, see https://github.com/Netflix/Hystrix/wiki/Configuration
        HystrixCommandProperties.Setter commandPropertiesSetter = HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(asyncThreadTimeout)
                .withRequestLogEnabled(false);
        HystrixThreadPoolProperties.Setter threadPoolSetter = HystrixThreadPoolProperties.Setter()
                .withCoreSize(asyncClientMaxThreads);
        return setter.andCommandPropertiesDefaults(commandPropertiesSetter).andThreadPoolPropertiesDefaults(threadPoolSetter);
    }


}
