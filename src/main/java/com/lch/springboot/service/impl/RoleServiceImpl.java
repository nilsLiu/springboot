package com.lch.springboot.service.impl;

import com.lch.springboot.entity.Role;
import com.lch.springboot.mapper.RoleMapper;
import com.lch.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hai
 * @since 2023-02-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
