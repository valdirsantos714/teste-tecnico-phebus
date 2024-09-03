package com.valdirsantos714.communitycenter.controller;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadResponse;
import com.valdirsantos714.communitycenter.service.CommunityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/community-centers")
public class CommunityCenterController {

    @Autowired
    private CommunityCenterService service;

    @PostMapping
    public ResponseEntity addCenter(@RequestBody CommunityCenterPayloadRequest center, UriComponentsBuilder builder) {
        var community = service.addCommunityCenter(center);
        var uri = builder.path("/api/community-centers/{id}").buildAndExpand(community.getId()).toUri();

        return ResponseEntity.created(uri).body(new CommunityCenterPayloadResponse(community));
    }

    @PutMapping("/{id}/occupancy")
    public ResponseEntity updateOccupancy(@PathVariable String id, @RequestParam int occupancy) {
        var community = service.updateOccupancy(id, occupancy);

        return ResponseEntity.ok().body(new CommunityCenterPayloadResponse(community));
    }

    @PostMapping("/exchange-resources")
    public ResponseEntity exchangeResources(@RequestParam String fromCenterId, @RequestParam String toCenterId,
                                  @RequestBody List<Resource> fromResources, @RequestBody List<Resource> toResources) {
        service.exchangeResources(fromCenterId, toCenterId, fromResources, toResources);


        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/high-occupancy")
    public ResponseEntity getHighOccupancyCenters() {
        var list = service.getHighOccupancyCenters();
        return ResponseEntity.ok(list.stream().map(CommunityCenterPayloadResponse::new));
    }

    @GetMapping
    public ResponseEntity getAllCenters() {
        var list = service.getAllCenters();
        return ResponseEntity.ok(list.stream().map(CommunityCenterPayloadResponse::new));
    }
}
