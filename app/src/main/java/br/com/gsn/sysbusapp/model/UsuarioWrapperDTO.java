package br.com.gsn.sysbusapp.model;

import java.io.Serializable;
import java.util.List;


public class UsuarioWrapperDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioDTO usuario;
	
	private List<LinhaFavoritaDTO> linhasFavoritas;

	public UsuarioWrapperDTO(){}
	
	public UsuarioWrapperDTO(String message){
		setMessage(message);
	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public List<LinhaFavoritaDTO> getLinhasFavoritas() {
		return linhasFavoritas;
	}

	public void setLinhasFavoritas(List<LinhaFavoritaDTO> linhasFavoritas) {
		this.linhasFavoritas = linhasFavoritas;
	}
}

