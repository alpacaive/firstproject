package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    // 이 파일이 폼 데이터를 받아 올 그릇, 즉 DTO가 된다.
    // 입력 폼에서 제목과 내용을 전송할 예정이니 DTO에도 필드 2개가 필요.
    private String title; // 제목을 받을 필드
    private String content; // 내용을 받을 필드

    /*public ArticleForm(String title, String content) {// 전송받은 제목과 내용을 필드에 저장하는 생성자 추가
        // Generate -> Constructor 선택하면 자동으로 생성
        this.title = title;
        this.content = content;
    }*/

    /*@Override
    public String toString() { // 폼 데이터를 잘 받았는지 확인하기 위해 toString() 메서드를 추가, Generate -> toString() 하면 자동으로 생성
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/

    public Article toEntity() { // 엔티티에 Article 클래스의 생성자 형식에 맞게 작성하면 된다.
        return new Article(null, title, content);
    }
}
