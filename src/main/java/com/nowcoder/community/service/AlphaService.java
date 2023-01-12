package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Scope("singleton")
//@Scope("prototype")
//默认是"singleton"单例的  写成"prototype"表示，每获取一次这个对象，就往容器中添加一个新的
/*
    默认不写是"singleton"单例的  写成"prototype"表示，每获取一次这个对象，就往容器中添加一个新的

    "singleton"单例的，对象的创建时机是，容器初始化完成后，对象就会加到容器里面，对应的是在主启动类加载的时候，就会初始化这个bean
    "prototype"，对象的创建时机是，当你调用applicationContext.getBean("alphaService", AlphaService.class);才会创建这个对象

* */
public class AlphaService {

    public AlphaService() {
        System.out.println("AlphaService初始化完成");
    }

    @PostConstruct
    public void init(){
        System.out.println("在AlphaService创建完成后，构造器被调用后，调用init()方法完成初始化");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("在AlphaService被销毁前，调用destroy()方法，完成资源的销毁");
    }

    @Autowired
    public AlphaDao alphaDao;

    public String find(){
        return alphaDao.select();
    }
}
