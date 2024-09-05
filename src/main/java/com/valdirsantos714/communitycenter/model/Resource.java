package com.valdirsantos714.communitycenter.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "resourcers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Resource {

    @Id
    private String idResource;
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
