package co.ean.compromisos.bean;
package ean.compromisos.bean.;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean..ManagedBean.;
import javax.faces.bean..SessionScoped;
import javax.faces.bean..ViewScoped;
import javax.faces.context.FacesContext;

import ean.compromisos.bean..Cuota;
import ean.compromisos.constante.Constante;

import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import co.sistemcobro.compromisos.bean.CompromisosEnLinea;
import co.sistemcobro.compromisos.bean.Cuota;
import co.sistemcobro.compromisos.bean.Gestion;
import co.sistemcobro.compromisos.bean.Lista;

import org.apache.log4j.Logger;

import ean.compromisos.ejb.ICompromisosEnLineaEJBLocal;
import ean.compromisos.session.LoginBean.;
import ean.compromisos.util.JasperReportUtil;
import ean.compromisos.util.NumeroLetras;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@ManagedBean.(name = "compromisoBean.")
@SessionScoped
public class CompromisoBean. implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ICompromisosEnLineaEJBLocal compromisoEJB;
	private CompromisosEnLinea compromiso;
	private LoginBean. loginBean.;

	private List<CompromisosEnLinea> listaCompromisos;
	private List<CompromisosEnLinea> selectedCompromisos;
	private List<CompromisosEnLinea> compromisosFiltro;

	private List<CompromisosEnLinea> numerosTarjetas;
	private Lista lista;

	private String consecutivos;
	private String email;
	private String nombre;
	private String documento;
	private String referencia_pago;
	private BigDecimal saldo;
	private BigDecimal interes_mora;
	private BigDecimal honorarios;
	private BigDecimal valor_pactado;
	private Date fecha_gestion;
	private Date fecha_actualizacion;
	private Date fecha_compromiso;
	private Date fecha_cuota;

	private Integer cantCuotas = 0;

	private Integer visualizarCompromiso1;
	private Integer visualizarCompromiso2;
	private Integer visualizarCompromiso3;
	private Integer visualizarCompromiso4;

	private List<Cuota> cuotas;
	private String mensajeError;

	private byte[] reportPdf;
	private StreamedContent media;
	private ByteArrayOutputStream outputStream;
	private StreamedContent file2;

	private String idAcuerdo;
	private Double descuentoAplicar;

	private static Logger logger = Logger.getLogger(CompromisoBean..class);

	private Integer idUsuarioCrea;
	private Integer ultimoValor;

	private Boolean. existe;
	private Boolean. existe2;
	private Boolean. existe3;
	private Boolean. existe4;
	private Boolean. cuota3;
	private Boolean. cuota4;

	@PostConstruct
	public void init() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Application application = context.getApplication();
			this.setLoginBean.(application.evaluateExpressionGet(context, "#{loginBean.}", LoginBean..class));
			visualizarCompromiso1 = 0;
			visualizarCompromiso2 = 0;
			visualizarCompromiso3 = 0;
			visualizarCompromiso4 = 0;

			referencia_pago = ("");
			
			consecutivos = ("");
			email = ("");
			descuentoAplicar = (0.0);
			fecha_cuota = new Date();
			cantCuotas = new Integer(0);

			existe = false;
			existe2 = false;
			existe3 = false;
			existe4 = false;
			cuota3 = false;
			cuota4 = false;

			idUsuarioCrea = Integer.parseInt(loginBean..getUsuarioHermes().getCodusuario());

		} catch (Exception e) {
			// logger.error("se presento un error mètodo contructor aqui... " +
			// e.getMessage(), e);
		} finally {

		}
	}

	/**
	 * Calculador. método donde se encargar de calcular el interes mora. 
	 * 2020-02-06
	 * 
	 * @author Andres Diaz
	 * @param campos provenientes de xhtml
	 * @throws Exception si hay valores en null
	 * @return
	 * @see CompromisosEnLineaEJB EJB
	 */
	public void calcularDescuento(Double descuentoAplicar) {
		Double descuento = 1 - (descuentoAplicar / 100);
		System.out.println("descuento: " + descuento);

		BigDecimal saldoMora = lista.getCompromisosEnLinea().get(0).getSaldo();
		BigDecimal interesesMora = lista.getCompromisosEnLinea().get(0).getInteres_mora();
		BigDecimal interesesMoraDescuento = interesesMora.multiply(new BigDecimal(descuento));
		BigDecimal saldoParcial = saldoMora.add(interesesMoraDescuento);
		BigDecimal honorariosDescuento = saldoParcial.multiply(new BigDecimal(0.15));

		lista.getCompromisosEnLinea().get(0)
				.setIntereses_mora_descuento(interesesMoraDescuento.setScale(0, RoundingMode.HALF_DOWN));
		lista.getCompromisosEnLinea().get(0)
				.setHonorarios_descuento(honorariosDescuento.setScale(0, RoundingMode.HALF_DOWN));
		lista.getCompromisosEnLinea().get(0)
				.setValor_pactado(saldoParcial.add(honorariosDescuento).setScale(0, RoundingMode.HALF_DOWN));

	}

	/**
	 * generateReport. método donde se generan los parametros para traer pasar
	 * los datos al report . 2020-02-07
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see CompromisosEnLineaEJB EJB
	 */
	@SuppressWarnings("deprecation")
	public void generateReport() throws Exception {
		Gestion gestion = new Gestion();
		try {

			this.limpiar();
			visualizarCompromiso1 = 1;

			ultimoValor = compromisoEJB.ultimoId();
			int consecutivo = ultimoValor;
			
			logger.info("el consecutivo es... " + consecutivo);

			String path = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/cartacompromiso01.jasper");
			logger.info("el path del compromiso01... " + path);
			

			String logoean. = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_ean.);

			logger.info("el logo de ean. esta en... " + logoean.);
			String ANFEDIMO = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_SISTEM);
			
			logger.info("el logo de ANFEDIMO esta en... " + ANFEDIMO);
			
			Map<String, Object> parametro = new HashMap<String, Object>();

			byte[] bites = null;

			lista = new Lista();

			CompromisosEnLinea compromiso = new CompromisosEnLinea();
			NumeroLetras convert = new NumeroLetras();

			compromiso.setReferencia_pago(referencia_pago);

			lista.setCompromisosEnLinea(compromisoEJB.buscarCompromiso(compromiso));
			
			logger.info("la lista es " + lista);
			if (!lista.getCompromisosEnLinea().isEmpty()) {
				logger.info("entro a !lista.getCompromisosEnLinea().isEmpty()");
				// parametros que enviamos al report.
				parametro.put("Email", email);
				parametro.put("Nombre", lista.getCompromisosEnLinea().get(0).getNombre());
				parametro.put("Cedula", lista.getCompromisosEnLinea().get(0).getDocumento());
				parametro.put("Referencia", referencia_pago);
				parametro.put("Obligacion_Total", lista.getCompromisosEnLinea().get(0).getObligacion_total());
				parametro.put("Obligacion_Total_Letra", convert.cantidadConLetra(lista.getCompromisosEnLinea().get(0)
						.getObligacion_total().setScale(0, RoundingMode.HALF_DOWN).toString()).toUpperCase());
				parametro.put("Obligacion_Total_Pesos", new java.text.DecimalFormat("$ #,##0.00")
						.format(lista.getCompromisosEnLinea().get(0).getObligacion_total()));
				parametro.put("Fecha_Compromiso", lista.getCompromisosEnLinea().get(0).getFecha_compromiso());

				Calendar cal = Calendar.getInstance();
				cal.setTime(lista.getCompromisosEnLinea().get(0).getFecha_compromiso());

				parametro.put("Dia_compromiso", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_compromiso", cal.get(Calendar.MONTH));
				parametro.put("Mes_compromiso_letras",
						convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_compromiso", cal.get(Calendar.YEAR));
				parametro.put("Anio_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				Date fecha = new Date();
				cal.setTime(fecha);
				parametro.put("Dia_acuerdo", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_acuerdo", cal.get(Calendar.MONTH));
				parametro.put("Mes_acuerdo_letras", convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_acuerdo", cal.get(Calendar.YEAR));
				parametro.put("Anio_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				parametro.put("TABLA", lista);
//				parametro.put("Id_acuerdo", Integer.toString(consecutivo));
				parametro.put("Id_acuerdo", consecutivos);

				parametro.put("LOGOean.", logoean.);
				parametro.put("LOGOSISTEM", ANFEDIMO);

				// guardarCompromiso();

				outputStream = JasperReportUtil.getOutputStreamFromReport(parametro, path);
				media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf",
						"compromiso.pdf");
				existe = true;
			} else {
				existe = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error número de referencia no existe", "número de referencia no existe"));
			}

		} catch (Exception e) {
			logger.error("se presento un error mètodo generateReport... " + e.getMessage(), e);

		} finally {
			gestion.setIdUsuarioCrea(idUsuarioCrea);
			gestion.setReferenciaPago(referencia_pago);
			compromisoEJB.insertarCompromiso(gestion);
			this.limpiarPaginas();
			this.cuotas = new ArrayList<>();

		}

	}

	/**
	 * generateReport02. método donde se generan los parametros para traer pasar
	 * los datos al report . 2020-02-07
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see CompromisosEnLineaEJB EJB
	 */
	@SuppressWarnings("deprecation")
	public void generateReport02() throws Exception {
		Gestion gestion = new Gestion();

		try {

			this.limpiar();
			visualizarCompromiso2 = 1;

			ultimoValor = compromisoEJB.ultimoId();
			int consecutivo = ultimoValor;

			String path = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/cartacompromiso02.jasper");

			String logoean. = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_ean.);

			String ANFEDIMO = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_SISTEM);

			Map<String, Object> parametro = new HashMap<String, Object>();

			byte[] bites = null;

			lista = new Lista();

			CompromisosEnLinea compromiso = new CompromisosEnLinea();
			NumeroLetras convert = new NumeroLetras();

			compromiso.setReferencia_pago(referencia_pago);

			lista.setCompromisosEnLinea(compromisoEJB.buscarCompromiso(compromiso));
			if (!lista.getCompromisosEnLinea().isEmpty()) {
				// parametros que enviamos al report.
				parametro.put("Email", email);
				parametro.put("Nombre", lista.getCompromisosEnLinea().get(0).getNombre());
				parametro.put("Cedula", lista.getCompromisosEnLinea().get(0).getDocumento());
				parametro.put("Referencia", referencia_pago);
				parametro.put("Obligacion_Total", lista.getCompromisosEnLinea().get(0).getObligacion_total());
				parametro.put("Obligacion_Total_Letra", convert.cantidadConLetra(lista.getCompromisosEnLinea().get(0)
						.getObligacion_total().setScale(0, RoundingMode.HALF_DOWN).toString()).toUpperCase());
				parametro.put("Obligacion_Total_Pesos", new java.text.DecimalFormat("$ #,##0.00")
						.format(lista.getCompromisosEnLinea().get(0).getObligacion_total()));
				parametro.put("Fecha_Compromiso", lista.getCompromisosEnLinea().get(0).getFecha_compromiso());
				parametro.put("Dias_Mora", lista.getCompromisosEnLinea().get(0).getDiasMora());

				Calendar cal = Calendar.getInstance();
				cal.setTime(lista.getCompromisosEnLinea().get(0).getFecha_compromiso());

				parametro.put("Dia_compromiso", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_compromiso", cal.get(Calendar.MONTH));
				parametro.put("Mes_compromiso_letras",
						convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_compromiso", cal.get(Calendar.YEAR));
				parametro.put("Anio_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				Date fecha = new Date();
				cal.setTime(fecha);
				parametro.put("Dia_acuerdo", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_acuerdo", cal.get(Calendar.MONTH));
				parametro.put("Mes_acuerdo_letras", convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_acuerdo", cal.get(Calendar.YEAR));
				parametro.put("Anio_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				parametro.put("TABLA", lista);
//				parametro.put("Id_acuerdo", Integer.toString(consecutivo));
				parametro.put("Id_acuerdo", consecutivos);

				parametro.put("LOGOean.", logoean.);
				parametro.put("LOGOSISTEM", ANFEDIMO);

				// Cambios para acuerdo 02

				parametro.put("Descuento_aplicar", descuentoAplicar);
				parametro.put("Descuento_aplicar_letras", convert.cantidadConLetra(Double.toString(descuentoAplicar)));

				calcularDescuento(descuentoAplicar);

				parametro.put("Obligacion_Total_Descuento",
						lista.getCompromisosEnLinea().get(0).getValor_pactado().setScale(0, RoundingMode.HALF_DOWN));
				parametro.put("Obligacion_Total_Descuento_letras",
						convert.cantidadConLetra(lista.getCompromisosEnLinea().get(0).getValor_pactado()
								.setScale(0, RoundingMode.HALF_DOWN).toString()).toUpperCase());

				// guardarCompromiso();

				outputStream = JasperReportUtil.getOutputStreamFromReport(parametro, path);
				media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf",
						"compromiso.pdf");

				existe2 = true;
			} else {
				existe2 = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error número de referencia no existe", "número de referencia no existe"));
			}
		} catch (Exception e) {
			logger.error("se presento un error mètodo generateReport02... " + e.getMessage(), e);
		} finally {
			gestion.setIdUsuarioCrea(idUsuarioCrea);
			gestion.setReferenciaPago(referencia_pago);
			compromisoEJB.insertarCompromiso(gestion);
			this.limpiarPaginas();
			this.cuotas = new ArrayList<>();
		}

	}

	/**
	 * generateReport03. método donde se generan los parametros para traer pasar
	 * los datos al report . 2020-02-07
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see CompromisosEnLineaEJB EJB
	 */
	@SuppressWarnings("deprecation")
	public void generateReport03() throws Exception {
		Gestion gestion = new Gestion();

		try {

			this.limpiar();
			visualizarCompromiso3 = 1;

			ultimoValor = compromisoEJB.ultimoId();
			int consecutivo = ultimoValor;

			String path = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/cartacompromiso03.jasper");

			String logoean. = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_ean.);

			String ANFEDIMO = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_SISTEM);

			Map<String, Object> parametro = new HashMap<String, Object>();

			byte[] bites = null;

			lista = new Lista();

			CompromisosEnLinea compromiso = new CompromisosEnLinea();
			NumeroLetras convert = new NumeroLetras();

			compromiso.setReferencia_pago(referencia_pago);

			lista.setCompromisosEnLinea(compromisoEJB.buscarCompromiso(compromiso));
			if (!lista.getCompromisosEnLinea().isEmpty()) {
				// parametros que enviamos al report.
				parametro.put("Email", email);
				parametro.put("Nombre", lista.getCompromisosEnLinea().get(0).getNombre());
				parametro.put("Cedula", lista.getCompromisosEnLinea().get(0).getDocumento());
				parametro.put("Referencia", referencia_pago);
				parametro.put("Obligacion_Total", lista.getCompromisosEnLinea().get(0).getObligacion_total());
				parametro.put("Obligacion_Total_Letra", convert.cantidadConLetra(lista.getCompromisosEnLinea().get(0)
						.getObligacion_total().setScale(0, RoundingMode.HALF_DOWN).toString()).toUpperCase());
				parametro.put("Obligacion_Total_Pesos", new java.text.DecimalFormat("$ #,##0.00")
						.format(lista.getCompromisosEnLinea().get(0).getObligacion_total()));
				parametro.put("Fecha_Compromiso", lista.getCompromisosEnLinea().get(0).getFecha_compromiso());
				parametro.put("Dias_Mora", lista.getCompromisosEnLinea().get(0).getDiasMora());

				Calendar cal = Calendar.getInstance();
				cal.setTime(lista.getCompromisosEnLinea().get(0).getFecha_compromiso());

				parametro.put("Dia_compromiso", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_compromiso", cal.get(Calendar.MONTH));
				parametro.put("Mes_compromiso_letras",
						convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_compromiso", cal.get(Calendar.YEAR));
				parametro.put("Anio_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				Date fecha = new Date();
				cal.setTime(fecha);
				parametro.put("Dia_acuerdo", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_acuerdo", cal.get(Calendar.MONTH));
				parametro.put("Mes_acuerdo_letras", convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_acuerdo", cal.get(Calendar.YEAR));
				parametro.put("Anio_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				parametro.put("TABLA", lista);
				parametro.put("CUOTAS", cuotas);
//				parametro.put("Id_acuerdo", Integer.toString(consecutivo));
				parametro.put("Id_acuerdo", consecutivos);

				parametro.put("LOGOean.", logoean.);
				parametro.put("LOGOSISTEM", ANFEDIMO);

				// Cambios para acuerdo 02

				parametro.put("Descuento_aplicar", descuentoAplicar);
				parametro.put("Descuento_aplicar_letras", convert.cantidadConLetra(Double.toString(descuentoAplicar)));

				calcularDescuento(descuentoAplicar);

				parametro.put("Obligacion_Total_Descuento",
						lista.getCompromisosEnLinea().get(0).getValor_pactado().setScale(0, RoundingMode.HALF_DOWN));
				parametro.put("Obligacion_Total_Descuento_letras",
						convert.cantidadConLetra(lista.getCompromisosEnLinea().get(0).getValor_pactado()
								.setScale(0, RoundingMode.HALF_DOWN).toString()).toUpperCase());

				// Cambios para Acuerdo 03

				parametro.put("Numero_cuotas", cantCuotas);
				parametro.put("Numero_cuotas_letras", convert.cantidadConLetra(Integer.toString(cantCuotas)));

				parametro.put("log", logger);

				// guardarCompromiso();

				outputStream = JasperReportUtil.getOutputStreamFromReport(parametro, path);
				media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf",
						"compromiso.pdf");

				existe3 = true;
			} else {
				existe3 = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error número de referencia no existe", "número de referencia no existe"));
			}
		} catch (Exception e) {
			logger.error("se presento un error mètodo generateReport03... " + e.getMessage(), e);
		} finally {
			gestion.setIdUsuarioCrea(idUsuarioCrea);
			gestion.setReferenciaPago(referencia_pago);
			compromisoEJB.insertarCompromiso(gestion);
			this.limpiarPaginas();
			this.cuotas = new ArrayList<>();

		}
	}

	/**
	 * generateReport04. método donde se generan los parametros para traer pasar
	 * los datos al report . 2020-02-07
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see CompromisosEnLineaEJB EJB
	 */
	@SuppressWarnings("deprecation")
	public void generateReport04() throws Exception {
		Gestion gestion = new Gestion();

		try {

			this.limpiar();
			visualizarCompromiso4 = 1;

			ultimoValor = compromisoEJB.ultimoId();
			int consecutivo = ultimoValor;

			String path = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/cartacompromiso04.jasper");

			String logoean. = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_ean.);

			String ANFEDIMO = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath(Constante.CONSTANTE_LOGO_SISTEM);

			Map<String, Object> parametro = new HashMap<String, Object>();

			byte[] bites = null;

			lista = new Lista();

			CompromisosEnLinea compromiso = new CompromisosEnLinea();
			NumeroLetras convert = new NumeroLetras();

			compromiso.setReferencia_pago(referencia_pago);

			lista.setCompromisosEnLinea(compromisoEJB.buscarCompromiso(compromiso));
			if (!lista.getCompromisosEnLinea().isEmpty()) {
				// parametros que enviamos al report.
				parametro.put("Email", email);
				parametro.put("Nombre", lista.getCompromisosEnLinea().get(0).getNombre());
				parametro.put("Cedula", lista.getCompromisosEnLinea().get(0).getDocumento());
				parametro.put("Referencia", referencia_pago);
				parametro.put("Obligacion_Total", lista.getCompromisosEnLinea().get(0).getObligacion_total());
				parametro.put("Obligacion_Total_Letra", convert.cantidadConLetra(lista.getCompromisosEnLinea().get(0)
						.getObligacion_total().setScale(0, RoundingMode.HALF_DOWN).toString()).toUpperCase());
				parametro.put("Obligacion_Total_Pesos", new java.text.DecimalFormat("$ #,##0.00")
						.format(lista.getCompromisosEnLinea().get(0).getObligacion_total()));
				parametro.put("Fecha_Compromiso", lista.getCompromisosEnLinea().get(0).getFecha_compromiso());
				parametro.put("Dias_Mora", lista.getCompromisosEnLinea().get(0).getDiasMora());

				Calendar cal = Calendar.getInstance();
				cal.setTime(lista.getCompromisosEnLinea().get(0).getFecha_compromiso());

				parametro.put("Dia_compromiso", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_compromiso", cal.get(Calendar.MONTH));
				parametro.put("Mes_compromiso_letras",
						convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_compromiso", cal.get(Calendar.YEAR));
				parametro.put("Anio_compromiso_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				Date fecha = new Date();
				cal.setTime(fecha);
				parametro.put("Dia_acuerdo", cal.get(Calendar.DAY_OF_MONTH));
				parametro.put("Dia_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.DAY_OF_MONTH))).toUpperCase());

				parametro.put("Mes_acuerdo", cal.get(Calendar.MONTH));
				parametro.put("Mes_acuerdo_letras", convert.convertNumeroAMes(cal.get(Calendar.MONTH)).toUpperCase());

				parametro.put("Anio_acuerdo", cal.get(Calendar.YEAR));
				parametro.put("Anio_acuerdo_letras",
						convert.cantidadConLetra(Integer.toString(cal.get(Calendar.YEAR))).toUpperCase());

				parametro.put("TABLA", lista);
				parametro.put("CUOTAS", cuotas);
//				parametro.put("Id_acuerdo", Integer.toString(consecutivo));
				parametro.put("Id_acuerdo", consecutivos);

				parametro.put("LOGOean.", logoean.);
				parametro.put("LOGOSISTEM", ANFEDIMO);

				// Cambios para Acuerdo 03

				parametro.put("Numero_cuotas", cantCuotas);
				parametro.put("Numero_cuotas_letras", convert.cantidadConLetra(Integer.toString(cantCuotas)));

				parametro.put("log", logger);

				// guardarCompromiso();

				outputStream = JasperReportUtil.getOutputStreamFromReport(parametro, path);
				media = JasperReportUtil.getStreamContentFromOutputStream(outputStream, "application/pdf",
						"compromiso.pdf");

				existe4 = true;
			} else {
				existe4 = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error número de referencia no existe", "número de referencia no existe"));
			}

		} catch (Exception e) {
			logger.error("se presento un error mètodo generateReport04... " + e.getMessage(), e);
		} finally {
			gestion.setIdUsuarioCrea(idUsuarioCrea);
			gestion.setReferenciaPago(referencia_pago);
			compromisoEJB.insertarCompromiso(gestion);
			this.limpiarPaginas();
			this.cuotas = new ArrayList<>();

		}
	}

	public void downloadFile() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();

			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + "reporte.pdf");

			OutputStream output = response.getOutputStream();
			output.write(outputStream.toByteArray());
			output.close();

			facesContext.responseComplete();

			this.existe = false;
			this.existe2 = false;
			this.existe3 = false;
			this.existe4 = false;

			RequestContext.getCurrentInstance().update("pnlCompromiso");
			RequestContext.getCurrentInstance().update("frmcompromiso3");
			RequestContext.getCurrentInstance().update("pnlCompromiso3");
			RequestContext.getCurrentInstance().update("frmcompromiso3:pnlCompromiso3");

		} catch (Exception e) {
			logger.error("se presento un error mètodo downloadFile... " + e.getMessage(), e);
		} finally {

			this.limpiarPaginas2();
			this.cuotas = new ArrayList<>();

		}

	}

	/**
	 * buscarCompromiso. método para listar los datos del compromiso. 2018-12-12
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see PagosEnLineaEJB EJB
	 */
	public void buscarCompromiso() {
		listaCompromisos = new ArrayList<>();
		compromiso = new CompromisosEnLinea();
		try {
			Date date = new Date();

			if (referencia_pago != null) {
				if (!referencia_pago.isEmpty()) {
					compromiso.setReferencia_pago(referencia_pago);
				}
			}

			listaCompromisos = compromisoEJB.buscarCompromiso(compromiso);

			RequestContext.getCurrentInstance().update("frmcompromiso1:txtReferencia");

		} catch (Exception e) {
			logger.error("se presento un error en método buscarCompromiso de clase CompromisoBean. " + e.getMessage(),
					e);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error: Se produjo un error.", "Se produjo un error."));

		}
	}

	public void generarCuotas() throws Exception {

		try {

			Lista lista = new Lista();

			CompromisosEnLinea compromiso = new CompromisosEnLinea();
			NumeroLetras convert = new NumeroLetras();

			compromiso.setReferencia_pago(referencia_pago);
			lista.setCompromisosEnLinea(compromisoEJB.buscarCompromiso(compromiso));

			if (!lista.getCompromisosEnLinea().isEmpty()) {

				existe3 = true;
				existe4 = true;

				cuotas = new ArrayList<Cuota>();
				BigDecimal montoFraccion = null;
				if (descuentoAplicar != 0.0) {
					Double descuento = 1 - (descuentoAplicar / 100);

					BigDecimal saldoMora = lista.getCompromisosEnLinea().get(0).getSaldo();
					BigDecimal interesesMora = lista.getCompromisosEnLinea().get(0).getInteres_mora();
					BigDecimal interesesMoraDescuento = interesesMora.multiply(new BigDecimal(descuento));
					BigDecimal saldoParcial = saldoMora.add(interesesMoraDescuento);
					BigDecimal honorariosDescuento = saldoParcial.multiply(new BigDecimal(0.15));
					BigDecimal valorPactado = saldoParcial.add(honorariosDescuento).setScale(0, RoundingMode.HALF_DOWN);

					montoFraccion = valorPactado;
				} else {
					montoFraccion = lista.getCompromisosEnLinea().get(0).getObligacion_total();
				}

				Calendar now = Calendar.getInstance();
				now.add(Calendar.DAY_OF_MONTH, -1);
				saldo = montoFraccion;
				BigDecimal cantidad = montoFraccion.divide(new BigDecimal(cantCuotas), 2, RoundingMode.HALF_UP);

				Cuota cuota = new Cuota();
				Date fechaTmp = new Date();
				int cantTmp = cuotas.size() + cantCuotas;

				for (int j = cuotas.size(); j < cantTmp; j++) {
					cuota = new Cuota();
					cuota.setValorCuota(cantidad);
					cuota.setNumeroCuota(j + 1);
					if (fecha_cuota == null) {
						now.add(Calendar.MONTH, 1);
						fechaTmp = now.getTime();
						cuota.setFechaCuota(fechaTmp);
					}
					if (fecha_cuota != null && !cuotas.isEmpty()) {
						now.setTime(fecha_cuota);
						now.add(Calendar.MONTH, 1);
						fechaTmp = now.getTime();
						cuota.setFechaCuota(fechaTmp);
						fecha_cuota = null;
					}

					if (fecha_cuota != null && cuotas.isEmpty()) {
						now.setTime(fecha_cuota);
						fechaTmp = now.getTime();
						cuota.setFechaCuota(fechaTmp);
						fecha_cuota = null;
					}
					setSaldo(saldo.subtract(cantidad));
					cuota.setSaldo(getSaldo());
					cuotas.add(cuota);
				}
				cantCuotas = 0;
			} else {
				existe3 = false;
				existe4 = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error numero de cuota no existe", "número de referencia no existe"));
			}

			RequestContext.getCurrentInstance().update("frmcompromiso3:pnlCompromiso3");

		} catch (Exception e) {
			logger.error("se presento un error al generar las cuotas... " + e.getMessage(), e);
		}
	}

	/**
	 * limpiarCuotas. método que limpia el campo cuotas de la vista este se
	 * aplica para los compromisos 03 y 04. 2018-12-17
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see
	 */
	public void limpiarCuotas() {

		cuotas = new ArrayList<>();

		// fechaPromesa = null;
		RequestContext.getCurrentInstance().update("frmcompromiso3:pnlCompromiso3");

	}

	/**
	 * limpiar. método que limpia los compromisos (PDF) 01,02,03,04. 2018-12-17
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see
	 */
	public void limpiar() {
		visualizarCompromiso1 = 0;
		visualizarCompromiso2 = 0;
		visualizarCompromiso3 = 0;
		visualizarCompromiso4 = 0;

	}

	public void limpiarPaginas() {

		descuentoAplicar = (0.0);
		fecha_cuota = new Date();
		cantCuotas = new Integer(0);

	}

	public void limpiarPaginas2() {

		referencia_pago = ("");
		consecutivos = ("");
		email = ("");
		descuentoAplicar = (0.0);
		fecha_cuota = new Date();
		cantCuotas = new Integer(0);

	}

	/**
	 * escribirConsecutivo. método que escribe el numero consecutivo. 2019-01-31
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return
	 * @see
	 */
	public void escribirConsecutivo() {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			int ultimoValor = this.leerConsecutivo();

			String ruta = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/consecutivo/consecutivo.txt");
			File file = new File(ruta);

			String data = "Hola stackoverflow.com...";
			// Si el archivo no existe, se crea!
			if (!file.exists()) {
				file.createNewFile();
			}
			// flag true, indica adjuntar información al archivo.
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(++ultimoValor + ";");
			System.out.println("información agregada!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cierra instancias de FileWriter y BufferedWriter
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public void escribirConsecutivo(int consecutivo) {

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			int ultimoValor = consecutivo;
			String ruta = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/consecutivo/consecutivo.txt");
			File file = new File(ruta);

			String data = "Hola compañero.com...";
			// Si el archivo no existe, se crea!
			if (!file.exists()) {
				file.createNewFile();
			}
			// flag true, indica adjuntar información al archivo.
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(ultimoValor + ";");
			System.out.println("información agregada!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cierra instancias de FileWriter y BufferedWriter
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * leerConsecutivo. método que lee el numero consecutivo. 2019-01-31
	 * 
	 * @author Andres Diaz
	 * @param campos
	 *            provenientes de xhtml
	 * @throws Exception
	 *             si hay valores en null
	 * @return valorUltimoNumero
	 * @throws IOException
	 * @see
	 */
	public int leerConsecutivo() throws IOException {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String[] fields = null;
		int valorUltimoNumero = 0;

		try {
			String ruta = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/consecutivo/consecutivo.txt");
			archivo = new File(ruta);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			System.out.println("Leyendo el contendio del archivo.txt");
			String linea = br.readLine();
			while (linea != null) {
				fields = linea.split(";");
				logger.info("Esta validando la linea de lectura");
				logger.info(linea);
				linea = br.readLine();
			}

			int tamanio = fields.length;
			int numero = tamanio - 1;

			valorUltimoNumero = Integer.parseInt(fields[numero]);

		} catch (Exception e) {
			logger.error("se esta presentando un error al leer la lista... " + e.getMessage(), e);
		}
		return valorUltimoNumero;
	}

	public ByteArrayOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(ByteArrayOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public StreamedContent getMedia() {
		return media;
	}

	public void setMedia(StreamedContent media) {
		this.media = media;
	}

	public ICompromisosEnLineaEJBLocal getCompromisoEJB() {
		return compromisoEJB;
	}

	public CompromisosEnLinea getCompromiso() {
		return compromiso;
	}

	public void setCompromiso(CompromisosEnLinea compromiso) {
		this.compromiso = compromiso;
	}

	public void setCompromisoEJB(ICompromisosEnLineaEJBLocal compromisoEJB) {
		this.compromisoEJB = compromisoEJB;
	}

	public LoginBean. getLoginBean.() {
		return loginBean.;
	}

	public void setLoginBean.(LoginBean. loginBean.) {
		this.loginBean. = loginBean.;
	}

	public List<CompromisosEnLinea> getListaCompromisos() {
		return listaCompromisos;
	}

	public void setListaCompromisos(List<CompromisosEnLinea> listaCompromisos) {
		this.listaCompromisos = listaCompromisos;
	}

	public List<CompromisosEnLinea> getSelectedCompromisos() {
		return selectedCompromisos;
	}

	public void setSelectedCompromisos(List<CompromisosEnLinea> selectedCompromisos) {
		this.selectedCompromisos = selectedCompromisos;
	}

	public List<CompromisosEnLinea> getCompromisosFiltro() {
		return compromisosFiltro;
	}

	public void setCompromisosFiltro(List<CompromisosEnLinea> compromisosFiltro) {
		this.compromisosFiltro = compromisosFiltro;
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

	public Date getFecha_gestion() {
		return fecha_gestion;
	}

	public void setFecha_gestion(Date fecha_gestion) {
		this.fecha_gestion = fecha_gestion;
	}

	public Date getFecha_actualizacion() {
		return fecha_actualizacion;
	}

	public void setFecha_actualizacion(Date fecha_actualizacion) {
		this.fecha_actualizacion = fecha_actualizacion;
	}

	public Date getFecha_compromiso() {
		return fecha_compromiso;
	}

	public void setFecha_compromiso(Date fecha_compromiso) {
		this.fecha_compromiso = fecha_compromiso;
	}

	public byte[] getReportPdf() {
		return reportPdf;
	}

	public void setReportPdf(byte[] reportPdf) {
		this.reportPdf = reportPdf;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<CompromisosEnLinea> getNumerosTarjetas() {
		return numerosTarjetas;
	}

	public void setNumerosTarjetas(List<CompromisosEnLinea> numerosTarjetas) {
		this.numerosTarjetas = numerosTarjetas;
	}

	public StreamedContent getFile2() {
		return file2;
	}

	public void setFile2(StreamedContent file2) {
		this.file2 = file2;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

	public String getIdAcuerdo() {
		return idAcuerdo;
	}

	public void setIdAcuerdo(String idAcuerdo) {
		this.idAcuerdo = idAcuerdo;
	}

	public Double getDescuentoAplicar() {
		return descuentoAplicar;
	}

	public void setDescuentoAplicar(Double descuentoAplicar) {
		this.descuentoAplicar = descuentoAplicar;
	}

	public List<Cuota> getCuotas() {
		return cuotas;
	}

	public void setCuotas(List<Cuota> cuotas) {
		this.cuotas = cuotas;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public int getCantCuotas() {
		return cantCuotas;
	}

	public void setCantCuotas(int cantCuotas) {
		this.cantCuotas = cantCuotas;
	}

	public void setCantCuotas(Integer cantCuotas) {
		this.cantCuotas = cantCuotas;
	}

	public Date getFecha_cuota() {
		return fecha_cuota;
	}

	public void setFecha_cuota(Date fecha_cuota) {
		this.fecha_cuota = fecha_cuota;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		CompromisoBean..logger = logger;
	}

	public Integer getVisualizarCompromiso1() {
		return visualizarCompromiso1;
	}

	public void setVisualizarCompromiso1(Integer visualizarCompromiso1) {
		this.visualizarCompromiso1 = visualizarCompromiso1;
	}

	public Integer getVisualizarCompromiso2() {
		return visualizarCompromiso2;
	}

	public void setVisualizarCompromiso2(Integer visualizarCompromiso2) {
		this.visualizarCompromiso2 = visualizarCompromiso2;
	}

	public Integer getVisualizarCompromiso3() {
		return visualizarCompromiso3;
	}

	public void setVisualizarCompromiso3(Integer visualizarCompromiso3) {
		this.visualizarCompromiso3 = visualizarCompromiso3;
	}

	public Integer getVisualizarCompromiso4() {
		return visualizarCompromiso4;
	}

	public void setVisualizarCompromiso4(Integer visualizarCompromiso4) {
		this.visualizarCompromiso4 = visualizarCompromiso4;
	}

	public Integer getIdUsuarioCrea() {
		return idUsuarioCrea;
	}

	public void setIdUsuarioCrea(Integer idUsuarioCrea) {
		this.idUsuarioCrea = idUsuarioCrea;
	}

	public Integer getUltimoValor() {
		return ultimoValor;
	}

	public void setUltimoValor(Integer ultimoValor) {
		this.ultimoValor = ultimoValor;
	}

	public Boolean. getExiste() {
		return existe;
	}

	public void setExiste(Boolean. existe) {
		this.existe = existe;
	}

	public Boolean. getExiste2() {
		return existe2;
	}

	public void setExiste2(Boolean. existe2) {
		this.existe2 = existe2;
	}

	public Boolean. getExiste3() {
		return existe3;
	}

	public void setExiste3(Boolean. existe3) {
		this.existe3 = existe3;
	}

	public Boolean. getExiste4() {
		return existe4;
	}

	public void setExiste4(Boolean. existe4) {
		this.existe4 = existe4;
	}

	public Boolean. getCuota3() {
		return cuota3;
	}

	public void setCuota3(Boolean. cuota3) {
		this.cuota3 = cuota3;
	}

	public Boolean. getCuota4() {
		return cuota4;
	}

	public void setCuota4(Boolean. cuota4) {
		this.cuota4 = cuota4;
	}

	public String getConsecutivos() {
		return consecutivos;
	}

	public void setConsecutivos(String consecutivos) {
		this.consecutivos = consecutivos;
	}

	

	
	
}
