package io.payworks.event.sourcing.client.internal.service;


import io.payworks.event.sourcing.client.dto.GwcEventDto;
import io.payworks.event.sourcing.model.detail.GenericEventDetail;
import io.payworks.event.sourcing.model.detail.TxEventDetailDto;
import io.payworks.event.sourcing.model.domain.GwcEvent;
import io.payworks.event.sourcing.model.domain.GwcTxEvent;
import io.payworks.event.sourcing.model.repository.GwcEventRepository;
import io.payworks.event.sourcing.model.repository.GwcTxEventRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;
import static org.springframework.util.StringUtils.isEmpty;

@Service
class GwcRequestEventServiceImpl implements GwcRequestEventService {

    private static final Logger logger = LoggerFactory.getLogger(GwcRequestEventServiceImpl.class);

    @Autowired
    private GwcEventRepository gwcEventRepository;
    @Autowired
    private GwcTxEventRepository gwcTxEventRepository;


    @Override
    public void logNewRequestEvent(GwcEventDto<?> dtoRq) {
        String requestId = dtoRq.getUuid();
        if (requestId == null) {
            logger.error("LogNewRequestEvent event without request id received. Ignoring event");
            return;
        }
        GwcEvent event = new GwcEvent();
        event.setGwcEventId(requestId);
        event.setType(dtoRq.getType());
        DateTime creationDate = now(UTC);
        event.setInboundDate(creationDate);
        if (dtoRq.getDetails().isPresent()) {
            GenericEventDetail details = dtoRq.getDetails().get();
            event.setInboundDetailsFromJsonObject(details);
            addGwcTxEventIfTransactionIdIsPresent(requestId, details, creationDate);
        }
        gwcEventRepository.save(event);
    }


    private void addGwcTxEventIfTransactionIdIsPresent(String requestId, GenericEventDetail details, DateTime creationDate) {
        TxEventDetailDto txEventDetail = details.getTxEventDetail();
        if (txEventDetail != null) {
            String transactionId = txEventDetail.getTransactionId();
            if (!isEmpty(transactionId)) {
                gwcTxEventRepository.save(new GwcTxEvent(transactionId, requestId, creationDate));
            }
        }
    }

    @Override
    public void updateRequestEvent(GwcEventDto<?> dtoRs) {
        String requestId = dtoRs.getUuid();
        if (requestId == null) {
            logger.error("UpdateRequestEvent event without request id received. Ignoring event");
            return;
        }

        Optional<GwcEvent> optionalEvent = gwcEventRepository.findOne(requestId);
        if (!optionalEvent.isPresent()) {
            logger.error("Event with id {} not found in the database. Ignoring UpdateRequestEvent", requestId);
            return;
        }

        GwcEvent event = optionalEvent.get();
        DateTime creationDate = now(UTC);
        event.setOutboundDate(creationDate);
        if (dtoRs.getDetails().isPresent()) {
            GenericEventDetail details = dtoRs.getDetails().get();
            event.setOutboundDetailsFromJsonObject(details);
            addGwcTxEventIfTransactionIdIsPresent(requestId, details, creationDate);
        }
        gwcEventRepository.save(event);
    }
}
