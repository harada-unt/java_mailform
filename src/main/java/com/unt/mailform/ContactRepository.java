package com.unt.mailform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // 名前またはemailで検索
    List<Contact> findByNameContainingOrEmailContaining(String name, String email);
}