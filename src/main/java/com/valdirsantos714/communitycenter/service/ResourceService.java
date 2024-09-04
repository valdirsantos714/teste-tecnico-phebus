package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceService {

    @Autowired
    private ResourceRepository repository;

    public Resource addResource(Resource resource) {
        return repository.save(resource);
    }

    public List<Resource> addAllResources(List<Resource> resources) {
        return repository.saveAll(resources);
    }

    public Resource getResourceById(String resourceId) {
        var resource = repository.findById(resourceId).orElseThrow(() -> new RuntimeException("Erro! Resource não encontrado!"));
        return resource;
    }
    
    public Resource updateResource(String resourceId, Resource resource) {
        Resource resourceOld = getResourceById(resourceId);

        resourceOld.setType(resource.getType());
        resourceOld.setQuantity(resource.getQuantity());
        resourceOld.setPoints(resource.getPoints());

        repository.save(resourceOld);
        return resourceOld;
    }

    
    public void deleteResource(String resourceId) {
        repository.deleteById(resourceId);
    }

    public List<Resource> getAllResources() {
        return repository.findAll();
    }
}