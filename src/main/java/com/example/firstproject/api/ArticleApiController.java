package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService; // 서비스 객체 주입
    // 1. GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }
    // 2. POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { // 반환형 수정
        // POST 요청에 대한 응답으로 ResponseEntity에 응답 실어 보냄
        // @RequestBody : 요청 시 본문에 실어 보내는 데이터를 create() 메서드의 매개변수로 받아올 수 있음
        Article created = articleService.create(dto);
        return (created != null) ? // 생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 3. PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto); // 서비스를 통해 게시글 수정
        return (updated != null) ? // 수정되면 정상, 안 되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 4. DELETE
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id); // 서비스를 통해 게시글 삭제
        return (deleted != null) ? // 삭제 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/transaction-test") // 여러 게시글 생성 요청 접수
    public ResponseEntity<List<Article>> transactionTest
            (@RequestBody List<ArticleForm> dtos) { // transactionTest() 메서드 정의
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
