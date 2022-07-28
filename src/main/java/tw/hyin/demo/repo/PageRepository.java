package tw.hyin.demo.repo;

import tw.hyin.demo.entity.PageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author H-yin on 2021.
 */
@RepositoryRestResource(exported = false)
public interface PageRepository extends JpaRepository<PageInfo, String> {

    @Query(value =
            "select"
                    + "	*"
                    + " from"
                    + "	PageInfo p "
                    + "	WHERE "
                    + "	p.PAGE_PARENT IS null", nativeQuery = true)
    public List<PageInfo> getParentPages();

    @Query(value =
            "select"
                    + "	*"
                    + " from"
                    + "	PageInfo p "
                    + "	WHERE "
                    + "	p.PAGE_PARENT IS NOT null;", nativeQuery = true)
    public List<PageInfo> getChildPages();

    @Query(value =
            "select"
                    + "	p"
                    + " from"
                    + "	PageInfo p "
                    + "	INNER JOIN "
                    + "		RolePage rp "
                    + " ON "
                    + "		p.pageId = rp.pageId "
                    + "	WHERE "
                    + "	    p.pageParent IS null "
                    + " and "
                    + "     rp.roleId IN :roles ")
    public List<PageInfo> getMyParentPages(List<String> roles);

    @Query(value =
            "select"
                    + "	p"
                    + " from"
                    + "	PageInfo p "
                    + "	INNER JOIN "
                    + "		RolePage rp "
                    + " ON "
                    + "		p.pageId = rp.pageId "
                    + "	WHERE "
                    + "	    p.pageParent IS NOT null "
                    + " and "
                    + "     rp.roleId IN :roles ")
    public List<PageInfo> getMyChildPages(List<String> roles);

    @Query(value =
            "select"
                    + "	p"
                    + " from"
                    + "	PageInfo p "
                    + "	INNER JOIN "
                    + "		RolePage rp "
                    + " ON "
                    + "		p.pageId = rp.pageId "
                    + "	WHERE "
                    + "     rp.roleId IN :roles ")
    public List<PageInfo> getMyAuthPages(List<String> roles);

}
