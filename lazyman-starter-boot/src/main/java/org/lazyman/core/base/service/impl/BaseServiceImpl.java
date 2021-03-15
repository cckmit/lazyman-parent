package org.lazyman.core.base.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.util.ThreadLocalUtils;
import org.lazyman.common.util.IDGeneratorUtils;
import org.lazyman.core.base.entity.BaseEntity;
import org.lazyman.core.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    @Override
    public boolean save(T entity) {
        if (ObjectUtil.isNull(entity)) {
            return false;
        }
        this.resolveEntity(entity, true);
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return false;
        }
        entityList.stream().forEach(entity -> {
            this.resolveEntity(entity, true);
        });
        return super.saveBatch(entityList);
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtil.isEmpty(entityList)) {
            return false;
        }
        entityList.stream().forEach(entity -> {
            this.resolveEntity(entity, true);
        });
        return super.saveBatch(entityList, batchSize);
    }

    @Override
    public boolean updateById(T entity) {
        if (ObjectUtil.isNull(entity)) {
            return false;
        }
        this.resolveEntity(entity, false);
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return false;
        }
        entityList.stream().forEach(entity -> {
            this.resolveEntity(entity, false);
        });
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtil.isEmpty(entityList)) {
            return false;
        }
        entityList.stream().forEach(entity -> {
            this.resolveEntity(entity, false);
        });
        return super.updateBatchById(entityList, batchSize);
    }


    @Override
    public boolean saveOrUpdate(T entity) {
        if (ObjectUtil.isNull(entity)) {
            return false;
        }
        return ObjectUtil.isNull(entity.getId()) ? this.save(entity) : this.updateById(entity);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        if (CollectionUtil.isEmpty(entityList)) {
            return false;
        }
        entityList.stream().forEach(entity -> {
            this.resolveEntity(entity, ObjectUtil.isNull(entity.getId()));
        });
        return super.saveOrUpdateBatch(entityList);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (CollectionUtil.isEmpty(entityList)) {
            return false;
        }
        entityList.stream().forEach(entity -> {
            this.resolveEntity(entity, ObjectUtil.isNull(entity.getId()));
        });
        return super.saveOrUpdateBatch(entityList, batchSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLogic(@NotNull Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        T entity = ReflectUtil.newInstance(this.currentModelClass());
        entity.setId(id);
        return this.updateById(entity) && removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLogic(Collection<Long> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return false;
        }
        List<T> list = new ArrayList();
        idList.forEach(id -> {
            T entity = ReflectUtil.newInstance(this.currentModelClass());
            entity.setId(id);
            entity.setIsDeleted(true);
            this.resolveEntity(entity, false);
            list.add(entity);
        });
        return updateBatchById(list) && removeByIds(idList);
    }

    @Override
    public boolean updateByWrapper(T entity, LambdaUpdateWrapper<T> updateWrapper) {
        if (ObjectUtil.isNull(updateWrapper)) {
            return false;
        }
        Long userId = ThreadLocalUtils.getCurrentUserId();
        if (ObjectUtil.isNotEmpty(userId)) {
            entity.setUpdateBy(userId);
        } else {
            entity.setUpdateBy(0L);
        }
        entity.setUpdateTime(DateUtil.date().toJdkDate());
        return update(entity, updateWrapper);
    }

    /**
     * 更新
     *
     * @param updateWrapper
     * @return
     */
    @Override
    public boolean updateByWrapper(LambdaUpdateWrapper<T> updateWrapper) {
        if (ObjectUtil.isNull(updateWrapper)) {
            return false;
        }
        Long userId = ThreadLocalUtils.getCurrentUserId();
        if (ObjectUtil.isNotEmpty(userId)) {
            updateWrapper.set(BaseEntity::getUpdateBy, userId);
        } else {
            updateWrapper.set(BaseEntity::getUpdateBy, 0L);
        }
        updateWrapper.set(BaseEntity::getUpdateTime, DateUtil.date().toJdkDate());
        return update(updateWrapper);
    }

    private void resolveEntity(T entity, boolean isNew) {
        if (isNew) {
            //初始化ID
            if (Objects.isNull(entity.getId())) {
                entity.setId(IDGeneratorUtils.getInstance().nextId());
            }
            //创建人
            Long userId = ThreadLocalUtils.getCurrentUserId();
            if (ObjectUtil.isNotEmpty(userId)) {
                entity.setCreateBy(userId);
            } else {
                //系统创建
                entity.setCreateBy(0L);
            }
            //创建时间
            if (ObjectUtil.isNull(entity.getCreateTime())) {
                entity.setCreateTime(DateUtil.date().toJdkDate());
            }
            //删除状态
            entity.setIsDeleted(false);
        } else {
            //初始化更新人
            Long userId = ThreadLocalUtils.getCurrentUserId();
            if (ObjectUtil.isNotEmpty(userId)) {
                entity.setUpdateBy(userId);
            } else {
                entity.setUpdateBy(0L);
            }
            //初始化更新时间
            entity.setUpdateTime(DateUtil.date().toJdkDate());
        }
    }
}
