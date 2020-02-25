package co.ean.compromisos.dao;

import java.net.InetAddress;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ean.compromisos.bean..CompromisosEnLinea;
import ean.compromisos.bean..Gestion;
import ean.compromisos.constante.EstadoEnum;

public class CompromisosEnLineaDAO extends BaseDAO {

	public CompromisosEnLineaDAO(DataSource ds) {
		super(ds);
	}

	private static Logger logger = Logger.getLogger(CompromisosEnLineaDAO.class);

	/**
	 * buscarCompromiso. m�todo para trae el dato mas actual del usaurio .
	 * 2020-02-07
	 * 
	 * @author Andres Diaz
	 * @param buscarCompromiso
	 * @param ninguno
	 * @throws SQLException
	 *             si existe error de sintaxis en la sentencia SQL. Exception si
	 *             hay valores en null
	 * @return List (devuelve los datos del titular)
	 * @see CompromisosEnLinea DAO
	 */
	public List<CompromisosEnLinea> buscarCompromiso(CompromisosEnLinea buscarCompromiso) throws Exception {
		
		List<CompromisosEnLinea> lista = new ArrayList<>();
		logger.info("entra a la lista ... " + lista);
		
		StringBuffer query = new StringBuffer();
		logger.info("entra a la query ... " + query);

		query.append(
				" SELECT top 1  ttd2.Email, ttd.Nombre, ttc.Documento as cedula, ttc.Referencia_Pago as Cuenta_Numero, ");
		query.append(" ttca.Saldo as Saldo, ttca.Interes_Mora + ttca.Iva_Interes_Mora  as Interes_Mora, ");
		query.append(
				" ttca.honorarios + ttca.Iva_Honorarios as Honorarios,  ttca.Saldo +  (ttca.honorarios + ttca.Iva_Honorarios) + (ttca.Interes_Mora + ttca.Iva_Interes_Mora)   as Obligacion_Total, ttc.Valor_Pactado, ");
		query.append(
				" ttc.Fecha_Gestion, ttc.Fecha_Actualizacion, ttc.Fecha_Compromiso, ttca.Fecha_Actualizo,ttca.Dias_Mora ");
		query.append("FROM telcos.ean.colombia.compromiso ttc with(nolock) ");
		query.append(
				"inner join telcos.ean.colombia.direccion ttd with(nolock) on ttd.Referencia_Pago = ttc.Referencia_Pago ");
		query.append(
				"left join telcos.ean.colombia.direccion ttd2 with(nolock) on ttd2.Referencia_Pago = ttc.Referencia_Pago and ttd2.Tipo_de_Direccion_Telefono = 'EMAIL' ");
		query.append(
				"inner join telcos.ean.colombia.cartera ttca with(nolock) on ttca.Referencia_Pago = ttc.Referencia_Pago ");
		query.append("where ttd.Referencia_Pago = ? order by Fecha_Actualizo desc, ttd.fecha_actualizacion desc");

		Date fecha = new Date();
		
	
		
		try {
			con = ds.getConnection();

			ps = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, buscarCompromiso.getReferencia_pago());

			rs = ps.executeQuery();

			while (rs.next()) {
				int t = 1;
				CompromisosEnLinea compromisos = new CompromisosEnLinea();
				logger.info("entra a la compromisos ... " + compromisos);
				

				compromisos.setEmail(rs.getString(t++));
				compromisos.setNombre(rs.getString(t++));
				compromisos.setDocumento(rs.getString(t++));
				compromisos.setReferencia_pago(rs.getString(t++));
				compromisos.setSaldo(rs.getBigDecimal(t++));
				compromisos.setInteres_mora(rs.getBigDecimal(t++));
				compromisos.setHonorarios(rs.getBigDecimal(t++));
				compromisos.setObligacion_total(rs.getBigDecimal(t++));
				compromisos.setValor_pactado(rs.getBigDecimal(t++));
				compromisos.setFecha_gestion(rs.getString(t++));
				compromisos.setFecha_actualizacion(rs.getTimestamp(t++));
				compromisos.setFecha_compromiso(rs.getTimestamp(t++));
				compromisos.setFecha_actualizo(rs.getTimestamp(t++));
				compromisos.setDiasMora(rs.getInt(t++));

				lista.add(compromisos);
			}

		} catch (SQLException e) {
			logger.error(
					"SQLException Error SQL al tratar de buscar la referencia de pago de la Clase CompromisosEnLineaDAO m�todo buscarCompromiso"
							+ e.getMessage() + "fecha...." + fecha + " id del registro.... "
							+ Statement.RETURN_GENERATED_KEYS + " tabla afectada.... telcos"
							+ "descripci�n de evento... listar datos compromiso titular" );
			throw new Exception("SQLException Error SQL al tratar de buscar la referencia de pago ");
		} catch (Exception e) {
			logger.error(
					"Exception Error al tratar de buscar la referencia de pago  Clase CompromisosEnLineaDAO m�todo CompromisosEnLinea"
							+ e.getMessage() + " fecha...." + fecha + " id del registro.... "
							+ Statement.RETURN_GENERATED_KEYS + " tabla afectada.... telcos"
							+ "descripci�n de evento... buscar la referencia de pago " );
			throw new Exception("Exception Error al tratar de buscar la referencia de pago : ");
		} finally {
			closeConexion();
			logger.info("finalizo dao CompromisosEnLinea!");
		}
		return lista;
	}

	public int ultimoId() throws Exception {

		try {
			int valor = 0;
			logger.info("entro a m�todo insertarCompromiso de la clase CompromisosEnLineaDAO ");

			StringBuffer query = new StringBuffer();
			query.append("select top (1) consecutivo_obligacion  ");
			query.append(" from compromiso.gestion order by 1 desc ");
			logger.info(" query " + query.toString());

			con = ds.getConnection();
			ps = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);

			rs = ps.executeQuery();

			while (rs.next()) {
				int t = 1;
				
				valor = rs.getInt(t++);
			}
			return valor;

		} catch (SQLException e) {
			logger.error(
					"SQLException Error SQL al tratar de insertar compromiso Clase PagosEnLineaDAO m�todo insertarPago"
							+ " usuario.... " + "fecha...." + " id del registro.... " + Statement.RETURN_GENERATED_KEYS
							+ " tabla afectada.... compromiso.gestion"
							+ "descripci�n de evento... insersi�n de registros en la tabla compromiso");
			throw new Exception("SQLException Error SQL al tratar de insertar compromiso ");
		} catch (Exception e) {

			throw new Exception("Exception Error al tratar de insertar compromisos : ");
		} finally {
			closeConexion();
			logger.info("finalizo dao insertarCompromiso!");

		}

	}

	/**
	 * Insertar Compromiso. m�todo para la insersion de los compromisos.
	 * 2020-02-07
	 * 
	 * @author Andres Diaz
	 * @param PagosEnLinea
	 *            Objeto (objeto que se desempaquetara para realizar la
	 *            insersion)
	 * @throws SQLException
	 *             si existe error de sintaxis en la sentencia SQL. Exception si
	 *             hay valores en null
	 * @return boolean. (devuelve true si se inserto con �xito, false en caso
	 *         contrario)
	 * @see PagosEnLinea DAO
	 */
	public boolean. insertarCompromiso(Gestion gestion) throws Exception {
		boolean. abueno = false;
		logger.info("entro a m�todo insertarCompromiso de la clase CompromisosEnLineaDAO ");

		StringBuffer query = new StringBuffer();

		query.append(" INSERT INTO compromiso.gestion ");
		query.append(" (  referencia_pago, ");
		query.append("  fechaCrea, estado) ");
		query.append(" VALUES (?,GETDATE(),?) ");
		logger.info(" query " + query.toString());

		Date fecha = new Date();
		// InetAddress address = InetAddress.getLocalHost();
		try {
			con = ds.getConnection();
			ps = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			int t = 1;

			ps.setString(t++, gestion.getReferenciaPago());
//			ps.setInt(t++, gestion.getIdUsuarioCrea());
			ps.setInt(t++, EstadoEnum.ACTIVO.getIndex());

			ps.executeUpdate();
			abueno = true;

		} catch (SQLException e) {
			logger.error(
					"SQLException Error SQL al tratar de insertar compromiso Clase PagosEnLineaDAO m�todo insertarPago"
							+ " usuario.... " + gestion.getIdUsuarioCrea() + "fecha...." + fecha
							+ " id del registro.... " + Statement.RETURN_GENERATED_KEYS
							+ " tabla afectada.... compromiso.gestion"
							+ "descripci�n de evento... insersi�n de registros en la tabla compromiso",e);
			throw new Exception("SQLException Error SQL al tratar de insertar compromiso ",e);
		} catch (Exception e) {
			logger.error("Exception Error al tratar de insertar compromiso Clase PagosEnLineaDAO m�todo insertarPago"
					+ " usuario.... " + gestion.getIdUsuarioCrea() + " fecha...." + fecha
					+ " id del registro.... " + Statement.RETURN_GENERATED_KEYS + " tabla afectada.... compromisos"
					+ "descripci�n de evento... insersi�n de registros en la tabla compromisos",e);
			throw new Exception("Exception Error al tratar de insertar compromisos : ",e);
		} finally {
			closeConexion();
			logger.info("finalizo dao insertarCompromiso!");
		}
		return abueno;
	}

}
