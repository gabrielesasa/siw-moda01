package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.repository.StudenteRepository;
@Component
public class StudenteValidator implements Validator {
		@Autowired
		private StudenteRepository studenteRepository;
		@Override
		public void validate(Object o, Errors errors) {
			Studente studente = (Studente)o;
		    if (studente.getNome() != null && studente.getCognome() != null
		        && studenteRepository.existsByNomeAndCognome(studente.getNome(), studente.getCognome())) {
		        errors.reject("studente.duplicate","questo studente gia esiste");
		    }
		}

		@Override
		public boolean supports(Class<?> aClass) {
		    return Studente.class.equals(aClass);
		}

	}

