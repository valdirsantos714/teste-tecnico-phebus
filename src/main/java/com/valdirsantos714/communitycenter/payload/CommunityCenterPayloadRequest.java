package com.valdirsantos714.communitycenter.payload;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.Resource;

import java.util.List;

public record CommunityCenterPayloadRequest(String name,
                                            String address,
                                            String location,
                                            int maxCapacity,
                                            int currentOccupancy,
                                            List<Resource> resources) {

    public CommunityCenterPayloadRequest(CommunityCenter c) {
        this(c.getName(), c.getAddress(), c.getLocation(), c.getMaxCapacity(), c.getCurrentOccupancy(), c.getResources());
    }
}

