package ean.compromisos.bean.;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Aplicacion {
	
	private String nombreexterno;
	private String idaplicacion;
	private String nombre;
	private String directivaacceso;
	private String linkproduccion;
	private String icono;
	private String favicon;
	private String linkpublico;
	private String estado;
	private String estadob;
	
	
	public Aplicacion(){
		
	}

	public String getNombreexterno() {
		return nombreexterno;
	}


	public void setNombreexterno(String nombreexterno) {
		this.nombreexterno = nombreexterno;
	}


	public String getIdaplicacion() {
		return idaplicacion;
	}


	public void setIdaplicacion(String idaplicacion) {
		this.idaplicacion = idaplicacion;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDirectivaacceso() {
		return directivaacceso;
	}


	public void setDirectivaacceso(String directivaacceso) {
		this.directivaacceso = directivaacceso;
	}


	public String getLinkproduccion() {
		return linkproduccion;
	}


	public void setLinkproduccion(String linkproduccion) {
		this.linkproduccion = linkproduccion;
	}


	public String getIcono() {
		return icono;
	}


	public void setIcono(String icono) {
		this.icono = icono;
	}


	public String getFavicon() {
		return favicon;
	}


	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}


	public String getLinkpublico() {
		return linkpublico;
	}


	public void setLinkpublico(String linkpublico) {
		this.linkpublico = linkpublico;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getEstadob() {
		return estadob;
	}


	public void setEstadob(String estadob) {
		this.estadob = estadob;
	}
}
