package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.OffertaLavoro;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.AziendaRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.OffertaLavoroRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.AziendaService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class OffertaLavoroController {
	//@Autowired 
	//private OffertaLavoroService offertaLavoroService;
	@Autowired 
	private OffertaLavoroRepository offertaLavoroRepository;
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
	@Autowired 
	private AziendaService aziendaService;
	@Autowired 
	private AziendaRepository aziendaRepository;
	
	@GetMapping("/azienda/sezioneOfferteLavoro")
    public String sezioneOfferteLavoro(Model model) {
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
		Azienda azienda=this.aziendaRepository.findByUser(user);
		
		model.addAttribute("offerteLavoro", this.offertaLavoroRepository.findOfferteLavoro(user.getId()));
		
    return "azienda/sezioneOfferteLavoro.html";
  }
	@GetMapping("/azienda/aggiungiOffertaLavoro")
    public String aggiungiOffertaLavoro(Model model) {
		model.addAttribute("offertaLavoro", new OffertaLavoro());
    return "azienda/formOffertaLavoro.html";
  }
	@PostMapping("/azienda/formOffertaLavoro")
	public String formOffertaLavoro(@Valid @ModelAttribute("offertaLavoro") OffertaLavoro offertaLavoro,BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
		
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
		Azienda azienda=this.aziendaRepository.findByUser(user);
		offertaLavoro.setAzienda(azienda);
		if(azienda.getOfferte().isEmpty())
			System.out.println("la lista è vuota");
		else
		System.out.println(azienda.getOfferte().get(0).getTipologia() + "ciaoooooo");
		this.offertaLavoroRepository.save(offertaLavoro);
		if(azienda.getOfferte().isEmpty())
			System.out.println("la lista è vuota");
		else
		System.out.println(azienda.getOfferte().get(0).getTipologia() + "ciaoooooo");
		return "/azienda/indexAzienda";
		}else {
		
			return "/azienda/formOffertaLavoro.html";
		}
}
	
	@GetMapping("/azienda/bacheca")
    public String getBacheca(Model model) {
		
		Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
		User user = credenziali.getUser();
		
		Azienda azienda=this.aziendaRepository.findByUser(user);
		if(azienda.getOfferte().isEmpty())
			System.out.println("la lista è vuota");
		else
		System.out.println(azienda.getOfferte().get(0).getTipologia() + "ciaoooooo");
		System.out.println(azienda.getPubblicate().get(0));
		model.addAttribute("pubblicate",azienda.getPubblicate() );
    return "azienda/bacheca.html";
  }
}
