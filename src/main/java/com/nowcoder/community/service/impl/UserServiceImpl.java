package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService, CommunityConstant {

    @Autowired
    private UserMapper userMapper;

    //注册的时候需要发邮件，还需要模板引擎，以及域名和项目的contextPath
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public User findUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();

        //空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }

        //验证账号在数据库中是否存在
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }

        User u2 = userMapper.selectByEmail(user.getEmail());
        if (u2 != null) {
            map.put("emailMsg", "该邮箱已被注册!");
            return map;
        }

        //注册用户，就是将用户插入到数据库中
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);//刚注册还没有激活
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //发送邮件，激活邮件
        //下面这个对象，用于向request作用域中存username的数据
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        //处理激活的请求地址，需要随着不同用户进行不用的拼接改变
        // http://localhost:8080 + /community + /activation + /userId + /激活码
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);

        return map;
    }

    @Override
    public int activation(Integer userId,String code){
        User user = userMapper.selectById(userId);
        if(user.getStatus()==1){
            return ACTIVATION_REPEAT;
        } else if(user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        } else{
            return ACTIVATION_FAILURE;
        }
    }
}
