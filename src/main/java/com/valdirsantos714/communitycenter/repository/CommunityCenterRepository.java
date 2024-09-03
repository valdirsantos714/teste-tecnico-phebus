package com.valdirsantos714.communitycenter.repository;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCenterRepository extends MongoRepository<CommunityCenter, String> {
    List<CommunityCenter> findByCurrentOccupancyGreaterThan(int occupancy);
}
