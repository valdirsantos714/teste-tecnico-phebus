package com.valdirsantos714.communitycenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valdirsantos714.communitycenter.payload.NegotiationsPayloadResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "negotiation_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NegotiationsReport {

    @Id
    private String id;
    private String sourceCenterId; // ID do centro comunitário que iniciou a negociação
    private String targetCenterId; // ID do centro comunitário que recebeu a negociação

    @JsonIgnore
    private List<Resource> exchangedResources; // Recursos trocados na negociação

    @JsonIgnore
    private LocalDateTime negotiationDate; // Data da negociação

    // Centro que iniciou a negociação
    @DBRef
    private CommunityCenter initiatingCommunityCenter;

    // Centro com o qual a negociação foi feita
    @DBRef
    private CommunityCenter otherCommunityCenter;

    public NegotiationsReport(String sourceCenterId, String targetCenterId, List<Resource> exchangedResources, LocalDateTime negotiationDate, CommunityCenter initiatingCommunityCenter, CommunityCenter otherCommunityCenter) {
        this.sourceCenterId = sourceCenterId;
        this.targetCenterId = targetCenterId;
        this.exchangedResources = exchangedResources;
        this.negotiationDate = negotiationDate;
        this.initiatingCommunityCenter = initiatingCommunityCenter;
        this.otherCommunityCenter = otherCommunityCenter;
    }
}
