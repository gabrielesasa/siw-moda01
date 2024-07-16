package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OffertaLavoro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titolo;
	private String tipologia;
	private Long stipendio;
	private String requisiti;

	@ManyToOne
	private Azienda azienda;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
		public Azienda getAzienda() {
		return azienda;
	}
	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public Long getStipendio() {
		return stipendio;
	}
	public void setStipendio(Long stipendio) {
		this.stipendio = stipendio;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getRequisiti() {
		return requisiti;
	}
	public void setRequisiti(String requisiti) {
		this.requisiti = requisiti;
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
		OffertaLavoro other = (OffertaLavoro) obj;
		return Objects.equals(id, other.id);
	}
	
}
