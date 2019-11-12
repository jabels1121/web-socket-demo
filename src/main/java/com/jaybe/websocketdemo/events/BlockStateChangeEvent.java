package com.jaybe.websocketdemo.events;

import com.jaybe.websocketdemo.models.Block;
import org.springframework.context.ApplicationEvent;

public class BlockStateChangeEvent extends ApplicationEvent {

    private Block block;

    public BlockStateChangeEvent(Object source, Block block) {
        super(source);
        this.block = block;
    }

    public Block getBlockWithUpdatedState() {
        return this.block;
    }
}
