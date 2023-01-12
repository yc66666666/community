package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        //作为方法的形参，DispatcherServlet在调用getIndexPage()方法的时候，会自动的实例化好Model和Page对象
        //同时会将Page对象加入到Model中，所以可以在thymeleaf中直接访问page对象
        page.setRows(discussPostService.findDiscussPostsRows(0));
        page.setPath("/index");

        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0, page.getStartIndex(), page.getLimit());
        List<Map<String,Object>> discussPostsAndUsers = new ArrayList<>();
        if (discussPosts != null) {
            for(DiscussPost discussPost : discussPosts){
                Map<String,Object> map = new HashMap<>();
                map.put("post",discussPost);
                User user = userService.findUserById(discussPost.getUserId());
                map.put("user",user);
                discussPostsAndUsers.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPostsAndUsers);
        return "index";
    }
}
