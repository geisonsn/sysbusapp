package br.com.gsn.sysbusapp.model;

import java.io.Serializable;
import java.util.List;


public class SincronizarFavoritoDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	
	private List<LinhaFavoritaDTO> linhasFavoritas;

	public SincronizarFavoritoDTO(){}
	
	public SincronizarFavoritoDTO(String message){
		setMessage(message);
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<LinhaFavoritaDTO> getLinhasFavoritas() {
		return linhasFavoritas;
	}

	public void setLinhasFavoritas(List<LinhaFavoritaDTO> linhasFavoritas) {
		this.linhasFavoritas = linhasFavoritas;
	}
}

