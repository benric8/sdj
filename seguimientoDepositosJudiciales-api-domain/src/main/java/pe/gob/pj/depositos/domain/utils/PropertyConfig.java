package pe.gob.pj.depositos.domain.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Configuration
@PropertySources(value = { @PropertySource("classpath:seguimientoDepositosJudiciales-api.properties") })
@Component
public class PropertyConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// #SEGURIDAD
	@Value("${configuracion.seguridad.secretToken:null}")
	private String seguridadSecretToken;
	@Value("${configuracion.seguridad.idaplicativo:0}")
	private Integer seguridadIdAplicativo;
	@Value("${configuracion.seguridad.authenticate.token.tiempo.expira.segundos:300}")
	private Integer seguridadTiempoExpiraSegundos;
	@Value("${configuracion.seguridad.authenticate.token.tiempo.refresh.segundos:180}")
	private Integer seguridadTiempoRefreshSegundos;

	// #CAPTCHA
	@Value("${captcha.url:null}")
	private String captchaUrl;
	@Value("${captcha.token:null}")
	private String captchaToken;
	
}
