package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

// 컨트롤러에 DTO를 엔티티로 변환하기위해 form객체의 toEntity()라는 메서드를 호출해서 반환값을 Article타입 article 엔티티에 저장
// 그러기 위해 Article 클래스와 toEntity() 메서드가 필요해서 만드는 과정.
@AllArgsConstructor
@ToString
@Entity // 엔티티 선언
public class Article {
    @Id // 엔티티의 대푯값 지정
    @GeneratedValue // 자동 생성 기능 추가(숫자가 자동으로 매겨짐)
    private Long id;
    @Column
    private String title; //title 필드 선언, DB 테이블의 title 열과 연결됨
    @Column
    private String content; // content 필드 선언, DB 테이블의 content 열과 연결됨

    /*public Article(Long id, String title, String content) { //Article 객체의 생성 및 초기화를 위해 생성자를 추가
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/
}
