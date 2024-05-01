package com.onlinebidding.shared.service;

import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    public UserDetailsServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

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
