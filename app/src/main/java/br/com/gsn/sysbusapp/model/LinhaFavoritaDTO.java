package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class LinhaFavoritaDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinhaFavorita;
	
	private Long idLinha;
	
	private String numeroLinha;
	
	private String descricaoLinha;
	
	private String empresaLinha;

	private Long idUsuario;
	
	private Integer totalVeiculosEmDeslocamento;
	
	public LinhaFavoritaDTO() {}
	
	public LinhaFavoritaDTO(String message) {
		setMessage(message);
	}
	
	public Long getIdLinhaFavorita() {
		return idLinhaFavorita;
	}

	public void setIdLinhaFavorita(Long idLinhaFavorita) {
		this.idLinhaFavorita = idLinhaFavorita;
	}
	
	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public String getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(String numeroLinha) {
		this.numeroLinha = numeroLinha;
	}
	
	public String getDescricaoLinha() {
		return descricaoLinha;
	}

	public void setDescricaoLinha(String descricaoLinha) {
		this.descricaoLinha = descricaoLinha;
	}

	public String getEmpresaLinha() {
		return empresaLinha;
	}

	public void setEmpresaLinha(String empresaLinha) {
		this.empresaLinha = empresaLinha;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getTotalVeiculosEmDeslocamento() {
		return totalVeiculosEmDeslocamento;
	}

	public void setTotalVeiculosEmDeslocamento(Integer totalVeiculosEmDeslocamento) {
		this.totalVeiculosEmDeslocamento = totalVeiculosEmDeslocamento;
	}
}
