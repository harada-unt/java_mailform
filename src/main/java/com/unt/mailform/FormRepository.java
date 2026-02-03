package com.unt.mailform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Integer> {
    // 名前またはemailで検索
    List<Form> findByNameContainingOrEmailContaining(String name, String email);
}