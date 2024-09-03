package com.valdirsantos714.communitycenter.controller;

import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadResponse;
import com.valdirsantos714.communitycenter.service.CommunityCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Salva Community Center no sistema",  requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto JSON contendo os dados da Community Center",
            required = true,
            content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(description = "Community Center salvo com sucesso", responseCode = "201",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @PostMapping
    public ResponseEntity addCenter(@RequestBody CommunityCenterPayloadRequest center, UriComponentsBuilder builder) {
        var community = service.addCommunityCenter(center);
        var uri = builder.path("/api/community-centers/{id}").buildAndExpand(community.getId()).toUri();

        return ResponseEntity.created(uri).body(new CommunityCenterPayloadResponse(community));
    }

    @Operation(summary = "Atualiza ocupação do centro comunitário",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                     @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @PutMapping("/{id}/occupancy")
    public ResponseEntity updateOccupancy(@PathVariable String id, @RequestParam int occupancy) {
        var community = service.updateOccupancy(id, occupancy);

        return ResponseEntity.ok().body(new CommunityCenterPayloadResponse(community));
    }

    @Operation(summary = "Realiza o intercâmbio de recursos entre dois centros comunitários.",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @PostMapping("/exchange-resources")
    public ResponseEntity exchangeResources(@RequestParam String fromCenterId, @RequestParam String toCenterId,
                                  @RequestBody List<Resource> fromResources, @RequestBody List<Resource> toResources) {
        service.exchangeResources(fromCenterId, toCenterId, fromResources, toResources);


        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @Operation(summary = "Retorna uma lista dos centros comunitários com que estão com as maiores ocupações no momento.",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @GetMapping("/high-occupancy")
    public ResponseEntity getHighOccupancyCenters() {
        var list = service.getHighOccupancyCenters();
        return ResponseEntity.ok(list.stream().map(CommunityCenterPayloadResponse::new));
    }


    @Operation(summary = "Retorna uma lista de todos os centros comunitários",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @GetMapping
    public ResponseEntity getAllCenters() {
        var list = service.getAllCenters();
        return ResponseEntity.ok(list.stream().map(CommunityCenterPayloadResponse::new));
    }
}
