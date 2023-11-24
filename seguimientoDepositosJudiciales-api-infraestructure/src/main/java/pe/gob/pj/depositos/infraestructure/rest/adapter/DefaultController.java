package pe.gob.pj.depositos.infraestructure.rest.adapter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import pe.gob.pj.depositos.domain.port.usecase.SeguridadUseCasePort;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.domain.utils.ProjectProperties;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.domain.utils.SecurityConstants;
import pe.gob.pj.depositos.infraestructure.rest.response.GlobalResponse;




@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DefaultController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("seguridadUseCasePort")
	private SeguridadUseCasePort seguridad;
    

	/**
	 * Método que sirve para verificar versión actual del aplicativo
	 * 
	 * @param cuo Código único de log
	 * @return Datos del aplicativo
	 * @throws Exception 
	 */
	
	@GetMapping(value = "/healthcheck")
	public ResponseEntity<GlobalResponse> healthcheck(@RequestAttribute(name = ProjectConstants.AUD_CUO) String cuo) {
		GlobalResponse res = new GlobalResponse();
		try {
			res.setCodigoOperacion(cuo.substring(1, cuo.length()-1));
			res.setCodigo(ProjectConstants.C_200);
			res.setDescripcion("Versión actual de aplicativo");
			Map<String, String> healthcheck = new HashMap<String, String>();
			healthcheck.put("Aplicativo", ProjectConstants.APLICATIVO_NOMBRE);
			healthcheck.put("Estado", "Disponible");
			healthcheck.put("Versión", ProjectConstants.APLICATIVO_VERSION);
			res.setData(healthcheck);
		} catch (Exception e) {
			res.setCodigo(ProjectConstants.C_500);
			res.setDescripcion(ProjectUtils.isNull(e.getCause()).concat(e.getMessage()));
			log.error("Error al consultar versión de aplicativo marcacionPersonal-api: {}", res.getDescripcion());
		}
		return new ResponseEntity<GlobalResponse>(res, HttpStatus.OK);
	}


	/**
	 * MÉTODO QUE GENERA NUEVO TOKEN A PARTIR DE TOKEN ANTERIOR
	 * 
	 * @param token           es token antentior
	 * @param ipRemota        es la ip desde donde lo solicita
	 * @param tokenAdmin      es el token de la seccion administrador
	 * @param validTokenAdmin indicador si necesitamos validar token del admin
	 * @param cuo             código único de log
	 * @return un nuevo token
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/seguridad/refresh")
	public ResponseEntity<GlobalResponse> refreshToken(@RequestAttribute(name = ProjectConstants.AUD_CUO) String cuo, 
			@RequestAttribute(name=ProjectConstants.AUD_IP) String ipRemota,
			@RequestParam(required = true) String token) {
		GlobalResponse res = new GlobalResponse();
		res.setCodigoOperacion(cuo.substring(1, cuo.length()-1));
		try {			
			byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

			Map<String, String> dataToken = new HashMap<String, String>();
			try {
				String jwt = token.replace("Bearer ", "");
				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt);
				String accesoBase =  (String) parsedToken.getBody().get(ProjectConstants.CLAIM_ACCESO);
				List<String> roles = (List<String>) parsedToken.getBody().get(ProjectConstants.CLAIM_ROL);
				String ipRemotaToken = parsedToken.getBody().get(ProjectConstants.CLAIM_IP).toString();
				int total = (int) parsedToken.getBody().get(ProjectConstants.CLAIM_NUMERO);
				String subject = parsedToken.getBody().getSubject();
				
				Date ahora = new Date();
				
				int tiempoSegundosExpira = ProjectProperties.getInstance().getSeguridadTiempoExpiraSegundos();
				int tiempoSegundosRefresh = ProjectProperties.getInstance().getSeguridadTiempoRefreshSegundos();
				
				Date limiteExpira = parsedToken.getBody().getExpiration();
				Date limiteRefresh = ProjectUtils.sumarRestarSegundos(limiteExpira, tiempoSegundosRefresh);
				
				if (ipRemota.equals(ipRemotaToken)) {
					if(!ahora.after(limiteRefresh)) {
							String tokenResult = Jwts.builder()
								.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
								.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
								.setIssuer(SecurityConstants.TOKEN_ISSUER)
								.setAudience(SecurityConstants.TOKEN_AUDIENCE)
								.setSubject(subject).setExpiration(ProjectUtils.sumarRestarSegundos(ahora, tiempoSegundosExpira))
								.claim(ProjectConstants.CLAIM_ROL, roles)
								.claim(ProjectConstants.CLAIM_IP, ipRemota)
								.claim(ProjectConstants.CLAIM_ACCESO, accesoBase)
								.claim(ProjectConstants.CLAIM_LIMIT, ProjectUtils.sumarRestarSegundos(ahora, tiempoSegundosExpira+tiempoSegundosRefresh))
								.claim(ProjectConstants.CLAIM_NUMERO, total + 1)
								.compact();
							
						res.setCodigo(ProjectConstants.C_EXITO);
						res.setDescripcion(ProjectConstants.X_EXITO);
						dataToken.put("token", tokenResult);
						res.setData(dataToken);
						return new ResponseEntity<>(res, HttpStatus.OK);
					}else {
						res.setCodigo(ProjectConstants.C_ERROR_REFRESH_TOKEN);
						res.setDescripcion(ProjectConstants.X_ERROR_REFRESH_TOKEN);
						return new ResponseEntity<>(res, HttpStatus.OK);
					}				
				} else {
					res.setCodigo(ProjectConstants.C_401);
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			} catch (ExpiredJwtException e) {
				String accesoBase =  (String) e.getClaims().get(ProjectConstants.CLAIM_ACCESO);
				List<String> roles = (List<String>) e.getClaims().get(ProjectConstants.CLAIM_ROL);
				String ipRemotaToken = e.getClaims().get(ProjectConstants.CLAIM_IP).toString();
				int total = (int) e.getClaims().get(ProjectConstants.CLAIM_NUMERO);
				String subject = e.getClaims().getSubject();

				Date ahora = new Date();
				

				int tiempoSegundosExpira = ProjectProperties.getInstance().getSeguridadTiempoExpiraSegundos();
				int tiempoSegundosRefresh = ProjectProperties.getInstance().getSeguridadTiempoRefreshSegundos();
				
				Date limiteExpira = e.getClaims().getExpiration();
				Date limiteRefresh = ProjectUtils.sumarRestarSegundos(limiteExpira, tiempoSegundosRefresh);
				
				if (ipRemota.equals(ipRemotaToken)) {
					if(!ahora.after(limiteRefresh)) {
						String tokenResult = Jwts.builder()
								.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
								.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
								.setIssuer(SecurityConstants.TOKEN_ISSUER)
								.setAudience(SecurityConstants.TOKEN_AUDIENCE)
								.setSubject(subject).setExpiration(ProjectUtils.sumarRestarSegundos(ahora, tiempoSegundosExpira))
								.claim(ProjectConstants.CLAIM_ROL, roles)
								.claim(ProjectConstants.CLAIM_IP, ipRemota)
								.claim(ProjectConstants.CLAIM_ACCESO, accesoBase)
								.claim(ProjectConstants.CLAIM_LIMIT, ProjectUtils.sumarRestarSegundos(ahora, tiempoSegundosExpira+tiempoSegundosRefresh))
								.claim(ProjectConstants.CLAIM_NUMERO, total + 1)
								.compact();
						res.setCodigo(ProjectConstants.C_EXITO);
						res.setDescripcion(ProjectConstants.X_EXITO);
						dataToken.put("token", tokenResult);
						res.setData(dataToken);
						return new ResponseEntity<>(res, HttpStatus.OK);
					}else {
						res.setCodigo(ProjectConstants.C_ERROR_REFRESH_TOKEN);
						res.setDescripcion(ProjectConstants.X_ERROR_REFRESH_TOKEN);
						return new ResponseEntity<>(res, HttpStatus.OK);
					}
				} else {
					res.setCodigo(ProjectConstants.C_401);
					log.warn(
							"{} No se ha encontrado coincidencias válidas del token anterior o se ha excedido el tiempo limite para refrescar token.",
							cuo);
					return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
				}
			}
		} catch (Exception e) {
			res.setCodigo(ProjectConstants.C_ERROR_VALIDAR_TOKEN);
			res.setDescripcion(ProjectConstants.X_ERROR_VALIDAR_TOKEN);
			log.error("{} error al intentar generar nuevo Token: {}", cuo, ProjectUtils.isNull(e.getCause()).concat(e.getMessage()));
		}
		return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
	}




	

}
