package com.onlinebidding.shared.config;

import com.onlinebidding.shared.filter.JwtTokenValidationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenValidationFilter jwtTokenValidationFilter;
    @MockBean
    private SecurityConfig securityConfig;

    @MockBean
    private HttpSecurity httpSecurity;
    @MockBean
    private SecurityFilterChain securityFilterChain;

    @Test
    public void testJwtTokenValidationFilter() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.setRequestURI("/auction/product/getBid");
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9");

        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtTokenValidationFilter.doFilter(request, response, (req, res) -> {
        });

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testEndpointWithoutAuthentication() throws Exception {
        when(securityConfig.securityFilterChain(httpSecurity)).thenReturn(securityFilterChain);
        mockMvc.perform(MockMvcRequestBuilders.get("/auction/user/register"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/auction/product/register")
                        .with(authentication(mock(Authentication.class))))
                .andExpect(status().isOk());
    }


}
