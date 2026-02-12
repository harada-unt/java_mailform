package com.unt.mailform.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unt.mailform.model.Contact;
import com.unt.mailform.model.ContactDto;
import com.unt.mailform.repository.ContactRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;

    // お問い合わせ登録
    @Transactional
    public Contact saveContact(ContactDto form) {
        Contact contact = new Contact();
        contact.setName(form.getName());
        contact.setEmail(form.getEmail());
        contact.setSubject(form.getSubject());
        contact.setContent(form.getContent());
        return contactRepository.save(contact);
    }
    
    public Page<Contact> getContact(Pageable pageable) {
    // 全件取得 ページング対応
    return contactRepository.findAll(pageable);
    }

    public Contact getContactById(Long id) {
    // IDでお問い合わせを取得
    return contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid form Id:" + id));
    }
    
    @Transactional
    public Contact updateContact(Long id, ContactDto form) {
        // お問い合わせ更新
    Contact contact = contactRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid form Id:" + id));
        contact.setName(form.getName());
        contact.setEmail(form.getEmail());
        contact.setSubject(form.getSubject());
        contact.setContent(form.getContent());
    return contactRepository.save(contact);
    }
    
    public void deleteContact(Long id) {
    // お問い合わせ削除
    contactRepository.deleteById(id);
    }

    public Page<Contact> searchContact(String keyword, Pageable pageable) {
    // 名前またはemailで検索 keywordに一致するものを取得
    return contactRepository.findByNameContainingOrEmailContaining(keyword, keyword, pageable);
    }
}
