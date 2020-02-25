package co.sistemcobro.compromisos.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class SesionCerradaFilter implements Filter{
	private Logger logger = Logger.getLogger(SesionCerradaFilter.class);
	
	@Override
	public void destroy() {
	}

	@Override	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String respuesta = (String)req.getParameter("response");
		HttpSession session = req.getSession(false);
		resp.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		//ClienteBean clienteBean = (ClienteBean) (req.getSession().getAttribute("clienteBean"));
		if(respuesta != null && respuesta.equals("0")){
			if(req.isRequestedSessionIdValid() && session != null){
				session.invalidate();
			}
		}
		if(respuesta != null && respuesta.equals("3")){
			logger.info("La sesion a expirado:...");
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}

}
