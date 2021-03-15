package org.lazyman.boot.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lazyman.boot.sys.dto.SysDeptFormDTO;
import org.lazyman.boot.sys.dto.SysDeptQueryDTO;
import org.lazyman.boot.sys.entity.SysDept;
import org.lazyman.boot.sys.mapper.SysDeptMapper;
import org.lazyman.boot.sys.service.ISysDeptService;
import org.lazyman.boot.sys.vo.SysDeptTreeVO;
import org.lazyman.boot.sys.vo.SysDeptVO;
import org.lazyman.boot.sys.vo.VueElTreeVO;
import org.lazyman.common.constant.CommonErrCode;
import org.lazyman.common.exception.BizException;
import org.lazyman.boot.wrapper.Wrapper;
import org.lazyman.boot.base.dto.StateActionDTO;
import org.lazyman.boot.base.service.impl.BaseServiceImpl;
import org.lazyman.boot.base.vo.PageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统部门 服务实现类
 * </p>
 *
 * @author wanglong
 * @since 2021-02-28
 */
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    @Override
    public Boolean exists(SysDeptFormDTO sysDeptFormDTO) {
        return ObjectUtil.isNotNull(getOne(Wrappers.<SysDept>query().lambda()
                .eq(StrUtil.isNotBlank(sysDeptFormDTO.getDeptName()), SysDept::getDeptName, sysDeptFormDTO.getDeptName())
                .ne(ObjectUtil.isNotNull(sysDeptFormDTO.getId()), SysDept::getId, sysDeptFormDTO.getId())));
    }

    @Override
    public SysDept existsById(Long id) {
        SysDept sysDept = getById(id);
        if (ObjectUtil.isNull(sysDept)) {
            throw new BizException(CommonErrCode.NOT_FOUND);
        }
        return sysDept;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(SysDeptFormDTO sysDeptFormDTO) {
        if (exists(sysDeptFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        SysDept sysDept = BeanUtil.copyProperties(sysDeptFormDTO, SysDept.class);
        save(sysDept);
        return sysDept.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(SysDeptFormDTO sysDeptFormDTO) {
        existsById(sysDeptFormDTO.getId());
        if (exists(sysDeptFormDTO)) {
            throw new BizException(CommonErrCode.REPEAT_CREATE);
        }
        return updateById(BeanUtil.copyProperties(sysDeptFormDTO, SysDept.class));
    }

    @Override
    public SysDeptVO getDetail(Long id) {
        return BeanUtil.copyProperties(existsById(id), SysDeptVO.class);
    }

    @Override
    public Boolean delete(Long[] ids) {
        if (ObjectUtil.isEmpty(ids)) {
            return false;
        }
        return deleteLogic(Arrays.asList(ids));
    }

    @Override
    public PageVO<SysDeptVO> listByPage(SysDeptQueryDTO sysDeptQueryDTO) {
        Page<SysDept> sysDeptPage = new Page<>(sysDeptQueryDTO.getPageNo(), sysDeptQueryDTO.getPageSize());
        IPage<SysDeptVO> sysDeptVOIPage = page(sysDeptPage, Wrappers.<SysDept>query().lambda()
                .like(StrUtil.isNotBlank(sysDeptQueryDTO.getDeptName()), SysDept::getDeptName, sysDeptQueryDTO.getDeptName())
                .eq(ObjectUtil.isNotEmpty(sysDeptQueryDTO.getState()), SysDept::getState, sysDeptQueryDTO.getState())
                .between(StrUtil.isAllNotBlank(sysDeptQueryDTO.getBeginTime(), sysDeptQueryDTO.getEndTime()), SysDept::getCreateTime, sysDeptQueryDTO.getBeginTime(), sysDeptQueryDTO.getEndTime())
                .orderByDesc(SysDept::getCreateTime)).convert(v -> BeanUtil.copyProperties(v, SysDeptVO.class));
        return new PageVO<>(sysDeptVOIPage.getTotal(), sysDeptVOIPage.getRecords());
    }


    @Override
    public Boolean updateState(StateActionDTO stateActionDTO) {
        existsById(stateActionDTO.getId());
        return updateByWrapper(Wrappers.<SysDept>update().lambda().eq(SysDept::getId, stateActionDTO.getId())
                .eq(SysDept::getState, !stateActionDTO.getState())
                .set(SysDept::getState, stateActionDTO.getState()));
    }

    /**
     * 构建树列表
     *
     * @param sysDeptParentIdMap
     * @param sysDeptList
     * @param sysDeptTreeVOList
     */
    private void buildTreeList(Map<Long, List<SysDept>> sysDeptParentIdMap, List<SysDept> sysDeptList, List<SysDeptTreeVO> sysDeptTreeVOList) {
        sysDeptList = sysDeptList.stream().sorted(Comparator.comparing(SysDept::getSort)).collect(Collectors.toList());
        sysDeptList.stream().forEach(p -> {
            SysDeptTreeVO sysDeptTreeVO = BeanUtil.copyProperties(p, SysDeptTreeVO.class);
            sysDeptTreeVOList.add(sysDeptTreeVO);
            List<SysDept> childrens = sysDeptParentIdMap.get(p.getId());
            if (CollectionUtil.isEmpty(childrens)) {
                sysDeptTreeVO.setChildren(new ArrayList<>());
                return;
            }
            List<SysDeptTreeVO> childrensVOList = new ArrayList<>();
            buildTreeList(sysDeptParentIdMap, childrens, childrensVOList);
            sysDeptTreeVO.setChildren(childrensVOList);
        });
    }

    @Override
    public List<SysDeptTreeVO> listByTree(SysDeptQueryDTO sysDeptQueryDTO) {
        List<SysDeptTreeVO> sysDeptTreeVOList = new ArrayList<>();
        List<SysDept> sysDeptList = list(Wrappers.<SysDept>query().lambda()
                .like(StrUtil.isNotBlank(sysDeptQueryDTO.getDeptName()), SysDept::getDeptName, sysDeptQueryDTO.getDeptName())
                .eq(ObjectUtil.isNotEmpty(sysDeptQueryDTO.getState()), SysDept::getState, sysDeptQueryDTO.getState())
                .notIn(CollectionUtil.isNotEmpty(sysDeptQueryDTO.getExcludeIds()), SysDept::getId, sysDeptQueryDTO.getExcludeIds()));
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return sysDeptTreeVOList;
        }
        Map<Long, List<SysDept>> sysDeptParentIdMap = sysDeptList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        List<SysDept> sysDeptRootList = sysDeptParentIdMap.get(CollectionUtil.min(sysDeptParentIdMap.keySet()));
        if (CollectionUtil.isEmpty(sysDeptRootList)) {
            return sysDeptTreeVOList;
        }
        buildTreeList(sysDeptParentIdMap, sysDeptRootList, sysDeptTreeVOList);
        return sysDeptTreeVOList;
    }

    @Override
    public List<VueElTreeVO> listVueElDeptByTree() {
        List<VueElTreeVO> vueElTreeVOList = new ArrayList<>();
        List<SysDept> sysDeptList = list(Wrappers.<SysDept>query().lambda().eq(SysDept::getState, true));
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return vueElTreeVOList;
        }
        Map<Long, List<SysDept>> sysDeptParentIdMap = sysDeptList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        List<SysDept> sysDeptRootList = sysDeptParentIdMap.get(CollectionUtil.min(sysDeptParentIdMap.keySet()));
        if (CollectionUtil.isEmpty(sysDeptRootList)) {
            return vueElTreeVOList;
        }
        buildVueElDeptTreeList(sysDeptParentIdMap, sysDeptRootList, vueElTreeVOList);
        return vueElTreeVOList;
    }

    /**
     * 构建vue路由树列表
     *
     * @param sysDeptParentIdMap
     * @param sysDeptList
     * @param vueElTreeVOList
     */
    private void buildVueElDeptTreeList(Map<Long, List<SysDept>> sysDeptParentIdMap, List<SysDept> sysDeptList, List<VueElTreeVO> vueElTreeVOList) {
        sysDeptList = sysDeptList.stream().sorted(Comparator.comparing(SysDept::getSort)).collect(Collectors.toList());
        sysDeptList.stream().forEach(p -> {
            VueElTreeVO vueElTreeVO = BeanUtil.copyProperties(p, VueElTreeVO.class);
            ((Wrapper<SysDept, VueElTreeVO>) (e, v) -> {
                v.setId(e.getId());
                v.setLabel(e.getDeptName());
            }).wrap(p, vueElTreeVO);
            vueElTreeVOList.add(vueElTreeVO);
            List<SysDept> childrens = sysDeptParentIdMap.get(p.getId());
            if (CollectionUtil.isEmpty(childrens)) {
                vueElTreeVO.setChildren(new ArrayList<>());
                return;
            }
            List<VueElTreeVO> childrensVOList = new ArrayList<>();
            buildVueElDeptTreeList(sysDeptParentIdMap, childrens, childrensVOList);
            vueElTreeVO.setChildren(childrensVOList);
        });
    }

    @Override
    public List<SysDeptVO> listChildrensByParentId(Long parentId) {
        List<SysDept> sysDeptList = list(Wrappers.<SysDept>query().lambda()
                        .eq(SysDept::getParentId, parentId)
                //todo query condition
        );
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return new ArrayList<>();
        }
        sysDeptList = sysDeptList.stream().sorted(Comparator.comparing(SysDept::getSort)).collect(Collectors.toList());
        return sysDeptList.stream().map(m -> BeanUtil.copyProperties(m, SysDeptVO.class)).collect(Collectors.toList());
    }

    /**
     * 递归获取所有部门节点
     *
     * @param deptIds
     * @param childDeptList
     * @param sysDeptParentIdMap
     */
    private void recursionChildrenDepts(List<Long> deptIds, List<SysDept> childDeptList, Map<Long, List<SysDept>> sysDeptParentIdMap) {
        if (CollectionUtil.isEmpty(childDeptList)) {
            return;
        }
        List<Long> childIds = childDeptList.stream().map(SysDept::getId).collect(Collectors.toList());
        deptIds.addAll(childIds);
        childIds.stream().forEach(p -> {
            recursionChildrenDepts(deptIds, sysDeptParentIdMap.get(p), sysDeptParentIdMap);
        });
    }

    @Override
    public List<Long> listByParentId(Long parentId) {
        List<Long> deptIds = new ArrayList<>();
        if (ObjectUtil.isEmpty(parentId)) {
            return deptIds;
        }
        List<SysDept> sysDeptList = list();
        if (CollectionUtil.isEmpty(sysDeptList)) {
            return deptIds;
        }
        deptIds.add(parentId);
        Map<Long, List<SysDept>> sysDeptParentIdMap = sysDeptList.stream().collect(Collectors.groupingBy(e -> e.getParentId()));
        this.recursionChildrenDepts(deptIds, sysDeptParentIdMap.get(parentId), sysDeptParentIdMap);
        return deptIds;
    }
}
