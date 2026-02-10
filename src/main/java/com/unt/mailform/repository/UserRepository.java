package com.unt.mailform.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unt.mailform.model.User;

/**
 * ユーザー情報をデータベースから操作するためのインターフェース
 */ 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // ユーザー名でユーザーを検索
    User findByUsername(String username);
}
