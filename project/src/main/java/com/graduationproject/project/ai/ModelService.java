package com.graduationproject.project.ai;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ModelService {
private final RestTemplate restTemplate;    

public ResponseEntity<String> inferPython(String query){
final HttpHeaders header = new HttpHeaders();
header.setContentType(MediaType.APPLICATION_JSON);
final HttpEntity<String> entity=new HttpEntity<String>(query);
   return restTemplate.postForEntity("/api/ai/infer",entity,String.class);

}


}
