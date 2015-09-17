package br.com.gsn.sysbusapp.model;

import java.io.Serializable;
import java.util.Date;

import br.com.gsn.sysbusapp.util.Dates;

public class LocalizacaoLinhaDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinha;
	
	private Long idVeiculo;

	private String numeroLinha;

	private String numeroRegistro;

	private String latitude;

	private String longitude;

	private String dataHoraRegistro;
	
	private String distancia;
	
	public LocalizacaoLinhaDTO() {}
	
	public LocalizacaoLinhaDTO(String numeroLinha, String numeroRegistro,
			String latitude, String longitude, Date dataHoraRegistro) {
		this(numeroLinha, numeroRegistro, latitude, longitude, dataHoraRegistro, null);
	}
	
	public LocalizacaoLinhaDTO(String numeroLinha, String numeroRegistro,
			String latitude, String longitude, Date dataHoraRegistro, String distancia) {
		this.numeroLinha = numeroLinha;
		this.numeroRegistro = numeroRegistro;
		this.latitude = latitude;
		this.longitude = longitude;
		this.dataHoraRegistro = Dates.format(dataHoraRegistro, Dates.FORMAT_PT_BR_DATE_HOUR);
		this.distancia = distancia;
	}

	public LocalizacaoLinhaDTO(String message) {
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

	public String getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(String numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
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

	public String getDistancia() {
		return distancia;
	}

	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}
}
