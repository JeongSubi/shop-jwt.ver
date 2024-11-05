package com.subi.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller()
public class BasicController {

    @GetMapping("/main")
    public String main() {
        return "index.html";
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        return LocalDateTime.now().toString();
    }

}
