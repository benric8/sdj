package pe.gob.pj.depositos.domain.utils;

public class ProjectConstants {

	public static final String METHOD_CORTA_ULTIMA_BARRA_INVERTIDA = "PUT";
	
	public static final int DEFAULT_PAGINATION_PAGE_SIZE = 10;
	
	public static final int NRO_VECES_REFRESH_CON_TOKEN_EXPIRADO = 100;
	
	public static final String APLICATIVO_NOMBRE = "seguimientoDepositosJudiciales-api";
	public static final String APLICATIVO_VERSION = "1.0.0";
	
	public class Esquema {
		public static final String SIDENUM = "USRSINAREJ";
	}
	
	public static final String AUDITORIA_B_INSTER = "I";
	
	public static final String ESTADO_ACTIVO = "1";
	public static final String ESTADO_ACTIVO_S = "S";
	
	public static final String PATTERN_NUMBER = "\\d+";
	public static final String PATTERN_ALPHANUMBER = "[a-zA-Z0-9]+";
	public static final String PATTERN_S_N = "[SN]";
	public static final String PATTERN_FORMATO_EXPEDIENTE = "(\\d{5})[-](\\d{4})[-](\\d{1,4})[-](\\d{4})[-]([A-Za-z]{2})[-]([A-Za-z]{2})[-](\\d{2})";
	public static final String PATTERN_FECHA = "([0][1-9]|[1-2][0-9]|[3][0-1])/([0][1-9]|[1][0-2])/(\\d{4})";
	public static final String PATTERN_FECHA_DD_MM_YYYY_HH_MM_SS_SSS = "(\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})";
	public static final String PATTERN_FECHA_YYYY_MM_DD_HH_MM_SS_SSS = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})";
	public static final String PATTERN_EMAIL = "([A-Za-z0-9]+[._-]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[-]?[A-Za-z0-9]+\\.)*[A-Za-z0-9]{2,6}";
	public static final String PATTERN_IP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final String PATTERN_MAC = "([0-9A-F]{2}[:-]){5}([0-9A-F]{2})";

	public static final String FORMATO_FECHA_YYYYMMDD = "yyyyMMdd";
	public static final String FORMATO_FECHA_YYYY_MM_DD = "yyyy/MM/dd";
	public static final String FORMATO_FECHA_YYYY_MM_DD_ = "yyyy-MM-dd";
	public static final String FORMATO_FECHA_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM = "dd/MM/yyyy hh:mm a";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_SSS="dd/MM/yyyy HH:mm:ss.SSS";
	public static final String FORMATO_FECHA_YYYY_MM_DD_HH_MM_SS_SSS="yyyy-MM-dd HH:mm:ss.SSS";

	public static final String AUD_CUO = "auditoriaCuo";
	public static final String AUD_IP = "auditoriaIp";
	public static final String AUD_JWT = "auditoriaJwt";
	public static final String REMOTE_IP = "ipRemota";
	public static final String TOKEN_ACCESO_EXTERNO = "out";
	public static final String TOKEN_ACCESO_INTERNO = "in";
	public static final String TOKEN_ACCESO_NEUTRO = "io";
	public static final String TOKEN_OPERACION_SPLIT = "@";

	public static final String CLAIM_ROL = "rol";
	public static final String CLAIM_USUARIO = "usuario";
	public static final String CLAIM_IP = "remoteIp";
	public static final String CLAIM_NUMERO = "numero";
	public static final String CLAIM_ACCESO = "log_";
	public static final String CLAIM_LIMIT= "limit";

	public static final String C_500 = "500";
	public static final String C_404 = "404";
	public static final String C_405 = "405";
	public static final String C_200 = "200";
	public static final String C_400 = "400";
	public static final String C_401 = "401";
	public static final String C_403 = "403";
	
	public static final String C_ERROR = "E";
	public static final String C_EXITO   = "0000";
	public static final String C_ERROR_NO_COINCIDENCIAS   = "0001";
	public static final String C_ERROR_INESPERADO = "E000";
	public static final String C_ERROR_SESSION_EXPIRA = "E001";
	public static final String C_ERROR_VALIDAR_TOKEN = "E002";
	public static final String C_ERROR_REFRESH_TOKEN = "E003";
	public static final String C_ERROR_EJECUCION_SENTENCIA = "E004";
	public static final String C_ERROR_VALIDAR_PERSONA_RENIEC= "E005";
	public static final String C_ERROR_EXPEDIENTE_DETALLE= "E006";
	public static final String C_ERROR_EXPEDIENTE_RESOLUCION= "E007";
	public static final String C_ERROR_CAPTCHA= "E008";
//	public static final String C_ERROR_EJECUCION_SP   = "E004";
//	public static final String C_ERROR_PJ_SEGURIDAD   = "E005";
//	public static final String C_ERROR_OBTENER_CONFIGURACION_CORTE = "E006";
//	public static final String C_ERROR_CONEXION_BD_SIJ   = "E007";
//	public static final String C_ERROR_CREDENCIALES_BD_SIJ   = "E008";
	
	public static final String X_CAUSA_NO_IDENTIFICADA = "ERROR: No se puede identificar.";
	public static final String X_EXITO = "La operación se realizo de manera exitosa.";
	public static final String X_ERROR_NO_COINCIDENCIAS = "Datos no encontrados al ";
	public static final String X_ERROR_SESSION_EXPIRADO = "Los sentimos, la sesión ha expirado.";
	public static final String X_ERROR_INESPERADO = " - Error inesperado.";
	public static final String X_ERROR_VALIDAR_TOKEN = "Lo sentimos, hubo un problema al validar el token.";
	public static final String X_ERROR_REFRESH_TOKEN = "Error al generar un nuevo token, el tiempo límite para refrescar el token enviado a expirado.";
	public static final String X_ERROR  = "Error al ";
	public static final String X_ERROR_EJECUCION_SENTENCIA  = " - Error al ejecutar sentencia JPA. ";
	public static final String X_ERROR_VALIDAR_PERSONA_RENIEC  = " - Los datos ingresados de la persona no se pudieron contrastar con RENIEC. ";
	public static final String X_ERROR_EXPEDIENTE_DETALLE  = " - El expediente no cuenta con resolución en la base de datos centralizada.";
	public static final String X_ERROR_EXPEDIENTE_RESOLUCION  = " - No se pudo obtener la resolución del expediente de CASILLERO.";
	public static final String X_ERROR_CAPTCHA  = " - Se indica que se valide el captcha y el token captcha es nulo o no se pudo validar correctamente el captcha.";
//	public static final String X_ERROR_PJ_SEGURIDAD   = "No se estableció conexión con la BD PJ Seguridad.";
//	public static final String X_ERROR_OBTENER_CONFIGURACION_CORTE   = "No se puede obtener datos de configuración de la BD: ";
//	public static final String X_ERROR_EJECUCION_SP   = " - Problemas en la ejecución del SP: ";
//	public static final String X_ERROR_CONEXION_BD_SIJ   = "- Problemas en la conexión con la BD: ";
//	public static final String X_ERROR_CREDENCIALES_BD_SIJ   = "- Problemas con las credenciales de conexión a la corte.";
//	public static final String X_ERROR_RETORNADO_SP   = "Retornado por ";
	
	public class Proceso {
		public static final String CONSULTA_PERSONA_RENIEC = "Consultar Persona En Reniec";
		public static final String CONSULTA_EXPEDIENTES = "Consultar Expedientes";
		public static final String CONSULTA_EXPEDIENTE_DETALLE = "Consultar Detalle Expediente";
		public static final String CONSULTA_EXPEDIENTE_RESOLUCION = "Consultar Resolución Expediente";
	}
	
	public class Mensajes {
		public static final String MSG_ERROR_GENERICO_CONVERSION = "El tipo de dato de entrada es incorrecto";
	}

}
