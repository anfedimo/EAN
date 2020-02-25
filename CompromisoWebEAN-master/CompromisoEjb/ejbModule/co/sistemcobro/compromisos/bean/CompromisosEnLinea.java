package ean.compromisos.bean.;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;

public class CompromisosEnLinea {

	private String consecutivo;
	private String email;
	private String nombre;
	private String documento;
	private String referencia_pago;
	private BigDecimal saldo;
	private BigDecimal interes_mora;
	private BigDecimal honorarios;
	private BigDecimal valor_pactado;
	private BigDecimal obligacion_total;
	private BigDecimal intereses_mora_descuento;
	private BigDecimal honorarios_descuento;
	private String fecha_gestion;
	private Timestamp fecha_actualizacion;
	private Timestamp fecha_compromiso;
	private Timestamp fecha_actualizo;
	private Integer idUsuarioCrea;
	private Timestamp fechaCrea;
	private Timestamp fechaMod;
	private Integer estado;
	private String nombreUsuario;
	private String saldo_s;
	private String interes_mora_s;
	private String honorarios_s;
	private String valor_pactado_s;

	private int diasMora;
	
	
	

	public String getConsecutivo() {
		return consecutivo;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getReferencia_pago() {
		return referencia_pago;
	}

	public void setReferencia_pago(String referencia_pago) {
		this.referencia_pago = referencia_pago;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getInteres_mora() {
		return interes_mora;
	}

	public void setInteres_mora(BigDecimal interes_mora) {
		this.interes_mora = interes_mora;
	}

	public BigDecimal getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(BigDecimal honorarios) {
		this.honorarios = honorarios;
	}

	public BigDecimal getValor_pactado() {
		return valor_pactado;
	}

	public void setValor_pactado(BigDecimal valor_pactado) {
		this.valor_pactado = valor_pactado;
	}

	public Timestamp getFecha_actualizacion() {
		return fecha_actualizacion;
	}

	public void setFecha_actualizacion(Timestamp fecha_actualizacion) {
		this.fecha_actualizacion = fecha_actualizacion;
	}

	public Timestamp getFecha_compromiso() {
		return fecha_compromiso;
	}

	public void setFecha_compromiso(Timestamp fecha_compromiso) {
		this.fecha_compromiso = fecha_compromiso;
	}

	public Timestamp getFecha_actualizo() {
		return fecha_actualizo;
	}

	public void setFecha_actualizo(Timestamp fecha_actualizo) {
		this.fecha_actualizo = fecha_actualizo;
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getFecha_gestion() {
		return fecha_gestion;
	}

	public void setFecha_gestion(String fecha_gestion) {
		this.fecha_gestion = fecha_gestion;
	}

	public int getDiasMora() {
		return diasMora;
	}

	public void setDiasMora(int diasMora) {
		this.diasMora = diasMora;
	}

	public BigDecimal getObligacion_total() {
		return obligacion_total;
	}

	public void setObligacion_total(BigDecimal obligacion_total) {
		this.obligacion_total = obligacion_total;
	}

	public BigDecimal getIntereses_mora_descuento() {
		return intereses_mora_descuento;
	}

	public void setIntereses_mora_descuento(BigDecimal intereses_mora_descuento) {
		this.intereses_mora_descuento = intereses_mora_descuento;
	}

	public BigDecimal getHonorarios_descuento() {
		return honorarios_descuento;
	}

	public void setHonorarios_descuento(BigDecimal honorarios_descuento) {
		this.honorarios_descuento = honorarios_descuento;
	}

	public String getSaldo_s() {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		return format.format(saldo);

	}

	public void setSaldo_s(String saldo_s) {
		this.saldo_s = saldo_s;
	}

	public String getInteres_mora_s() {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		return format.format(interes_mora);

	}

	public void setInteres_mora_s(String interes_mora_s) {
		this.interes_mora_s = interes_mora_s;
	}

	public String getHonorarios_s() {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		return format.format(honorarios);

	}

	public void setHonorarios_s(String honorarios_s) {
		this.honorarios_s = honorarios_s;
	}

	public String getValor_pactado_s() {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		return format.format(valor_pactado);

	}

	public void setValor_pactado_s(String valor_pactado_s) {
		this.valor_pactado_s = valor_pactado_s;
	}

		
}
