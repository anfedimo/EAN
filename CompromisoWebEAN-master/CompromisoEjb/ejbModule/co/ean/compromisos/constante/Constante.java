package co.ean.compromisos.constante;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class Constante {

	
	//public static final  String INPUT_FOLDER = "D:\\ZIP";
	//public static final  String ZIPPED_FOLDER = "D:\\ZIP.zip";
	//public static final String ROOT_FILE_TEMPORARY ="D:\\ZIP";

	public static final  String INPUT_FOLDER = getPath()+"\\resources\\"+"ZIP";
	public static final  String ZIPPED_FOLDER = getPath()+"\\resources\\"+"ZIP.zip";
	public static final String ROOT_FILE_TEMPORARY = getPath()+"\\resources\\"+"ZIP";
	
	public static final int CODIGO_USUARIO_PASSWORD_INCORRECTO = 1;
	public static final int CODIGO_USUARIO_SIN_PERMISOS=2;
	public static final int CODIGO_SESION_EXPIRADO=3;
	public static final int CODIGO_USUARIO_NO_EXISTE=4;
	public static final String SEPERADOR_PUNTO_COMA="\\;";
	public static final String NUMERICO = "NUMERICO";
	public static final String TEXTO = "TEXTO";
	public static final String FECHA = "FECHA"; 
	public static final String FORMATO_NUMERO_SIN_SEPARADORES = "###";
	public static final String SEPARADOR = "/";
	public static final String CONSTANTE_LOGO_SISTEM = "/resources/img/Logo_Sistem.png";
	public static final String CONSTANTE_LOGO_ean. = "/resources/img/Logo_ean..png";
	
	public static String getPath() {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            return ctx.getRealPath("/");

        } catch (Exception e) {
            System.out.print("getPath() " + e.getLocalizedMessage());
        }
        return null;
    }
	
	}
