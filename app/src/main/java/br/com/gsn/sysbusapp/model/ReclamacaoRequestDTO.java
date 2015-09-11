package br.com.gsn.sysbusapp.model;

import java.io.Serializable;

public class ReclamacaoRequestDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String descricao;
	
	private String objetoReclamado;
	
	private Long idOrigemReclamacao;
	
	private Long idLinha;
	
	private String placa;
	
	private String dataOcorrencia;
	
	private String hora;
	
	private String dataHoraRegistro;

	public ReclamacaoRequestDTO(){}
	
	public ReclamacaoRequestDTO(String message) {
		super.setMessage(message);
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObjetoReclamado() {
		return objetoReclamado;
	}

	public void setObjetoReclamado(String objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}

	public Long getIdOrigemReclamacao() {
		return idOrigemReclamacao;
	}

	public void setIdOrigemReclamacao(Long idOrigemReclamacao) {
		this.idOrigemReclamacao = idOrigemReclamacao;
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(String dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDataHoraRegistro() {
		return dataHoraRegistro;
	}

	public void setDataHoraRegistro(String dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}
	
	

}
