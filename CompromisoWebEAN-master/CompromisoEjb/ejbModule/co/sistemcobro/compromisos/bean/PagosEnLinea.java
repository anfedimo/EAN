package ean.compromisos.bean.;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PagosEnLinea {

	private Integer idPago;
	private Timestamp fecha;
	private String cuenta;
	private String numeroTarjeta;
	private Timestamp fechaVencimiento;
	private String fechaVencimientoString;
	private String bancoEmisor;
	private BigDecimal monto;
	private String correo;
	private Integer idUsuarioCrea;
	private Timestamp fechaCrea;
	private Integer idUsuarioMod;
	private Timestamp fechaMod;
	private Integer estado;	
	private String nombreUsuario;
		
	public Integer getIdPago() {
		return idPago;
	}
	public void setIdPago(Integer idPago) {
		this.idPago = idPago;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public Timestamp getFechaVencimiento() {
		return fechaVencimiento;
	}
	public void setFechaVencimiento(Timestamp fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	public String getBancoEmisor() {
		return bancoEmisor;
	}
	public void setBancoEmisor(String bancoEmisor) {
		this.bancoEmisor = bancoEmisor;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
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
	public Integer getIdUsuarioMod() {
		return idUsuarioMod;
	}
	public void setIdUsuarioMod(Integer idUsuarioMod) {
		this.idUsuarioMod = idUsuarioMod;
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
	public String getFechaVencimientoString() {
		return fechaVencimientoString;
	}
	public void setFechaVencimientoString(String fechaVencimientoString) {
		this.fechaVencimientoString = fechaVencimientoString;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	

}
