package pe.gob.pj.depositos.infraestructure.rest.adapter;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.depositos.infraestructure.rest.response.GlobalResponse;


@Slf4j
@RestController
@RequestMapping(value = "consulta",produces = MediaType.APPLICATION_JSON_VALUE)
public class ConsultaDepositosJudicialesController {
	
	@GetMapping(value="depositoJudicial")
	public ResponseEntity<GlobalResponse> consultarDeposito(){
		GlobalResponse res = new GlobalResponse();
		try {
			res.setCodigoOperacion("200");
			res.setCodigo("kasd");
			res.setDescripcion("versionkde doc");
			ArrayList<String> data = new ArrayList<String>();
			data.add("vamos");
			data.add("con");
			data.add("todo");
			res.setData(data);
		} catch (Exception e) {
			res.setCodigoOperacion("500");
			res.setCodigo("mal");
			log.error("eror");
		}
		
		return new ResponseEntity<GlobalResponse>(res,HttpStatus.OK);
		
		
	}
	
	@GetMapping(value="sp")
	public ResponseEntity<GlobalResponse> sp(){
		GlobalResponse res = new GlobalResponse();
		try {
			res.setCodigoOperacion("200");
			res.setCodigo("ddddddd");
			res.setDescripcion("versionkde doc");
			ArrayList<String> data = new ArrayList<String>();
			data.add("vamos");
			data.add("con");
			data.add("todo");
			res.setData(data);
		} catch (Exception e) {
			res.setCodigoOperacion("500");
			res.setCodigo("mal");
			log.error("eror");
		}
		
		return new ResponseEntity<GlobalResponse>(res,HttpStatus.OK);
		
		
	}
}
