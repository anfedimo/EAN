package ean.compromisos.bean.;

import java.util.List;

public class Lista {
	private List<CompromisosEnLinea>  compromisosEnLinea;

	private CompromisosEnLinea compromisos;
	
	
	public List<CompromisosEnLinea> getCompromisosEnLinea() {
		return compromisosEnLinea;
	}

	public void setCompromisosEnLinea(List<CompromisosEnLinea> compromisosEnLinea) {
		this.compromisosEnLinea = compromisosEnLinea;
	}

	public CompromisosEnLinea getCompromisos() {
		return compromisos;
	}

	public void setCompromisos(CompromisosEnLinea compromisos) {
		this.compromisos = compromisos;
	} 
	
	
}
