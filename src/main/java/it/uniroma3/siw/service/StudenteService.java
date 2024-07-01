package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	 public List<Studente> getClassifica() {
	        Pageable topThree = PageRequest.of(0, 3);
	        return studenteRepository.findClassifica(topThree);
	    }
}
