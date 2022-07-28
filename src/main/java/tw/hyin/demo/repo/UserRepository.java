package tw.hyin.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import tw.hyin.demo.entity.UserInfo;

/**
 * @author H-yin on 2021.
 */
@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<UserInfo, String> {

}
