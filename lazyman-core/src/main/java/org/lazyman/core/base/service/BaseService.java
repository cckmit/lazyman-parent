package org.lazyman.core.base.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface BaseService<T> extends IService<T> {
    /**
     * 单条保存
     *
     * @param entity
     * @return
     */
    @Override
    boolean save(T entity);

    /**
     * 批量保存
     *
     * @param entityList
     * @return
     */
    @Override
    boolean saveBatch(Collection<T> entityList);

    /**
     * 批量保存
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    @Override
    boolean saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 单条更新
     *
     * @param entity
     * @return
     */
    @Override
    boolean updateById(T entity);

    /**
     * 批量更新
     *
     * @param entityList
     * @return
     */
    @Override
    boolean updateBatchById(Collection<T> entityList);

    /**
     * 批量更新
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    @Override
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * 保存或者更新
     *
     * @param entity
     * @return
     */
    @Override
    boolean saveOrUpdate(T entity);

    /**
     * 批量保存或者更新
     *
     * @param entityList
     * @return
     */
    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList);

    /**
     * 批量保存或者更新
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    /**
     * 单条逻辑删除
     *
     * @param id
     * @return
     */
    boolean deleteLogic(@NotNull Long id);

    /**
     * 批量逻辑删除
     *
     * @param idList
     * @return
     */
    boolean deleteLogic(@NotEmpty Collection<Long> idList);

    /**
     * 按照entity非空字段更新
     *
     * @param entity
     * @param updateWrapper
     * @return
     */
    boolean updateByWrapper(T entity, LambdaUpdateWrapper<T> updateWrapper);

    /**
     * 条件更新
     *
     * @param updateWrapper
     * @return
     */
    boolean updateByWrapper(LambdaUpdateWrapper<T> updateWrapper);
}
