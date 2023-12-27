package com.graduationproject.project.subscription;

import lombok.Getter;

public enum SubscriptionType {
MONTHLY(1000*60*24*30,9.99),
SEMIANNUAL(1000*60*24*30*6,29.99), 
ANNUALLY(1000*60*24*30*12,54.99);
@Getter
private final long period;
@Getter
private final double price;
private SubscriptionType(final long period,final double price){
this.period=period;
this.price=price;
}
}
