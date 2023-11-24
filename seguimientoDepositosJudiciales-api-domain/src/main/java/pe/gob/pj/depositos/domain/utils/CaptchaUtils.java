package pe.gob.pj.depositos.domain.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import pe.gob.pj.depositos.domain.model.client.CaptchaValid;


public class CaptchaUtils {
	
	private static final Logger logger = LogManager.getLogger(CaptchaUtils.class);
	
	public static final boolean validCaptcha(String token, String remoteIp, String cuo) {
		try {
			
			String URL = ProjectProperties.getInstance().getCaptchaUrl();
			String TOKEN = ProjectProperties.getInstance().getCaptchaToken();
			
			RestTemplate plantilla = new RestTemplate();			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(URL)
		                .queryParam("secret", TOKEN)
		                .queryParam("response", token)
		                .queryParam("remoteip", remoteIp).build();
			logger.info("{} {}", cuo ,  builder.toUriString());
			CaptchaValid resultado = plantilla.postForObject(builder.toUriString(), null, CaptchaValid.class);
			logger.info("{} {}", cuo, resultado.getSuccess());
			
			
			if(resultado.getSuccess().equals("true")) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			logger.error("{} {}", cuo, ProjectUtils.convertExceptionToString(e));
			logger.fatal(cuo, e);
		}
		return Boolean.FALSE;
	}

}
