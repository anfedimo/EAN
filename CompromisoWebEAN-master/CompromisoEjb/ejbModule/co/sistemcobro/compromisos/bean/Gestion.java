package ean.compromisos.bean.;

import java.sql.Timestamp;

public class Gestion {
	
	private Integer consecutivoObligacion;
	private String referenciaPago;
	private Integer idUsuarioCrea;
	private Timestamp fechaCrea;
	private Timestamp fechaMod;
	private Integer estado;
	public Integer getConsecutivoObligacion() {
		return consecutivoObligacion;
	}
	public void setConsecutivoObligacion(Integer consecutivoObligacion) {
		this.consecutivoObligacion = consecutivoObligacion;
	}
	public String getReferenciaPago() {
		return referenciaPago;
	}
	public void setReferenciaPago(String referenciaPago) {
		this.referenciaPago = referenciaPago;
	}
	public Integer getIdUsuarioCrea() {
		return idUsuarioCrea;
	}
	public void setIdUsuarioCrea(Integer idUsuarioCrea) {
		this.idUsuarioCrea = idUsuarioCrea;
	}
	public Timestamp getFechaCrea() {
		return fechaCrea;
	}
	public void setFechaCrea(Timestamp fechaCrea) {
		this.fechaCrea = fechaCrea;
	}
	public Timestamp getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(Timestamp fechaMod) {
		this.fechaMod = fechaMod;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
	
	

}
