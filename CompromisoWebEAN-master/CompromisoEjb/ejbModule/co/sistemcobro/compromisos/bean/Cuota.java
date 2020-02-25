package ean.compromisos.bean.;

import java.math.BigDecimal;
import java.util.Date;

public class Cuota {

	private int id;
	private BigDecimal valorCuota;
	private Date fechaPago;
	private BigDecimal saldo;
	private int numeroCuota;
	private Date fechaCuota;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public int getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(int numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public Date getFechaCuota() {
		return fechaCuota;
	}

	public void setFechaCuota(Date fechaCuota) {
		this.fechaCuota = fechaCuota;
	}

}
