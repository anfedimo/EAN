package ean.compromisos.bean.;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Grupo {
	
	 private String idgrupo;
	 private String nombre;
	 private String estado;
	 private String estadob;
	 private String iddirectivared;
	 private GrupoTipo grupotipo;
	 private String codgrupo;
	 private String idgrupotipo;
	 
	 public Grupo(){
	 }

	public String getIdgrupo() {
		return idgrupo;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getIddirectivared() {
		return iddirectivared;
	}

	public void setIddirectivared(String iddirectivared) {
		this.iddirectivared = iddirectivared;
	}

	public GrupoTipo getGrupotipo() {
		return grupotipo;
	}

	public void setGrupotipo(GrupoTipo grupotipo) {
		this.grupotipo = grupotipo;
	}

	public String getCodgrupo() {
		return codgrupo;
	}

	public void setCodgrupo(String codgrupo) {
		this.codgrupo = codgrupo;
	}

	public String getIdgrupotipo() {
		return idgrupotipo;
	}

	public void setIdgrupotipo(String idgrupotipo) {
		this.idgrupotipo = idgrupotipo;
	}
}
