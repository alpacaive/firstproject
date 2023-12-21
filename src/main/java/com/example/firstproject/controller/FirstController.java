package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러 선언
public class FirstController {

    @GetMapping("/hi") //greetings.mustache를 반환해 달라는 URL 요청을 접수하는 부분
    public String niceToMeetYou(Model model) { //반환형이 문자열인 메서드 선언
        model.addAttribute("username","나현");
        return"greetings"; //greetings.mustache 파일 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("username","나현");
        return "goodbye";
    }
}
