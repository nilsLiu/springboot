package com.lch.springboot.controller;

import com.lch.springboot.common.Result;
import com.lch.springboot.entity.User;
import com.lch.springboot.service.IRoleService;
import com.lch.springboot.entity.Role;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @PostMapping
    //新增&修改
    public Result save(@RequestBody Role role) {
        //RequestBody:用于接收前端的参数，当我们使用post请求的时候，我们会将参数放在request body
        return Result.success(roleService.saveOrUpdate(role)); //新增或更新
    }

    @DeleteMapping("/{id}")
    //根据id删除数据
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }


    @GetMapping
    //查询所有数据
    public Result findAll() {
        return Result.success(roleService.list());
    }

    @GetMapping("/{id}")
    //根据id查询
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    @GetMapping("/page")
    //分页查询
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (name != null) {
            queryWrapper.like("name", name);
        }
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/del/batch")
    //批量删除
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeByIds(ids));
    }

}

