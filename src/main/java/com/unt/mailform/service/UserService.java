package com.unt.mailform.service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unt.mailform.model.User;
import com.unt.mailform.model.UserDto;
import com.unt.mailform.repository.UserRepository;

/**
 * ユーザー情報を扱うサービスクラス
 */
@Service
public class UserService implements UserDetailsService {
    
    // ユーザー情報をデータベースから取得・保存するためのリポジトリ
    @Autowired
    private UserRepository userRepository;

    // パスワードをハッシュ化するためのエンコーダー
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // UserDetailsを実装したUserPrincipalオブジェクトを返す
        return new UserPrincipal(user);
    }

    // 新たにメソッドを追加
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ユーザー登録処理
    @Transactional
    public void save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);  
    }
}
