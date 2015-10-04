package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class ReclamacaoPorLinhaDTO extends InformationRequest implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private String reclamado;

	private String tipoReclamacao;

	private Long totalReclamacoes;

	public ReclamacaoPorLinhaDTO() {
	}

	public ReclamacaoPorLinhaDTO(String message) {
		super.setMessage(message);
	}

	public ReclamacaoPorLinhaDTO(String reclamado, String tipoReclamacao,
			Long totalReclamacoes) {
		super();
		this.reclamado = reclamado;
		this.tipoReclamacao = tipoReclamacao;
		this.totalReclamacoes = totalReclamacoes;
	}

	public String getReclamado() {
		return reclamado;
	}

	public void setReclamado(String reclamado) {
		this.reclamado = reclamado;
	}

	public String getTipoReclamacao() {
		return tipoReclamacao;
	}

	public void setTipoReclamacao(String tipoReclamacao) {
		this.tipoReclamacao = tipoReclamacao;
	}

	public Long getTotalReclamacoes() {
		return totalReclamacoes;
	}

	public void setTotalReclamacoes(Long totalReclamacoes) {
		this.totalReclamacoes = totalReclamacoes;
	}
}
