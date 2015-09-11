package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class OrigemReclamacaoDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idOrigemReclamacao;
	
	private Long idTipoReclamacao;
	
	private String objetoReclamado;
	
	private String descricaoTipoReclamacao;
	
	public OrigemReclamacaoDTO() {}

	public OrigemReclamacaoDTO(String message) {
		setMessage(message);
	}

	public Long getIdOrigemReclamacao() {
		return idOrigemReclamacao;
	}

	public void setIdOrigemReclamacao(Long idOrigemReclamacao) {
		this.idOrigemReclamacao = idOrigemReclamacao;
	}

	public Long getIdTipoReclamacao() {
		return idTipoReclamacao;
	}

	public void setIdTipoReclamacao(Long idTipoReclamacao) {
		this.idTipoReclamacao = idTipoReclamacao;
	}

	public String getObjetoReclamado() {
		return objetoReclamado;
	}

	public void setObjetoReclamado(String objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}

	public String getDescricaoTipoReclamacao() {
		return descricaoTipoReclamacao;
	}

	public void setDescricaoTipoReclamacao(String descricaoTipoReclamacao) {
		this.descricaoTipoReclamacao = descricaoTipoReclamacao;
	}

	@Override
	public String toString() {
		return this.getDescricaoTipoReclamacao();
	}
}
