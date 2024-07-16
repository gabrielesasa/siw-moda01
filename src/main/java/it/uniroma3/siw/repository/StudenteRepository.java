
package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.model.User;

public interface StudenteRepository extends CrudRepository<Studente, Long> {
	public List<Studente> findByYear(Integer year);
	public Studente findByUser(User utente);
	public boolean existsByNomeAndCognome(String nome,String cognome);
	@Query(value = "SELECT s.* FROM Studente s JOIN esami e ON s.id = e.studente_id GROUP BY s.id ORDER BY SUM(e.voto_esame) DESC", nativeQuery = true)
	    List<Studente> findClassifica(Pageable pageable);

	public Studente findByNomeEndingWith(String nome);
	public Studente findByNome(String nome);
	
}

