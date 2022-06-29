package br.com.project.model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.project.annotation.IdentificaCampoPesquisa;

@Audited
@Entity
@Table(name = "estado")
@SequenceGenerator(name = "estado_seq", sequenceName = "estado_seq", allocationSize = 1, initialValue = 1)
public class Estado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@IdentificaCampoPesquisa(descricaoCampo = "Código", campoConsulta = "est_id")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estado_seq")
	private Long est_id;
	
	@Column(length = 10, nullable = true)
	private Long est_uf;
	
	@IdentificaCampoPesquisa(descricaoCampo = "Nome", campoConsulta = "est_nome", principal = 1)
	@Column(length = 100, nullable = false)
	private Long est_nome;
	
	
	@NotAudited
	@OneToMany(mappedBy = "estado", orphanRemoval = false)
	@Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<Cidade> cidades = new ArrayList<Cidade>();
	
	@Basic
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pais")
	@ForeignKey(name = "pais_fk")
	private Pais pais = new Pais();
	
	@Version
	@Column(name = "versionNum")
	private int versionNum;

	public Long getEst_id() {
		return est_id;
	}

	public void setEst_id(Long est_id) {
		this.est_id = est_id;
	}

	public Long getEst_uf() {
		return est_uf;
	}

	public void setEst_uf(Long est_uf) {
		this.est_uf = est_uf;
	}

	public Long getEst_nome() {
		return est_nome;
	}

	public void setEst_nome(Long est_nome) {
		this.est_nome = est_nome;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public int getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}

	@Override
	public int hashCode() {
		return Objects.hash(est_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return Objects.equals(est_id, other.est_id);
	}
	
	
}
