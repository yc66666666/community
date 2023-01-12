package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
//注意新的2.7.6的springboot的不需要@RunWith注解，我没有加@ContextConfiguration，也对，但是在2.1.5中不行
public class CommunityApplicationTests implements ApplicationContextAware {//实现了ApplicationContextAware接口后，需要实现setApplicationContext，形参中就可获得容器对象

    private ApplicationContext applicationContext;//用成员变量保存起来

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testApplicationContext() {
        System.out.println(applicationContext);

        AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
        System.out.println(alphaDao.select());

        alphaDao = applicationContext.getBean("alphaDaoHibernate",AlphaDao.class);
        System.out.println(alphaDao.select());
    }

    @Test
    public void testBeanManagement(){
//        AlphaService alphaService = applicationContext.getBean("alphaService", AlphaService.class);
//        System.out.println(alphaService);
//
//        alphaService = applicationContext.getBean("alphaService", AlphaService.class);
//        System.out.println(alphaService);
    }

    @Test
    public void testBeanConfig(){
        SimpleDateFormat simpleDateFormat =
                applicationContext.getBean("simpleDateFormat",SimpleDateFormat.class);
        System.out.println(simpleDateFormat.format(new Date()));
    }

    @Autowired
    @Qualifier(value = "alphaDaoHibernate")
    AlphaDao alphaDao;

    @Autowired
    AlphaService alphaService;

    @Autowired
    SimpleDateFormat simpleDateFormat;

    @Test
    public void testDI(){
        System.out.println(alphaDao.select());
        System.out.println(alphaService);
        System.out.println(simpleDateFormat);
    }
}
