package com.valdirsantos714.communitycenter.payload;

import com.valdirsantos714.communitycenter.model.Resource;

import java.util.List;

public record ExchangeResourcesRequest(List<Resource> fromResources, List<Resource> toResources) {
}
