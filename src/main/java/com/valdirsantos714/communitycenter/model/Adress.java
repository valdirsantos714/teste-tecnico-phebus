package com.valdirsantos714.communitycenter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "addresses")
public class Adress {

    @Id
    private String adressId;
    private String adressCep;
    private String adressStreet;
    private String adressCity;
    private int adressNumber;
    private String adressComplement;
    private String adressReference;

}
