package com.jaybe.websocketdemo.services;

import com.jaybe.websocketdemo.controllers.request.ChangeBlockStateRequest;
import com.jaybe.websocketdemo.controllers.response.ErrorResponse;
import com.jaybe.websocketdemo.controllers.response.GenericResponse;
import com.jaybe.websocketdemo.events.BlockStateChangePublisher;
import com.jaybe.websocketdemo.exceptions.BlockNotFoundException;
import com.jaybe.websocketdemo.models.Customer;
import com.jaybe.websocketdemo.repositories.BlockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class BlockService {

    private final BlockRepository blockRepository;
    private final BlockStateChangePublisher blockStateChangePublisher;

    public BlockService(BlockRepository blockRepository,
                        BlockStateChangePublisher blockStateChangePublisher) {
        this.blockRepository = blockRepository;
        this.blockStateChangePublisher = blockStateChangePublisher;
    }

    public GenericResponse changeBlockState(ChangeBlockStateRequest changeBlockStateRequest, Customer customer) {
        var result = validationBlockNumberToCustomer(changeBlockStateRequest.getBlockNumber(), customer);
        if (!result) {
            GenericResponse response = new GenericResponse();
            response.setResult(false);
            ErrorResponse error = new ErrorResponse();
            error.setMessage("Incorrect blockNumber");
            response.setError(error);
            return response;
        }

        var byBlockNumberOptional = blockRepository.findByBlockNumber(changeBlockStateRequest.getBlockNumber());
        var block = byBlockNumberOptional.orElseThrow(() -> {
            throw new BlockNotFoundException("Block not found!");
        });

        block.setLastUpdated(Instant.now().toEpochMilli());
        block.setBlockState(changeBlockStateRequest.getChangeStateTo());
        blockRepository.save(block);
        blockStateChangePublisher.publishBlockStateChangedEvent(block);
        GenericResponse response = new GenericResponse();
        response.setResult(true);
        response.setData("Change state successfully.");
        return response;
    }

    private boolean validationBlockNumberToCustomer(String blockNumber, Customer customer) {
        AtomicBoolean result = new AtomicBoolean(false);
        customer.getAppUser().getBlocks()
                .forEach(block -> {
                    if (block.getBlockNumber().equalsIgnoreCase(blockNumber)) result.set(true);
                });
        return result.get();
    }
}
