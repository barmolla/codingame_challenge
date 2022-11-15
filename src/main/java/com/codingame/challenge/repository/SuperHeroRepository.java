package com.codingame.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codingame.challenge.entity.SuperHero;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {

    @Query(value = "SELECT sh from SuperHero sh WHERE sh.uuid = ?1")
	Optional<SuperHero> findByUUID(String uuid);

    @Query(value = "SELECT sh from SuperHero sh WHERE sh.name LIKE %?1%")
	List<SuperHero> findByName(String search);
}
