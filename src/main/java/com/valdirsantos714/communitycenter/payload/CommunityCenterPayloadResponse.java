package com.valdirsantos714.communitycenter.payload;

import com.valdirsantos714.communitycenter.model.Address;
import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.model.Resource;

import java.util.List;

public record CommunityCenterPayloadResponse(String id,
                                             String name,
                                             Address address,
                                             String location,
                                             int maxCapacity,
                                             int currentOccupancy,
                                             List<Resource> resources,
                                             List<NegotiationsReport> negotiationsReports) {

    public CommunityCenterPayloadResponse(CommunityCenter c) {
        this(c.getId(), c.getName(), c.getAddress(), c.getLocation(), c.getMaxCapacity(), c.getCurrentOccupancy(), c.getResources(), c.getNegotiationReports());
    }
}
