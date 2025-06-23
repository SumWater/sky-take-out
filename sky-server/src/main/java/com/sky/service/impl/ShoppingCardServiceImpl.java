package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCardMapper;
import com.sky.service.SetmealService;
import com.sky.service.ShoppingCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCardServiceImpl implements ShoppingCardService {

    @Autowired
    private ShoppingCardMapper shoppingCardMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //1.判断当前商品是否在购物车中
        ShoppingCart cartDTO = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,cartDTO);
        Long userId = BaseContext.getCurrentId();
        cartDTO.setUserId(userId);
        List<ShoppingCart> list = shoppingCardMapper.list(cartDTO);

        //2.如果已经存在，则增加数量
        if (list != null && !list.isEmpty()) {
            ShoppingCart shoppingCart = list.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCardMapper.update(shoppingCart);
        }else{
            //3.如果不存在，则添加到购物车，数量默认为1
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null){
                Dish dish = dishMapper.getById(dishId);
                cartDTO.setName(dish.getName());
                cartDTO.setImage(dish.getImage());
                cartDTO.setAmount(dish.getPrice());
            }else{
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                cartDTO.setName(setmeal.getName());
                cartDTO.setImage(setmeal.getImage());
                cartDTO.setAmount(setmeal.getPrice());
            }
            cartDTO.setNumber(1);
            cartDTO.setCreateTime(LocalDateTime.now());
            shoppingCardMapper.insert(cartDTO);

        }
    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> show() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        return shoppingCardMapper.list(shoppingCart);
    }

    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCardMapper.deleteByUserId(userId);
    }
}
