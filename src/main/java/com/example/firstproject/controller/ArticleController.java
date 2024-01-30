package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j // 로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
    @Autowired // 이를 컨트롤러 필드에 붙이면 스프링 부트가 만들어 놓은 객체를 가져와 주입해준다 : 의존성 주입(DI : Dependecy Injection)
    private ArticleRepository articleRepository; //articleRepository 객체 선언
    // 자바에서는 new를 사용해 구현체를 만들어야했지만, 스프링 부트에서는 객체를 만들지 않아도 부트가 알아서 객체를 만들어준다.
    @Autowired
    private CommentService commentService; // 서비스 객체 주입
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
        return "redirect:/articles/" + saved.getId(); // 리다이렉트를 작성할 위치, 클라이언트의 요청을 받아 새로운 URL 주소로 재요청하라고 지시하는 것
    }
    @GetMapping("/articles/{id}") //데이터 조회 요청 접수
    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기, 모델 등록 : MVC 패턴에 따라 조회한 데이터를 뷰 페이지에 사용하기 위해
        // @PathVariable : URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션
        log.info("id = " + id); // id를 잘 받았는지 확인하는 로그 찍기
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); //해당 id값이 없으면 null을 반환하라는 뜻
        List<CommentDto> commentDtos = commentService.comments(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity); //article이라는 이름으로 articleEntity 객체 등록
        model.addAttribute("commentDtos", commentDtos); // 댓글 목록 모델에 등록
        // 3. 뷰 페이지 반환하기
        return "articles/show"; //show.mustache 반환
    }
    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);
        // 3. 뷰 페이지 설정하기
        return "/articles/index";
    }
    @GetMapping("/articles/{id}/edit") // URL 요청 접수
    public String edit(@PathVariable Long id, Model model) { // 메서드 생성 및 뷰 페이지 설정, id를 매개변수로 받아 오기, model객체 받아오기
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // DB에서 수정할 데이터 가져오기
        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity); // articleEntity를 article로 등록
        // 뷰 페이지 설정하기
        return "articles/edit";
    }
    @PostMapping("/articles/update") // URL 요청 접수
    public String update(ArticleForm form) { // 매개변수로 DTO 받아 오기
        log.info(form.toString()); // 수정 데이터 잘 받았는지 로그 찍어 확인
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity(); // DTO(form)를 엔티티(articleEntity)로 변환하기
        log.info(articleEntity.toString()); // 엔티티로 잘 변환됐는지 로그 찍기
        // 2. 엔티티를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터 값을 갱신하기
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }
    @GetMapping("/articles/{id}/delete") // URL 요청 접수
    public String delete(@PathVariable Long id, RedirectAttributes rttr) { // 메서드 생성 및 null값 반환, id를 매개변수로 가져오기
        // RedirectAttributes를 활용하려면 delete() 메서드의 매개변수로 받아와야 함
        log.info("삭제 요청이 들어왔습니다!");
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null); // 데이터 찾기
        log.info(target.toString());
        // 2. 대상 엔티티 삭제하기
        if (target != null) { // 삭제할 대상 있는지 확인
            articleRepository.delete(target); // delete() 메서드로 대상 삭제
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
            // 형식 : 객체명.addFlashAttribute(넘겨_주려는_키_문자열, 넘겨_주려는_값_객치);
        }
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}
