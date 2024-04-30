package com.onlinebidding.shared.jwt;

import com.onlinebidding.shared.models.UserInfo;

public interface JwtGenerator {
    String generateJwtToken(UserInfo userInfo);
}
