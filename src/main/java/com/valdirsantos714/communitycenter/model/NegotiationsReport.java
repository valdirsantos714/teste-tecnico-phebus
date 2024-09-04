package com.valdirsantos714.communitycenter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import java.util.List;

@Data
@Document(collection = "negotiation_reports")
public class NegotiationsReport {

    @Id
    private String id;
    private String sourceCenterId; // ID do centro comunitário que iniciou a negociação
    private String targetCenterId; // ID do centro comunitário que recebeu a negociação
    private List<Resource> exchangedResources; // Recursos trocados na negociação
    private LocalDate negotiationDate; // Data da negociação
}
