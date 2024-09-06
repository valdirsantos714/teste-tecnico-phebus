package com.valdirsantos714.communitycenter.payload;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.model.Resource;

import java.time.LocalDateTime;
import java.util.List;

public record NegotiationsReportPayloadResponse (String id,
                                                 String sourceCenterId,
                                                 String targetCenterId,
                                                 List<Resource> exchangedResources,
                                                 LocalDateTime negotiationDate,
                                                 CommunityCenter initiatingCommunityCenter,
                                                 CommunityCenter otherCommunityCenter) {

    public NegotiationsReportPayloadResponse(NegotiationsReport negotiationsReport) {
        this(negotiationsReport.getId(), negotiationsReport.getSourceCenterId(), negotiationsReport.getTargetCenterId(), negotiationsReport.getExchangedResources(), negotiationsReport.getNegotiationDate(), negotiationsReport.getInitiatingCommunityCenter(), negotiationsReport.getOtherCommunityCenter());
    }
}
