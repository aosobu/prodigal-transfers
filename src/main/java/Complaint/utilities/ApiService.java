package Complaint.utilities;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Collections;

/**
 * @author A Osobu
 * @date 15/09/2020
 */
@Service
public class ApiService {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static Logger logger = LoggerFactory.getLogger(ApiService.class);

    public static <T> ResponseEntity exchange(URI uri, HttpMethod httpMethod, HttpEntity<T> request) throws Exception {
        ResponseEntity<?> responseEntity;
        try {
            logger.info("URI :: " + uri.toString());
            logger.info("Http Method :: " + httpMethod.toString());

            responseEntity = restTemplate.exchange(uri, httpMethod, request, String.class);
        }catch(HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }catch(HttpServerErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }catch(Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
        return responseEntity;
    }

    public static <T> HttpEntity<T> generateRequest(T requestBody, HttpHeaders headers){
        return new HttpEntity<>(requestBody, headers);
    }

    public static URI getUri(String url){
        URI uri = null;
        try {
            uri = new URI(url).normalize();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }

    public static  HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        return headers;
    }

    public static HttpHeaders setAuthorizationHeader(HttpHeaders headers, String token){
        headers.add("Authorization", token);
        return headers;
    }

    public static HttpHeaders getAuthorizationHeader(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.add("Authorization", token);
        return headers;
    }

    private static HttpHeaders setCustomHeader(String key, String value, HttpHeaders headers){
        headers.set(key, value);
        return headers;
    }

    public static String generateBasicAuthorizationToken(String username, String password, String seperator){
        seperator = StringUtils.isEmpty(seperator) ? ":" : seperator;
        String basicToken = username.concat(seperator).concat(password);
        String base64Creds = Base64.getEncoder().encodeToString(basicToken.getBytes());
        return "Basic " + base64Creds;
    }
}

