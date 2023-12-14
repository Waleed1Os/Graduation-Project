package com.graduationproject.project.subscription;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;





@Getter
@Setter
public class SubscriptionDTO {

    private int id;
    private Date whenMade;
    private SubscriptionType type;
    private double price;

}
