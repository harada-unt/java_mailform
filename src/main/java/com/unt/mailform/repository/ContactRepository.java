package com.unt.mailform.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.unt.mailform.model.Contact;

/**
 * お問い合わせ情報をデータベースから操作するためのインターフェース
 */ 
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // 名前またはemailで検索
    List<Contact> findByNameContainingOrEmailContaining(String name, String email);
}