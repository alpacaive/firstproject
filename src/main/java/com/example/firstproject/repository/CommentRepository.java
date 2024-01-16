package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글의 모든 댓글 조회
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", // value 속성에 실행하려는 쿼리 작성
            nativeQuery = true) // SQL 문의 WHERE 절에 조건을 쓸 때 매개변수 앞에는 꼭 콜론(:)을 붙여 줘야 한다
    List<Comment> findByArticleId(Long articleId); // 실행 결과로 댓글의 묶음을 반환할 테니 반환형은 List<Comment>로 작성
    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);
}
