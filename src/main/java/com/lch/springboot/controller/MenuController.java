package com.lch.springboot.controller;

import com.lch.springboot.common.Result;
import com.lch.springboot.service.IMenuService;
import com.lch.springboot.entity.Menu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hai
 * @since 2023-02-20
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping
    //新增&修改
    public Result save(@RequestBody Menu menu) {
        //RequestBody:用于接收前端的参数，当我们使用post请求的时候，我们会将参数放在request body
        return Result.success(menuService.saveOrUpdate(menu)); //新增或更新
    }

    @DeleteMapping("/{id}")
    //根据id删除数据
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }


    @GetMapping
    public Result findAll( @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        //查询所有数据
        List<Menu> list = menuService.list(queryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentNode) {
            //筛选所有数据中pid=父级id的数据为二级菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return Result.success(parentNode);
    }

    @GetMapping("/{id}")
    //根据id查询
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    //分页查询
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (name != null) {
            queryWrapper.like("name", name);
        }
        return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/del/batch")
    //批量删除
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(menuService.removeByIds(ids));
    }

}

