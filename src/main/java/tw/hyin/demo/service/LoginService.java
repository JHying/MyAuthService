package tw.hyin.demo.service;

import tw.hyin.demo.dto.LoginInfo;
import tw.hyin.demo.dto.LoginReq;
import tw.hyin.demo.dto.SideNavObj;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rita6 on 2021.
 */
@Service
@Transactional
public interface LoginService {

	/**
	 * 
	 * @author YingHan
	 * @since 2021-12-16
	 * 
	 * @Description 身分驗證
	 */
    public boolean validate(LoginReq loginReq) throws Exception;

    /**
     * 
     * @author YingHan
     * @since 2021-12-16
     * 
     * @Description 取得登入資訊
     */
    public LoginInfo getLoginInfo(LoginReq loginReq) throws Exception;

    public List<SideNavObj> getAllSideNav();

	List<SideNavObj> getMySideNav(List<String> role);
}
