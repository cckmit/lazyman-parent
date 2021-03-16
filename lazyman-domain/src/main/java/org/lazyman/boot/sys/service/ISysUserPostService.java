package org.lazyman.boot.sys.service;

import org.lazyman.boot.base.service.BaseService;
import org.lazyman.boot.sys.entity.SysUserPost;

import java.util.List;

/**
 * <p>
 * 系统用户岗位 服务类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
public interface ISysUserPostService extends BaseService<SysUserPost> {
    /**
     * 查询用户岗位ID列表
     *
     * @param userId
     * @return
     */
    List<Long> listUserPostIds(Long userId);

    /**
     * 创建用户岗位
     *
     * @param userId
     * @param postIds
     */
    Boolean createUserPost(Long userId, List<Long> postIds);
}
