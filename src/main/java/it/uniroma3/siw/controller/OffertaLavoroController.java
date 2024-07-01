package it.uniroma3.siw.controller;

import java.util.List;

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
		
		model.addAttribute("offerteLavoro",azienda.getOfferte());
		model.addAttribute("azienda", azienda);
    return "azienda/sezioneOfferteLavoro.html";
  }
	@GetMapping("/azienda/aggiungiOffertaLavoro")
    public String aggiungiOffertaLavoro(Model model) {
		model.addAttribute("offertaLavoro", new OffertaLavoro());
    return "azienda/formOffertaLavoro.html";
  }
	@GetMapping("/azienda/modificaOffertaLavoro/{idofferta}/{idazienda}")
    public String cancellaAziende(@PathVariable("idofferta") Long idofferta,@PathVariable("idazienda") Long idazienda,Model model) {
	OffertaLavoro o=this.offertaLavoroRepository.findById(idofferta).get();
	Azienda a=this.aziendaService.findById(idazienda);
	List<OffertaLavoro> offerte=a.getOfferte();
	offerte.remove(o);
	a.setOfferte(offerte);
	this.offertaLavoroRepository.delete(o);
	
    model.addAttribute("aziende",this.aziendaService.findAll());
    return "paginaAziende.html";
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
		
		model.addAttribute("pubblicate",this.offertaLavoroRepository.findAll() );
    return "azienda/bacheca.html";
  }
	@GetMapping("/studente/bacheca")
    public String getBachecaStudente(Model model) {
		
		
	
		model.addAttribute("pubblicate",this.offertaLavoroRepository.findAll() );
    return "studente/bacheca.html";
  }
	@GetMapping("/azienda/offertaLavoro/{id}")
    public String aziendaOffertaLavoro(@PathVariable("id") Long id,Model model) {
		OffertaLavoro offertaLavoro =this.offertaLavoroRepository.findById(id).get();
		model.addAttribute("offertaLavoro", this.offertaLavoroRepository.findById(id).get());
    return "azienda/offertaLavoro.html";
}
	@GetMapping("/studente/offertaLavoro/{id}")
    public String studenteOffertaLavoro(@PathVariable("id") Long id,Model model) {
		model.addAttribute("offertaLavoro", this.offertaLavoroRepository.findById(id).get());
    return "studente/offertaLavoro.html";
}

}
