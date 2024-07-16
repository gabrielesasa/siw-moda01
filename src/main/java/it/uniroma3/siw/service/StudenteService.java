package it.uniroma3.siw.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.StudenteRepository;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;

    public Iterable<Studente> findAll() {
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

    @Transactional
    public Studente findByUser(User user) {
        return studenteRepository.findByUser(user);
    }

    @Transactional
    public Studente findByNomes(String nome) {
        return studenteRepository.findByNomeEndingWith(nome);
    }
    

    @Transactional
    public List<Studente> getClassifica() {
        Pageable topThree = PageRequest.of(0, 3);
        return studenteRepository.findClassifica(topThree);
    }

    @Transactional
    public void modifica(@RequestParam("id") Long id,
                         @RequestParam("nuovoNome") String nuovoNome,
                         @RequestParam("nuovocognome") String nuovocognome,
                         @RequestParam("nuovoAnno") Integer nuovoAnno,
                         @RequestParam("nuovoEmail") String nuovoEmail) {
        Studente s = this.findById(id);
        s.setNome(nuovoNome);
        s.setCognome(nuovocognome);
        s.setYear(nuovoAnno);
        s.setEmail(nuovoEmail);
        this.save(s);
    }

    @Transactional
    public Studente aggiungiEsame(@RequestParam("nomeEsame") String nomeEsame,
                                  @RequestParam("votoEsame") Integer votoEsame,
                                  User user) {
        Studente studente = this.studenteRepository.findByUser(user);
        Map<String, Integer> esami = studente.getEsami();
        esami.put(nomeEsame, votoEsame);
        studente.setEsami(esami);
        this.save(studente);
        return studente;
    }
    @Transactional
    public Studente findByNome(String nome) {
        return studenteRepository.findByNome(nome);
    }
}
