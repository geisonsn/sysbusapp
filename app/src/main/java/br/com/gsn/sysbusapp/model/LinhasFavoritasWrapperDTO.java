package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class LinhasFavoritasWrapperDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer intervalo;
	
	private LinhaFavoritaDTO[] linhasFavoritas;

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public LinhaFavoritaDTO[] getLinhasFavoritas() {
		return linhasFavoritas;
	}

	public void setLinhasFavoritas(LinhaFavoritaDTO[] linhasFavoritas) {
		this.linhasFavoritas = linhasFavoritas;
	}
}
