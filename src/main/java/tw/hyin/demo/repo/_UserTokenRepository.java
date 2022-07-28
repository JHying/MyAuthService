package tw.hyin.demo.repo;

import tw.hyin.demo.entity._UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;

@RepositoryRestResource(exported = false)
public interface _UserTokenRepository extends JpaRepository<_UserToken, String>, JpaSpecificationExecutor<_UserToken> {

    @Query(value = "select"
            + "	* "
            + " from"
            + "	UserToken ut "
            + "	WHERE ut.USER_ID = :userId"
            + " AND ut.EXPIRED_TIME >= :now", nativeQuery = true)
    public _UserToken findTokenNotExpired(String userId, Date now);

}