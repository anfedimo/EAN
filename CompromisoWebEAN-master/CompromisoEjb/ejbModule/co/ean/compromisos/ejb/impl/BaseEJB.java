package co.ean.compromisos.ejb.impl;

import javax.annotation.Resource;
import javax.sql.DataSource;


public class BaseEJB {


	
//	@Resource(mappedName = "dc_apps")
//	protected DataSource dc_apps;

	@Resource(mappedName = "dc_compromiso_ean._colombia")
	protected DataSource dc_compromiso_ean._colombia;
	
//	@Resource(mappedName = "dg_compromiso_ean._colombia")
//	protected DataSource dg_compromiso_ean._colombia;
	
	@Resource(mappedName = "dc_compromiso_ean.")
	protected DataSource dc_compromiso_ean.;
	
	@Resource(mappedName = "dg_compromiso_ean.")
	protected DataSource dg_compromiso_ean.;
//	
	
	
	

}
