package io.payworks.event.sourcing.client.internal.service;

import com.netflix.hystrix.HystrixCommand;
import io.payworks.event.sourcing.client.dto.GwcEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoggingThatFailsSilentlyCommand extends HystrixCommand<String> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingThatFailsSilentlyCommand.class);

    private GwcEventDto event;
    private Runnable action;
    private long currentTimeMillis;

    static LoggingThatFailsSilentlyCommand createCommand(Runnable action, GwcEventDto event, HystrixCommand.Setter commandSetter) {
        return new LoggingThatFailsSilentlyCommand(action, event, commandSetter);
    }

    private LoggingThatFailsSilentlyCommand(Runnable action, GwcEventDto event, HystrixCommand.Setter commandSetter) {
        super(commandSetter);
        this.action = action;
        this.event = event;
    }

    @Override
    protected String run() {
        currentTimeMillis = System.currentTimeMillis();
        action.run();
        // TODO Remove this logging when we can enable hystrix monitoring
        logger.info("Time taken to log the event with id='{}': {}ms.", event.getUuid(), System.currentTimeMillis() - currentTimeMillis);
        return "success";
    }

    @Override
    protected String getFallback() {
        logger.info("Time taken to before fallback logging the event with id='{}': {}ms.", event.getUuid(), System.currentTimeMillis() - currentTimeMillis);
        if (isFailedExecution()) {
            Throwable failedExecutionException = getFailedExecutionException();
            logger.warn("Unexpected exception found executing logging command: {}", failedExecutionException);
            logger.error("Unexpected exception found executing logging command: {}", failedExecutionException.getMessage());
        } else {
            logger.error("EventSourcing command timed out");
        }
        return "error";
    }

}
