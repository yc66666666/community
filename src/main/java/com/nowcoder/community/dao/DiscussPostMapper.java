package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    /*
        这里或者是以后定义方法的时候，需要定义考虑扩展性
        对于这个查询讨论帖的查询方法，今后还要适用于某个用户查询自己发布的帖子，另外还要考虑分页的功能\

        limit startIndex,length  或者是 limit length（默认开始的序号是0）

        select * from user limit 2,3;//表示查询从2这个索引开始，查询三条数据
    * */
    List<DiscussPost> selectDiscussPosts(@Param("userId") Integer userId,@Param("startIndex") int startIndex,@Param("length") int length);

    /*
        这里的@Param要不要加的问题，在不同的mybatis版本中不同，在2.3.0中，不加也行，在老师的这个2.0.1中，必须加，不然报错
    * */
    int selectDiscussPostsRows(@Param("userId") Integer userId);
}
