package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import com.sky.vo.TurnoverReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);

    /**
     * 分页查看历史订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    void update(Orders orders);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    OrderVO getById(Long id);

    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);

    /**
     * 营业额统计
     * @param dateList
     * @return
     */
    List<Double> getTurnoverStatistics(List<LocalDate> dateList, Integer status);

    /**
     * 每日订单数统计
     * @param dateList
     * @return
     */
    List<Integer> getOrderCountStatistics(List<LocalDate> dateList);

    /**
     * 每日有效订单数统计
     * @param dateList
     * @return
     */
    List<Integer> getValidOrderCountStatistics(List<LocalDate> dateList, Integer status);

    /**
     * 根据条件统计订单数量
     * @param map
     * @return
     */

    Integer countByMap(Map map);

    /**
     * 根据条件统计订单总金额
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 查询Top10
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
