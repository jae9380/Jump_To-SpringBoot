package com.example.jump_to_sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";
        // redirect:<URL> => 해당 URL로 리다이렉트 (새로운 요청이지만)
        // forward:<URL> => 해당 URL로 포워드 (포워드는 기존 값들을 유지한 생태로)
    }
}
