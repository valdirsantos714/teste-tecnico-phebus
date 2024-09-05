package com.valdirsantos714.communitycenter.payload;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.model.Resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record NegotiationsPayloadResponse(String id,
                                          String sourceCenterId,
                                          String targetCenterId,
                                          List<Resource> exchangedResources,
                                          LocalDateTime negotiationDate,
                                          CommunityCenter initiatingCommunityCenter,
                                          CommunityCenter otherCommunityCenter) {

    public NegotiationsPayloadResponse(NegotiationsReport report) {
        this(report.getId(), report.getSourceCenterId(), report.getTargetCenterId(), report.getExchangedResources(), report.getNegotiationDate(), report.getInitiatingCommunityCenter(), report.getOtherCommunityCenter());
    }
}
