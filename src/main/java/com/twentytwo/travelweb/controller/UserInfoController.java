package com.twentytwo.travelweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twentytwo.travelweb.entity.*;
import com.twentytwo.travelweb.service.NewsService;
import com.twentytwo.travelweb.service.OrderService;
import com.twentytwo.travelweb.service.UserService;
import com.twentytwo.travelweb.util.FileUtil;
import jdk.nashorn.internal.IntDeque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    NewsService newsService;

    @GetMapping({"/news/header.html","/header.html","/payCompleted/header.html"})
    public String showHeader(){
        return "redirect:/header.html";
    }

    @GetMapping({"/news/findUserServlet","/findUserServlet","/payCompleted/findUserServlet"})
    public String findUserServlet(){
        return "redirect:/findUserServlet";
    }

    @GetMapping({"/news/quitServlet","/quitServlet","/payCompleted/quitServlet"})
    public String quitServlet(){
        return "redirect:/quitServlet";
    }

    @GetMapping("userinfo")
    public String getUserInfo(Model model,HttpServletRequest request){
        User user=userService.getUserById(request.getSession().getAttribute("user").toString());
        model.addAttribute("user",user);
        return "foreground/user_info";
    }

    @GetMapping("/updateUserInfo")
    public String updateUserInfo(Model model){
        User user=userService.getUserById("123");
        model.addAttribute("user",user);
        return "foreground/user_update";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(User user,@RequestParam("filepic") MultipartFile file){
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
            User user1 = userService.getUserById(user.getUser_id());
            fileName = user1.getUser_img_url();
        }
        user.setUser_img_url(fileName);
        userService.updateUserById(user);
        return "redirect:/user/userinfo";
    }

    @GetMapping("showMyOrder")
    public String showMyOrder(Model model,String user_id,HttpServletRequest request){
        List<UserOrderInfo> orderInfoList=orderService.getOrderInfoByUser(request.getSession().getAttribute("user").toString());
        model.addAttribute("orderInfoList",orderInfoList);
        return "foreground/myorder";

    }

    @GetMapping("payMethod/{order_id}")
    public String payMethod(@PathVariable("order_id") int order_id){
        orderService.updateOrderInfo(order_id);
        return "foreground/pay_method";
    }

    @GetMapping("payCompleted")
    public String payCompleted(){
        return "foreground/pay_completed";
    }

    @GetMapping("/news/{news_id}")
    public String news(@PathVariable("news_id") int news_id,Model model){
        NewsInfo newsInfo=newsService.getNewsInfoByID(news_id);
        model.addAttribute("newsInfo",newsInfo);
        return "foreground/news";
    }

    @GetMapping({"/news/getServerName","/getServerName","/payCompleted/getServerName"})
    public void getServerName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //http://localhost:8080/travel_war_exploded
        String serverName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        myWriteValue(response, serverName);
    }
    private void myWriteValue(HttpServletResponse response, Object object) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),object);

    }


}
