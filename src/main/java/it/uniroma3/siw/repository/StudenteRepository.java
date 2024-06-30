
package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.model.User;

public interface StudenteRepository extends CrudRepository<Studente, Long> {
	public List<Studente> findByYear(Integer year);
	public Studente findByUser(User utente);
	//public boolean existsByNomeAndYear(String nome,Integer year);
	
}

