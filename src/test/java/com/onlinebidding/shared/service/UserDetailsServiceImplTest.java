package com.onlinebidding.shared.service;

import com.onlinebidding.shared.models.UserInfo;
import com.onlinebidding.shared.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDetailsServiceImplTest {

    private final UserInfoRepository userInfoRepository;

    private final UserDetailsServiceImpl userDetailsService;
    @Autowired
    public UserDetailsServiceImplTest(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userDetailsService = new UserDetailsServiceImpl(userInfoRepository);

    }
    @BeforeEach
    void setUp() {

        UserInfo userInfo = UserInfo.builder().userName("testUser").password("testPassword").role("SELLER").name("Test").build();
        userInfoRepository.save(userInfo);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnUserDetails() {
        String username = "testUser";

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SELLER")));
    }
}
