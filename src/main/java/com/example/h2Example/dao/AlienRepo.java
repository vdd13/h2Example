package com.example.h2Example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.h2Example.model.Alien;

public interface AlienRepo extends JpaRepository<Alien, Integer>{  //CrudRepository<Alien, Integer>{

	List<Alien> findByTech(String tech);
	List<Alien> getByTech(String tech);
	
	List<Alien> findByIdGreaterThan(int id);
	List<Alien> findByIdLessThan(int id);
	
	@Query("from AlienDB where tech = ?1 order by name desc")
	List<Alien> findByTechSorted(String tech);
}
