package com.unt.mailform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormService {
    // formから受け取った値をデータベースに登録
    @Autowired
    private FormRepository formRepository;

    @Transactional
    public Form saveForm(Form form) {
        Form entity = new Form();
        entity.setName(form.getName());
        entity.setEmail(form.getEmail());
        entity.setSubject(form.getSubject());
        entity.setContent(form.getContent());

        return formRepository.save(entity);
    }
}
