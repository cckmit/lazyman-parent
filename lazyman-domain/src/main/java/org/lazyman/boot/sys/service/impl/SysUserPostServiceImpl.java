package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.lazyman.boot.sys.entity.SysPost;
import org.lazyman.boot.sys.entity.SysUserPost;
import org.lazyman.boot.sys.mapper.SysUserPostMapper;
import org.lazyman.boot.sys.service.ISysPostService;
import org.lazyman.boot.sys.service.ISysUserPostService;
import org.lazyman.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户岗位 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Service
public class SysUserPostServiceImpl extends BaseServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {
    @Resource
    private ISysPostService iSysPostService;

    @Override
    public List<Long> listUserPostIds(Long userId) {
        List<Long> postIdList = new ArrayList<>();
        List<SysUserPost> sysUserPostList = list(Wrappers.<SysUserPost>query().lambda().eq(SysUserPost::getUserId, userId));
        if (CollectionUtil.isEmpty(sysUserPostList)) {
            return postIdList;
        }
        postIdList = sysUserPostList.stream().map(SysUserPost::getPostId).distinct().collect(Collectors.toList());
        return postIdList;
    }

    @Override
    public Boolean createUserPost(Long userId, List<Long> postIds) {
        List<SysUserPost> sysUserPostList = list(Wrappers.<SysUserPost>query().lambda().eq(SysUserPost::getUserId, userId));
        if (CollectionUtil.isNotEmpty(sysUserPostList)) {
            removeByIds(sysUserPostList.stream().map(SysUserPost::getId).collect(Collectors.toList()));
        }
        if (CollectionUtil.isEmpty(postIds)) {
            return false;
        }
        List<SysPost> sysPostList = iSysPostService.list(Wrappers.<SysPost>query().lambda().in(SysPost::getId, postIds));
        if (CollectionUtil.isEmpty(sysPostList)) {
            return false;
        }
        sysUserPostList.clear();
        sysPostList.stream().forEach(p -> {
            SysUserPost sysUserPost = new SysUserPost();
            sysUserPost.setUserId(userId);
            sysUserPost.setPostId(p.getId());
            sysUserPostList.add(sysUserPost);
        });
        return saveBatch(sysUserPostList);
    }
}
