package ean.compromisos.bean.;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UsuarioAplicacion {
	
	private String idaplicacion;
	private String idusuario;
	private String usuario;
	private String idgrupo;
	private String idusuariocrea;
	private String fechacrea;
	private String estado;
	private String estadob;
	private String idusuariomod;
	private String fechamod;
	private Grupo grupo;
	private String iddirectivared;
	private Aplicacion aplicacion;
	
	public UsuarioAplicacion (){
		
	}

	public String getIdaplicacion() {
		return idaplicacion;
	}

	public void setIdaplicacion(String idaplicacion) {
		this.idaplicacion = idaplicacion;
	}

	public String getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getIdusuariocrea() {
		return idusuariocrea;
	}

	public void setIdusuariocrea(String idusuariocrea) {
		this.idusuariocrea = idusuariocrea;
	}

	public String getFechacrea() {
		return fechacrea;
	}

	public void setFechacrea(String fechacrea) {
		this.fechacrea = fechacrea;
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

	public String getIdusuariomod() {
		return idusuariomod;
	}

	public void setIdusuariomod(String idusuariomod) {
		this.idusuariomod = idusuariomod;
	}

	public String getFechamod() {
		return fechamod;
	}

	public void setFechamod(String fechamod) {
		this.fechamod = fechamod;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getIddirectivared() {
		return iddirectivared;
	}

	public void setIddirectivared(String iddirectivared) {
		this.iddirectivared = iddirectivared;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}
}
