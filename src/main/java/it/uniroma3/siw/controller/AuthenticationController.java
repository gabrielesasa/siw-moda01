
package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Azienda;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	@Autowired 
	private CredentialsRepository credentialsRepository;
	@Autowired 
	private CredentialsService credentialsService;
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private UserService userService;
	
	@GetMapping("/formRegistrazione")
	public String getRegistrazione(Model model) {
		model.addAttribute("user",new User());
		model.addAttribute("credentials",new Credentials());
		return "/formRegistrazione.html";
		}
	@PostMapping("/registrazione")
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 Model model) {

        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB 
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            userService.saveUser(user);
            model.addAttribute("user", user);
            model.addAttribute("credentials", credentials);
           
           if(user.isSonoazienda()) {
            	credentialsService.saveCredentialsAzienda(credentials);
            	model.addAttribute("azienda", new Azienda());
            	return "formCreaAzienda.html";
            }
            return "index.html";
    }
	@GetMapping("/login") 
	public String showLoginForm (Model model) {
		return "/formLogin";
	}
	@GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
    	else if(credentials.getRole().equals(Credentials.AZIENDA_ROLE)) {
                return "azienda/indexAzienda.html";
    	}
    	else if(credentials.getRole().equals(Credentials.STUDENTE_ROLE)) {
            return "studente/indexStudente.html";
	}
        return "/index.html";
    }
	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
			else if(credentials.getRole().equals(Credentials.AZIENDA_ROLE)) {
                return "azienda/indexAzienda.html";
    	}
		}
        return "index.html";
	}
}
