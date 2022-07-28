package tw.hyin.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.hyin.demo.entity.UserInfo;

/**
 * @author rita6 on 2021.
 */
@Service
@Transactional
public interface UserService {

	/**
	 * 
	 * @author YingHan
	 * @since 2021-12-16
	 * 
	 * @Description 建立使用者
	 */
    public void create(UserInfo userInfo);

}
