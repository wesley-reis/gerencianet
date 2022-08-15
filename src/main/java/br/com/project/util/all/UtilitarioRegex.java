package br.com.project.util.all;

public class UtilitarioRegex {
	
	public String retiraAcentos(String string) {
		String aux = new String(string);
		aux = aux.replaceAll("[àâáäãÁÀÂÄ]", "a");
		aux = aux.replaceAll("[èéêëÈÉÊË]", "e");
		aux = aux.replaceAll("[ïîíìÏÎÍÌ]", "i");
		aux = aux.replaceAll("[óòôöõÓÒÔÖ]", "o");		
		aux = aux.replaceAll("[ûùüúÛÚÙÜ]", "u");
		
		return aux;
	}

}
