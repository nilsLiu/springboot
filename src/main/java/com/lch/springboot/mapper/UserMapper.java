package com.lch.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lch.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hai
 * @since 2023-02-11
 */
@Transactional
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
