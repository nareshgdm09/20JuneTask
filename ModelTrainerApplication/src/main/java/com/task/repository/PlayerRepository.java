package com.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}