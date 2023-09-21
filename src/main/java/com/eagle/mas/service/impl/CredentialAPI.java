package com.eagle.mas.service.impl;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class CredentialAPI {


    private RestTemplate restTemplate;

    private HttpEntity<Object> setRequestHeader(Object requestType, MediaType mediaType, String token)
            throws IOException {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Cookie", token);
//        headers.add("Authorization", token);
        if (mediaType != null) {
            headers.add("Content-Type", mediaType.toString());

        }
        if (requestType != null) {
            try {
                HttpEntity<Object> httpEntity = (HttpEntity<Object>) requestType;
//                HttpHeaders httpHeader = httpEntity.getHeaders();
//                Iterator<String> iterator = httpHeader.keySet().iterator();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//                    if (!(headers.containsKey("Content-Type") && key == "Content-Type"))
//                        headers.add(key, httpHeader.get(key).get(0));
//                }
                return new HttpEntity<Object>(httpEntity.getBody(), headers);
            } catch (ClassCastException e) {
                return new HttpEntity<Object>(requestType, headers);
            }
        } else
            return new HttpEntity<Object>(headers);
    }

    public <T> T postApi(String uri, MediaType mediaType, Object requestType, Class<?> responseClass, String token)
             {
        try {
            RestTemplate restTemplate;
//            SslOff sslOff = new SslOff();
//            RestTemplate restTemplate = sslOff.turnOffSslChecking();
            restTemplate = getRest();
            T response = (T) restTemplate.postForObject(uri, setRequestHeader(requestType, mediaType, token),
                    responseClass);


            return response;


        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public <T> T getApi(String uri, Class<?> responseClass, String token)
    {
        try {
//            RestTemplate restTemplate;
            SslOff sslOff = new SslOff();
            RestTemplate restTemplate = sslOff.turnOffSslChecking();
//            restTemplate = getRest();
            T response = (T) restTemplate.exchange(uri,HttpMethod.GET,setRequestHeader(null, null, token),
                    responseClass);
            return response;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public RestTemplate getRest(){
        if(restTemplate == null) {
            HttpClientBuilder httpClientBuilder = HttpClients.custom().setMaxConnPerRoute(100)
                    .setMaxConnTotal(100).disableCookieManagement();
//            CloseableHttpClient httpClient = HttpClients.custom().build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClientBuilder.build());
            restTemplate = new RestTemplate(requestFactory);
        }
        return restTemplate;
    }
    public String issueCred(String TOKEN, String path){

        String apiRes = "";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,TOKEN);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; utf-8");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<String> req = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.POST,req,String.class);
        if (response.getStatusCode().toString().equals("200 OK")){
            JSONObject resJson = new JSONObject(response.getBody());

            try{
                apiRes = resJson.getJSONObject("response").toString();
            }catch (org.json.JSONException e){

                apiRes = null;
            }
        }else{

            apiRes = null;
        }
        return apiRes;

    }
}
