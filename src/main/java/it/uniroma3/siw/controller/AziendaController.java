package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.AziendaRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.AziendaService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AziendaController {
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
	
	@GetMapping("/paginaAziende")
    public String paginaAziende(Model model) {
    model.addAttribute("aziende",this.aziendaService.findAll());
    return "paginaAziende.html";
  }
	@GetMapping("/azienda/{id}")
    public String Azienda(@PathVariable("id") Long id,Model model) {
    model.addAttribute("azienda",this.aziendaService.findById(id));
    return "azienda.html";
  }
	@GetMapping("/formCreaAzienda")
    public String formCreaAziende(Model model) {
    model.addAttribute("azienda",new Azienda());
    return "azienda/formCreaAzienda.html";
  }
	@PostMapping("/azienda/formCreaAzienda")
	public String nuovaRicetta(@Valid @ModelAttribute("azienda") Azienda azienda,BindingResult bindingResult, Model model) {
		
		if (!bindingResult.hasErrors()) {
		this.aziendaService.save(azienda);
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
			azienda.setUser(user);
			this.aziendaService.save(azienda);
			
		return "/azienda/indexAzienda";
		}else {
			model.addAttribute("aziende",new Azienda());
			return"azienda/formCreaAzienda";
	}}
	@GetMapping("/azienda/profiloAzienda")
    public String profiloAziende(Model model) {
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User utente = credenziali.getUser();
		Azienda azienda1=this.aziendaRepository.findByUser(utente);
		if(azienda1!=null) {
		model.addAttribute("azienda", this.aziendaRepository.findByUser(utente));
		
    return "azienda/profiloAzienda.html";
  }else {
	  model.addAttribute("azienda",new Azienda());
	  return "azienda/formCreaAzienda.html";
  }
	}
	

}
