package it.uniroma3.siw.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Studente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@NotNull
	private Integer matricola;
	@Email
	private String email;
	@Min(1980)
	@Max(2004)
	private Integer year;
	 @ElementCollection
	    @CollectionTable(name = "esami", joinColumns = @JoinColumn(name = "studente_id"))
	    @MapKeyColumn(name = "nome_esame")
	    @Column(name = "voto_esame")
	    private Map<String, Integer> esami = new HashMap<>();
	@OneToOne
	private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getMatricola() {
		return matricola;
	}
	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}
	public Map<String, Integer> getEsami() {
		return esami;
	}
	public void setEsami(Map<String, Integer> esami) {
		this.esami = esami;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		return Objects.equals(id, other.id);
	}
	
}
