package com.unt.mailform;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@RestController
@RequestMapping("/v1/api/list")
public class ApiListController {

    @Autowired
    private FormService formService;

    @RequestMapping(method = RequestMethod.GET)
    List<Form> getList() {
        return formService.getContactForm();
    }

}
