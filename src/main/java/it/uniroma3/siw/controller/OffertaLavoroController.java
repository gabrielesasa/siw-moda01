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
import it.uniroma3.siw.model.OffertaLavoro;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AziendaService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.OffertaLavoroService;
import jakarta.validation.Valid;

@Controller
public class OffertaLavoroController {

    @Autowired 
    private OffertaLavoroService offertaLavoroService;

    @Autowired 
    private CredentialsService credentialsService;

    @Autowired
    GlobalController globalController;

    @Autowired 
    private AziendaService aziendaService;

    @GetMapping("/azienda/sezioneOfferteLavoro")
    public String sezioneOfferteLavoro(Model model) {
        Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
        User user = credenziali.getUser();
        Azienda azienda = this.aziendaService.findByUser(user);

        model.addAttribute("offerteLavoro", azienda.getOfferte());
        model.addAttribute("azienda", azienda);
        return "azienda/sezioneOfferteLavoro.html";
    }

    @GetMapping("/azienda/aggiungiOffertaLavoro")
    public String aggiungiOffertaLavoro(Model model) {
        model.addAttribute("offertaLavoro", new OffertaLavoro());
        return "azienda/formOffertaLavoro.html";
    }

    @GetMapping("/azienda/modificaOffertaLavoro/{idofferta}/{idazienda}")
    public String cancellaAziende(@PathVariable("idofferta") Long idofferta, @PathVariable("idazienda") Long idazienda, Model model) {
        this.offertaLavoroService.modifica(idofferta, idazienda);
        model.addAttribute("aziende", this.aziendaService.findAll());
        return "azienda/indexAzienda.html";
    }

    @PostMapping("/azienda/formOffertaLavoro")
    public String formOffertaLavoro(@Valid @ModelAttribute("offertaLavoro") OffertaLavoro offertaLavoro, BindingResult bindingResult, Model model) {
        System.out.println("la lista è vuota");
        if (!bindingResult.hasErrors()) {
            Credentials credenziali = credentialsService.getCredentials(globalController.getUser());
            User user = credenziali.getUser();
            Azienda azienda = this.aziendaService.findByUser(user);
            offertaLavoro.setAzienda(azienda);
            this.offertaLavoroService.save(offertaLavoro);
            return "/azienda/indexAzienda";
        } else {
            return "/azienda/formOffertaLavoro.html";
        }
    }

    @GetMapping("/azienda/bacheca")
    public String getBacheca(Model model) {
        model.addAttribute("pubblicate", this.offertaLavoroService.findAll());
        model.addAttribute("countOfferteTotali", this.offertaLavoroService.count());
        model.addAttribute("countOfferteAzienda", this.offertaLavoroService.countOffertePerAzienda());
        return "azienda/bacheca.html";
    }

    

    @GetMapping("/azienda/offertaLavoro/{id}")
    public String aziendaOffertaLavoro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("offertaLavoro", this.offertaLavoroService.findById(id));
        return "azienda/offertaLavoro.html";
    }

    @GetMapping("/studente/offertaLavoro/{id}")
    public String studenteOffertaLavoro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("offertaLavoro", this.offertaLavoroService.findById(id));
        return "studente/offertaLavoro.html";
    }
}
