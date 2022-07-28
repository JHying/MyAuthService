package tw.hyin.demo.service.impl;

import tw.hyin.demo.config.KeyConfig;
import tw.hyin.demo.entity.UserInfo;
import tw.hyin.demo.repo.UserRepository;
import tw.hyin.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * 
 * @author YingHan 2021-11-02
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userReopsitory;

	@Autowired
	public UserServiceImpl(UserRepository userReopsitory) {
		this.userReopsitory = userReopsitory;
	}

	@SneakyThrows
	@Override
	public void create(UserInfo userInfo) {
		//前端使用公鑰加密訊息傳送，後端使用私鑰解密
		String decryptPW = RSAUtil.decrypt(userInfo.getUserPass().getBytes(), KeyConfig.privateKey);
		// 密碼使用 AES 轉為密文
		String encryptPass = AESUtil.encrypt(decryptPW, KeyConfig.secretKey);
		userInfo.setUserPass(encryptPass);
		userReopsitory.save(userInfo);
	}

}
