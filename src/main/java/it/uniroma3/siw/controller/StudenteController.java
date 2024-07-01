package it.uniroma3.siw.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.AziendaRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.StudenteRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.AziendaService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.StudenteService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;


@Controller
public class StudenteController {
	@Autowired 
	private StudenteService studenteService;
	@Autowired 
	private StudenteRepository studenteRepository;
	@Autowired 
	private AziendaService aziendaService;
	@Autowired 
	private AziendaRepository aziendaRepository;
	@Autowired 
	private CredentialsRepository credentialsRepository;
	@Autowired 
	private CredentialsService credentialsService;
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private UserService userService;
	@Autowired
	GlobalController globalController;
	
	
	
	
	@PostMapping("/studente/formCreaStudente")
	public String nuovaStudente(@Valid @ModelAttribute("studente") Studente studente,BindingResult bindingResult, Model model) {
		
		if (!bindingResult.hasErrors()) {
		this.studenteService.save(studente);
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
			studente.setUser(user);
			this.studenteService.save(studente);
			
		return "/studente/indexStudente";
		}else {
			model.addAttribute("studente",new Studente());
			return"studente/formCreaStudente";
	}}
	@GetMapping("/studente/profiloStudente")
    public String profiloStudente(Model model) {
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
		Studente studente=this.studenteRepository.findByUser(user);
		if(studente!=null) {
		model.addAttribute("studente", this.studenteRepository.findByUser(user));
		
    return "studente/profiloStudente.html";
  }else {
	  model.addAttribute("studente",new Studente());
	  return "studente/formCreaStudente.html";
  }
	}
	@GetMapping("/studente/modificaProfilo")
    public String modificaProfilo(Model model) {
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
		model.addAttribute("studente", this.studenteRepository.findByUser(user));
	  return "studente/formModificaProfilo.html";
  }
	@PostMapping("/studente/formModificaStudente")
	public String modificaStudente(@Valid @ModelAttribute("studente") Studente studente,BindingResult bindingResult,@RequestParam("id") Long id,@RequestParam("nuovoNome") String nuovoNome,@RequestParam("nuovocognome") String nuovocognome,@RequestParam("nuovoAnno") Integer nuovoAnno,@RequestParam("nuovoEmail") String nuovoEmail, Model model) {
		
		if (!bindingResult.hasErrors()) {
			Studente s=this.studenteService.findById(id);
			s.setNome(nuovoNome);
			s.setCognome(nuovocognome);
			s.setYear(nuovoAnno);
			s.setEmail(nuovoEmail);
			this.studenteService.save(s);
			
		return "/studente/indexStudente";
		}else {
			model.addAttribute("studente",new Studente());
			return"studente/formCreaStudente";
	}}
	
	@GetMapping("/studente/aggiungiEsame")
    public String aggiungiEsame(Model model) {
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
		model.addAttribute("studente", this.studenteRepository.findByUser(user));
	  return "studente/formCreaEsame.html";
  
	}
	 @PostMapping("/studente/aggiungiEsame")
	    public String aggiungiEsame(@RequestParam("nomeEsame") String nomeEsame, @RequestParam("votoEsame") Integer votoEsame, Model model) {
	        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
	        User user = credenziali.getUser();
	        Studente studente = this.studenteRepository.findByUser(user);

	        if (studente != null) {
	            Map<String, Integer> esami = studente.getEsami();
	            esami.put(nomeEsame, votoEsame);
	            studente.setEsami(esami);
	            this.studenteService.save(studente);
	        }

	        model.addAttribute("studente", studente);
	        return "/studente/profiloStudente";
	    }
	}
	


