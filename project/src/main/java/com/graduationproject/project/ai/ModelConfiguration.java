package com.graduationproject.project.ai;


import java.io.IOException;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.modality.Classifications.Classification;
import ai.djl.modality.nlp.qa.QAInput;
import ai.djl.nn.Parameter;
import ai.djl.training.initializer.Initializer;
import ai.djl.translate.Translator;


@Configuration
public class ModelConfiguration {
    
@Bean
public Model loadAraBERTModel() throws IOException, ModelException{
    return Model.newInstance("models/arabert_model.pth");
    }
    @Bean
    public Translator<QAInput,Classification> createAraBERTTranslator(){
        return createAraBERTTranslator();
    }
    @Bean
    public Parameter parameters(){
        return new Parameter
        .Builder()
        .optInitializer(Initializer.ZEROS)
        .build();
    }
}
