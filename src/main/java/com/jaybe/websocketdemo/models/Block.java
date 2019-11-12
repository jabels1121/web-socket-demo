package com.jaybe.websocketdemo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "BLOCKS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "BLOCK_NUMBER")
    private String blockNumber;

    @Column(name = "BLOCK_STATE")
    private State blockState;

    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private AppUser appUser;

    public static Block createNewBlock(String blockNumber) {
        var newBlock = new Block();
        newBlock.setBlockNumber(blockNumber);
        newBlock.setLastUpdated(LocalDateTime.now(ZoneOffset.UTC));
        return newBlock;
    }
}
