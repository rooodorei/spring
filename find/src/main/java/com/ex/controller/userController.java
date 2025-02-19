package com.ex.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.poji.user;

import cn.hutool.core.io.IoUtil;

// 用户信息Controller
@RestController
public class userController {

    @RequestMapping("/list")
    public List list() throws Exception {
        // 1.加载并读取用户数据
        // InputStream in = new FileInputStream(new
        // File("D:\\WORK\\spring\\find\\src\\main\\resources\\user.txt"));
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("user.txt");

        ArrayList<String> lines = IoUtil.readLines(in, StandardCharsets.UTF_8, new ArrayList<>());
        // 2.解析用户信息，list集合
        List<user> userList = lines.stream().map(line -> {
            String[] parts = line.split(",");
            Integer id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            String name = parts[3];
            Integer age = Integer.parseInt(parts[4]);
            LocalDateTime upDateTime = LocalDateTime.parse(parts[5],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new user(id, username, password, name, age, upDateTime);
        }).collect(Collectors.toList());
        // 返回数据
        return userList;
    }
}
