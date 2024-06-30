
package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Studente;

public interface StudenteRepository extends CrudRepository<Studente, Long> {
	public List<Studente> findByYear(Integer year);
	
	//public boolean existsByNomeAndYear(String nome,Integer year);
	
}

