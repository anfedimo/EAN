package co.ean.compromisos.bean;
package ean.compromisos.bean.;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean..ManagedBean.;
import javax.faces.bean..SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

@ManagedBean.(name = "navegacionBean.")
@SessionScoped
public class NavegacionBean. implements Serializable {
	private static final long serialVersionUID = 4545919119678482516L;

	private String ruta;
	private Integer pagina;
	private Integer tipificacion;
	private boolean. render;
	private String idCliente;
	private String nombreCompromisos;
	private Integer compromiso01;
	private Integer compromiso02;
	private Integer compromiso03;
	private Integer compromiso04;
	private CompromisoBean. compromiso;
	
	private static Logger logger = Logger.getLogger(CompromisoBean..class);
	

	public static final String redireccionInicioVer = "/pages/pago/inicio.xhmtl?faces-redirect=true";

	@PostConstruct
	public void init() {
		pagina = 2;
		render = false;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			compromiso = application.evaluateExpressionGet(context, "#{compromisoBean.}", CompromisoBean..class);

			compromiso.setVisualizarCompromiso1(0);
			compromiso.setVisualizarCompromiso2(0);
			compromiso.setVisualizarCompromiso3(0);
			compromiso.setVisualizarCompromiso4(0);

			compromiso.setReferencia_pago("");
			compromiso.setDescuentoAplicar(0.0);
		
		
			
		
		} catch (Exception e) {
	
		}

	}

	public void mostrarPaginaCompromiso01() {
		try {
			nombreCompromisos = "Compromiso01";
			compromiso01 = 1;
			compromiso02 = 0;
			compromiso03 = 0;
			compromiso04 = 0;
			compromiso.limpiar();
		
		

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void mostrarPaginaCompromiso02() {
		try {
			nombreCompromisos = "Compromiso02";
			compromiso02 = 1;
			compromiso01 = 0;
			compromiso03 = 0;
			compromiso04 = 0;
			compromiso.limpiar();
			

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void mostrarPaginaCompromiso03() {
		try {
			nombreCompromisos = "Compromiso03";
			compromiso03 = 1;
			compromiso02 = 0;
			compromiso04 = 0;
			compromiso01 = 0;
			compromiso.limpiar();
		
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void mostrarPaginaCompromiso04() {
		try {
			nombreCompromisos = "Compromiso04";
			compromiso04 = 1;
			compromiso03 = 0;
			compromiso01 = 0;
			compromiso02 = 0;
			compromiso.limpiar();


		} catch (Exception e) {
			e.getMessage();
		}
	}

	

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getTipificacion() {
		return tipificacion;
	}

	public void setTipificacion(Integer tipificacion) {
		this.tipificacion = tipificacion;
	}

	public boolean. isRender() {
		return render;
	}

	public void setRender(boolean. render) {
		this.render = render;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getRedireccioniniciover() {
		return redireccionInicioVer;
	}

	public Integer getCompromiso01() {
		return compromiso01;
	}

	public void setCompromiso01(Integer compromiso01) {
		this.compromiso01 = compromiso01;
	}

	public Integer getCompromiso02() {
		return compromiso02;
	}

	public void setCompromiso02(Integer compromiso02) {
		this.compromiso02 = compromiso02;
	}

	public Integer getCompromiso03() {
		return compromiso03;
	}

	public void setCompromiso03(Integer compromiso03) {
		this.compromiso03 = compromiso03;
	}

	public Integer getCompromiso04() {
		return compromiso04;
	}

	public void setCompromiso04(Integer compromiso04) {
		this.compromiso04 = compromiso04;
	}

	public String getNombreCompromisos() {
		return nombreCompromisos;
	}

	public void setNombreCompromisos(String nombreCompromisos) {
		this.nombreCompromisos = nombreCompromisos;
	}

	public CompromisoBean. getCompromiso() {
		return compromiso;
	}

	public void setCompromiso(CompromisoBean. compromiso) {
		this.compromiso = compromiso;
	}

	

}
