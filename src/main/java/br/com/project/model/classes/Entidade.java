package br.com.project.model.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;



@Audited
@Entity
public class Entidade implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long ent_codigo;
	
	private String ent_login = null;
	
	private String ent_senha;
	
	private boolean ent_inativo = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ent_ultimoacesso;
	
	
	
	public Date getEnt_ultimoacesso() {
		return ent_ultimoacesso;
	}

	public void setEnt_ultimoacesso(Date ent_ultimoacesso) {
		this.ent_ultimoacesso = ent_ultimoacesso;
	}

	public boolean getEnt_inativo() {
		return ent_inativo;
	}

	public void setEnt_inativo(boolean ent_inativo) {
		this.ent_inativo = ent_inativo;
	}

	public Long getEnt_codigo() {
		return ent_codigo;
	}

	public void setEnt_codigo(Long ent_codigo) {
		this.ent_codigo = ent_codigo;
	}

	public String getEnt_login() {
		return ent_login;
	}

	public void setEnt_login(String ent_login) {
		this.ent_login = ent_login;
	}

	public String getEnt_senha() {
		return ent_senha;
	}

	public void setEnt_senha(String ent_senha) {
		this.ent_senha = ent_senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ent_codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidade other = (Entidade) obj;
		return Objects.equals(ent_codigo, other.ent_codigo);
	}


	


}
