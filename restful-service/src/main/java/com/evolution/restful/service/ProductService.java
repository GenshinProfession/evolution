package com.evolution.restful.service;

import com.evolution.restful.mapper.ProductMapper;
import com.evolution.restful.pojo.Product;
import com.evolution.restful.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class ProductService {

    private final ProductMapper productMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(ProductMapper productMapper,RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.productMapper = productMapper;
    }

    public Product getProductById(Long id) {
        // 1.查询商品,并且抛出异常
        Product product = productMapper.selectProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        // 2.通过RestTemplate远程发起Http请求拿到详细数据
        // 2.1 获取用户id
        Long userId = product.getId();
        // 2.2 url地址,发送请求
        String url = "http://test-service/test/user/" + userId;
        User user = restTemplate.getForObject(url,User.class);

        // 3.封装到Product里面进去
        product.setUser(user);

        // 4.返回
        return product;
    }

}
