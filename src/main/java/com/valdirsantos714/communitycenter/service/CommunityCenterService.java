package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import com.valdirsantos714.communitycenter.repository.CommunityCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommunityCenterService {

    @Autowired
    private CommunityCenterRepository repository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AdressService adressService;

    @Autowired
    private NegotiationsReportService reportService;

    public CommunityCenter addCommunityCenter(CommunityCenterPayloadRequest center) {
        var community = new CommunityCenter(center);

        var adress = adressService.addAdress(community.getAddress());
        var resources = resourceService.addAllResources(community.getResources());
        community.setAddress(adress);
        community.setResources(resources);

        return repository.save(community);
    }

    public CommunityCenter findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Erro! CommunityCenter não encontrado"));
    }

    public CommunityCenter updateOccupancy(String id, int newOccupancy) {
        CommunityCenter center = findById(id);

        center.setCurrentOccupancy(newOccupancy);

        if (center.getOccupancyPercentage() >= 100) {
            throw new RuntimeException("Erro! Centro comunitário está completamente lotado");
        }

        return repository.save(center);
    }

    public List<CommunityCenter> getHighOccupancyCenters() {

        var list = getAllCenters().stream().filter(c -> c.getOccupancyPercentage() > 90).collect(Collectors.toList());
        return list;
    }

    public List<CommunityCenter> getAllCenters() {
        return repository.findAll();
    }

    public void exchangeResources(String fromCenterId, String toCenterId, List<Resource> fromResources, List<Resource> toResources) {
        CommunityCenter fromCenter = findById(fromCenterId);
        CommunityCenter toCenter = findById(toCenterId);

        int fromPoints = fromResources.stream().mapToInt(Resource::getTotalPoints).sum();
        int toPoints = toResources.stream().mapToInt(Resource::getTotalPoints).sum();

        if (fromPoints >= toPoints || fromCenter.getOccupancyPercentage() > 90) {
            fromCenter.getResources().removeAll(fromResources);
            toCenter.getResources().removeAll(toResources);
            fromCenter.getResources().addAll(toResources);
            toCenter.getResources().addAll(fromResources);

            repository.save(fromCenter);
            repository.save(toCenter);

            var negotiation = new NegotiationsReport(null, fromCenterId, toCenterId, fromCenter.getResources(), LocalDateTime.now(), fromCenter, toCenter);
            reportService.addNegotiationsReport(negotiation);

            fromCenter.getNegotiationReports().add(negotiation);
            toCenter.getNegotiationReports().add(negotiation);
            repository.save(fromCenter);
            repository.save(toCenter);

        } else {
            throw new RuntimeException("Os recursos trocados não têm pontos equivalentes e o centro de origem não está superlotado.");
        }
    }

    public List<NegotiationsReport> findNegotiations(String idCommunityCenter) {
        var listNegotiations = reportService.getAllNegotiationsReports().stream().filter(n -> n.getInitiatingCommunityCenter().getId() == idCommunityCenter).collect(Collectors.toList());

        return listNegotiations;
    }

    public Map<String, Double> getAverageResourcesPerCenter() {
        List<CommunityCenter> allCenters = getAllCenters();
        Map<String, Integer> totalResourcesByType = new HashMap<>();
        Map<String, Integer> resourceCountByType = new HashMap<>();

        for (CommunityCenter center : allCenters) {
            for (Resource resource : center.getResources()) {
                String resourceType = resource.getType();
                int quantity = resource.getQuantity();

                // Adiciona ao total de quantidade por tipo de recurso
                totalResourcesByType.put(resourceType, totalResourcesByType.getOrDefault(resourceType, 0) + quantity);

                // Conta quantos centros têm esse recurso
                resourceCountByType.put(resourceType, resourceCountByType.getOrDefault(resourceType, 0) + 1);
            }
        }

        // Calcula a média por tipo de recurso
        Map<String, Double> averageResourcesByType = new HashMap<>();
        for (String resourceType : totalResourcesByType.keySet()) {
            int totalQuantity = totalResourcesByType.get(resourceType);
            int count = resourceCountByType.get(resourceType);

            // Evita divisão por zero
            if (count > 0) {
                averageResourcesByType.put(resourceType, (double) totalQuantity / count);
            }
        }

        return averageResourcesByType;
    }

}
