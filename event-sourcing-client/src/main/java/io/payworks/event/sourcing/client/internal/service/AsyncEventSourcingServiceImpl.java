package io.payworks.event.sourcing.client.internal.service;

import com.netflix.hystrix.HystrixCommand;
import io.payworks.event.sourcing.client.dto.GwcEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
class AsyncEventSourcingServiceImpl implements AsyncEventSourcingService {

    @Autowired
    private HystrixCommand.Setter silentAsyncCommandSetter;


    @Override
    public Observable<String> executeSilentAsyncAction(Runnable action, GwcEventDto event) {
        return LoggingThatFailsSilentlyCommand.createCommand(action, event, silentAsyncCommandSetter).observe();
    }
}
