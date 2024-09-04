package com.valdirsantos714.communitycenter.repository;

import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegotiationsReportRepository extends MongoRepository<NegotiationsReport, String> {
}
