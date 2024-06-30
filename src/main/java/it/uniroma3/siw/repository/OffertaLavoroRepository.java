package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.User;

import it.uniroma3.siw.model.OffertaLavoro;

//attenzione import
public interface OffertaLavoroRepository extends CrudRepository<OffertaLavoro, Long> {

	//public boolean existsByOffertaLavoroAndUser(OffertaLavoro offertaLavoro, User utente);
	//public Optional<OffertaLavoro> findByOffertaLavoroAndUser(OffertaLavoro offertaLavoro, OffertaLavoro utente);
	
	//public List<OffertaLavoro> findByOffertaLavoro(OffertaLavoro offertaLavoro);
	
	//public List<OffertaLavoro> findByUser(User utente);
	
	
	@Query("SELECT o FROM OffertaLavoro o WHERE o.azienda.user.id = :userid")
	List<OffertaLavoro> findOfferteLavoro(@Param("userid") Long id);

}
