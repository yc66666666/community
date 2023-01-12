package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    /*
        这是在测试类中，不像在Controller中一样只要返回逻辑视图名称就行
        需要自己手动的调用Template的模板引擎
    * */
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail(){
        mailClient.sendMail("854521399@qq.com","test","发送text形式的邮件");
        //mailClient.sendMail("568450028@qq.com","balabala","我在用java程序给你发邮件");
    }

    @Test
    public void testHTMLMail(){
        //下面这个对象，用于向request作用域中存username的数据
        Context context = new Context();
        context.setVariable("username","尹超");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("854521399@qq.com","test",content);
    }
}
