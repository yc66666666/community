package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    public AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "hello springboot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求的数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        System.out.println(request.getContextPath());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " :: " +value);
        }
        System.out.println(request.getParameter("code"));

        //返回响应的数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter out = response.getWriter()
        ){
            out.write("<h2>牛客网</h2>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //get请求，获取多个学生
    // /students?current=1&limit=10
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") Integer current,
            @RequestParam(name = "limit",required = false,defaultValue = "10") Integer limit){
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }
    /*
        @RequestParam注解，可以使用的required属性来表示这个形参是不是必须传值，required = false 且没有defaultValue的情况下，不传这个参数就会报400错误
        另外有默认值的情况下，required失效，
    * */

    //get请求，获取单个学生
    //
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(
            @PathVariable("id") Integer id){
        System.out.println(id);
        return "a student";
    }

    /*
        上面的两种传参的方式，
            第一种是 /students?current=1&limit=10的方式，只要 name和方法形参名一致，就能自动的获取到，不需要@RequestParam注解，这个注解只是为了方便处理请求参数名和方法的形参名不一样的情况，以及设置默认值，允不允许有该参数不传
            第二种是 /student/123，采用restful风格的传参方式，这时候，必须采用@PathVariable，否则就算路径名和形参名一致，也不能自动的赋值
    * */


    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,Integer age){//这里也可以采用前面学过的@RequestParam注解来进行额外的设置
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    /*
        上面的两个都是请求（不论是get还是post），携带的参数，怎么被控制器方法接收
        讲了两种：?name=value&name=value的方式，需要name和控制器方法的形参名一致，若是post请求，表单的input标签的name属性需要和控制器方法的形参名一致。另外也可以使用@RequestParam进行额外的设置
                /student/1223，采用restful风格的传参的方式，需要使用@PathVariable进行说明，

        下面要讲的是怎么向浏览器响应数据，前面都是@ResponseBody + 返回值String的方式，返回一个字符串
    * */
    //响应html
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",20);

        mav.setViewName("/demo/view");
        return mav;
    }
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","北京大学");
        model.addAttribute("age",80);
        return "/demo/view";
    }
    //响应json数据。异步请求之中
    //java对象 -> JSON字符串 -> JS对象
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",21);
        emp.put("salary",8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",21);
        emp.put("salary",8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李四");
        emp.put("age",25);
        emp.put("salary",7500.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age",29);
        emp.put("salary",18000.00);
        list.add(emp);

        return list;
    }
}
