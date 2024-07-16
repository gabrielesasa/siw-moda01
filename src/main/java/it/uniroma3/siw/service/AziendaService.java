package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.GlobalController;
import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.AziendaRepository;
@Service
public class AziendaService {
	@Autowired
	private AziendaRepository aziendaRepository;
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	GlobalController globalController;
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
	@Transactional
	public void save2(Azienda azienda,MultipartFile file) throws IOException {
		azienda.setImmagine(Base64.getEncoder().encodeToString(file.getBytes()));
		aziendaRepository.save(azienda);		
	}
	@Transactional
	public void modifica(@RequestParam("id") Long id,
			@RequestParam("nuovoNome") String nuovoNome, @RequestParam("nuovoindirizzo") String nuovoindirizzo,
			@RequestParam("nuovotelefono") Long nuovotelefono, @RequestParam("nuovoEmail") String nuovoEmail) {
		Azienda s = this.findById(id);
		s.setNome(nuovoNome);
		s.setIndirizzo(nuovoindirizzo);
		s.setTelefono(nuovotelefono);
		s.setEmail(nuovoEmail);
		this.save(s);	
	}
	 @Transactional
	    public Azienda findByUser(User user) {
	        return aziendaRepository.findByUser(user);
	    }
	 @Transactional
		public void creaAzienda(@ModelAttribute("azienda") Azienda azienda, BindingResult bindingResult,
				@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
		 this.save(azienda);
			Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
			User user = credenziali.getUser();
			azienda.setUser(user);
			this.save2(azienda, imageFile);	
		}

}
