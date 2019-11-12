package com.jaybe.websocketdemo.models;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BlockState {

    private State state;
    private LocalDateTime lastUpdated;

}
