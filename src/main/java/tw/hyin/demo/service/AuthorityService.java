package tw.hyin.demo.service;

import java.util.List;
import java.util.Map;

import tw.hyin.demo.dto.LoginInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author YingHan on 2021.
 */
@Service
@Transactional
public interface AuthorityService {

	List<String> getMyAuthUrls(LoginInfo loginInfo);

	/**
	 * @author YingHan
	 * @since 2021-12-24
	 *
	 * @Description 更新角色權限表
	 */
	void saveRoleResource(RedisTemplate<String, Object> redisTemplate);


	Map<String, List<String>> getRoleResource();
}
