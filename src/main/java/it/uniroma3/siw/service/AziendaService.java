package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.repository.AziendaRepository;
@Service
public class AziendaService {
	@Autowired
	private AziendaRepository aziendaRepository;
	public Iterable<Azienda>findAll(){
		return aziendaRepository.findAll();
	}
	@Transactional
	public Azienda findById(Long id) {
		return aziendaRepository.findById(id).get();
	}
	@Transactional
	public Azienda save(Azienda azienda) {
		return aziendaRepository.save(azienda);
		
	}
}
