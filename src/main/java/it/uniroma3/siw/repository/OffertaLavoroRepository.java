package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.OffertaLavoro;

//attenzione import
public interface OffertaLavoroRepository extends CrudRepository<OffertaLavoro, Long> {

	
	//public Optional<OffertaLavoro> findByOffertaLavoroAndUser(OffertaLavoro offertaLavoro, OffertaLavoro utente);
	
	//public List<OffertaLavoro> findByOffertaLavoro(OffertaLavoro offertaLavoro);
	
	@Query("SELECT o.azienda.nome, COUNT(o) FROM OffertaLavoro o GROUP BY o.azienda.nome")
    List<Object[]> countOffertePerAzienda();
	
	
	@Query("SELECT o FROM OffertaLavoro o WHERE o.azienda.user.id = :userid")
	List<OffertaLavoro> findOfferteLavoro(@Param("userid") Long id);

	public boolean existsByTitolo(String titolo);


	

}
