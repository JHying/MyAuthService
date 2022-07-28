package tw.hyin.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import tw.hyin.demo.config.KeyConfig;
import tw.hyin.demo.dto.SideNavObj;
import tw.hyin.demo.entity.PageInfo;
import tw.hyin.demo.entity.UserInfo;
import tw.hyin.demo.entity.UserRole;
import tw.hyin.demo.dto.LoginInfo;
import tw.hyin.demo.dto.LoginReq;
import tw.hyin.demo.repo.PageRepository;
import tw.hyin.demo.repo.UserRepository;
import tw.hyin.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.hyin.java.utils.security.AESUtil;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * @author YingHan 2021-11-02
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepo;
    private final PageRepository pageRepo;

    @Autowired
    public LoginServiceImpl(UserRepository userRepo, PageRepository pageRepo) {
        this.userRepo = userRepo;
        this.pageRepo = pageRepo;
    }

    @Override
    public boolean validate(LoginReq loginReq) throws Exception {
        Optional<UserInfo> userInfo = userRepo.findById(loginReq.getUserId());
        if (userInfo.isEmpty())
            return false;
        UserInfo user = userInfo.get();
        String passInDB = AESUtil.decrypt(user.getUserPass().getBytes(), KeyConfig.secretKey);
        //前端使用公鑰加密訊息傳送，後端使用私鑰解密
        String decryptPW = RSAUtil.decrypt(loginReq.getUserPW().getBytes(), KeyConfig.privateKey);
        return passInDB.equals(decryptPW);
    }

    @Override
    public LoginInfo getLoginInfo(LoginReq loginReq) {
        Optional<UserInfo> userInfo = userRepo.findById(loginReq.getUserId());
        if (userInfo.isEmpty())
            return null;
        UserInfo user = userInfo.get();
        return LoginInfo.builder().userId(user.getUserId())
                .userName(user.getUserName())
                .roles(user.getUserRoles()
                        .stream().map(UserRole::getRoleId).collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<SideNavObj> getAllSideNav() {
        List<PageInfo> parentPages = this.pageRepo.getParentPages();
        List<PageInfo> childPages = this.pageRepo.getChildPages();
        List<SideNavObj> sideNavObjs = new ArrayList<>();
        for (PageInfo parentPage : parentPages) {
            SideNavObj sideNavObj = new SideNavObj();
            sideNavObj.setPageIcon(parentPage.getPageIcon());
            sideNavObj.setPageName(parentPage.getPageName());
            sideNavObj.setPageUrl(parentPage.getPageUrl());
            sideNavObj.setChildPages(childPages.stream().filter(
                    pageInfo -> pageInfo.getPageParent().equals(parentPage.getPageId())).collect(Collectors.toList()));
            sideNavObjs.add(sideNavObj);
        }
        return sideNavObjs;
    }

    @Override
    public List<SideNavObj> getMySideNav(List<String> role) {
        List<PageInfo> parentPages = this.pageRepo.getMyParentPages(role);
        List<PageInfo> childPages = this.pageRepo.getMyChildPages(role);
        List<SideNavObj> sideNavObjs = new ArrayList<>();
        for (PageInfo parentPage : parentPages) {
            SideNavObj sideNavObj = new SideNavObj();
            sideNavObj.setPageIcon(parentPage.getPageIcon());
            sideNavObj.setPageName(parentPage.getPageName());
            sideNavObj.setPageUrl(parentPage.getPageUrl());
            sideNavObj.setChildPages(childPages.stream().filter(
                    pageInfo -> pageInfo.getPageParent().equals(parentPage.getPageId())).collect(Collectors.toList()));
            sideNavObjs.add(sideNavObj);
        }
        return sideNavObjs;
    }

}
