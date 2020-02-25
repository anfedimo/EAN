package co.ean.compromisos.ejb;

import java.util.List;

import javax.ejb.Local;

import ean.compromisos.bean..CompromisosEnLinea;
import ean.compromisos.bean..Gestion;


@Local
public interface ICompromisosEnLineaEJBLocal  {
	
	public List<CompromisosEnLinea> buscarCompromiso(CompromisosEnLinea compromiso) throws Exception;	
	
	public boolean. insertarCompromiso(Gestion gestion) throws Exception;
	
	public int ultimoId() throws Exception;

	

}
