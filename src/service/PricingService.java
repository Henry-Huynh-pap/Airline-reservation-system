package service;

import model.TicketClass;

public class PricingService {

    private static final double BUSINESS_MULTIPLIER = 1.5;

    public double calculatePrice(double basePrice, TicketClass ticketClass){
        if(basePrice <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }
        else if(ticketClass == null ){
            throw new IllegalArgumentException("Ticket class can't be empty");
        }

        return switch (ticketClass){
            case ECONOMY -> basePrice;
            case BUSINESS -> basePrice*BUSINESS_MULTIPLIER;
        };
    }
}
