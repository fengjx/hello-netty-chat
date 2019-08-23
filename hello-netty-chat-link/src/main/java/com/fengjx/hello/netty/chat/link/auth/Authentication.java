package com.fengjx.hello.netty.chat.link.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Authentication {

    private static final String PRIVATE_KEY = "ahihiconcho";

    public static boolean verifyToken(String payload, String token) {
        if (payload == null || token == null) {
            return false;
        }

        String[] part = token.split("\\.");
        String payloadDecoded = base64Decode(part[0]);
        String payloadSecret = hmacSha256(part[0]);

        return payload.equals(payloadDecoded) && part[1].equals(payloadSecret);
    }

    private static String hmacSha256(String msg) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec(PRIVATE_KEY.getBytes(StandardCharsets.UTF_8), "hmacSha256");
            Mac mac = Mac.getInstance("hmacSha256");
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes(StandardCharsets.US_ASCII));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return digest;
    }

    private static String base64Encode(String msg) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(msg.getBytes());
    }

    private static String base64Decode(String msg) {
        byte[] decoded = Base64.getUrlDecoder().decode(msg);
        return new String(decoded);
    }

    public static String generateToken(String payload) {
        String encodedPayload = base64Encode(payload);
        return encodedPayload + "." + hmacSha256(encodedPayload);
    }
}
