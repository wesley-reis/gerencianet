package br.com.framework.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.bean.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * Responsavel por estabelecer a conexão do hibernate
 * 
 */
@ApplicationScoped
public class HibernateUtil implements Serializable {

	private static final String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:/comp/env/jdbc/datasouce";

	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Responsável por ler o arquivo de configuração hibernate.cfg.xml
	 * @return SessionFactory
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				sessionFactory = (new Configuration()).configure()
						.buildSessionFactory();
			}
			return sessionFactory;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(
					"Erro ao criar conexão SessionFactory");
		}
	}

	/**
	 * Retorna o sessionFactory corrente.
	 * @return sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Retonar a sessão do SessionFactory
	 * @return Session
	 */
	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	/**
	 *  Abre uma nova sessão no SessionFactory
	 * @return Session
	 */
	public static Session openSession() {
		if (sessionFactory == null)
			buildSessionFactory();
		return sessionFactory.openSession();
	}

	/**
	 * Obtem a conection do provedor de conexões configurado
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnectionProvider() throws SQLException {
		return ((SessionFactoryImplementor) sessionFactory)
				.getConnectionProvider().getConnection();
	}

	/**
	 * 
	 * @return Connection no InitialContext java:/comp/env/jdbc/datasouce
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		InitialContext context = new InitialContext();
		DataSource ds = (DataSource) (DataSource) context
				.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		return ds.getConnection();
	}

	/**
	 * 
	 * @return DataSource JNDI tomcat
	 * @throws NamingException
	 */
	public DataSource getDataSourceJndi() throws NamingException {
		InitialContext context = new InitialContext();
		return (DataSource) context
				.lookup(br.com.framework.implementacao.crud.VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
	}

}
