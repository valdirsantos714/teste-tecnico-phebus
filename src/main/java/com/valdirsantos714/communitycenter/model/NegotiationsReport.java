package com.valdirsantos714.communitycenter.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

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
    private List<Resource> exchangedResources; // Recursos trocados na negociação
    private LocalDate negotiationDate; // Data da negociação

    // Centro que iniciou a negociação
    @DBRef
    private CommunityCenter initiatingCommunityCenter;

    // Centro com o qual a negociação foi feita
    @DBRef
    private CommunityCenter otherCommunityCenter;
}
