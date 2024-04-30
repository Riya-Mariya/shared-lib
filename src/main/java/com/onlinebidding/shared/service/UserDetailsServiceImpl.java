package com.onlinebidding.shared.service;

import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByUserName(username);

        if (userInfo != null) {
            return User.withUsername(userInfo.getUserName())
                    .password(userInfo.getPassword())
                    .authorities(userInfo.getRole())
                    .build();
        }
        return null;
    }

}
