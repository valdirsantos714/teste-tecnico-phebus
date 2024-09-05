package com.valdirsantos714.communitycenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "community_centers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommunityCenter {

    @Id
    private String id;
    private String name;

    // Referência para o endereço
    @DBRef
    private Address address;

    private String location;
    private int maxCapacity;
    private int currentOccupancy;

    @JsonIgnore
    @DBRef
    private List<Resource> resources = new ArrayList<>();

    @JsonIgnore
    @DBRef
    private List<NegotiationsReport> negotiationReports = new ArrayList<>();

    // Métodos auxiliares
    public int getOccupancyPercentage() {
        return (currentOccupancy * 100) / maxCapacity;
    }

    public CommunityCenter(CommunityCenterPayloadRequest payloadRequest) {
        this.name = payloadRequest.name();
        this.address = payloadRequest.address();
        this.location = payloadRequest.location();
        this.maxCapacity = payloadRequest.maxCapacity();
        this.currentOccupancy = payloadRequest.currentOccupancy();
        this.resources = payloadRequest.resources();

    }


}
