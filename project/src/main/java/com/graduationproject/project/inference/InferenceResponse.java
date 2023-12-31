package com.graduationproject.project.inference;

import lombok.Builder;

/**
 *This will be the response from the AI if we intended that Java's server is responsible for it 
 */
@Builder
public record InferenceResponse( int id,    
 String response) {     
}
