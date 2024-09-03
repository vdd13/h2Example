package com.example.h2Example.controler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.h2Example.dao.AlienRepo;
import com.example.h2Example.model.Alien;

import jakarta.persistence.PostRemove;

//@Controller  // zwraca np. "home.jsp" - adress widoku 
@RestController //zwraca json/xml
public class AlienController {

	@Autowired
	AlienRepo repo;
	
	@RequestMapping(path ="/", method = RequestMethod.GET)
	public String home() {
		return "home.jsp";
	}
	
	
	@PostMapping("/alien")
	public Alien addAlienPost(@RequestBody Alien alien) {
		System.out.println(alien);
		repo.save(alien);
		return alien;
	}
	
	@DeleteMapping("/alien/{id}")
	public String deleteAlien(@PathVariable("id") int id) {
		
		Alien a = repo.getReferenceById(id);
		repo.delete(a);
		return "deleted";
	}
	
	@PutMapping(path= "/alien", consumes = "application/json")
	public Alien saveOrUpdateAlien(@RequestBody Alien alien) {
		
		repo.save(alien);
		return alien;
	}
	
	
//	@GetMapping("/aliens")
//	@RequestMapping (path = "/aliens", produces = "application/xml")
//	@GetMapping (path = "/aliens", produces = "application/xml")
	@GetMapping (path = "/aliens")
	@ResponseBody
	public List<Alien> getAliens() {
		System.out.println(repo.findAll().toString());
		return repo.findAll();
	}
	
	@RequestMapping("/alien/{id}")
	@ResponseBody
	public Optional<Alien> getAlien1(@PathVariable("id") int aid) {
		return repo.findById(aid);
	}
	
	@RequestMapping(path = "/addAlien")
	public String addAlien(Alien alien) {
		System.out.println("TEST!! addAlien");
		
		if(!Objects.isNull(alien))
			repo.save(alien);
		return "home.jsp";
	}
	
	@RequestMapping("/getAlien")
	public ModelAndView getAlien(@RequestParam(name = "id") int aid) {
		System.out.println("TEST!! getAlien");
		ModelAndView mv = new ModelAndView();
		Optional<Alien> alien = repo.findById(aid);
		System.out.println(alien);
		
		System.out.println(repo.getByTech("java"));
		System.out.println(repo.findByIdGreaterThan(aid));
		System.out.println(repo.findByIdLessThan(aid));
		
		System.out.println(repo.findByTechSorted("java"));
		
		mv.addObject(alien.orElse(new Alien()));
		mv.setViewName("showAlien.jsp");

		return mv;
	}
}
