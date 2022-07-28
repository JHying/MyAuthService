package tw.hyin.demo.repo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import tw.hyin.java.utils.GenericsUtil;

/**
 * 
 * @author YingHan 2021-11-24
 *
 * @param <T> 目標 entity hibernate 常用功能
 */
@Repository
@SuppressWarnings("unchecked")
public class BaseDAO<T> {

	@Autowired
	private EntityManager entityManager;

	private Class<T> entityClass;// 接收繼承類別

	@Getter
	private static final Integer maxLimit = 1000;

	public BaseDAO() {
		this.entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	/**
	 * 產生流水號編號 (以日期為前綴)
	 *
	 * @param datePattern 前綴日期格式
	 * @param SerialNoCol 流水號編號屬性名稱
	 * @return 流水號編號
	 */
	public String createSerialNoByDate(String datePattern, String suffix, String SerialNoCol) throws Exception {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		// Create a query object by creating an instance of the CriteriaQuery interface
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		// Set the query Root by calling the from() method on the CriteriaQuery object
		// to define a range variable in FROM clause
		Root<?> root = query.from(entityClass);

		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		String date = sdf.format(new Date());
		// 所有以 yyyyMMdd 起頭的字串 (convert to BigInteger desc)
		Long maxNo = entityManager.createQuery(query.select(cb.max(root.get(SerialNoCol).as(Long.class)))
				.where(cb.like(root.get(SerialNoCol), date + "[0-9]%"))).getSingleResult();

		maxNo = (maxNo != null ? ++maxNo : Long.valueOf(date + suffix));
		return String.valueOf(maxNo);
	}

	/**
	 * 產生流水號編號
	 *
	 * @param SerialNoCol 流水號編號屬性名稱
	 * @return 流水號編號
	 */
	public Long createSerialNo(Long firstNo, String SerialNoCol) throws Exception {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		// Create a query object by creating an instance of the CriteriaQuery interface
		CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);

		// Set the query Root by calling the from() method on the CriteriaQuery object
		// to define a range variable in FROM clause
		Root<?> root = query.from(entityClass);

		// ex 6000000000L
		Integer msgNo = firstNo.intValue();
		Integer maxNO = entityManager.createQuery(query.select(criteriaBuilder.max(root.get(SerialNoCol)))
				.where(criteriaBuilder.ge(root.get(SerialNoCol), msgNo))).getSingleResult();

		msgNo = (maxNO != null ? maxNO : msgNo);
		return (long) (msgNo + 1);
	}

	public void callSP(String SPname) throws Exception {
		StoredProcedureQuery pc = entityManager.createStoredProcedureQuery(SPname);
		pc.execute();
	}

}