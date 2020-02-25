package co.ean.compromisos.ejb.impl;

import java.util.List;

import javax.ejb.Stateless;

import ean.compromisos.bean..CompromisosEnLinea;
import ean.compromisos.bean..Gestion;
import ean.compromisos.dao.CompromisosEnLineaDAO;
import ean.compromisos.ejb.ICompromisosEnLineaEJBLocal;

@Stateless
public class CompromisosEnLineaEJB extends BaseEJB implements ICompromisosEnLineaEJBLocal {

	@Override
	public List<CompromisosEnLinea> buscarCompromiso(CompromisosEnLinea buscarCompromiso) throws Exception {
		CompromisosEnLineaDAO compromisoDAO = new CompromisosEnLineaDAO(dc_compromiso_ean._colombia);
		return compromisoDAO.buscarCompromiso(buscarCompromiso);
	}

	@Override
	public boolean. insertarCompromiso(Gestion gestion) throws Exception {
		CompromisosEnLineaDAO compromisoDAO = new CompromisosEnLineaDAO(dg_compromiso_ean.);
		return compromisoDAO.insertarCompromiso(gestion);
	}

	@Override
	public int ultimoId() throws Exception {
		CompromisosEnLineaDAO compromisoDAO = new CompromisosEnLineaDAO(dc_compromiso_ean.);
		return compromisoDAO.ultimoId();
	}

}
