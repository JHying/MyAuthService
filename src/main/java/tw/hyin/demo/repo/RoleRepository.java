package tw.hyin.demo.repo;

import java.util.List;

import tw.hyin.demo.entity.PageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import tw.hyin.demo.dto.i.IRolePageList;

/**
 * @author H-yin on 2021.
 */
@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<PageInfo, String> {

	/**
	 * 
	 * @author YingHan
	 * @since 2021-12-22
	 * 
	 * @Description 取得角色權限對應表 (自定義輸出結果：需使用 interface 建構 get method for all attributes)
	 */
	//You need to define an interface that defines a getter method for each attribute your projection shall contain.
	//At runtime, Spring Data JPA then generates a class that implements that interface.
	@Query(value = "select"
			+ "		rps.ROLE_ID as roleId,"
			+ "		pi2.PAGE_URL as pageUrl"
			+ " from"
			+ "	(select "
			+ "		ri.ROLE_ID,"
			+ "		rp.PAGE_ID"
			+ "	from"
			+ "	RoleInfo ri "
			+ "	inner join RolePage rp ON ri.ROLE_ID = rp.ROLE_ID) AS rps"
			+ "	left join PageInfo pi2 on rps.PAGE_ID = pi2.PAGE_ID;", nativeQuery = true)
	public List<IRolePageList> getRolePageList();
	
}
