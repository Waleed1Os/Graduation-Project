package com.graduationproject.project.subscription;

import lombok.Getter;

public enum SubscriptionType {
MONTHLY(1000*60*24*30),
SEMIANNUAL(1000*60*24*30*6), 
ANNUALLY(1000*60*24*30*12);
@Getter
private final long period;
private SubscriptionType(int period){
this.period=period;
}
}
