package com.twentytwo.travelweb.controller;

import com.twentytwo.travelweb.entity.*;
import com.twentytwo.travelweb.mapper.NewsMapper;
import com.twentytwo.travelweb.service.CompanyService;
import com.twentytwo.travelweb.service.NewsService;
import com.twentytwo.travelweb.service.OrderService;
import com.twentytwo.travelweb.service.ProductService;
import com.twentytwo.travelweb.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    NewsService newsService;

    @GetMapping("index")
    public String indexPage(){
        return "background/com_index";
    }

    @RequestMapping("/getProductClickCount")
    @ResponseBody
    public List<Product> getProductClickCount(HttpServletRequest request){
        List<Product> pro_click_count=productService.getProductClickByComId(request.getSession().getAttribute("user").toString());

        return pro_click_count;
    }

    @RequestMapping("/getOrderCount")
    @ResponseBody
    public List<Sales> getOrderCountByCom(HttpServletRequest request){
        List<Sales> order_count=orderService.getOrderSumByComId(request.getSession().getAttribute("user").toString());

        return order_count;
    }

    @RequestMapping("/getOrderPriceSum")
    @ResponseBody
    public List<OrderSumPrice> getOrderPriceSumByCom(HttpServletRequest request){
        List<OrderSumPrice> order_count=orderService.getOrderPriceSumByComId(request.getSession().getAttribute("user").toString());

        return order_count;
    }

    @RequestMapping("/getOrderSumBySexCom")
    @ResponseBody
    public List<UserOrderBySex> getOrderSumBySexCom(HttpServletRequest request){
        List<UserOrderBySex> sales=orderService.getUserOrderBySexCom(request.getSession().getAttribute("user").toString());

        return sales;
    }

    @RequestMapping("/getOrderSumByJobCom")
    @ResponseBody
    public List<UserSumByJob> getOrderSumByJobCom(HttpServletRequest request){
        List<UserSumByJob> sales=orderService.getOrderSumByJobCom(request.getSession().getAttribute("user").toString());

        return sales;
    }

    @RequestMapping("/getComProductCount")
    @ResponseBody
    public List<OrderByMonth> getComProductCount(@RequestParam(value = "product_id",defaultValue = "0") int product_id, HttpServletRequest request){
        List<OrderByMonth> product_count=new ArrayList<>();
        if(product_id==0) {
            product_count = orderService.getSumByMonthCom(request.getSession().getAttribute("user").toString());
        }else{
            product_count=orderService.getSumByMonthByPro(product_id);
        }
        return product_count;
    }

    @RequestMapping("/getComPriceSum")
    @ResponseBody
    public List<OrderPriceByMonth> getComPriceSum(@RequestParam(value = "product_id",defaultValue = "0") int product_id, HttpServletRequest request){
        List<OrderPriceByMonth> product_count=new ArrayList<>();
        if(product_id==0) {
            product_count = orderService.getOrderSumPriceByComMonth(request.getSession().getAttribute("user").toString());
        }else{
            product_count=orderService.getPriceSumByMonthPro(product_id);
        }
        return product_count;
    }

    @RequestMapping("/getComProductCountBySex")
    @ResponseBody
    public List<UserOrderBySex> getComProductCountBySex(@RequestParam(value = "product_id",defaultValue = "0") int product_id, HttpServletRequest request){
        List<UserOrderBySex> product_count=new ArrayList<>();
        if(product_id==0) {
            product_count = orderService.getUserOrderBySexCom(request.getSession().getAttribute("user").toString());
        }else{
            product_count=orderService.getOrderBySexPro(product_id);
        }
        return product_count;
    }

    @RequestMapping("/getComProductCountByJob")
    @ResponseBody
    public List<UserSumByJob> getComProductCountByJob(@RequestParam(value = "product_id",defaultValue = "0") int product_id, HttpServletRequest request){
        List<UserSumByJob> product_count=new ArrayList<>();
        if(product_id==0) {
            product_count = orderService.getOrderSumByJobCom(request.getSession().getAttribute("user").toString());
        }else{
            product_count=orderService.getSumByUserJobPro(product_id);
        }
        return product_count;
    }

    @RequestMapping("/comproductcount")
    public String getProductCount(
            @RequestParam(value = "product_id",defaultValue = "0") int product_id,
            Model model, HttpServletRequest request){

        List<ProductInfo> product=productService.getProductInfoByComId(request.getSession().getAttribute("user").toString());
        model.addAttribute("product",product);
        model.addAttribute("product_id",product_id);

        return "background/com_pro_sales_search";
    }

    @GetMapping("productSalesCount")
    public String getOrderCount(){
        return "background/com_pro_sales_count";
    }

    @GetMapping("productClickCount")
    public String getProductClick(){

        return "background/com_pro_click_count";

    }

    @GetMapping("updateinfo")
    public String updateInfo(Model model, HttpServletRequest request){
        Company company = companyService.getCompanyById(request.getSession().getAttribute("user").toString());
        model.addAttribute("company",company);
        return "background/com_info";
    }

    @PostMapping("updateinfo")
    public String updateInfo(Company company){
        companyService.updateCompanyById(company);
        return "redirect:/company/updateinfo";
    }

    @GetMapping("orderlist")
    public String getOrderList(Model model, HttpServletRequest request){
        List<OrderInfo> orderInfoList = orderService.getOrderInfoByComId(request.getSession().getAttribute("user").toString());
        model.addAttribute("orders",orderInfoList);
        return "background/com_order";
    }

    @GetMapping("deleteorder/{order_id}")
    public String deleteOrder(@PathVariable("order_id") Integer order_id){

        orderService.deleteOrder(order_id);
        return "redirect:/company/orderlist";
    }

    @GetMapping("productlist")
    public String getProductList(Model model,HttpServletRequest request){
        List<ProductInfo> productInfos = productService.getProductInfoByComId(request.getSession().getAttribute("user").toString());
        model.addAttribute("products",productInfos);
        return "background/com_productlist";
    }

    @GetMapping("deleteproduct/{product_id}")
    public String deleteProduct(@PathVariable("product_id") Integer product_id){
        productService.deleteProduct(product_id);
        return "redirect:/company/productlist";
    }

    @GetMapping("/updateproduct/{product_id}")
    public String updateProduct(@PathVariable("product_id") Integer product_id,Model model){
        Product product = productService.getProductById(product_id);
        model.addAttribute("product",product);

        return "background/com_product_update";
    }

    @PostMapping("/updateproduct")
    public String updateProduct(Product product,@RequestParam("filepic") MultipartFile file){
        String fileName = file.getOriginalFilename();
        if(fileName.contains(".")){
            String filePath = FileUtil.getUploadFilePath();
            fileName = System.currentTimeMillis()+fileName;

            try {
                FileUtil.uploadFile(file.getBytes(),filePath,fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            Product product1 = productService.getProductById(product.getProduct_id());
            fileName = product1.getProduct_img_url();
        }
        product.setProduct_img_url(fileName);
        productService.updateProduct(product);
        return "redirect:/company/productlist";
    }

    @GetMapping("newslist")
    public String getNewsList(Model model,HttpServletRequest request){
        List<NewsInfo> newsInfoList = newsService.getNewsInfoByComId(request.getSession().getAttribute("user").toString());
        model.addAttribute("news",newsInfoList);
        return "background/com_newslist";
    }

    @GetMapping("deletenews/{news_id}")
    public String deleteNews(@PathVariable("news_id") Integer news_id){
        newsService.deleteNews(news_id);
        return "redirect:/company/newslist";
    }

    @GetMapping("updatenews/{news_id}")
    public String updateNews(@PathVariable("news_id") Integer news_id,Model model,HttpServletRequest request){
        News news = newsService.getNewsById(news_id);
        model.addAttribute("news",news);
        List<ProductInfo> productInfos = productService.getProductInfoByComId(request.getSession().getAttribute("user").toString());
        model.addAttribute("products",productInfos);
        return "background/com_news_update";
    }

    @PostMapping("updatenews")
    public String updateNews(News news,@RequestParam("filepic") MultipartFile file){
        String fileName = file.getOriginalFilename();
        if(fileName.contains(".")){
            String filePath = FileUtil.getUploadFilePath();
            fileName = System.currentTimeMillis()+fileName;

            try {
                FileUtil.uploadFile(file.getBytes(),filePath,fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            News news1 = newsService.getNewsById(news.getNews_id());
            fileName = news1.getNews_img_url();
        }
        news.setNews_img_url(fileName);
        newsService.updateNews(news);
        return "redirect:/company/newslist";
    }

    @GetMapping("newsadd")
    public String addNews(Model model,HttpServletRequest request){
        List<ProductInfo> productInfos = productService.getProductInfoByComId(request.getSession().getAttribute("user").toString());
        model.addAttribute("products",productInfos);
        return "background/com_news_release";
    }

    @PostMapping("newsadd")
    public String addNews(News news,@RequestParam("filepic") MultipartFile file){
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.getUploadFilePath();
        fileName = System.currentTimeMillis()+fileName;

        try {
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        news.setNews_img_url(fileName);
        newsService.releaseNews(news);
        return "redirect:/company/newslist";
    }

    @GetMapping("addproduct")
    public String addProduct(){

        return "background/com_product_add";
    }

    @PostMapping("addproduct")
    public String addProduct(Product product,@RequestParam("filepic") MultipartFile file,HttpServletRequest request){
        String fileName = file.getOriginalFilename();
        String filePath = FileUtil.getUploadFilePath();
        fileName = System.currentTimeMillis()+fileName;

        try {
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.setProduct_img_url(fileName);
        product.setProduct_com(request.getSession().getAttribute("user").toString());
        productService.addPorduct(product);
        return "redirect:/company/productlist";

    }

}
