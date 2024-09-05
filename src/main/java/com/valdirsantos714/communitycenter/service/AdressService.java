package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.Address;
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

    public Address addAdress(Address address) {
        return repository.save(address);
    }

    public List<Address> addAllAdresss(List<Address> addresses) {
        return repository.saveAll(addresses);
    }

    public Address getAdressById(String adressId) {
        var adress = repository.findById(adressId).orElseThrow(() -> new RuntimeException("Erro! Adress não encontrado!"));
        return adress;
    }
    
    public Address updateAdress(String adressId, Address address) {
        Address addressOld = getAdressById(adressId);

        addressOld.setAdressCep(address.getAdressCep());
        addressOld.setAdressCity(address.getAdressCity());
        addressOld.setAdressComplement(address.getAdressComplement());
        addressOld.setAdressNumber(address.getAdressNumber());
        addressOld.setAdressStreet(address.getAdressStreet());
        addressOld.setAdressReference(address.getAdressReference());

        repository.save(addressOld);
        return addressOld;
    }

    
    public void deleteAdress(String AdressId) {
        repository.deleteById(AdressId);
    }

    public List<Address> getAllAdresss() {
        return repository.findAll();
    }
}