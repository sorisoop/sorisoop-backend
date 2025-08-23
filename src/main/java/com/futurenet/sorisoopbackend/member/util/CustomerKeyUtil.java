package com.futurenet.sorisoopbackend.member.util;

import com.futurenet.sorisoopbackend.member.application.exception.MemberErrorCode;
import com.futurenet.sorisoopbackend.member.application.exception.MemberException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CustomerKeyUtil {

    private static String CUSTOMER_KEY_SECRET;

    @Value("${billing.customer-key-secret}")
    public void setCustomerKeySecret(String secret) {
        CUSTOMER_KEY_SECRET = secret;
    }

    private CustomerKeyUtil() {}

    public static String generate(Long memberId) {
        try {
            String raw = CUSTOMER_KEY_SECRET + "_" + memberId;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new MemberException(MemberErrorCode.CUSTOMER_KEY_GENERATION_FAIL);
        }
    }


}
