package com.jaybe.websocketdemo.controllers;

import com.jaybe.websocketdemo.controllers.request.ChangeBlockStateRequest;
import com.jaybe.websocketdemo.controllers.response.GenericResponse;
import com.jaybe.websocketdemo.models.Customer;
import com.jaybe.websocketdemo.services.BlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class BlockController extends AbstractRestController {

    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping(path = "/block/state/change")
    public GenericResponse changeBlockState(@RequestBody ChangeBlockStateRequest changeBlockStateRequest,
                                           Principal principal) {
        log.info("Principal={}", principal);
        return blockService.changeBlockState(changeBlockStateRequest, ((Customer) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()));
    }
}
