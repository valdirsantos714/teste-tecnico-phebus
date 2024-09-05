package com.valdirsantos714.communitycenter.repository;

import com.valdirsantos714.communitycenter.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends MongoRepository<Address, String> {
}
