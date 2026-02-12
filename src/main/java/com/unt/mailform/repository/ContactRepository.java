package com.unt.mailform.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unt.mailform.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * お問い合わせ情報をデータベースから操作するためのインターフェース
 */ 
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // 名前またはemailで検索
    Page<Contact> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
}