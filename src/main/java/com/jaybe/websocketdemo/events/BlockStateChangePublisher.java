package com.jaybe.websocketdemo.events;

import com.jaybe.websocketdemo.models.Block;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlockStateChangePublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public BlockStateChangePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishBlockStateChangedEvent(final Block block) {
        log.info("Start publishing block state changed event.");
        var event = new BlockStateChangeEvent(this, block);
        applicationEventPublisher.publishEvent(event);
        log.info("Event={} was publish.", event);
    }
}
