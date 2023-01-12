package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 工具类，用于生成随机字符串，以及MD5加密算法
 */
public class CommunityUtil {

    // 注册时生成激活码，上传头像功能，上传文件的功能，上传的时候需要给其一个随机字符串
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5加密
    //注册的时候提交的密码是明文，需要经过MD5加密算法再存到数据库里面
    //MD5加密算法的特点：1.只能加密，不能解密 2.同一个字符串，每次经过MD5算法加密后得到的都是同一个
    //为了进一步提高安全性，需要在你传入的密码的后面，再追加几位的随机salt进行拼接，然后再进行MD5加密，这样更加不容易破解
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
