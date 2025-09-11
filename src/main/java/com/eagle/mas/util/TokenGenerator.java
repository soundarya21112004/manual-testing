package com.eagle.mas.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.dto.ClientIdSecretKeyRequestDto;
import com.eagle.mas.dto.NewTokenRequestDto;
import com.eagle.mas.service.impl.DateUtils;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class TokenGenerator {

    @Autowired
    Environment environment;
    public static String token =null;
    private final static String AUTHORIZATION = "Authorization=";
    public static String validToken;

    Logger logger = LoggerFactory.getLogger(TokenGenerator.class);

    /**
     * This method gets the token for the user details present in config server.
     *
     * @return
     * @throws IOException
     */

    public void getToken() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
    logger.info("Getting auth token");
    generateToken(setRequestDto());
    validToken = AUTHORIZATION+token;

    }
    public void generateToken(ClientIdSecretKeyRequestDto dto) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        boolean isValid=false;

        if(token != null) {
            DecodedJWT decodedJWT = JWT.decode(token);
            LocalDateTime expiryTime = DateUtils.convertUTCToLocalDateTime(DateUtils.getUTCTimeFromDate(decodedJWT.getExpiresAt()));
            if (DateUtils.before(DateUtils.getUTCCurrentDateTime(), expiryTime)) {
                isValid = true;
            }
        }
        if(!isValid) {
            System.out.println("started creating token");

            NewTokenRequestDto tokenRequest = new NewTokenRequestDto();
            tokenRequest.setId("String");

            tokenRequest.setRequesttime(getUTCCurrentDateTime().toString());
            tokenRequest.setRequest(dto);
            tokenRequest.setVersion("String");

            Gson gson = new Gson();
            HttpClient httpClient;

            httpClient = HttpClients
                    .custom()
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();

            HttpPost post = new HttpPost(ConstantValue.KERNELAUTHMANAGERAPI);
            try {
                StringEntity postingString = new StringEntity(gson.toJson(tokenRequest));
                post.setEntity(postingString);
                post.setHeader("Content-type", "application/json");
                HttpResponse response = httpClient.execute(post);

                org.apache.http.HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity, "UTF-8");
                System.out.println("response" + responseBody);
                Header[] cookie = response.getHeaders("Set-Cookie");
                token = response.getHeaders("Set-Cookie")[0].getValue();
                token =  token.substring(14, token.indexOf(';'));
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }


    }

    public ClientIdSecretKeyRequestDto setRequestDto() {
        ClientIdSecretKeyRequestDto request = new ClientIdSecretKeyRequestDto();
        request.setAppId(ConstantValue.TokenAppId);
        request.setClientId(ConstantValue.TokenClientId);
        request.setSecretKey(ConstantValue.secretKey);
        return request;
    }

    public static LocalDateTime getUTCCurrentDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
    }

}
