package com.valdirsantos714.communitycenter.repository;

import com.valdirsantos714.communitycenter.model.Adress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdressRepository extends MongoRepository<Adress, String> {
}
