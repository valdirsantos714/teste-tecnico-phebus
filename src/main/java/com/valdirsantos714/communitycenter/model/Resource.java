package com.valdirsantos714.communitycenter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    private String type;
    private int quantity;
    private int points;

    public static final int DOCTOR_POINTS = 4;
    public static final int VOLUNTEER_POINTS = 3;
    public static final int MEDICAL_SUPPLIES_POINTS = 7;
    public static final int TRANSPORT_VEHICLE_POINTS = 5;
    public static final int FOOD_BASKET_POINTS = 2;

    // Método auxiliar
    public int getTotalPoints() {
        return this.points * this.quantity;
    }
}
