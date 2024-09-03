package com.valdirsantos714.communitycenter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "community_centers")
public class CommunityCenter {

    @Id
    private String id;
    private String name;
    private String address;
    private String location;
    private int maxCapacity;
    private int currentOccupancy;
    private List<Resource> resources = new ArrayList<>();

    // Métodos auxiliares
    public int getOccupancyPercentage() {
        return (currentOccupancy * 100) / maxCapacity;
    }
}
