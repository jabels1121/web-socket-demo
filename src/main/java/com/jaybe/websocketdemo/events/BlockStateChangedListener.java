package com.jaybe.websocketdemo.events;

import com.jaybe.websocketdemo.models.Block;
import com.jaybe.websocketdemo.repositories.AppUserRepository;
import com.jaybe.websocketdemo.repositories.WebSocketSessionsStore;
import com.jaybe.websocketdemo.services.WebSocketSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlockStateChangedListener {

    private final WebSocketSenderService webSocketSenderService;

    public BlockStateChangedListener(WebSocketSenderService webSocketSenderService) {
        this.webSocketSenderService = webSocketSenderService;
    }

    @Async
    @EventListener
    public void handleBlockStateChangedEvent(BlockStateChangeEvent event) {
        log.info("Handle block state changed event={}", event);
        var blockWithUpdatedState = event.getBlockWithUpdatedState();
        var appUser = blockWithUpdatedState.getAppUser();

        try {
            Thread.sleep(3500L);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        webSocketSenderService.broadCastByUserName(appUser.getUserName(), blockWithUpdatedState, Block.class);
    }

}
