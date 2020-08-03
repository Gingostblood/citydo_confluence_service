package cn.gingost.base;

import java.util.List;

/**
 * @author:lezzy
 * @Date:2020/7/30 16:28
 */
public interface BaseMapper<D,E> {

    D toDto(E entity);

    List<D> toDto(List<E> entityList);

    E toEntity(D dto);

    List<E> toEntity(List<D> dtoList);
}
