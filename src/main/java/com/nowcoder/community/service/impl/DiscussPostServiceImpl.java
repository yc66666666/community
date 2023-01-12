package com.nowcoder.community.service.impl;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Override
    public List<DiscussPost> findDiscussPosts(Integer userId, int startIndex, int length) {
        return discussPostMapper.selectDiscussPosts(userId,startIndex,length);
    }

    @Override
    public int findDiscussPostsRows(Integer userId) {
        return discussPostMapper.selectDiscussPostsRows(userId);
    }
}
