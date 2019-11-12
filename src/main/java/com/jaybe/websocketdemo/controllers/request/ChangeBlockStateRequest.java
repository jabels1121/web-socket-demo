package com.jaybe.websocketdemo.controllers.request;

import com.jaybe.websocketdemo.models.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeBlockStateRequest {

    private String blockNumber;
    private State changeStateTo;

}
