package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController("adminReportController")
@RequestMapping("/admin/report")
@Api(tags = "数据统计相关接口")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额数据统计")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate begin,
                                                       @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("营业额数据统计：{}到{}", begin, end);
        TurnoverReportVO turnoverReportVO = reportService.getOrderStatistics(begin, end);
        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserReportVO> userStatistics(@DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("用户数据统计：{}到{}", begin, end);
        UserReportVO userReportVO = reportService.getUserStatistics(begin, end);
        return Result.success(userReportVO);
    }
    /**
     * 订单统计
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> ordersStatistics(@DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate begin,
                                                  @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("订单数据统计：{}到{}", begin, end);
        OrderReportVO ordersStatistics = reportService.getOrdersStatistics(begin, end);
        return Result.success(ordersStatistics);
    }

    @GetMapping("/top10")
    @ApiOperation("查询top10")
    public Result<SalesTop10ReportVO> top10(@DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate begin,
                                            @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("查询top10：{}到{}", begin, end);
        return Result.success(reportService.getTop10(begin, end));
    }
}
