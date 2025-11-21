package com.eagle.mas.service;

import com.eagle.mas.dto.FieldResponseDto;
import com.eagle.mas.dto.MvJsonResponseDto;
import com.eagle.mas.model.MvJson;
import com.eagle.mas.repository.MvJsonRepository;
import com.eagle.mas.util.JsonUtility;
import com.eagle.mas.util.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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


    public JSONObject getJson(String rid) throws IOException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException, ParseException {

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

    /*public JSONObject validateJson(JSONObject jsonObject1, String rid) throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        JsonUtility.initializeExecutor();
        tokenGenerator.getToken();
        if (jsonObject1 != null) {
            logger.info("validating Mvjson");
//            CompletableFuture<Void> documentsFuture = CompletableFuture.completedFuture(null);
            CompletableFuture<Void> biometricsFuture = CompletableFuture.completedFuture(null);
            CompletableFuture<Void> metaInfoFuture = CompletableFuture.completedFuture(null);
            CompletableFuture<Void> auditsFuture = CompletableFuture.completedFuture(null);
            CompletableFuture<Void> identityFuture = CompletableFuture.completedFuture(null);

            // Documents
            try{
            if (jsonObject1.get("documents") == null || (jsonObject1.get("documents") instanceof JSONObject && ((JSONObject) jsonObject1.get("documents")).isEmpty())) {
                logger.info("Document field is null! Fetching from Packet manager");
                List<String> documentTypes = Arrays.asList("proofOfAddress", "proofOfIdentity", "proofOfEvidence");
                Map<String, String> docMap = new HashMap<>();
                List<CompletableFuture<Void>> docFutures = documentTypes.stream()
                        .map(dType -> jsonUtility.getDocumentAsync(rid, dType).thenAccept(docResponse -> {
                            if (docResponse != null && docResponse.getResponse() != null) {
                                try {
                                    com.eagle.mas.dto.Document doc = obj.readValue(
                                            obj.writeValueAsString(docResponse.getResponse()), com.eagle.mas.dto.Document.class);
                                    docMap.put(dType, Base64.getEncoder().encodeToString(doc.getDocument()));
                                } catch (JsonProcessingException e) {
//                                    throw new RuntimeException("Error processing document response", e);
                                    logger.info("Error processing document response", e);
                                }
                            }
                        }))
                        .collect(Collectors.toList());

                CompletableFuture.allOf(docFutures.get(0), docFutures.get(1), docFutures.get(2))
                        .thenRun(() -> {
                            jsonObject1.put("documents", new JSONObject(docMap));
                            logger.info("Successfully fetched documents");
                        }).join();
            }
            }
            catch (Exception e){
                logger.error("Error while waiting for document futures", e);
            }

            // Biometrics
            try{
                if (jsonObject1.get("biometrics") == null || jsonObject1.get("biometrics").toString().trim().isEmpty()) {
                    logger.info("Biometrics field is null! Fetching from Packet manager");

                    biometricsFuture = jsonUtility.getBiometricsAsync(rid)
                            .thenAccept(bioResponse -> {
                                if (bioResponse != null && bioResponse.getResponse() != null) {
                                    byte[] bio = jsonUtility.xmlString(bioResponse.getResponse());
                                    String bioEncode = Base64.getUrlEncoder().encodeToString(bio);
                                    jsonObject1.put("biometrics", bioEncode);
                                    logger.info("Successfully fetched biometrics");
                                } else {
                                    logger.info("Biometrics not available");
                                }

                            });
                }
            }
            catch (Exception e) {
                logger.error("Error while processing biometrics",e);
            }

            // MetaInfo
            try{
                if (jsonObject1.get("metaInfo") instanceof String && ("{}".equals(jsonObject1.get("metaInfo")) || ((String) jsonObject1.get("metaInfo")).trim().isEmpty())) {
                    logger.info("Meta info field is empty! Fetching from Packet manager");
                    metaInfoFuture = jsonUtility.getMetaInfoAsync(rid)
                            .thenAccept(metaInfoResponse -> {
                                JsonNode fieldsNode = obj.valueToTree(metaInfoResponse.getResponse()).path("fields");
                                if (!fieldsNode.isMissingNode()) {
                                    jsonObject1.put("metaInfo", fieldsNode.toString());
                                    logger.info("Successfully fetched meta info");
                                } else {
                                    logger.info("MetaInfo not available");
                                }
                            });
                }
            }
            catch (Exception e) {
                logger.error("Error while processing MetaInfo",e);
            }


            // Audits
            try{
                if (jsonObject1.get("audits") instanceof String && ("[]".equals(jsonObject1.get("audits")) || ((String) jsonObject1.get("audits")).trim().isEmpty())) {
                    logger.info("Audits field is empty! Fetching from Packet manager");
                    auditsFuture = jsonUtility.getAuditsAsync(rid)
                            .thenAccept(auditResponse -> {
                                if (auditResponse != null && auditResponse.getResponse() != null) {
                                    try {
                                        jsonObject1.put("audits", obj.writeValueAsString(auditResponse.getResponse()));
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                    logger.info("Successfully fetched audits");
                                }
                                else{
                                    logger.info("Audit response not available");
                                }


                            });
                }
            }catch (Exception e) {
                logger.error("Error while processing audit response",e);
            }


            try{
                if (jsonObject1.get("identity") instanceof JSONObject && ((JSONObject) jsonObject1.get("identity")).isEmpty()) {
                    logger.info("Identity field is Empty! Fetching from Packet manager");
                    identityFuture = jsonUtility.getIdentityAsync(rid)
                            .thenAccept(identityResponse -> {
                                if (identityResponse != null && identityResponse.getResponse() != null) {
                                    FieldResponseDto fieldResponseDto = null;
                                    try {
                                        fieldResponseDto = obj.readValue(
                                                JsonUtility.javaObjectToJsonString(identityResponse.getResponse()), FieldResponseDto.class);
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                    jsonObject1.put("identity", new JSONObject(fieldResponseDto.getFields()));
                                    logger.info("Successfully fetched identity");
                                }
                                else {
                                    logger.info("Identity response not available");
                                }

                            });
                }

        } catch (Exception e) {
//                                throw new RuntimeException("Error processing identity", e);
            logger.error("Error processing identity",e);
        }

            // Identity

            // Wait for all futures to complete
            try{
                CompletableFuture.allOf(biometricsFuture, metaInfoFuture, auditsFuture, identityFuture)
                        .join();
            }
            catch (Exception e){
                logger.error("Error while fetching ");
            }


            logger.info("All Individual API calls completed");
        }
        return jsonObject1;

    }*/


    public JSONObject validateJson(JSONObject jsonObject1, String rid)
            throws NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        logger.info("Validating MvJson");
        JsonUtility.initializeExecutor();
        tokenGenerator.getToken();

        if (jsonObject1 == null) return null;

        CompletableFuture<Void> biometricsFuture = CompletableFuture.completedFuture(null);
        CompletableFuture<Void> metaInfoFuture = CompletableFuture.completedFuture(null);
        CompletableFuture<Void> auditsFuture = CompletableFuture.completedFuture(null);
        CompletableFuture<Void> identityFuture = CompletableFuture.completedFuture(null);

        // Documents
        if (jsonObject1.get("documents") == null ||
                (jsonObject1.get("documents") instanceof JSONObject && ((JSONObject) jsonObject1.get("documents")).isEmpty())) {

            logger.info("Document field is null! Fetching from Packet manager");

            List<String> documentTypes = Arrays.asList("proofOfAddress", "proofOfIdentity", "proofOfEvidence");
            Map<String, String> docMap = new HashMap<>();

            List<CompletableFuture<Void>> docFutures = documentTypes.stream()
                    .map(dType -> jsonUtility.getDocumentAsync(rid, dType)
                            .thenAccept(docResponse -> {
                                if (docResponse != null && docResponse.getResponse() != null) {
                                    try {
                                        com.eagle.mas.dto.Document doc = obj.readValue(
                                                obj.writeValueAsString(docResponse.getResponse()),
                                                com.eagle.mas.dto.Document.class);
                                        docMap.put(dType, Base64.getEncoder().encodeToString(doc.getDocument()));
                                    } catch (IOException e) {
                                        logger.error("Error processing document response for {}", dType, e);
                                    }
                                }
                            })
                            .exceptionally(ex -> {
                                logger.error("Error fetching document for {}", dType, ex);
                                return null;
                            }))
                    .collect(Collectors.toList());

            CompletableFuture.allOf(docFutures.toArray(new CompletableFuture[0]))
                    .thenRun(() -> {
                        jsonObject1.put("documents", new JSONObject(docMap));
                        logger.info("Successfully fetched documents");
                    })
                    .join();
        }

        // Biometrics
        if (jsonObject1.get("biometrics") == null || jsonObject1.get("biometrics").toString().trim().isEmpty()) {
            logger.info("Biometrics field is null! Fetching from Packet manager");

            biometricsFuture = jsonUtility.getBiometricsAsync(rid)
                    .thenAccept(bioResponse -> {
                        if (bioResponse != null && bioResponse.getResponse() != null) {
                            byte[] bio = jsonUtility.xmlString(bioResponse.getResponse());
                            String bioEncode = Base64.getUrlEncoder().encodeToString(bio);
                            jsonObject1.put("biometrics", bioEncode);
                            logger.info("Successfully fetched biometrics");
                        } else {
                            logger.info("Biometrics not available");
                        }
                    })
                    .exceptionally(ex -> {
                        logger.error("Error fetching biometrics", ex);
                        return null;
                    });
        }

        // Meta Info
        if (jsonObject1.get("metaInfo") instanceof String &&
                ("{}".equals(jsonObject1.get("metaInfo")) || ((String) jsonObject1.get("metaInfo")).trim().isEmpty())) {

            logger.info("Meta info field is empty! Fetching from Packet manager");

            metaInfoFuture = jsonUtility.getMetaInfoAsync(rid)
                    .thenAccept(metaInfoResponse -> {
                        JsonNode fieldsNode = obj.valueToTree(metaInfoResponse.getResponse()).path("fields");
                        if (!fieldsNode.isMissingNode()) {
                            jsonObject1.put("metaInfo", fieldsNode.toString());
                            logger.info("Successfully fetched meta info");
                        } else {
                            logger.info("MetaInfo not available");
                        }
                    })
                    .exceptionally(ex -> {
                        logger.error("Error fetching metaInfo", ex);
                        return null;
                    });
        }

        // Audits
        if (jsonObject1.get("audits") instanceof String &&
                ("[]".equals(jsonObject1.get("audits")) || ((String) jsonObject1.get("audits")).trim().isEmpty())) {

            logger.info("Audits field is empty! Fetching from Packet manager");

            auditsFuture = jsonUtility.getAuditsAsync(rid)
                    .thenAccept(auditResponse -> {
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
                    })
                    .exceptionally(ex -> {
                        logger.error("Error fetching audits", ex);
                        return null;
                    });
        }

        // Identity
        if (jsonObject1.get("identity") instanceof JSONObject &&
                ((JSONObject) jsonObject1.get("identity")).isEmpty()) {

            logger.info("Identity field is Empty! Fetching from Packet manager");

            identityFuture = jsonUtility.getIdentityAsync(rid)
                    .thenAccept(identityResponse -> {
                        if (identityResponse != null && identityResponse.getResponse() != null) {
                            try {
                                FieldResponseDto fieldResponseDto = obj.readValue(
                                        JsonUtility.javaObjectToJsonString(identityResponse.getResponse()),
                                        FieldResponseDto.class);
                                jsonObject1.put("identity", new JSONObject(fieldResponseDto.getFields()));
                                logger.info("Successfully fetched identity");
                            } catch (IOException e) {
                                logger.error("Error while processing identity", e);
                            }
                        } else {
                            logger.info("Identity response not available");
                        }
                    })
                    .exceptionally(ex -> {
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
}
