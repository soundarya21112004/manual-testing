package com.eagle.mas.common;


import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eagle.mas.service.impl.SslOff.UNQUESTIONING_TRUST_MANAGER;

@Component
public class HitUsers {

    @Autowired
    RestTemplate restTemplate;

    public static Logger logger = LoggerFactory.getLogger(HitUsers.class);


    @PostMapping("test")
    public String test(@RequestBody String body) throws NoSuchAlgorithmException, KeyManagementException {

        turnOffSslChecking();

        System.out.println(body);

        return  body;

    }

    public static void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException {
        // Install the all-trusting trust manager

        System.out.println("Entry in turnOffSslChecking");

        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, UNQUESTIONING_TRUST_MANAGER, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }


   // @PostMapping("/loginUser")
    public void AuthenticateLogin1(){

        System.out.println("name");

        Map<String, Object> request = new HashMap<String, Object>();
        request.put("id", "");
        request.put("metadata", new HashMap<>());

        Map<String,Object> requestParams = new HashMap<String, Object>();
        requestParams.put("appId", "fdms");
        requestParams.put("clientId", "Kabi@250598");
        requestParams.put("secretKey", "kkabilan");
        request.put("request", requestParams);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);
        String requestTime = formatter.format(Instant.now());
        request.put("requesttime", requestTime);
        request.put("version", "");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request);

        ResponseEntity<String> response = restTemplate.postForEntity("https://api.apps-external.uat2.phylsys.gov.ph/fdms/v1/login/user",
                requestEntity, String.class);

        System.out.println(response);


    }

    public synchronized HashMap<String,Object> AuthenticateLogin() throws NoSuchAlgorithmException, KeyManagementException {

        logger.info("To login the Authenticate API");

        turnOffSslChecking();

        System.out.println("Exit turnOffSslChecking");

        HashMap<String,Object> result = new HashMap<>();

        //    if (authToken == null || LocalDateTime.now().isAfter(tokenExpiryTime)) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", "");
        requestBody.put("metadata", "");

        Map<String, String> innerRequest = new HashMap<>();
        innerRequest.put("appId", ConstantValue.loginAppId);
        innerRequest.put("password", ConstantValue.loginPassword);
        innerRequest.put("userName", ConstantValue.loginUsername);
        requestBody.put("request", innerRequest);


        requestBody.put("version", "");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);
        String requestTime = formatter.format(Instant.now());
        requestBody.put("requesttime", requestTime);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        logger.info("Login Authenticate API Request : "+request);

        ResponseEntity<String> response = restTemplate.postForEntity(ConstantValue.loginURL, request, String.class);

       // createCase();

        if (response.getStatusCode() == HttpStatus.OK && response.getHeaders().containsKey(HttpHeaders.SET_COOKIE)) {

            logger.info("Login Successfully and "+ response.getStatusCodeValue());

            result.put("status", "success");
            result.put("message", response.getStatusCodeValue());
//            List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
//            if (cookies != null) {
//                for (String cookie : cookies) {
//                    if (cookie.startsWith("Authorization=")) {
////                            authToken = cookie.split(";")[0].split("=")[1];
////                            tokenExpiryTime = LocalDateTime.now().plusMinutes(25);
//                        result.put("status", "success");
//                        result.put("message", "Authentication Success");
//                        break;
//                    }
//                }
//            } else {
//
//            }
//                if (authToken == null) {
//                    throw new RuntimeException("Token not found in cookies");
//                }
        } else {

            result.put("status", "error");
            result.put("message", response.getStatusCodeValue());


            logger.info("Login Failed and "+ response.getStatusCodeValue());

            throw new RuntimeException("Authentication failed");
        }

        return result;

     //   return true;
//        }
    }


    public  HashMap<String,Object> createCase(String RID,String location) throws NoSuchAlgorithmException, KeyManagementException, IOException {

        logger.info("To send the Case details Start");

        HashMap<String,Object> result = new HashMap<>();

        HashMap<String,Object> authenticateLogin =   AuthenticateLogin();

        logger.info("AuthenticateLogin "+authenticateLogin);

        if(authenticateLogin != null && "success".equals(authenticateLogin.get("status"))){

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("source","110");
            requestBody.put("caseType","MVS");
            requestBody.put("individualId",RID);
            requestBody.put("idType","MVS");
            requestBody.put("riskScore","0");
            requestBody.put("location",location);
            requestBody.put("macAddress",MacAddress.MacAddressWindows());
            requestBody.put("transactionId","");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            logger.info("Case API Request : "+request);

            ResponseEntity<String> response = restTemplate.postForEntity(ConstantValue.caseAuthURL, request, String.class);

            logger.info("Case API response : "+response);

            if (response.getStatusCode() == HttpStatus.OK ) {

                result.put("status", "success");
                result.put("message", "Data Shared Successfully");

            } else {
                result.put("status", "error");
                result.put("message", response.getStatusCodeValue());
            }



        } else {
            result.put("status", "error");
            result.put("message", "Authentication failed");

        }


return result;
    }





}
