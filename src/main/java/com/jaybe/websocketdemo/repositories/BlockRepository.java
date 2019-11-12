package com.jaybe.websocketdemo.repositories;

import com.jaybe.websocketdemo.models.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    Optional<Block> findBlockByAppUser_Id(Long appUserId);

}
