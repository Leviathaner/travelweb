package com.twentytwo.travelweb.serviceimpl;

import com.twentytwo.travelweb.entity.Product;
import com.twentytwo.travelweb.entity.ProductInfo;
import com.twentytwo.travelweb.mapper.ProductMapper;
import com.twentytwo.travelweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public List<ProductInfo> getAllProductInfo() {
        return productMapper.getAllProductInfo();
    }

    @Override
    public Integer deleteProduct(Integer product_id) {
        return productMapper.deleteProduct(product_id);
    }

    @Override
    public Integer updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    @Override
    public Product getProductById(Integer product_id) {
        return productMapper.getProductById(product_id);
    }
}