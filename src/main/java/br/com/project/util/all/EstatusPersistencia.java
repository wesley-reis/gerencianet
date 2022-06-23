package br.com.project.util.all;

public enum EstatusPersistencia {
	
	ERROR("Erro"), SUCESSO("Sucesso"),OBJETO_REFERENCIADO("Esse registro n�o pode ser apagado por possuir refer�ncias ao mesmo.");

	private String name;

	private EstatusPersistencia(String s) {
		this.name = s;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
