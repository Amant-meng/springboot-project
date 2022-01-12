package com.ym.springbootproject.config.ws;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description TODO 服务端与客户端进行交互测试
 * @Author yangmeng
 * @Date 2022/1/12 11:26
 */
@RestController
public class WebSocketTest {

    @RequestMapping("/test")
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
