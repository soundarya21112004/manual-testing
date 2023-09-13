package com.eagle.mas.service;

import com.eagle.mas.controller.LoginController;
import com.eagle.mas.model.MvJson;
import com.eagle.mas.repository.MvJsonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class MvJsonService {
    @Autowired
    MvJsonRepository mvJsonRepository;
    @Autowired
    EncryptData decryptData;
    Logger logger = LoggerFactory.getLogger(MvJsonService.class);



    public String getJson(String probe, String candidate,String requestId){
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

    }


}
