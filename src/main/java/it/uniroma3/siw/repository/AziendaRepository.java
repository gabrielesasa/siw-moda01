
package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.User;

public interface AziendaRepository extends CrudRepository<Azienda, Long> {

	public Azienda findByUser(User utente);
	//public List<Azienda> findByYear(Integer year);
	
	public boolean existsByNome(String nome);
	
}

