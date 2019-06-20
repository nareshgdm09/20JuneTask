package com.task.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.task.entity.Player;
import com.task.exception.PlayerDataAccessException;

public interface PlayerRepository {

	List<Player> findCustomPayer(Player playerobj) throws DataAccessException, PlayerDataAccessException;

	Player findById(int id) throws DataAccessException;

	void save(Player player) throws DataAccessException;

	Collection<Player> findAll() throws DataAccessException;

	void delete(Player pet) throws DataAccessException;

	void saveAll(List<Player> entities) throws PlayerDataAccessException;

}