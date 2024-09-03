package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.repository.CommunityCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityCenterService {

    @Autowired
    private CommunityCenterRepository repository;

    public CommunityCenter addCommunityCenter(CommunityCenter center) {
        return repository.save(center);
    }

    public CommunityCenter updateOccupancy(String id, int newOccupancy) {
        CommunityCenter center = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro comunitário não encontrado"));

        center.setCurrentOccupancy(newOccupancy);

        if (center.getOccupancyPercentage() >= 100) {

        }

        return repository.save(center);
    }

    public List<CommunityCenter> getHighOccupancyCenters() {
        return repository.findByCurrentOccupancyGreaterThan(90);
    }

    public List<CommunityCenter> getAllCenters() {
        return repository.findAll();
    }

    public void exchangeResources(String fromCenterId, String toCenterId, List<Resource> fromResources, List<Resource> toResources) {
        CommunityCenter fromCenter = repository.findById(fromCenterId)
                .orElseThrow(() -> new RuntimeException("Centro comunitário de origem não encontrado"));
        CommunityCenter toCenter = repository.findById(toCenterId)
                .orElseThrow(() -> new RuntimeException("Centro comunitário de destino não encontrado"));

        int fromPoints = fromResources.stream().mapToInt(Resource::getTotalPoints).sum();
        int toPoints = toResources.stream().mapToInt(Resource::getTotalPoints).sum();

        if (fromPoints >= toPoints || fromCenter.getOccupancyPercentage() > 90) {
            fromCenter.getResources().removeAll(fromResources);
            toCenter.getResources().removeAll(toResources);
            fromCenter.getResources().addAll(toResources);
            toCenter.getResources().addAll(fromResources);

            repository.save(fromCenter);
            repository.save(toCenter);
        } else {
            throw new RuntimeException("Os recursos trocados não têm pontos equivalentes e o centro de origem não está superlotado.");
        }
    }
}
