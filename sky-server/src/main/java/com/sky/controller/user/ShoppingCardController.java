package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userShoppingCardController")
@RequestMapping("/user/shoppingCart")
@Api(tags = "C端-购物车接口")
@Slf4j
public class ShoppingCardController {

    @Autowired
    private ShoppingCardService shoppingCardService;

    /**
     * 向购物车添加商品
     */
    @PostMapping("/add")
    @ApiOperation("向购物车添加商品")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车...{}",shoppingCartDTO);
        shoppingCardService.add(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车
     */
    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车...");
        List<ShoppingCart> list = shoppingCardService.show();
        return Result.success(list);
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean() {
        log.info("清空购物车...");
        shoppingCardService.clean();
        return Result.success();
    }

    /**
     * 删除购物车中一个商品
     */
    @PostMapping("/sub")
    @ApiOperation("删除购物车中一个商品")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("删除购物车中一个商品...{}",shoppingCartDTO);
        shoppingCardService.sub(shoppingCartDTO);
        return Result.success();
    }

}
