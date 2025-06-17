package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id查询对应的口味是否存在
     * @param id
     * @return
     */
    @Select("select count(*) from dish_flavor where dish_id = #{id}")
    Long countByDishId(Long id);
    /**
     * 根据菜品id删除对应的口味
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);

    /**
     * 根据菜品id批量删除对应的口味,sql语句中foreach循环
     * @param ids
     */
    void deleteByDishIds(List<Long> ids);

    /**
     * 根据菜品id查询对应的口味
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
