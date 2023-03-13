package com.lch.springboot.service;

import com.lch.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lch.springboot.entity.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hai
 * @since 2023-02-11
 */
@Transactional
public interface IUserService extends IService<User> {

    UserDto login(UserDto userDto);

    User register(UserDto userDto);
}
