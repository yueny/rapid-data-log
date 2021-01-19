package com.whosly.rapid.data.log.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:yueny09@126.com"> 袁洋
 * @date 2021/1/193:01 下午
 * @inc
 * @category
 */
@Slf4j
@Controller
public class DemoController {

    @GetMapping("/get")
    @ResponseBody
    public String getKaptcha(HttpServletResponse response) {
        try {
            return "a";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常：{}", e.getMessage());
        }

        return "b";
    }

}
