package com.valdirsantos714.communitycenter.controller;

import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.*;
import com.valdirsantos714.communitycenter.service.CommunityCenterService;
import com.valdirsantos714.communitycenter.service.NegotiationsReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/community-centers")
public class CommunityCenterController {

    @Autowired
    private CommunityCenterService service;

    @Autowired
    private NegotiationsReportService negotiationsReportService;

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
    @PostMapping("/exchangeResources")
    public ResponseEntity exchangeResources(@RequestParam String fromCenterId, @RequestParam String toCenterId,
                                            @RequestBody ExchangeResourcesRequest request) {
        service.exchangeResources(fromCenterId, toCenterId, request.fromResources(), request.toResources());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    @GetMapping("/all")
    public ResponseEntity getAllCenters() {
        var list = service.getAllCenters();
        return ResponseEntity.ok(list.stream().map(CommunityCenterPayloadResponse::new));
    }

    @Operation(summary = "Retorna uma lista de Negotiations do centro comunitário",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @GetMapping("/{idCommunity}")
    public ResponseEntity getNegotiationsFromCommunityCenter(@PathVariable(name = "idCommunity") String idCommunity) {
        var list = service.findNegotiations(idCommunity);
        return ResponseEntity.ok(list.stream().map(NegotiationsPayloadResponse::new));
    }

    @Operation(summary = "Retorna a quantidade média de cada tipo de recurso por centro comunitário",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @GetMapping("/average-resources")
    public ResponseEntity<Map<String, Double>> getAverageResources() {
        Map<String, Double> averageResources = service.getAverageResourcesPerCenter();
        return ResponseEntity.ok(averageResources);
    }

    @Operation(summary = "Busca relatórios de negociações para um centro comunitário específico a partir de uma data.",
            responses = {
                    @ApiResponse(description = "Requisição feita com sucesso", responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "Centro comunitário não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @GetMapping("/reports/by-community")
    public ResponseEntity getNegotiationsReportsForGivenPeriod(
            @RequestParam("idCommunity") String idCommunity,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime oldDate) {

        // Busca os relatórios de negociações filtrados pela data e pelo ID do centro comunitário
        var reports = negotiationsReportService.getNegotiationsReportsForGivenPeriod(idCommunity, oldDate);

        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se nenhum relatório for encontrado
        }

        // Mapeia os relatórios para o payload de resposta
        List<NegotiationsReportPayloadResponse> responsePayload = reports.stream()
                .map(NegotiationsReportPayloadResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responsePayload);
    }
}
