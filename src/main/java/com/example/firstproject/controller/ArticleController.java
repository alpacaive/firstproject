package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j // 로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired // 이를 컨트롤러 필드에 붙이면 스프링 부트가 만들어 놓은 객체를 가져와 주입해준다 : 의존성 주입(DI : Dependecy Injection)
    private ArticleRepository articleRepository; //articleRepository 객체 선언
    // 자바에서는 new를 사용해 구현체를 만들어야했지만, 스프링 부트에서는 객체를 만들지 않아도 부트가 알아서 객체를 만들어준다.
    @GetMapping("/articles/new") // URL 요청 접수, localhost:8080/articles/new에 뷰 페이지를 반환할 수 있도록
    public String newArticleForm() {
        return "articles/new"; //반환값은 뷰페이지 이름
    }
    @PostMapping("/articles/create")// 폼 데이터를 post 방식으로 전송했으므로 컨트롤러에서 받을때도 @PostMapping()으로 받음,// <form> 태그에 action="/articles/create"로 설정했으므로 이렇게 작성
    public String createArticle(ArticleForm form) {
        // 폼에서 전송한 데이터를 메서드의 매개변수로 받아온다, ArticleForm 타입의 form 객체를 매개변수로 선언
        log.info(form.toString());
        //System.out.println(form.toString()); // 잘 담겼는지 확인하기 위해 출력문을 추가
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());
        // 2. 라파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); // article 엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "";
    }
}
