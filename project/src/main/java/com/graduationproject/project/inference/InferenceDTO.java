package com.graduationproject.project.inference;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class InferenceDTO {
private Integer id;
private String query;
private String response;
private Date whenMade;
private boolean correct;    
private String incorrectWords;

public InferenceDTO(Integer id, String query, String response, Date whenMade, boolean correct,String incorrectWords) {
    this.id = id;
    this.query = query;
    this.response = response;
    this.whenMade = whenMade;
    this.correct = correct;
    this.incorrectWords = incorrectWords;
}

}
