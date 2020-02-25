package co.sistemcobro.compromisos.ejb;

import java.util.List;

import javax.ejb.Local;

import co.sistemcobro.compromisos.bean.CompromisosEnLinea;
import co.sistemcobro.compromisos.bean.Gestion;


@Local
public interface ICompromisosEnLineaEJBLocal  {
	
	public List<CompromisosEnLinea> buscarCompromiso(CompromisosEnLinea compromiso) throws Exception;	
	
	public boolean insertarCompromiso(Gestion gestion) throws Exception;
	
	public int ultimoId() throws Exception;

	

}
