package com.evolution.restful.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ProductSqlProvider {

    public String selectByIdSql(Map<String, Object> para){
        return new SQL(){{
            SELECT("*");
            FROM("product");
            WHERE("id = " + para.get("id"));
        }}.toString();
    }

}
