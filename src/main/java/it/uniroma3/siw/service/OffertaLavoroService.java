package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.OffertaLavoro;
import it.uniroma3.siw.repository.OffertaLavoroRepository;

@Service
public class OffertaLavoroService {
    
    @Autowired 
    private OffertaLavoroRepository offertaLavoroRepository;

    @Autowired 
    private AziendaService aziendaService;

    @Transactional
    public List<Object[]> countOffertaAzienda() {
        return this.offertaLavoroRepository.countOffertePerAzienda();
    }

    @Transactional
    public OffertaLavoro findById(Long id) {
        return offertaLavoroRepository.findById(id).get();
    }

    @Transactional
    public Iterable<OffertaLavoro> findAll() {
        return offertaLavoroRepository.findAll();
    }

    @Transactional
    public void modifica(@PathVariable("idofferta") Long idofferta, @PathVariable("idazienda") Long idazienda) {
        OffertaLavoro o = this.findById(idofferta);
        Azienda a = this.aziendaService.findById(idazienda);
        List<OffertaLavoro> offerte = a.getOfferte();
        offerte.remove(o);
        a.setOfferte(offerte);
        this.offertaLavoroRepository.delete(o);
    }

    @Transactional
    public Object count() {
        return offertaLavoroRepository.count();
    }

    @Transactional
    public List<Object[]> countOffertePerAzienda() {
        return this.offertaLavoroRepository.countOffertePerAzienda();
    }

    @Transactional
    public void save(OffertaLavoro offertaLavoro) {
        this.offertaLavoroRepository.save(offertaLavoro);
    }
}
