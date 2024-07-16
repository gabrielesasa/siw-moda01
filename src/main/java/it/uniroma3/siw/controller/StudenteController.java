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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.StudenteService;
import it.uniroma3.siw.validator.StudenteValidator;
import jakarta.validation.Valid;

@Controller
public class StudenteController {
    
    @Autowired 
    private StudenteService studenteService;

    @Autowired 
    private CredentialsService credentialsService;

    @Autowired
    private StudenteValidator studenteValidator;

    @Autowired
    GlobalController globalController;

    @PostMapping("/studente/formCreaStudente")
    public String nuovaStudente(@Valid @ModelAttribute("studente") Studente studente, BindingResult bindingResult, Model model) {
        this.studenteValidator.validate(studente, bindingResult);
        if (!bindingResult.hasErrors()) {
            this.studenteService.save(studente);
            Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
            User user = credenziali.getUser();
            studente.setUser(user);
            this.studenteService.save(studente);
            return "/studente/indexStudente";
        } else {
            return "studente/formCreaStudente";
        }
    }

    @GetMapping("/studente/profiloStudente")
    public String profiloStudente(Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User user = credenziali.getUser();
        Studente studente = this.studenteService.findByUser(user);
        if (studente != null) {
            model.addAttribute("studente", studente);
            return "studente/profiloStudente.html";
        } else {
            model.addAttribute("studente", new Studente());
            return "studente/formCreaStudente.html";
        }
    }

    @GetMapping("/studente/modificaProfilo")
    public String modificaProfilo(Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User user = credenziali.getUser();
        model.addAttribute("studente", this.studenteService.findByUser(user));
        return "studente/formModificaProfilo.html";
    }

    @PostMapping("/studente/formModificaStudente")
    public String modificaStudente(@Valid @RequestParam("id") Long id, 
                                   @RequestParam("nuovoNome") String nuovoNome, 
                                   @RequestParam("nuovocognome") String nuovocognome, 
                                   @RequestParam("nuovoAnno") Integer nuovoAnno, 
                                   @RequestParam("nuovoEmail") String nuovoEmail, 
                                   Model model) {
        this.studenteService.modifica(id, nuovoNome, nuovocognome, nuovoAnno, nuovoEmail);
        return "/studente/indexStudente";
    }

    @GetMapping("/studente/aggiungiEsame")
    public String aggiungiEsame(Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User user = credenziali.getUser();
        model.addAttribute("studente", this.studenteService.findByUser(user));
        return "studente/formCreaEsame.html";
    }

    @PostMapping("/studente/aggiungiEsame")
    public String aggiungiEsame(@Valid @RequestParam("nomeEsame") String nomeEsame, 
                                @RequestParam("votoEsame") Integer votoEsame, 
                                Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User user = credenziali.getUser();
        model.addAttribute("studente", this.studenteService.aggiungiEsame(nomeEsame, votoEsame, user));
        return "/studente/profiloStudente";
    }

    @GetMapping("/studente/paginaStudenti")
    public String paginaStudenti(Model model) {
        List<Studente> studenti = this.studenteService.getClassifica();
        Studente s1 = studenti.get(0);
        Studente s2 = studenti.get(1);
        Studente s3 = studenti.get(2);
        System.out.println("primo " + s1.getCognome() + " secondo " + s2.getCognome() + " terzo " + s3.getCognome());
        model.addAttribute("s1", s1);
        model.addAttribute("s2", s2);
        model.addAttribute("s3", s3);
        return "studente/paginaStudenti";
    }

    @GetMapping("/studente/studente/{id}")
    public String getStudente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("studente", this.studenteService.findById(id));
        return "studente/studente.html";
    }

    @GetMapping("/studente/studenti")
    public String getStudenti(Model model) {
        model.addAttribute("studenti", this.studenteService.findAll());
        return "studente/studenti.html";
    }

    @GetMapping("/studente/cercaStudente")
    public String cercaStudente(@RequestParam("nomeStudente") String nomeStudente, Model model) {
        Studente studente = studenteService.findByNome(nomeStudente);
        if (studente == null) {
            return "studente/studenteNull.html";
        }
        model.addAttribute("studente", studente);
        return "studente/studente.html";
    }
}
