package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.repository.AziendaRepository;

@Component
public class AziendaValidator implements Validator {
		@Autowired
		private AziendaRepository aziendaRepository;
		@Override
		public void validate(Object o, Errors errors) {
			Azienda azienda = (Azienda)o;
		    if (azienda.getNome() != null && aziendaRepository.existsByNome(azienda.getNome())) {
		        errors.reject("azienda.duplicate", "Questo nome è gia in uso");
		    }
		}

		@Override
		public boolean supports(Class<?> aClass) {
		    return Azienda.class.equals(aClass);
		}

	}

