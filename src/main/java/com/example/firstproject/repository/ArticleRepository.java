package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

// 라퍼지터리는 사용자가 직접 구현할 수도 있지만 JPA에서 제공하는 라퍼지터리 인터페이스를 활용해 만들 수도 있다
public interface ArticleRepository extends CrudRepository<Article, Long> {
    //클래스 타입: Article, 대표값 = id 따라서 id의 타입인 Long
}
