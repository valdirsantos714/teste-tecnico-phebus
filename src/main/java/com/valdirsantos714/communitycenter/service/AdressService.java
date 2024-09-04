package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.Adress;
import com.valdirsantos714.communitycenter.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdressService {

    @Autowired
    private AdressRepository repository;

    public Adress addAdress(Adress adress) {
        return repository.save(adress);
    }

    public List<Adress> addAllAdresss(List<Adress> adress) {
        return repository.saveAll(adress);
    }

    public Adress getAdressById(String adressId) {
        var adress = repository.findById(adressId).orElseThrow(() -> new RuntimeException("Erro! Adress não encontrado!"));
        return adress;
    }
    
    public Adress updateAdress(String adressId, Adress adress) {
        Adress adressOld = getAdressById(adressId);

        adressOld.setAdressCep(adress.getAdressCep());
        adressOld.setAdressCity(adress.getAdressCity());
        adressOld.setAdressComplement(adress.getAdressComplement());
        adressOld.setAdressNumber(adress.getAdressNumber());
        adressOld.setAdressStreet(adress.getAdressStreet());
        adressOld.setAdressReference(adress.getAdressReference());

        repository.save(adressOld);
        return adressOld;
    }

    
    public void deleteAdress(String AdressId) {
        repository.deleteById(AdressId);
    }

    public List<Adress> getAllAdresss() {
        return repository.findAll();
    }
}