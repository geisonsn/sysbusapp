package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class LocalizacaoLinhaInsertDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinha;
	
	private Long idVeiculo;

	private Long idUsuario;
	
	private String lotacaoVeiculo;

	private String status;

	private String latitude;

	private String longitude;

	private String dataHoraRegistro;
	
	public LocalizacaoLinhaInsertDTO() {}
	
	public LocalizacaoLinhaInsertDTO(String message) {
		setMessage(message);
	}
	
	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLotacaoVeiculo() {
		return lotacaoVeiculo;
	}

	public void setLotacaoVeiculo(String lotacaoVeiculo) {
		this.lotacaoVeiculo = lotacaoVeiculo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDataHoraRegistro() {
		return dataHoraRegistro;
	}

	public void setDataHoraRegistro(String dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}
}
