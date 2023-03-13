package com.lch.springboot.service.impl;

import com.lch.springboot.entity.Menu;
import com.lch.springboot.mapper.MenuMapper;
import com.lch.springboot.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
