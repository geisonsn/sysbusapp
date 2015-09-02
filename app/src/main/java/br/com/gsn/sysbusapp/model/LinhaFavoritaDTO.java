package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class LinhaFavoritaDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinha;
	
	private String numeroLinha;

	private Long idUsuario;
	
	public LinhaFavoritaDTO() {}
	
	public LinhaFavoritaDTO(String message) {
		setMessage(message);
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	
}
