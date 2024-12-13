package com.evolution.restful.mapper;

import com.evolution.restful.mapper.provider.ProductSqlProvider;
import com.evolution.restful.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Optional;

@Mapper
public interface ProductMapper {

    @SelectProvider(type = ProductSqlProvider.class, method = "selectByIdSql")
    Optional<Product> selectProductById(@Param(value="id") Long id);

}
