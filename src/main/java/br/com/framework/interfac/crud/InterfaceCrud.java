package br.com.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable {

	T findMaxObjectEntity(Class<T> classObj) throws Exception;

	void save(T obj) throws Exception;

	void clearSession() throws Exception;

	T saveRetorno(T obj) throws Exception;

	void persist(T obj) throws Exception;

	void saveOrUpdate(Object obj) throws Exception;

	void update(T obj) throws Exception;

	void delete(T obj) throws Exception;

	T merge(T obj) throws Exception;

	List<T> findList(Class<T> obj) throws Exception;

	Object findById(Class<T> entidade, Long id) throws Exception;

	List<T> findListByProperty(Class<T> entidade, Object atributo, Object valor)
			throws Exception;

	List<T> findListByListDeIds(Class<T> obj, List<Long> cods) throws Exception;

	List<T> findListByQueryDinamica(String s) throws Exception;

	void executeUpdateQueryDinamica(String s) throws Exception;

	void executeUpdateSQLDinamica(String s) throws Exception;

	void evict(Object obj) throws Exception;

	T carregar(Class<T> class1, Long long1) throws Exception;

	Class<T> getClass(Class<T> entidade) throws Exception;

	List<T> findListByLike(Class<T> entidade, String atributoClass, String valor)
			throws Exception;

	List<T> findByPropertyId(Class<T> entidade, Long id, Object atributo)
			throws Exception;

	Session getSession() throws Exception;

	List<?> getListSQLDinamica(String sql) throws Exception;

	T findUninqueByPropertyId(Class<T> entidade, Long id, Object atributo)
			throws Exception;

	T findUniqueByQueryDinamica(String query) throws Exception;

	void executeUpdateSQLDinamica(String query, Long... cods) throws Exception;

	List<T> findListOrderByProperty(Class<T> entidade, String propriedade)
			throws Exception;

	T findUninqueByProperty(Class<T> entidade, Object valor, String atributo)
			throws Exception;

	T findUninqueByPropertyId(Class<T> entidade, Long id, Object atributo,
			String condicaoAdicional) throws Exception;

	//JDBC
	JdbcTemplate getJdbcTemplate();

	SimpleJdbcTemplate getSimpleJdbcTemplate();

	SimpleJdbcInsert getSimpleJdbcInsert();
	
	public T findUninqueByProperty(Class<T> entidade, Object valor,
			String atributo, String condicao) throws Exception;
	
	Long totalRegistro(String tabela) throws Exception;
	
	Query obterQuery(String query) throws Exception;
	
	 List<T> findListByQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception; 
}
