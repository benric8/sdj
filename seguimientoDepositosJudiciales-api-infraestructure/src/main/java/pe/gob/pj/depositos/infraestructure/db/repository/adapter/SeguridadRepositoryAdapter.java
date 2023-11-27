package pe.gob.pj.depositos.infraestructure.db.repository.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.depositos.domain.model.seguridad.Rol;
import pe.gob.pj.depositos.domain.model.seguridad.Usuario;
import pe.gob.pj.depositos.domain.port.repository.SeguridadRepositoryPort;
import pe.gob.pj.depositos.domain.utils.EncryptUtils;
import pe.gob.pj.depositos.domain.utils.ProjectProperties;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.infraestructure.db.entity.security.MaeRol;
import pe.gob.pj.depositos.infraestructure.db.entity.security.MaeRolUsuario;
import pe.gob.pj.depositos.infraestructure.db.entity.security.MaeUsuario;


@Slf4j
@Component("seguridadRepositoryPort")
public class SeguridadRepositoryAdapter implements SeguridadRepositoryPort{

	@Autowired
	@Qualifier("sessionSeguridad")
	private SessionFactory sf;

	@Override
	public String autenticarUsuario(String cuo, String codigoCliente, String codigoRol, String usuario, String clave) throws Exception {
		Usuario user = new Usuario();
		int nAplicacion = ProjectProperties.getInstance().getSeguridadIdAplicativo();
		Object[] params = { usuario, codigoRol, nAplicacion, codigoCliente };
		try {			
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeRolUsuario.AUTENTICAR_USUARIO, MaeUsuario.class);
			query.setParameter(MaeRolUsuario.P_COD_USUARIO, usuario);
			query.setParameter(MaeRolUsuario.P_COD_ROL, codigoRol);
			query.setParameter(MaeRolUsuario.P_COD_CLIENTE, codigoCliente);
			query.setParameter(MaeRolUsuario.P_N_APLICATIVO, nAplicacion);
			String claveFinal = EncryptUtils.encrypt(usuario, clave);
			log.info("{} vemos la clave que se ercupera desde header combinada con usuario ",claveFinal);
			MaeUsuario usr =  query.getSingleResult();
			log.info("{} vemos la clave que se ercupera desde l bd ",usr.getCClave());
			if(ProjectUtils.isNull(usr.getCClave()).trim().equals(claveFinal)) {
				user.setId(usr.getNUsuario());
				user.setCClave(ProjectUtils.isNull(usr.getCClave()));
			}
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro usuario registrado en BD Seguridad con los datos ->").concat(Arrays.toString(params)));
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
		}
		return user.getId() == null? null: user.getId().toString();
	}
	
	@Override
	public Usuario recuperaInfoUsuario(String cuo, String id) throws Exception {
		Usuario user = new Usuario();
		Object[] params = { Integer.parseInt(id) };
		try {
			TypedQuery<MaeUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeUsuario.FIND_BY_ID, MaeUsuario.class);
			query.setParameter(MaeUsuario.P_N_USUARIO, Integer.parseInt(id));
			MaeUsuario u = query.getSingleResult();
			user.setCClave(u.getCClave());
			user.setCUsuario(u.getCUsuario());
			user.setId(u.getNUsuario());
			user.setLActivo(u.getLActivo());
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro usuario registrado en BD con los datos ->").concat(Arrays.toString(params)));
			user = null;
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
			user = null;
		}
		return user;
	}
	
	@Override
	public List<Rol> recuperarRoles(String cuo, String id) throws Exception {
		List<Rol> lista = new ArrayList<Rol>();
		Object[] params = { Integer.parseInt(id) };
		try {
			TypedQuery<MaeRol> query = this.sf.getCurrentSession().createNamedQuery(MaeRol.FIND_ROLES_BY_ID_USUARIO, MaeRol.class);
			query.setParameter(MaeUsuario.P_N_USUARIO, Integer.parseInt(id));
			query.getResultStream().forEach(maeRol -> {
				lista.add(new Rol(maeRol.getNRol(), maeRol.getCRol(), maeRol.getXRol(), maeRol.getLActivo()));
			});
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro roles registrado en BD con los datos -> ").concat(Arrays.stream(params)
	                .map(Object::toString)
	                .collect(Collectors.joining(", "))));
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
		}
		return lista;
	}

	@Override
	public String validarAccesoMetodo(String cuo, String usuario, String rol, String operacion) throws Exception {
		StringBuilder rpta = new StringBuilder("");
		Object[] params = {usuario,rol,operacion};
		try {
			TypedQuery<MaeRolUsuario> query = this.sf.getCurrentSession().createNamedQuery(MaeRolUsuario.VALIDAR_ACCESO_METODO , MaeRolUsuario.class);
			query.setParameter(MaeRolUsuario.P_COD_USUARIO, usuario);
			query.setParameter(MaeRolUsuario.P_COD_ROL, rol);
			query.setParameter(MaeRolUsuario.P_OPERACION, operacion);
			MaeRolUsuario rolusuario = query.getResultStream().findFirst().orElse(null);
			if(rolusuario!=null) {
				rolusuario.getMaeRol().getMaeOperacions().forEach(x->{
					if(x.getXEndpoint().equalsIgnoreCase(operacion))
						rpta.append(x.getXOperacion());
				});
			}
		} catch (NoResultException not) {
			log.info(cuo.concat("No se encontro permiso a la operacion con el rol del usuario ").concat(Arrays.toString(params)));
		} catch (Exception e) {
			log.error(cuo.concat(e.getMessage()));
		}		
		return rpta.toString();
	}
}
