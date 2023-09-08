package com.bg.commons.convert;

import java.util.List;

/**
 * 属性映射-基类
 *
 * @author jiewus
 * @since 2023/09/05
 */
public interface BaseConvertMapper<D, E> {

  /**
   * DTO转entity
   *
   * @param dto
   * @return entity
   */
  E toEntity(D dto);

  /**
   * entity转DTO
   *
   * @param entity
   * @return dto
   */
  D toDto(E entity);

  /**
   * DTO集合转Entity集合
   *
   * @param dtoList dtoList
   * @return
   */
  List<E> toEntity(List<D> dtoList);

  /**
   * Entity集合集合转DTO
   *
   * @param entityList entityList
   * @return
   */
  List<D> toDto(List<E> entityList);

}
