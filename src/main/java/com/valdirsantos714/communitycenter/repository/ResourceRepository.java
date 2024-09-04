package com.valdirsantos714.communitycenter.repository;

import com.valdirsantos714.communitycenter.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String> {
}
