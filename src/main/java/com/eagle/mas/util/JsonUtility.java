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
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@Component
public class JsonUtility {

   /* @Value("${KEYBASEDTOKENAPI}")
    public String authUrl;

    @Value("${BIOAPI}")
    public String bioUrl;

    @Value("${METAINFOAPI}")
    public String metaInfoUrl;

    @Value("${DOCUMENTAPI}")
    public String documentUrl;

    @Value("${SEARCHFIELDAPI}")
    public String searchFieldUrl;

    @Value("${AUDITAPI}")
    public String auditUrl;*/

    @Autowired
    Environment environment;

    @Autowired
    TokenGenerator tokenGenerator;

    @Autowired
    RestTemplate restTemplate;

    public static List<String> fields;

    private static Logger logger = LoggerFactory.getLogger(JsonUtility.class);

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




    public LocalDateTime getUTCCurrentDateTime() {
        return ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
    }

    private <T> ResponseDto makePostRequest(T requestBody, String url) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", tokenGenerator.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<T> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, ResponseDto.class);

        return response.getBody();
    }

    private <T> RequestDto createRequestDto(T requestDetails) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("", ""); // Add actual metadata if needed

        return new RequestDto(
                "",  // ID (Set dynamically)
                metadata,
                requestDetails,
                LocalDateTime.now(),
                ""   // Version (Set dynamically)
        );
    }

    public ResponseDto getAudits(String rid) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        AuditRequestDto auditRequestDto = new AuditRequestDto();
        auditRequestDto.setId(rid);
        auditRequestDto.setProcess("NEW");
        auditRequestDto.setBypassCache(true);
        auditRequestDto.setSource("REGISTRATION_CLIENT");
        return makePostRequest(createRequestDto(auditRequestDto), ConstantValue.AUDITAPI);
    }

    public ResponseDto getBiometrics(String rid) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        BiometricRequestDto biometricRequestDto = new BiometricRequestDto();
        biometricRequestDto.setId(rid);
        biometricRequestDto.setBypassCache(true);
        biometricRequestDto.setProcess("NEW");
        biometricRequestDto.setSource("REGISTRATION_CLIENT");
        biometricRequestDto.setModalities(new ArrayList<>());
        biometricRequestDto.setPerson("individualBiometrics");
        return makePostRequest(createRequestDto(biometricRequestDto), ConstantValue.BIOAPI);
    }

    public ResponseDto getDocument(String rid, String dType) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        DocumentRequestDto documentRequestDto = new DocumentRequestDto();
        documentRequestDto.setId(rid);
        documentRequestDto.setSource("REGISTRATION_CLIENT");
        documentRequestDto.setProcess("NEW");
        documentRequestDto.setDocumentName(dType);
        return makePostRequest(createRequestDto(documentRequestDto), ConstantValue.DOCUMENTAPI);
    }

    public ResponseDto getIdentity(String rid) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        IdentityRequestDto searchFieldRequestDto = new IdentityRequestDto();
        searchFieldRequestDto.setId(rid);
        searchFieldRequestDto.setFields(fields);
        searchFieldRequestDto.setSource("REGISTRATION_CLIENT");
        searchFieldRequestDto.setProcess("NEW");
        searchFieldRequestDto.setBypassCache(true);

        return makePostRequest(createRequestDto(searchFieldRequestDto), ConstantValue.SEARCHFIELDAPI);
    }

    public ResponseDto getMetaInfo(String rid) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        MetaInfoRequestDto metaInfoRequestDto = new MetaInfoRequestDto();
        metaInfoRequestDto.setId(rid);
        metaInfoRequestDto.setProcess("NEW");
        metaInfoRequestDto.setSource("REGISTRATION_CLIENT");
        metaInfoRequestDto.setBypassCache(true);
        return makePostRequest(createRequestDto(metaInfoRequestDto), ConstantValue.METAINFOAPI);
    }

    public MvJsonResponseDto getMVJson(String rid) throws Exception{

        MvJsonResponseDto res = new MvJsonResponseDto();
        ResponseDto auditResponse = getAudits(rid);
        if(auditResponse != null && auditResponse.getResponse() != null) {
            res.setAudits(obj.writeValueAsString(auditResponse.getResponse()));
        }
        else{
            throw new Exception("Audit Response not available");
        }

        logger.info("-----------AUDIT----------");
        File f = new File("audit.txt");
        FileWriter fw = new FileWriter(f);
        fw.write(res.getAudits());
        fw.flush();

        Map<String,String> docs = new HashMap<>();
        List<String> dl = Arrays.asList("proofOfAddress","proofOfIdentity","proofOfEvidence");
        for(String d : dl){
            ResponseDto documentResponse = getDocument(rid,d);
            if(documentResponse != null && documentResponse.getResponse() != null) {
                com.eagle.mas.dto.Document dts = obj.readValue(obj.writeValueAsString(documentResponse.getResponse()), com.eagle.mas.dto.Document.class);
                docs.put(d, (Base64.getEncoder().encodeToString(dts.getDocument())));
                res.setDocuments(docs);
            }

            if(docs.isEmpty()){
                throw new Exception("Document Response not available");
            }

        }


        logger.info("------------DOCUMENT------------");

        ResponseDto metaInfoResponse = getMetaInfo(rid);
        JsonNode responseNode = obj.valueToTree(metaInfoResponse.getResponse());  // Converts the response to a JsonNode
        JsonNode fieldsNode = responseNode.path("fields");

        if (!fieldsNode.isMissingNode()) {
            res.setMetaInfo(fieldsNode.toString());
        } else {
            throw new Exception("MetaInfo Response not available");
        }

        logger.info("-------------META INFO-------------");

        ResponseDto identityResponse = getIdentity(rid);
        if(identityResponse != null && identityResponse.getResponse() != null) {
            FieldResponseDto fieldResponseDto = obj.readValue(javaObjectToJsonString(identityResponse.getResponse()), FieldResponseDto.class);
            res.setIdentity(fieldResponseDto.getFields());
        }
        else {
            throw new Exception("Identity Response not available");
        }

        logger.info("------------IDENTITY--------------");

        ResponseDto bioResponse = getBiometrics(rid);
        if(bioResponse != null && bioResponse.getResponse() != null) {
           byte[] bio =  xmlString(bioResponse.getResponse());
           String bioEncode = Base64.getUrlEncoder().encodeToString(bio);
           res.setBiometrics(bioEncode);
        }
        else {
            throw new Exception("Identity Response not available");
        }

        logger.info("---------------BIOMETRIC---------------");
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



}

