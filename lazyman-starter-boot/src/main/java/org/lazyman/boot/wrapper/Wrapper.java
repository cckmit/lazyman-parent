package org.lazyman.boot.wrapper;

/**
 * 用于对象之间包装，转换
 *
 * @param <E>
 * @param <R>
 */
public interface Wrapper<E, R> {
    /**
     * 包装，转换方法
     *
     * @param e
     * @param r
     */
    void wrap(E e, R r);
}
