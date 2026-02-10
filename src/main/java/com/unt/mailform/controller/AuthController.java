package com.unt.mailform.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.unt.mailform.model.User;
import com.unt.mailform.model.UserDto;
import com.unt.mailform.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String redirectToIndex() {
        // 現在のユーザーの認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 認証されている場合、indexページにリダイレクト
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        } 
        return "redirect:/login";
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView mav = new ModelAndView(); // ModelAndViewオブジェクトを作成
        mav.addObject("user", new UserDto()); // モデルにUserDtoオブジェクトを追加
        mav.setViewName("register"); // ビュー名を設定
        return mav;
    }

    // ユーザー登録処理
    @PostMapping("/register")
    public String register (@ModelAttribute UserDto userDto) {
        User existing = userService.findByUsername(userDto.getUsername());
        if (existing != null) {
            return "register";
        }
        userService.save(userDto);
        return "redirect:/login";
    }
}