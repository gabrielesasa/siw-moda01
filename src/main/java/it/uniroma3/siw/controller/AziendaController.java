package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AziendaService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.validator.AziendaValidator;
import jakarta.validation.Valid;

@Controller
public class AziendaController {
    
    @Autowired
    private AziendaService aziendaService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private AziendaValidator aziendaValidator;
    
    @Autowired
    GlobalController globalController;

    @GetMapping("/paginaAziende")
    public String paginaAziende(Model model) {
        model.addAttribute("aziende", this.aziendaService.findAll());
        return "paginaAziende.html";
    }

    @GetMapping("/azienda2")
    public String pagina(Model model) {
        model.addAttribute("azienda", new Azienda());
        return "azienda2.html";
    }

    @GetMapping("/azienda/modificaAzienda")
    public String aggiornaAziende(Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User utente = credenziali.getUser();
        model.addAttribute("azienda", this.aziendaService.findByUser(utente));

        return "azienda/formModificaAzienda.html";
    }

    @PostMapping("/azienda/formModificaAzienda")
    public String modificaazienda(@RequestParam("id") Long id, 
                                  @RequestParam("nuovoNome") String nuovoNome,
                                  @RequestParam("nuovoindirizzo") String nuovoindirizzo, 
                                  @RequestParam("nuovotelefono") Long nuovotelefono,
                                  @RequestParam("nuovoEmail") String nuovoEmail, 
                                  Model model) {
        this.aziendaService.modifica(id, nuovoNome, nuovoindirizzo, nuovotelefono, nuovoEmail);
        return "/azienda/indexAzienda";
    }

    @GetMapping("/azienda/{id}")
    public String Azienda(@PathVariable("id") Long id, Model model) {
        model.addAttribute("azienda", this.aziendaService.findById(id));
        return "azienda.html";
    }

    @GetMapping("/formCreaAzienda")
    public String formCreaAziende(Model model) {
        model.addAttribute("azienda", new Azienda());
        return "azienda/formCreaAzienda.html";
    }

    @PostMapping("/azienda/formCreaAzienda")
    public String creaAzienda(@Valid @ModelAttribute("azienda") Azienda azienda, 
                              BindingResult bindingResult,
                              @RequestParam("imageFile") MultipartFile imageFile, 
                              Model model) throws IOException {
        this.aziendaValidator.validate(azienda, bindingResult);
        if (!bindingResult.hasErrors()) {
            this.aziendaService.creaAzienda(azienda, bindingResult, imageFile);
            return "/azienda/indexAzienda";
        } else {
            return "azienda/formCreaAzienda";
        }
    }

    @GetMapping("/azienda/profiloAzienda")
    public String profiloAziende(Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User utente = credenziali.getUser();
        Azienda azienda1 = this.aziendaService.findByUser(utente);
        if (azienda1 != null) {
            model.addAttribute("azienda", this.aziendaService.findByUser(utente));
            return "azienda/profiloAzienda.html";
        } else {
            model.addAttribute("azienda", new Azienda());
            return "azienda/formCreaAzienda.html";
        }
    }
}
