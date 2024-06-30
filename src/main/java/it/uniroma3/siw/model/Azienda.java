package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Azienda {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String indirizzo;
	private Long telefono;
	private String email;
	@OneToMany(mappedBy="aziend2")
	private List<OffertaLavoro> pubblicate;
	private String immagine;
	@OneToOne
	private User user;
	@OneToMany(mappedBy="azienda")
	private List<OffertaLavoro> offerte;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<OffertaLavoro> getPubblicate() {
		return pubblicate;
	}
	public void setPubblicate(List<OffertaLavoro> pubblicate) {
		this.pubblicate = pubblicate;
	}
	public List<OffertaLavoro> getOfferte() {
		return offerte;
	}
	public void setOfferte(List<OffertaLavoro> offerte) {
		this.offerte = offerte;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getImmagine() {
		return immagine;
	}
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public Long getTelefono() {
		return telefono;
	}
	public void setTelefono(Long telefono) {
		this.telefono = telefono;
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
		Azienda other = (Azienda) obj;
		return Objects.equals(id, other.id);
	}
	
}
