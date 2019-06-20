package com.task.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.task.entity.Player;
import com.task.exception.PlayerDataAccessException;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {
	private static final Logger logger = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Player> findCustomPayer(Player playerobj) throws PlayerDataAccessException {
		logger.debug("inside of findCustomPayer(Player playerobj) methos :");

		List<Player> allPlayers = null;
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Player> query = builder.createQuery(Player.class);
			Root<Player> player = query.from(Player.class);

			List<Predicate> predicates = new ArrayList<Predicate>();

			// Adding predicates in case of parameter not being null
			if (playerobj.getId() != 0) {
				predicates.add(builder.equal(player.get("id"), playerobj.getId()));
			}
			if (playerobj.getName() != null) {
				predicates.add(builder.equal(player.get("name"), playerobj.getName()));
			}

			query.select(player).where(predicates.toArray(new Predicate[] {}));

			entityManager.createQuery(query).getResultList();
		} catch (Exception e) {
			logger.error("Error Occured at findCustomPayer(Player playerobj)");
			throw new PlayerDataAccessException("findCustomPayer method exception");
		}
		return allPlayers;
	}

	@Override
	@Transactional
	public Player findById(int id) throws DataAccessException {
		return this.entityManager.find(Player.class, id);
	}

	@Override
	@Transactional
	public void save(Player player) throws DataAccessException {
		this.entityManager.persist(player);
	}

	@Override
	@Transactional
	public void delete(Player player) throws DataAccessException {
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Collection<Player> findAll() throws DataAccessException {
		return this.entityManager.createQuery("SELECT pla FROM Player pla").getResultList();
	}

	@Override
	@Transactional
	public void saveAll(List<Player> players) throws PlayerDataAccessException {

		try {

			for (Player player : players) {
				this.entityManager.merge(player);
			}

		} catch (Exception e) {
			logger.error("Error Occured at PlayerRepositoryImpl.saveAll()");
			throw new PlayerDataAccessException("Player saving method exception");
		}
	}

}
