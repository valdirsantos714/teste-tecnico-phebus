package com.valdirsantos714.communitycenter.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "addresses")
@Getter
@Setter
public class Address {

    @Id
    private String adressId;
    private String adressCep;
    private String adressStreet;
    private String adressCity;
    private int adressNumber;
    private String adressComplement;
    private String adressReference;

}
