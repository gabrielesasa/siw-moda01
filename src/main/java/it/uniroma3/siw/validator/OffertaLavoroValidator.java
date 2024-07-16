package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.OffertaLavoro;
import it.uniroma3.siw.repository.OffertaLavoroRepository;
@Component
public class OffertaLavoroValidator implements Validator {
		@Autowired
		private OffertaLavoroRepository offertaLavoroRepository;
		@Override
		public void validate(Object o, Errors errors) {
			OffertaLavoro offertaLavoro = (OffertaLavoro)o;
		    if (offertaLavoro.getTitolo() != null && offertaLavoroRepository.existsByTitolo(offertaLavoro.getTitolo())) {
		        errors.reject("cuoco.duplicate");
		    }
		}

		@Override
		public boolean supports(Class<?> aClass) {
		    return OffertaLavoro.class.equals(aClass);
		}

	}

