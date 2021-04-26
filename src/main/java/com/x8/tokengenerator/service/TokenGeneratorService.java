package com.x8.tokengenerator.service;

import com.x8.tokengenerator.domain.JaaSJwtBuilder;
import com.x8.tokengenerator.domain.Permissions;
import com.x8.tokengenerator.domain.TokenClaims;
import com.x8.tokengenerator.utils.TenantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;

import static com.x8.tokengenerator.domain.Permissions.*;

@Service
@Slf4j
public class TokenGeneratorService {

    /**
     * Placeholder helper string.
     */
    private static final String BEGIN_PRIVATE_KEY = "-----BEGIN (.*)-----";

    /**
     * Placeholder helper string.
     */
    private static final String END_PRIVATE_KEY = "-----END (.*)-----";

    /**
     * Placeholder empty string.
     */
    private static final String EMPTY = "";

    /**
     * Helper string for key algorithm.
     */
    private static final String RSA = "RSA";


    private static RSAPrivateKey getPemPrivateKey(String pem) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKey = stripBeginEnd(pem);
        BASE64Decoder b64 = new BASE64Decoder();
        byte[] decoded = b64.decodeBuffer(privateKey);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    private static String stripBeginEnd(String pem) {
        String stripped = pem.replaceFirst(BEGIN_PRIVATE_KEY, EMPTY);
        stripped = stripped.replaceFirst(END_PRIVATE_KEY, EMPTY);
        stripped = stripped.replaceAll("\r\n", EMPTY);
        stripped = stripped.replaceAll("\n", EMPTY);
        stripped = stripped.replaceAll("\\n", EMPTY);
        return stripped.trim();
    }

    public String getToken(TokenClaims tokenClaims, String kid, String privateKey) throws Exception {
        List<Permissions> permissions = tokenClaims.getPermissions();
        return JaaSJwtBuilder.builder()
                .withDefaults()
                .withUserId(tokenClaims.getId())
                .withUserName(tokenClaims.getName())
                .withUserEmail(tokenClaims.getEmail())
                .withUserAvatar(tokenClaims.getAvatar())
                .withModerator(tokenClaims.isModerator())
                .withOutboundEnabled(permissions.contains(OUTBOUND_CALL))
                .withTranscriptionEnabled(permissions.contains(TRANSCRIPTION))
                .withLiveStreamingEnabled(permissions.contains(LIVESTREAMING))
                .withLobbyEnabled(permissions.contains(LOBBY))
                .withRecordingEnabled(permissions.contains(RECORDING))
                .withTenantName(TenantUtils.extractTenant(kid))
                .withApiKey(kid)
                .signWith(getPemPrivateKey(privateKey));
    }
}
