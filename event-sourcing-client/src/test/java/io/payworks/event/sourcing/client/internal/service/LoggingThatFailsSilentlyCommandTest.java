package io.payworks.event.sourcing.client.internal.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import io.payworks.event.sourcing.client.dto.GwcEventDto;
import io.payworks.event.sourcing.model.domain.GwcEventType;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Test;

import java.util.Optional;

import static io.payworks.event.sourcing.client.internal.service.LoggingThatFailsSilentlyCommand.createCommand;
import static java.util.Optional.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.fail;

public class LoggingThatFailsSilentlyCommandTest {

    private HystrixCommand.Setter COMMAND_SETTER_CONFIG = HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("group"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("name"));

    @Test
    public void testSuccess() {
        LoggingThatFailsSilentlyCommand victim = givenCommand(getNopRunnable());
        assertThat(victim.execute(), CoreMatchers.is("success"));
    }

    private Runnable getNopRunnable() {
        return () -> {
        };
    }

    @SuppressWarnings("unchecked")
    private LoggingThatFailsSilentlyCommand givenCommand(Runnable runnable) {
        GwcEventDto<?> event = new GwcEventDto("1341234", GwcEventType.FINALIZE_TRANSACTION, empty());
        return createCommand(runnable, event, COMMAND_SETTER_CONFIG);
    }

    @Test
    public void testFailure() {
        LoggingThatFailsSilentlyCommand victim = givenCommand(getRuntimeExceptionRunnable());
        try {
            victim.execute();
        } catch (HystrixRuntimeException e) {
            fail("we should not get an exception as we fail silently with a fallback");
        }
    }

    private Runnable getRuntimeExceptionRunnable() {
        return () -> {
            throw new RuntimeException("error found");
        };
    }


}
