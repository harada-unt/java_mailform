package com.unt.mailform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class FormService {
    
    @Autowired
    private FormRepository formRepository;

    @Transactional
    public Form saveForm(Form form) {
        // お問い合わせを登録
        Form entity = new Form();
        entity.setName(form.getName());
        entity.setEmail(form.getEmail());
        entity.setSubject(form.getSubject());
        entity.setContent(form.getContent());

        return formRepository.save(entity);
    }
    
    public List<Form> getContactForm() {
        // データベースからformsテーブルのレコードを全て取得
        return formRepository.findAll();
    }

    public Form getContactFormById(Integer id) {
        // IDでお問い合わせを取得
        return formRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid form Id:" + id));
    }

    public Form updateForm(Integer id, Form form) {
        // お問い合わせ更新
        Form entity = formRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid form Id:" + id));
        entity.setName(form.getName());
        entity.setEmail(form.getEmail());
        entity.setSubject(form.getSubject());
        entity.setContent(form.getContent());
        return formRepository.save(entity);
    }
    
    public void deleteForm(Integer id) {
        // お問い合わせ削除
        formRepository.deleteById(id);
    }

    public List<Form> searchContact(String keyword) {
        // 名前またはemailで検索 keywordに一致するものを取得
        return formRepository.findByNameContainingOrEmailContaining(keyword, keyword);
    }
}
