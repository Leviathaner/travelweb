package com.twentytwo.travelweb.service;

import com.twentytwo.travelweb.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    List<ProductInfo> getAllProductInfo();
    Integer deleteProduct(Integer product_id);
    Integer updateProduct(Product product);
    Product getProductById(Integer product_id);
    List<Product> getProductClickSum();
    List<ProductCom> getComClickSum();
    List<ProductInfo> getProductInfoByComId(String com_id);
    Integer addPorduct(Product product);
    ProductInfo findOneProductInfo(Integer product_id);
    Integer addIntoOrder(Order order);
    PageBean<Product> pageQuery(int category_id, int currentPage, int pageSize, String name);
    Product findOneRoute(int product_id);
    List<Product> clickFourRank();

    List<Product> theNewFour();

    int findTotalRoute();

    List<Product> findRandFourRoute(int one,int two,int three,int four);

    List<NewsInfo> newsList();

    List<Product> getProductClickByComId(String com_id);
}
