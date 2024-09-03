package com.valdirsantos714.communitycenter.payload;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.Resource;

import java.util.List;

public record CommunityCenterPayloadResponse(String id,
                                             String name,
                                             String address,
                                             String location,
                                             int maxCapacity,
                                             int currentOccupancy,
                                             List<Resource> resources) {

    public CommunityCenterPayloadResponse(CommunityCenter c) {
        this(c.getId(), c.getName(), c.getAddress(), c.getLocation(), c.getMaxCapacity(), c.getCurrentOccupancy(), c.getResources());
    }
}
