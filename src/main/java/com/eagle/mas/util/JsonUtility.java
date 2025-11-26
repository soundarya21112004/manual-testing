package com.eagle.mas.util;

import com.eagle.mas.config.BIR;
import com.eagle.mas.config.BIRResponse;
import com.eagle.mas.config.ConstantValue;
import com.eagle.mas.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
public class JsonUtility {

    @Autowired
    Environment environment;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    RestTemplate restTemplate;

    public static List<String> fields;

    private static Logger logger = LoggerFactory.getLogger(JsonUtility.class);

    //    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public static ThreadPoolExecutor executor = null;

    @Autowired
    ObjectMapper obj;


    static {
        fields = Arrays.asList(
                "presentAddressLine1",
                "presentZipcode",
                "presentProvince",
                "lastName",
                "presentAddressLine2",
                "presentAddressLine3",
                "pobCountry",
                "presentAddressLine4",
                "proofOfAddress",
                "pobProvince",
                "gender",
                "proofOfConsent",
                "permanentBarangay",
                "mobileno",
                "suffix",
                "bloodType",
                "individualBiometrics",
                "presentBarangay",
                "proofOfDateOfBirth",
                "residenceStatus",
                "email",
                "permanentZipcode",
                "pobCity",
                "dateOfBirth",
                "presentCity",
                "firstName",
                "proofOfIdentity",
                "permanentAddressLine1",
                "proofOfException",
                "permanentCountry",
                "presentCountry",
                "permanentProvince",
                "middleName",
                "proofOfRelationship",
                "permanentCity",
                "permanentAddressLine2",
                "permanentAddressLine3",
                "permanentAddressLine4",
                "maritalStatus"
        );
    }

    public static void initializeExecutor() {

       executor = new ThreadPoolExecutor(
                ConstantValue.corePoolSize,
                ConstantValue.maximumPoolSize,
                ConstantValue.keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), // Unbounded queue to prevent rejection
                new ThreadPoolExecutor.CallerRunsPolicy() // Handles rejected tasks
        );
    }

    private <T> ResponseDto makePostRequest(T requestBody, String url) {
        try {
            logger.info("Calling Url : "+ url);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Cookie", TokenGenerator.validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<T> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, ResponseDto.class);
//            logger.info("Response Time :" + url);
            return response.getBody();
        } catch (Exception e) {
            logger.info("Error calling API : " + url, e);
//            throw new RuntimeException("Error calling API: " + url, e);
        }

        return new ResponseDto<>();

    }

    private <T> RequestDto createRequestDto(T requestDetails) {
        return new RequestDto("", new HashMap<>(), requestDetails, LocalDateTime.now(), "");
    }

    // Asynchronous API calls using CompletableFuture
    public CompletableFuture<ResponseDto> getAuditsAsync(String rid) {
        return CompletableFuture.supplyAsync(() -> {
            AuditRequestDto auditRequestDto = new AuditRequestDto();
            auditRequestDto.setId(rid);
            auditRequestDto.setProcess("NEW");
            auditRequestDto.setBypassCache(true);
            auditRequestDto.setSource("REGISTRATION_CLIENT");
            return makePostRequest(createRequestDto(auditRequestDto), ConstantValue.AUDITAPI);
//        }, executor);
        }, executor).orTimeout(ConstantValue.executorShutdown, TimeUnit.SECONDS);
    }

    public CompletableFuture<ResponseDto> getBiometricsAsync(String rid) {
        return CompletableFuture.supplyAsync(() -> {
            BiometricRequestDto biometricRequestDto = new BiometricRequestDto();
            biometricRequestDto.setId(rid);
            biometricRequestDto.setBypassCache(true);
            biometricRequestDto.setProcess("NEW");
            biometricRequestDto.setSource("REGISTRATION_CLIENT");
            biometricRequestDto.setModalities(new ArrayList<>());
            biometricRequestDto.setPerson("individualBiometrics");
            return makePostRequest(createRequestDto(biometricRequestDto), ConstantValue.BIOAPI);
//        }, executor);
        }, executor).orTimeout(ConstantValue.executorShutdown, TimeUnit.SECONDS);
    }

    public CompletableFuture<ResponseDto> getDocumentAsync(String rid, String dType) {
        return CompletableFuture.supplyAsync(() -> {
            DocumentRequestDto documentRequestDto = new DocumentRequestDto();
            documentRequestDto.setId(rid);
            documentRequestDto.setSource("REGISTRATION_CLIENT");
            documentRequestDto.setProcess("NEW");
            documentRequestDto.setDocumentName(dType);
            return makePostRequest(createRequestDto(documentRequestDto), ConstantValue.DOCUMENTAPI);
//        }, executor);
        }, executor).orTimeout(ConstantValue.executorShutdown, TimeUnit.SECONDS);
    }

    public CompletableFuture<ResponseDto> getIdentityAsync(String rid) {
        return CompletableFuture.supplyAsync(() -> {
            IdentityRequestDto searchFieldRequestDto = new IdentityRequestDto();
            searchFieldRequestDto.setId(rid);
            searchFieldRequestDto.setFields(fields);
            searchFieldRequestDto.setSource("REGISTRATION_CLIENT");
            searchFieldRequestDto.setProcess("NEW");
            searchFieldRequestDto.setBypassCache(true);
            return makePostRequest(createRequestDto(searchFieldRequestDto), ConstantValue.SEARCHFIELDAPI);
//        }, executor);
        }, executor).orTimeout(ConstantValue.executorShutdown, TimeUnit.SECONDS);
    }

    public CompletableFuture<ResponseDto> getMetaInfoAsync(String rid) {
        return CompletableFuture.supplyAsync(() -> {
            MetaInfoRequestDto metaInfoRequestDto = new MetaInfoRequestDto();
            metaInfoRequestDto.setId(rid);
            metaInfoRequestDto.setProcess("NEW");
            metaInfoRequestDto.setSource("REGISTRATION_CLIENT");
            metaInfoRequestDto.setBypassCache(true);
            return makePostRequest(createRequestDto(metaInfoRequestDto), ConstantValue.METAINFOAPI);
        }, executor).orTimeout(ConstantValue.executorShutdown, TimeUnit.SECONDS);
//        }, executor);
    }

    // Fetch MV JSON Data with parallel execution
   /* public MvJsonResponseDto getMVJson(String rid) throws Exception {

        logger.info("`Inside MVJson for all api call ");
        MvJsonResponseDto res = new MvJsonResponseDto();
        initializeExecutor();
        tokenGenerator.getToken();



        // Fetch audit and other responses in parallel
        CompletableFuture<ResponseDto> auditFuture = getAuditsAsync(rid);
        CompletableFuture<ResponseDto> metaInfoFuture = getMetaInfoAsync(rid);
        CompletableFuture<ResponseDto> identityFuture = getIdentityAsync(rid);
        CompletableFuture<ResponseDto> bioFuture = getBiometricsAsync(rid);

        // Fetch documents in parallel
        List<String> documentTypes = Arrays.asList("proofOfAddress", "proofOfIdentity", "proofOfEvidence");
        List<CompletableFuture<Map<String, String>>> documentFutures = new ArrayList<>();
        Map<String, String> docMap = new ConcurrentHashMap<>();
        try{
             documentFutures = documentTypes.stream()
                    .map(dType -> getDocumentAsync(rid, dType).thenApply(docResponse -> {
                        if (docResponse != null && docResponse.getResponse() != null) {
                            try {
                                com.eagle.mas.dto.Document doc = obj.readValue(
                                        obj.writeValueAsString(docResponse.getResponse()), com.eagle.mas.dto.Document.class);
                                docMap.put(dType, Base64.getEncoder().encodeToString(doc.getDocument()));
                                logger.info("Successfully fetched documents");
                                return docMap;
                            } catch (JsonProcessingException e) {
//                            throw new RuntimeException("Error processing document response", e);
                                logger.info("Error while processing document response",e);
                            }
                        }
                        return null;
                    }))
                    .collect(Collectors.toList());

                 CompletableFuture.allOf(auditFuture, metaInfoFuture, identityFuture, bioFuture,documentFutures.get(0),documentFutures.get(1), documentFutures.get(2))
                    .thenRun(() -> {
                        logger.info("All API calls completed");
                    })
                    .join();
        }
        catch (Exception e){
            logger.info("Error while calling documents api");
        }


        // Wait for all async operations to complete

        try{
            ResponseDto auditResponse = auditFuture.get();
            if (auditResponse != null && auditResponse.getResponse() != null) {
                res.setAudits(obj.writeValueAsString(auditResponse.getResponse()));
                logger.info("Successfully fetched audits");
            } else {
//            throw new Exception("Audit Response not available");
                logger.info("Audit Response not available");
            }
        }
        catch (Exception e){
            logger.info("Error while Processing audit response",e);
        }


        try {
            if (docMap != null && !docMap.isEmpty()) {
                res.setDocuments(docMap);
            } else {
                logger.warn("Document map is empty");
            }
        } catch (Exception e) {
            logger.error("Error while processing documents", e);
        }

        // Process meta info
        try{
            ResponseDto metaInfoResponse = metaInfoFuture.get();
            JsonNode responseNode = obj.valueToTree(metaInfoResponse.getResponse());
            JsonNode fieldsNode = responseNode.path("fields");
            if (!fieldsNode.isMissingNode()) {
                res.setMetaInfo(fieldsNode.toString());
                logger.info("Successfully fetched meta info");

            } else {
//            throw new Exception("MetaInfo Response not available");
                logger.info("MetaInfo Response not available");
            }
        }
        catch (Exception e){
            logger.info("Error while processing MetaInfo response",e);
        }


        // Process identity

        try{
            ResponseDto identityResponse = identityFuture.get();
            if (identityResponse != null && identityResponse.getResponse() != null) {
                FieldResponseDto fieldResponseDto = obj.readValue(
                        javaObjectToJsonString(identityResponse.getResponse()), FieldResponseDto.class);
                res.setIdentity(fieldResponseDto.getFields());
                logger.info("Successfully fetched Identity");

            } else {
//            throw new Exception("Identity Response not available");
                logger.info("Identity Response not available");
            }
        }
        catch (Exception e){
            logger.info("Error while processing Identity response",e);
        }


        // Process biometrics
        try{
            ResponseDto bioResponse = bioFuture.get();
            if (bioResponse != null && bioResponse.getResponse() != null) {
                byte[] bio = xmlString(bioResponse.getResponse());
                String bioEncode = Base64.getUrlEncoder().encodeToString(bio);
                res.setBiometrics(bioEncode);
                logger.info("Successfully fetched biometrics");

            } else {
                logger.info("Biometric Response not available");
            }
        }
        catch (Exception e){
            logger.info("Error while processing biometrics response",e);
        }

        return res;
    }*/



    public MvJsonResponseDto getMVJson(String rid) {
        logger.info("Inside MVJson for all api call");
        MvJsonResponseDto res = new MvJsonResponseDto();

        try {
            initializeExecutor();
            tokenGenerator.getToken();

            // Fetch audit and other responses in parallel with exception handling
            CompletableFuture<ResponseDto> auditFuture = getAuditsAsync(rid)
                    .exceptionally(ex -> {
                        logger.error("Error fetching audits", ex);
                        return null;
                    });

            CompletableFuture<ResponseDto> metaInfoFuture = getMetaInfoAsync(rid)
                    .exceptionally(ex -> {
                        logger.error("Error fetching meta info", ex);
                        return null;
                    });

            CompletableFuture<ResponseDto> identityFuture = getIdentityAsync(rid)
                    .exceptionally(ex -> {
                        logger.error("Error fetching identity", ex);
                        return null;
                    });

            CompletableFuture<ResponseDto> bioFuture = getBiometricsAsync(rid)
                    .exceptionally(ex -> {
                        logger.error("Error fetching biometrics", ex);
                        return null;
                    });

            // Fetch documents in parallel
            List<String> documentTypes = Arrays.asList("proofOfAddress", "proofOfIdentity", "proofOfEvidence");
            Map<String, String> docMap = new HashMap<>();

            List<CompletableFuture<Void>> docFutures = documentTypes.stream()
                    .map(dType -> getDocumentAsync(rid, dType)
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
                        res.setDocuments(docMap);
                        logger.info("Successfully fetched documents");
                    })
                    .join();

            // Combine all futures
            List<CompletableFuture<?>> allFutures = new ArrayList<>();
            allFutures.add(auditFuture);
            allFutures.add(metaInfoFuture);
            allFutures.add(identityFuture);
            allFutures.add(bioFuture);

            // Wait for all async operations to complete
            CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).thenRun(() -> {
                        logger.info("All API calls completed");
                    })
                    .join();

            // Process audit response
            ResponseDto auditResponse = auditFuture.get();
            if (auditResponse != null && auditResponse.getResponse() != null) {
                try {
                    res.setAudits(obj.writeValueAsString(auditResponse.getResponse()));
                    logger.info("Successfully fetched audits");
                } catch (JsonProcessingException e) {
                    logger.error("Error processing audit response", e);
                }
            } else {
                logger.warn("Audit Response not available");
            }

            // Process meta info
            ResponseDto metaInfoResponse = metaInfoFuture.get();
            if (metaInfoResponse != null && metaInfoResponse.getResponse() != null) {
                try {
                    JsonNode responseNode = obj.valueToTree(metaInfoResponse.getResponse());
                    JsonNode fieldsNode = responseNode.path("fields");
                    if (!fieldsNode.isMissingNode()) {
                        res.setMetaInfo(fieldsNode.toString());
                        logger.info("Successfully fetched meta info");
                    } else {
                        logger.warn("MetaInfo fields not found in response");
                    }
                } catch (Exception e) {
                    logger.error("Error processing meta info response", e);
                }
            } else {
                logger.warn("MetaInfo Response not available");
            }

            // Process identity
            ResponseDto identityResponse = identityFuture.get();
            if (identityResponse != null && identityResponse.getResponse() != null) {
                try {
                    FieldResponseDto fieldResponseDto = obj.readValue(
                            javaObjectToJsonString(identityResponse.getResponse()),
                            FieldResponseDto.class);
                    res.setIdentity(fieldResponseDto.getFields());
                    logger.info("Successfully fetched Identity");
                } catch (Exception e) {
                    logger.error("Error processing identity response", e);
                }
            } else {
                logger.warn("Identity Response not available");
            }

            // Process biometrics
            ResponseDto bioResponse = bioFuture.get();
            if (bioResponse != null && bioResponse.getResponse() != null) {
                try {
                    byte[] bio = xmlString(bioResponse.getResponse());
                    String bioEncode = Base64.getUrlEncoder().encodeToString(bio);
                    res.setBiometrics(bioEncode);
                    logger.info("Successfully fetched biometrics");
                } catch (Exception e) {
                    logger.error("Error processing biometrics response", e);
                }
            } else {
                logger.warn("Biometric Response not available");
            }

        } catch (Exception e) {
            logger.error("Error in getMVJson processing", e);
        }

        return res;
    }


    public byte[] xmlString(Object jsonObject){
        try {
            // Initialize the ObjectMapper and XmlMapper
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(jsonObject));  // Parse the JSON string to a JsonNode

            // Extract segments and map to BIR objects
            List<BIR> birList = new ArrayList<>();
            JsonNode segments = jsonNode.get("segments");
            for (JsonNode segment : segments) {
                BIR bir = objectMapper.treeToValue(segment, BIR.class);
                birList.add(bir);
            }

            // Create BIRResponse object
            BIRResponse birResponse = new BIRResponse();
            birResponse.setXmlns("http://standards.iso.org/iso-iec/19785/-3/ed-2/");
            birResponse.setBirList(birList);

            // Convert to XML
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(birResponse);

            String modifiedXml = xml.replaceAll("<type>", "<Type>").replaceAll("</type>", "</Type>")
                    .replaceAll("<subtype>", "<Subtype>").replaceAll("</subtype>", "</Subtype>")
                    .replaceAll("<score>", "<Score>").replaceAll("</score>", "</Score>")
                    .replaceAll("<bdb>", "<BDB>").replaceAll("</bdb>", "</BDB>");
            return modifiedXml.getBytes();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String javaObjectToJsonString(Object className) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        String outputJson = null;

        try {
            outputJson = objectMapper.writeValueAsString(className);
            return outputJson;
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
           e.printStackTrace();
           return null;
        }
    }


    public CompletableFuture<ResponseDto> getIdentityAsync1(String rid) {
        return CompletableFuture.supplyAsync(() -> {
            IdentityRequestDto searchFieldRequestDto = new IdentityRequestDto();
            searchFieldRequestDto.setId(rid);
            searchFieldRequestDto.setFields(List.of("UIN"));
            searchFieldRequestDto.setSource("REGISTRATION_CLIENT");
            searchFieldRequestDto.setProcess("UPDATE");
            searchFieldRequestDto.setBypassCache(true);
            return makePostRequest(createRequestDto(searchFieldRequestDto), ConstantValue.SEARCHFIELDAPI);
//        }, executor);
        }, executor).orTimeout(ConstantValue.executorShutdown, TimeUnit.SECONDS);
    }


    public <T> ResponseDto makeGetRequest(String rid, String url) {
        try {
            logger.info("External api calling url: "+ url);
            HttpHeaders headers = new HttpHeaders();
            tokenGenerator.getToken();
            headers.set("Cookie", TokenGenerator.validToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<T> entity = new HttpEntity<>(headers);
            ResponseEntity<ResponseDto> response = restTemplate.exchange(url + rid, HttpMethod.GET, entity, ResponseDto.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling API: " + url, e);
        }
    }





}

