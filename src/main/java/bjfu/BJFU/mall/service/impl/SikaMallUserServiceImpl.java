
package bjfu.BJFU.mall.service.impl;

import bjfu.BJFU.mall.common.Constants;
import bjfu.BJFU.mall.common.ServiceResultEnum;
import bjfu.BJFU.mall.controller.vo.SikaMallUserVO;
import bjfu.BJFU.mall.dao.MallUserMapper;
import bjfu.BJFU.mall.entity.MallUser;
import bjfu.BJFU.mall.service.SikaMallUserService;
import bjfu.BJFU.mall.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SikaMallUserServiceImpl implements SikaMallUserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Override
    public PageResult getSikaUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            SikaMallUserVO sikaMallUserVO = new SikaMallUserVO();
            BeanUtil.copyProperties(user, sikaMallUserVO);
            //设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, sikaMallUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public SikaMallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        SikaMallUserVO userTemp = (SikaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            userFromDB.setNickName(SikaMallUtils.cleanString(mallUser.getNickName()));
            userFromDB.setAddress(SikaMallUtils.cleanString(mallUser.getAddress()));
            userFromDB.setIntroduceSign(SikaMallUtils.cleanString(mallUser.getIntroduceSign()));
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                SikaMallUserVO sikaMallUserVO = new SikaMallUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, sikaMallUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, sikaMallUserVO);
                return sikaMallUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }

    @Override
    public MallUser getUserById(Long userId) {
        return mallUserMapper.selectByPrimaryKey(userId);
    }


}
