package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Azienda;
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
	

}
