package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {


    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(*) from dish where category_id = #{categoryId}")
    Long countByCategoryId(Integer categoryId);

    /**
     *  新增菜品，同时需要保存菜品对应的口味数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     */
    Page<Dish> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id删除菜品数据
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 根据ids批量删除菜品数据，在sql语句中使用foreach循环
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /* *
     * 根据id修改菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据id查询菜品
     * @param dish
     * @return
     * */
    @Select("select * from dish where category_id = #{categoryId} and status = 1")
    List<Dish> list(Dish dish);
}
