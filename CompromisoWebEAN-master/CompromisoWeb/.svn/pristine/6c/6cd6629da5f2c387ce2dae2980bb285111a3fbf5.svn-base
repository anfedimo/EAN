package co.sistemcobro.compromisos.filter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.sistemcobro.compromisos.bean.NavegacionBean;
import co.sistemcobro.compromisos.bean.UsuarioHermes;
import co.sistemcobro.compromisos.constante.Constante;
import co.sistemcobro.compromisos.session.LoginBean;
import sun.misc.BASE64Encoder;

public class LoginFilter implements Filter {
	Logger logger = Logger.getLogger(LoginFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Filtro de Sesion Inicializado..");
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String contextPath = ((HttpServletRequest) request).getContextPath();
		LoginBean loginBean = (LoginBean) (req.getSession().getAttribute("loginBean"));
		String idcliente = request.getParameter("idcliente");
		String idgestion = request.getParameter("idgestion");
		UsuarioHermes usuarioLogin = new UsuarioHermes();
		resp.addHeader("X-Frame-Options", "DENY");
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		 
		if (loginBean != null && StringUtils.isNoneBlank(loginBean.getUsuario())
				&& StringUtils.isNoneBlank(loginBean.getPassword())) {
			usuarioLogin = autenticacionUsuario(loginBean);

			if (Integer.parseInt(usuarioLogin.getCodigoRespuesta()) == 203) {
				logger.info("Usuario no existe..");
				resp.sendRedirect(contextPath + "/login.xhtml?response=" + Constante.CODIGO_USUARIO_NO_EXISTE);
			}
			if (Integer.parseInt(usuarioLogin.getCodigoRespuesta()) == 202) {
				logger.info("Usuario y/o contraseña incorrectos..");
				resp.sendRedirect(
						contextPath + "/login.xhtml?response=" + Constante.CODIGO_USUARIO_PASSWORD_INCORRECTO);
			}
			if (Integer.parseInt(usuarioLogin.getCodigoRespuesta()) == 201) {
				logger.info("Usuario no tiene permisos para la aplicación SistemCartera..");
				resp.sendRedirect(contextPath + "/login.xhtml?response=" + Constante.CODIGO_USUARIO_SIN_PERMISOS);
			}
			if (Integer.parseInt(usuarioLogin.getCodigoRespuesta()) == 200) {
				loginBean.setUsuarioHermes(usuarioLogin);
				MDC.put("ip", request.getRemoteAddr());
				MDC.put("usuario", usuarioLogin.getUsuario());		
			}

			// DECISION PARA ALTITUDE
			// if (StringUtils.isNoneBlank(idcliente) &&
			// StringUtils.isNoneBlank(idgestion)) {
			// resp.sendRedirect(contextPath +
			// "../../pages/obligacion/obligacion.xhtml"+"?dcliente=" +
			// idcliente + "?idgestion=" + idgestion);
			// ClienteBean clienteBean = (ClienteBean)
			// (req.getSession().getAttribute("clienteBean"));
			// if (clienteBean != null) {
			// clienteBean.init();
			// Cliente cliente = new Cliente();
			// cliente.setIdcliente(idcliente);
			// clienteBean.setCliente(cliente);
			// clienteBean.setIdgestion(idgestion);
			// loginBean.setIdCall(idCall);
			// } else {
			// if (StringUtils.isNoneBlank(idcliente) &&
			// StringUtils.isNoneBlank(idgestion)) {
			// resp.sendRedirect(contextPath + "/login.xhtml?response=" +
			// Constante.CODIGO_SESION_EXPIRADO
			// + "&idcliente=" + idcliente + "&idgestion=" + idgestion);
			// } else {
			// resp.sendRedirect(contextPath + "/login.xhtml?response=" +
			// Constante.CODIGO_SESION_EXPIRADO);
			// }
			// }
			// try {
			// clienteBean.datosCliente();
			// } catch (Exception e) {
			//
			// e.printStackTrace();
			// }
			//
			// }
			// DECISION PARA IPDIALBOX
			// if (StringUtils.isNoneBlank(idcliente) &&
			// StringUtils.isBlank(idgestion)) {
			// ClienteBean clienteBean = (ClienteBean)
			// (req.getSession().getAttribute("clienteBean"));
			// if (clienteBean != null) {
			// clienteBean.init();
			// Cliente cliente = new Cliente();
			// cliente.setIdcliente(idcliente);
			// clienteBean.setCliente(cliente);
			// } else {
			// if (StringUtils.isNoneBlank(idcliente) &&
			// StringUtils.isNoneBlank(idgestion)) {
			// resp.sendRedirect(contextPath + "/login.xhtml?response=" +
			// Constante.CODIGO_SESION_EXPIRADO
			// + "&idcliente=" + idcliente + "&idgestion=" + idgestion);
			// }
			// else {
			// resp.sendRedirect(contextPath + "/login.xhtml?response=" +
			// Constante.CODIGO_SESION_EXPIRADO);
			// }
			// }
			// try {
			// clienteBean.datosCliente();
			// clienteBean.init();
			// } catch (Exception e) {

			// e.printStackTrace();
			// }

			// }

		} else {
			logger.info("La sesion en SistemCartera a expirado..");
			if (StringUtils.isNoneBlank(idcliente) && StringUtils.isNoneBlank(idgestion)) {
				resp.sendRedirect(contextPath + "/login.xhtml?response=" + Constante.CODIGO_SESION_EXPIRADO
						+ "&idcliente=" + idcliente + "&idgestion=" + idgestion);
			} else {
				resp.sendRedirect(contextPath + "/login.xhtml?response=" + Constante.CODIGO_SESION_EXPIRADO);
			}

		}
		chain.doFilter(req, resp);
	}

	public UsuarioHermes autenticacionUsuario(LoginBean loginBean) {
		URL url = null;
		HttpURLConnection conn = null;
		UsuarioHermes usuarioLogin = null;
		try {
			JSONObject data = new JSONObject();
			data.put("usr", loginBean.getUsuario());
			data.put("pwd", loginBean.getPassword());
			//produccion
			//data.put("app", "107");
			//desarrollo
			data.put("app", "113");		
			
			//desarrollo
			//url = new URL("http://172.16.1.60:8180/hermes/servicios/WS/login");
			
			//pruebas
			//url = new URL("http://172.16.15.70:8080/hermes/servicios/WS/login");
			
			//producccion 
			url = new URL("http://172.16.1.134:28080/hermes/servicios/WS/login");
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			String authString = "sistemcobro" + ":" + "prueba";
			BASE64Encoder encoder = new BASE64Encoder();
			String authStringEnc = encoder.encode(authString.getBytes());
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.write(data.toString().getBytes());
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				ObjectMapper mapper = new ObjectMapper();
				usuarioLogin = mapper.readValue(output, UsuarioHermes.class);
			}
		} catch (Exception e) {
			logger.error("Error consultando servicio web de autenticacion en hermes :", e);
		} finally {
			conn.disconnect();
		}
		return usuarioLogin;
	}
}
