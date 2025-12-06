package com.eagle.mas.service;

import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.dto.FieldResponseDto;
import com.eagle.mas.dto.MvJsonResponseDto;
import com.eagle.mas.dto.ResponseDto;
import com.eagle.mas.model.MvJson;
import com.eagle.mas.regproc.model.Registration;
import com.eagle.mas.regproc.repo.RegistrationRepo;
import com.eagle.mas.repository.MvJsonRepository;
import com.eagle.mas.util.JsonUtility;
import com.eagle.mas.util.TokenGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.imageio.spi.RegisterableService;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MvJsonService {
    @Autowired
    MvJsonRepository mvJsonRepository;
    @Autowired
    EncryptData decryptData;

    Logger logger = LoggerFactory.getLogger(MvJsonService.class);

    @Autowired
    JsonUtility jsonUtility;

    @Autowired
    ObjectMapper obj;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    private RegistrationRepo registrationRepo;


    public JSONObject getJson(String rid) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, ParseException {

        JSONParser jsonParser = new JSONParser();
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedMvJson;

        List<MvJson> jsonList = mvJsonRepository.getJson(rid, PageRequest.of(0, 1));

        if (jsonList == null || jsonList.isEmpty()) {
            try {
                logger.info("Mvjson is null or empty. Calling packet manager...");
                MvJsonResponseDto mvJsonResponse = jsonUtility.getMVJson(rid);
                updatedMvJson = objectMapper.writeValueAsString(mvJsonResponse);
                logger.info("Fetched mvJson from external source: {}", updatedMvJson.substring(0, Math.min(updatedMvJson.length(), 50)));
            } catch (Exception e) {
                logger.error("Exception while fetching MVJson : {}", e);
                return null; // or throw a custom exception if appropriate
            }
        } else {
            logger.info("Mvjson found in DB.");
            updatedMvJson = jsonList.get(0).getMvReqJson();
        }

        // Parse and validate JSON
        JSONObject parsedJson = (JSONObject) jsonParser.parse(updatedMvJson);
        JSONObject finalMVJson = validateJson(parsedJson, rid);

        logger.info("Returning validated MVJson data.");
        return finalMVJson;
    }


    public JSONObject validateJson(JSONObject jsonObject1, String rid) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        logger.info("Validating MvJson");
        JsonUtility.initializeExecutor();
        tokenGenerator.getToken();

        if (jsonObject1 == null) return null;

        CompletableFuture<Void> biometricsFuture = CompletableFuture.completedFuture(null);
        CompletableFuture<Void> metaInfoFuture = CompletableFuture.completedFuture(null);
        CompletableFuture<Void> auditsFuture = CompletableFuture.completedFuture(null);
        CompletableFuture<Void> identityFuture = CompletableFuture.completedFuture(null);

        // Documents
        if (jsonObject1.get("documents") == null || (jsonObject1.get("documents") instanceof JSONObject && ((JSONObject) jsonObject1.get("documents")).isEmpty())) {

            logger.info("Document field is null! Fetching from Packet manager");

            List<String> documentTypes = Arrays.asList("proofOfAddress", "proofOfIdentity", "proofOfEvidence");
            Map<String, String> docMap = new HashMap<>();

            List<CompletableFuture<Void>> docFutures = documentTypes.stream().map(dType -> jsonUtility.getDocumentAsync(rid, dType).thenAccept(docResponse -> {
                if (docResponse != null && docResponse.getResponse() != null) {
                    try {
                        com.eagle.mas.dto.Document doc = obj.readValue(obj.writeValueAsString(docResponse.getResponse()), com.eagle.mas.dto.Document.class);
                        docMap.put(dType, Base64.getEncoder().encodeToString(doc.getDocument()));
                    } catch (IOException e) {
                        logger.error("Error processing document response for {}", dType, e);
                    }
                }
            }).exceptionally(ex -> {
                logger.error("Error fetching document for {}", dType, ex);
                return null;
            })).collect(Collectors.toList());

            CompletableFuture.allOf(docFutures.toArray(new CompletableFuture[0])).thenRun(() -> {
                jsonObject1.put("documents", new JSONObject(docMap));
                logger.info("Successfully fetched documents");
            }).join();
        }

        // Biometrics
        if (jsonObject1.get("biometrics") == null || jsonObject1.get("biometrics").toString().trim().isEmpty()) {
            logger.info("Biometrics field is null! Fetching from Packet manager");

            biometricsFuture = jsonUtility.getBiometricsAsync(rid).thenAccept(bioResponse -> {
                if (bioResponse != null && bioResponse.getResponse() != null) {
                    byte[] bio = jsonUtility.xmlString(bioResponse.getResponse());
                    String bioEncode = Base64.getUrlEncoder().encodeToString(bio);
                    jsonObject1.put("biometrics", bioEncode);
                    logger.info("Successfully fetched biometrics");
                } else {
                    logger.info("Biometrics not available");
                }
            }).exceptionally(ex -> {
                logger.error("Error fetching biometrics", ex);
                return null;
            });
        }

        // Meta Info
        if (jsonObject1.get("metaInfo") instanceof String && ("{}".equals(jsonObject1.get("metaInfo")) || ((String) jsonObject1.get("metaInfo")).trim().isEmpty())) {

            logger.info("Meta info field is empty! Fetching from Packet manager");

            metaInfoFuture = jsonUtility.getMetaInfoAsync(rid).thenAccept(metaInfoResponse -> {
                JsonNode fieldsNode = obj.valueToTree(metaInfoResponse.getResponse()).path("fields");
                if (!fieldsNode.isMissingNode()) {
                    jsonObject1.put("metaInfo", fieldsNode.toString());
                    logger.info("Successfully fetched meta info");
                } else {
                    logger.info("MetaInfo not available");
                }
            }).exceptionally(ex -> {
                logger.error("Error fetching metaInfo", ex);
                return null;
            });
        }

        // Audits
        if (jsonObject1.get("audits") instanceof String && ("[]".equals(jsonObject1.get("audits")) || ((String) jsonObject1.get("audits")).trim().isEmpty())) {

            logger.info("Audits field is empty! Fetching from Packet manager");

            auditsFuture = jsonUtility.getAuditsAsync(rid).thenAccept(auditResponse -> {
                if (auditResponse != null && auditResponse.getResponse() != null) {
                    try {
                        jsonObject1.put("audits", obj.writeValueAsString(auditResponse.getResponse()));
                        logger.info("Successfully fetched audits");
                    } catch (IOException e) {
                        logger.error("Error processing audit response", e);
                    }
                } else {
                    logger.info("Audit response not available");
                }
            }).exceptionally(ex -> {
                logger.error("Error fetching audits", ex);
                return null;
            });
        }

        // Identity
        if (jsonObject1.get("identity") instanceof JSONObject && ((JSONObject) jsonObject1.get("identity")).isEmpty()) {

            logger.info("Identity field is Empty! Fetching from Packet manager");

            identityFuture = jsonUtility.getIdentityAsync(rid).thenAccept(identityResponse -> {
                if (identityResponse != null && identityResponse.getResponse() != null) {
                    try {
                        FieldResponseDto fieldResponseDto = obj.readValue(JsonUtility.javaObjectToJsonString(identityResponse.getResponse()), FieldResponseDto.class);
                        jsonObject1.put("identity", new JSONObject(fieldResponseDto.getFields()));
                        logger.info("Successfully fetched identity");
                    } catch (IOException e) {
                        logger.error("Error while processing identity", e);
                    }
                } else {
                    logger.info("Identity response not available");
                }
            }).exceptionally(ex -> {
                logger.error("Error fetching identity", ex);
                return null;
            });
        }

        CompletableFuture.allOf(biometricsFuture, metaInfoFuture, auditsFuture, identityFuture).join();

        logger.info("All individual API calls completed");
        return jsonObject1;
    }

    public void saveMvJson(String mvJson, String probe) {
        mvJsonRepository.saveMvJson(mvJson, probe);
    }

    public List<MvJson> getUpdateStatus(String rid) {
        return mvJsonRepository.getUpdateStatus(rid, PageRequest.of(0, 1));
    }

    public JSONObject getDemoFromIdRepo(String rid, JSONObject jsonObject, String regType) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        JSONObject updatedIdentityJson = new JSONObject();
        JSONObject updated = new JSONObject();
        Map<String, String> docMap = new HashMap<>();
        try {
            JsonUtility.initializeExecutor();
            tokenGenerator.getToken();

            ResponseDto ProbeIdentityResponse = null;

            String uin = null;

                try {
                    if(regType.equalsIgnoreCase("update")){

                        CompletableFuture<ResponseDto> identityFuture = jsonUtility.getIdentityAsync1(rid).exceptionally(ex -> {
                            logger.error("Error fetching identity", ex);
                            return null;
                        });
                        if(identityFuture != null){
                            ProbeIdentityResponse = identityFuture.get();
                        }

                        if (ProbeIdentityResponse != null && ProbeIdentityResponse.getResponse() != null) {
                            FieldResponseDto fieldResponseDto = obj.readValue(JsonUtility.javaObjectToJsonString(ProbeIdentityResponse.getResponse()), FieldResponseDto.class);
                            uin = fieldResponseDto.getFields().get("UIN");
                        }
                        else {
                            logger.warn("Identity Response not available");
                        }
                    }
                    else {
                        uin = rid;
                    }

//                                        System.out.println("UIN : " + uin);

//                                        System.out.println("mvjson identity : " + jsonObj1);

                    ResponseDto<?> idRepoUinResponse = jsonUtility.makeGetRequest(uin, ConstantValue.IDREPOGETAPI);
                    if (idRepoUinResponse.getResponse() != null) {
                        Map<?, ?> responseMap = (Map<?, ?>) idRepoUinResponse.getResponse();

//                                            System.out.println("identityMap : " + identityMap);

                        obj.enable(SerializationFeature.INDENT_OUTPUT);
                        Map<String, Object> localIdentity = new HashMap<>();
                        Map<String, Object> apiIdentity = (Map<String, Object>) responseMap.get("identity");

                        if("update".equalsIgnoreCase(regType)){
                            localIdentity = obj.readValue(jsonObject.get("identity").toString(), Map.class);
                            for (String key : localIdentity.keySet()) {

                                if (!apiIdentity.containsKey(key)) {
                                    continue; // Only update existing DB fields
                                }

                                Object apiValue = apiIdentity.get(key);
                                if (apiValue == null) {
                                    continue; // keep null
                                }

                                // Case 1: List value -> convert to JSON string
                                if (apiValue instanceof List) {
                                    String jsonString = obj.writeValueAsString(apiValue);
                                    localIdentity.put(key, jsonString);
                                }

                                // Case 2: Map value -> convert to JSON string
                                else if (apiValue instanceof Map) {
                                    String jsonString = obj.writeValueAsString(apiValue);
                                    localIdentity.put(key, jsonString);
                                }

                                // Case 3: Primitive/string -> convert to string
                                else {
                                    localIdentity.put(key, apiValue.toString());
                                }
                            }

                        }
                        else{

                            for (String field : JsonUtility.fields) {

                                Object apiValue = apiIdentity.get(field);

                                if (apiValue == null) {
                                    localIdentity.put(field, apiValue);
                                }

                                // Case 1: List value -> convert to JSON string
                                if (apiValue instanceof List) {
                                    String jsonString = obj.writeValueAsString(apiValue);
                                    localIdentity.put(field, jsonString);
                                }

                                // Case 2: Map value -> convert to JSON string
                                else if (apiValue instanceof Map) {
                                    String jsonString = obj.writeValueAsString(apiValue);
                                    localIdentity.put(field, jsonString);
                                }

                                // Case 3: Primitive/string -> convert to string
                                else if (apiValue != null) {
                                    localIdentity.put(field, apiValue.toString());
                                }
                            }

                        }

                        updatedIdentityJson = new JSONObject(localIdentity);
                        jsonObject.put("identity",updatedIdentityJson);

                        List<Map<String, String>> documentMap = (List<Map<String, String>>) responseMap.get("documents");
                        for ( Map<String, String> entry : documentMap){
                            if(entry.get("category").equals("individualBiometrics")){
                                jsonObject.put("biometrics",entry.get("value"));
                            }
                            else{
                                docMap.put(entry.get("category"), entry.get("value"));
                            }
                        }
                        jsonObject.put("documents", new JSONObject(docMap));
                        return jsonObject;
                    } else {
                        logger.info("Id repo uin response is null for regid: {}", rid);
                    }
                    logger.info("Successfully fetched Identity");
                } catch (Exception e) {
                    logger.error("Error processing identity response", e);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getRegTypeFromRegistration(String rid){
        try{
            Registration registration = registrationRepo.findByRegId(rid);
            return registration.getRegistrationType();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }


}
