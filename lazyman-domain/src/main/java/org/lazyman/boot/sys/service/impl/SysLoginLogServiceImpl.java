package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sys.dto.SysLoginLogQueryDTO;
import org.lazyman.boot.sys.entity.SysLoginLog;
import org.lazyman.boot.sys.mapper.SysLoginLogMapper;
import org.lazyman.boot.sys.service.ISysLoginLogService;
import org.lazyman.boot.sys.vo.SysLoginLogVO;
import org.lazyman.common.util.WebUtils;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 系统登录日志 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-03-04
 */
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {
    @Override
    public Long save(String username, Boolean state, String remark) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setState(state);
        sysLoginLog.setRemark(remark);
        sysLoginLog.setIpAddr(WebUtils.getRemoteIpAddr());
        sysLoginLog.setOs(WebUtils.getClientOS());
        sysLoginLog.setBrowser(WebUtils.getClientBrowser());
        save(sysLoginLog);
        return sysLoginLog.getId();
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public Boolean deleteAll() {
        return updateByWrapper(Wrappers.<SysLoginLog>update().lambda().set(SysLoginLog::getIsDeleted, true));
    }

    @Override
    public PageVO<SysLoginLogVO> listByPage(SysLoginLogQueryDTO sysLoginLogQueryDTO) {
        Page<SysLoginLog> sysLoginLogPage = new Page<>(sysLoginLogQueryDTO.getPageNo(), sysLoginLogQueryDTO.getPageSize());
        IPage<SysLoginLogVO> sysLoginLogVOIPage = page(sysLoginLogPage, Wrappers.<SysLoginLog>query().lambda()
                .like(StrUtil.isNotBlank(sysLoginLogQueryDTO.getUsername()), SysLoginLog::getUsername, sysLoginLogQueryDTO.getUsername())
                .like(StrUtil.isNotBlank(sysLoginLogQueryDTO.getIpAddr()), SysLoginLog::getIpAddr, sysLoginLogQueryDTO.getIpAddr())
                .eq(ObjectUtil.isNotEmpty(sysLoginLogQueryDTO.getState()), SysLoginLog::getState, sysLoginLogQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysLoginLogQueryDTO.getBeginTime(), sysLoginLogQueryDTO.getEndTime()), SysLoginLog::getCreateTime, sysLoginLogQueryDTO.getBeginTime(), sysLoginLogQueryDTO.getEndTime())
                .orderByDesc(SysLoginLog::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysLoginLogVO.class));
        return new PageVO<>(sysLoginLogVOIPage.getTotal(), sysLoginLogVOIPage.getRecords());
    }
}
