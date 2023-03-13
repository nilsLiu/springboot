package com.lch.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import com.lch.springboot.common.Result;
import com.lch.springboot.entity.User;
import com.lch.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();

        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        Map<String, Object> map = new HashMap<>();
        ArrayList<Object> ageList = CollUtil.newArrayList();
        ArrayList<Object> psaLiat = CollUtil.newArrayList();
        for (User user : list) {
            ageList.add(user.getAge());
            psaLiat.add(user.getPsa());
        }
        map.put("age", ageList);
        map.put("psa", psaLiat);
        return Result.success(map);
    }
}
