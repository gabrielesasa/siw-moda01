package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.repository.StudenteRepository;
@Service
public class StudenteService {
	@Autowired
	private StudenteRepository studenteRepository;
	public Iterable<Studente>findAll(){
		return studenteRepository.findAll();
	}
	@Transactional
	public Studente findById(Long id) {
		return studenteRepository.findById(id).get();
	}
	@Transactional
	public Studente save(Studente studente) {
		return studenteRepository.save(studente);
		
	}
}
