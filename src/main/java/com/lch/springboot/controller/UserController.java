package com.lch.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.lch.springboot.common.Constants;
import com.lch.springboot.common.Result;
import com.lch.springboot.entity.dto.UserDto;
import com.lch.springboot.service.IUserService;
import com.lch.springboot.entity.User;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lch.springboot.utils.TokenUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hai
 * @since 2023-02-11
 */
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    //注册
    public Result login(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.login(userDto));
    }

    @PostMapping("/register")
    //注册
    public Result register(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDto));
    }

    @PostMapping

    //新增&修改
    public Result save(@RequestBody User user) {
        //RequestBody:用于接收前端的参数，当我们使用post请求的时候，我们会将参数放在request body
        String sql = "alter table sys_user drop column Id;\n" +
                "alter table sys_user add Id int not null primary key auto_increment first;";
        return Result.success(userService.saveOrUpdate(user)); //新增或更新
    }

    @DeleteMapping("/{id}")
    //根据id删除数据
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }


    @GetMapping
    //查询所有数据
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    //根据id查询
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/page")
    //分页查询
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") Integer id,
                           @RequestParam(defaultValue = "") String age) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (id != null) {
            queryWrapper.like("id", id);
        }
        if (!age.equals("")) {
            queryWrapper.eq("age", age);
        }
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @PostMapping("/del/batch")
    //批量删除
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeByIds(ids));
    }

    /**
     * 导出接口
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有数据
        List<User> list = userService.list();
        //通过工具类创建writer 写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //一次性写出list内对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        //设置浏览器相应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");

        String fileName = URLEncoder.encode("患者信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    /**
     * 导入接口
     *
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result importExcel(MultipartFile file) throws IOException {

        //文件处理成io流
        InputStream in = file.getInputStream();
        //io流给ExcelReader
        ExcelReader excelReader = ExcelUtil.getReader(in);
        List<List<Object>> list = excelReader.read(1);
        List<User> listUser = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();

            String StringAge = row.get(0).toString();
            user.setAge(Double.valueOf(StringAge));
            String StringPSa = row.get(1).toString();
            user.setPsa(Double.valueOf(StringPSa));
            String StringPcaProbability = row.get(2).toString();
            user.setPcaProbability(Double.valueOf(StringPcaProbability));

            listUser.add(user);

//            ****类似一一对应****
        }
        //批量注册进数据库
        userService.saveBatch(listUser);
        return Result.success(true);
    }

}

