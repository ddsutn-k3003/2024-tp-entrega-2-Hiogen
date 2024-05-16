package ar.edu.utn.dds.k3003.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import ar.edu.utn.dds.k3003.facades.dtos.EstadoViandaEnum;

@AllArgsConstructor
@Getter
public class Vianda {

	private Long id;
	
	private String qr;
	private Long colaboradorId;
	private Integer heladeraId;
	private EstadoViandaEnum estado;
	private LocalDateTime fechaElaboracion;
    public Vianda(String qr, LocalDateTime fechaElaboracion, EstadoViandaEnum estado, Long colaboradorId, Integer heladeraId) {
    	this.qr = qr;
    	this.colaboradorId = colaboradorId;
    	this.heladeraId = heladeraId;
    	this.estado = estado;
    	this.fechaElaboracion = LocalDateTime.now();
    }
    
    public void setEstado(EstadoViandaEnum nuevoEstado) {
		this.estado = nuevoEstado;
	}
    
    public void setId(Long nuevoId) {
		this.id = nuevoId;
	}
    public void setHeladeraId(Integer heladeraDestino) {
		this.heladeraId = heladeraDestino;
	}

	public Long getId() {
		return id;
	}
	public String getQr() {
		return qr;
	}
	public Long getColaboradorId() {
		return colaboradorId;
	}
	public Integer getHeladeraId() {
		return heladeraId;
	}
	public EstadoViandaEnum getEstado() {
		return estado;
	}
	public LocalDateTime getFechaElaboracion() {
		return fechaElaboracion;
	}

	

}