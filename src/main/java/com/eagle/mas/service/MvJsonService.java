package com.eagle.mas.service;

import com.eagle.mas.dto.MvJsonResponseDto;
import com.eagle.mas.model.MvJson;
import com.eagle.mas.repository.MvJsonRepository;
import com.eagle.mas.util.JsonUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class MvJsonService {
    @Autowired
    MvJsonRepository mvJsonRepository;
    @Autowired
    EncryptData decryptData;

    Logger logger = LoggerFactory.getLogger(MvJsonService.class);

    @Autowired
    JsonUtility jsonUtility;


/*    public String getJson(String probe, String candidate,String requestId){
        MvJson json= mvJsonRepository.getJson(probe,candidate,requestId);
        if(json!=null && json.isEncryptionStatus()){
            logger.info("returning mvjson data decrypted");
            return decryptData.AESDecrypt(json.getMvReqJson());
        }
        else {
            logger.info("returning mvjson data ");
            return json.getMvReqJson();
        }

    }
    public String getProbJson(String probe,String requestId){
        MvJson json= mvJsonRepository.getProbJson(probe,requestId);
        if(json != null && json.isEncryptionStatus()){
            logger.info("returning mvjson data decrypted");
            return decryptData.AESDecrypt(json.getMvReqJson());
        }else {
            logger.info("returning mvjson data ");
            return json.getMvReqJson();
        }

    }*/

    public String getJson(String probe, String candidate,String requestId) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
//        MvJson json= mvJsonRepository.getJson(probe,candidate,requestId);
        List<MvJson> json= mvJsonRepository.getJson(probe,candidate, PageRequest.of(0, 1));
//        if(json!=null && json.isEncryptionStatus()){
//            logger.info("returning mvjson data decrypted");
//            return decryptData.AESDecrypt(json.getMvReqJson());
//        }
//        else {
//            logger.info("returning mvjson data ");
//            return json.getMvReqJson();
//        }
        // commented decryption for production build
        logger.info("returning candidate mvjson data ");
        if ( json == null || json.isEmpty() ){
            try{
                logger.info("calling candidate mvjson data packet manager");
                MvJsonResponseDto mvJsonResponse = jsonUtility.getMVJson(candidate);
                ObjectMapper obj = new ObjectMapper();
                System.out.println("res candidate json : "+obj.writeValueAsString(mvJsonResponse).substring(0,50));
                String updatedMvJson = obj.writeValueAsString(mvJsonResponse);
                mvJsonRepository.saveMvJson(updatedMvJson, candidate);
                return updatedMvJson;
//                 mvJsonResponse.toString();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        return json.get(0).getMvReqJson();
    }

    public String getProbJson(String probe,String requestId){
//        MvJson json= mvJsonRepository.getProbJson(probe,requestId);
        List<MvJson> json= mvJsonRepository.getProbJson(probe, PageRequest.of(0, 1));
//        if(json != null && json.isEncryptionStatus()){
//            logger.info("returning mvjson data decrypted");
//            return decryptData.AESDecrypt(json.getMvReqJson());
//        }else {
//            logger.info("returning mvjson data ");
//            return json.getMvReqJson();
//        }
        logger.info("returning prob mvjson data ");
        if ( json == null || json.isEmpty() ){
            try{
                logger.info("calling prob mvjson data packet manager");
                MvJsonResponseDto mvJsonResponse = jsonUtility.getMVJson(probe);
                ObjectMapper obj = new ObjectMapper();
                System.out.println("res prob json : "+obj.writeValueAsString(mvJsonResponse).substring(0,50));
                String updatedMvJson = obj.writeValueAsString(mvJsonResponse);
                mvJsonRepository.saveMvJson(updatedMvJson, probe);
                return updatedMvJson;
//                 mvJsonResponse.toString();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        return json.get(0).getMvReqJson();

    }


}
