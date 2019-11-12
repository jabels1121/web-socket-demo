package com.jaybe.websocketdemo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "BLOCKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    @Id
    @Column(name = "BLOCK_NUMBER", unique = true, nullable = false)
    private String blockNumber;

    @Column(name = "BLOCK_STATE")
    private State blockState;

    @Column(name = "LAST_UPDATED")
    private Long lastUpdated;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private AppUser appUser;

    public static Block createNewBlock(String blockNumber) {
        var newBlock = new Block();
        newBlock.setBlockNumber(blockNumber);
        newBlock.setLastUpdated(Instant.now().toEpochMilli());
        return newBlock;
    }
}
