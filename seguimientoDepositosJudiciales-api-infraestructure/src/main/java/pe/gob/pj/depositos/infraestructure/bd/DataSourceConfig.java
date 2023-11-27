package pe.gob.pj.depositos.infraestructure.bd;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	
	private static Properties getHibernatePropertiesPostgresql() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
		hibernateProperties.put("hibernate.show_sql", false);
		return hibernateProperties;
	}
	
	
	
	/* Creación de conexión con base de datos seguridad */
	@Bean(name = "cxSeguridadDS")
	public DataSource jndiConexionSeguridad() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:jboss/datasources/seguimientoDepositosJudicialesAPISeguridad");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}	
		
	@Bean(name = "sessionSeguridad")
	public SessionFactory getSessionFactorySeguridad(@Qualifier("cxSeguridadDS") DataSource seguridadDS) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("pe.gob.pj.depositos.infraestructure.db.entity.security");
		sessionFactoryBean.setHibernateProperties(getHibernatePropertiesPostgresql());
		sessionFactoryBean.setDataSource(seguridadDS);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "txManagerSeguridad")
	public HibernateTransactionManager getTransactionManagerSeguridad(@Qualifier("sessionSeguridad") SessionFactory sessionSeguridad) throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionSeguridad);
		return transactionManager;
	}
	
	/* Creación de conexión con la base de datos SIJ_002 */
	@Bean(name = "cxSij_002DS")
	public DataSource jndiConexionSIJ() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:jboss/datasources/seguimientoDepositosJudicialesAPISIJ_002");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.setCache(true);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}	
		
	@Bean(name = "sessionSij002")
	public SessionFactory getSessionFactorySIJ(@Qualifier("cxSij_002DS") DataSource sijDS) throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setPackagesToScan("pe.gob.pj.depositos.infraestructure.db.entity.sij");
		sessionFactoryBean.setHibernateProperties(getHibernatePropertiesPostgresql());
		sessionFactoryBean.setDataSource(sijDS);
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "txManagerSij")
	public HibernateTransactionManager getTransactionManagerSIJ(@Qualifier("sessionSij002") SessionFactory sessionSeguridad) throws IOException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionSeguridad);
		return transactionManager;
	}
	
	
	
}
