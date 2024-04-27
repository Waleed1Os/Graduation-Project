package com.graduationproject.project.inference;
import java.util.Date;
public record InferenceRequest(
    String query,
    Date whenCreated
) {}
