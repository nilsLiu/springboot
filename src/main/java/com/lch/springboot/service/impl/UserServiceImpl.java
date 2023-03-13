package com.lch.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lch.springboot.common.Constants;
import com.lch.springboot.entity.User;
import com.lch.springboot.entity.dto.UserDto;
import com.lch.springboot.exception.ServiceException;
import com.lch.springboot.mapper.UserMapper;
import com.lch.springboot.service.IUserService;
import com.lch.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hai
 * @since 2023-02-11
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserDto login(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one != null) {
            BeanUtil.copyProperties(one, userDto, true);
            // 设置token
            String token= TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDto.setToken(token);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override


    public User register(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDto, one, true);
            save(one);
        }else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    private User getUserInfo(UserDto userDto){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;

    }
}
