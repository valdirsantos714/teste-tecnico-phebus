package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.Address;
import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import com.valdirsantos714.communitycenter.repository.AdressRepository;
import com.valdirsantos714.communitycenter.repository.CommunityCenterRepository;
import com.valdirsantos714.communitycenter.repository.NegotiationsReportRepository;
import com.valdirsantos714.communitycenter.repository.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommunityCenterServiceTest {

    @InjectMocks
    private CommunityCenterService communityCenterService;

    @Mock
    private CommunityCenterRepository repository;

    @Mock
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private AdressRepository adressRepository;

    @Mock
    private NegotiationsReportRepository negotiationsReportRepository;

    @Mock
    private AdressService adressService;

    @Mock
    private NegotiationsReportService reportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Adiciona um novo centro comunitário com recursos e endereço")
    void testAddCommunityCenter() {
        // Dados de teste
        Address address = new Address("1", "12345", "Rua Teste", "Cidade Teste", 100, "Complemento", "Referencia");
        Resource resource = new Resource("1", "Medical Supplies", 10, 7);

        CommunityCenterPayloadRequest payloadRequest = new CommunityCenterPayloadRequest("Community Test", address, "Local Teste", 100, 98, List.of(resource), null);
        CommunityCenter communityCenter = new CommunityCenter(payloadRequest);
        communityCenter.setAddress(address);
        communityCenter.setResources(List.of(resource));

        // Configurando o retorno dos mocks
        when(adressService.addAdress(any(Address.class))).thenReturn(address);
        when(resourceService.addAllResources(anyList())).thenReturn(List.of(resource));
        when(repository.save(any(CommunityCenter.class))).thenReturn(communityCenter);

        // Chamando o método do serviço
        CommunityCenter result = communityCenterService.addCommunityCenter(payloadRequest);

        // Verificando as interações com os mocks
        verify(adressService).addAdress(any(Address.class));
        verify(resourceService).addAllResources(anyList());
        verify(repository).save(any(CommunityCenter.class));

        // Verificando o resultado
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Community Test", result.getName());
        Assertions.assertEquals(address, result.getAddress());
        Assertions.assertEquals(List.of(resource), result.getResources());
    }

    @Test
    @DisplayName("Troca recursos falha quando pontos não são suficientes e centro de origem não está superlotado")
    void testExchangeResourcesFailure() {
        // Dados de teste
        String fromCenterId = "1";
        String toCenterId = "2";

        Resource fromResource1 = new Resource("1", "Doctor", 5, Resource.DOCTOR_POINTS);
        Resource fromResource2 = new Resource("2", "Volunteer", 10, Resource.VOLUNTEER_POINTS);
        Resource toResource1 = new Resource("3", "Medical Supplies", 20, Resource.MEDICAL_SUPPLIES_POINTS);
        Resource toResource2 = new Resource("4", "Transport Vehicle", 3, Resource.TRANSPORT_VEHICLE_POINTS);

        List<Resource> fromResources = List.of(fromResource1, fromResource2);
        List<Resource> toResources = List.of(toResource1, toResource2);

        CommunityCenter fromCenter = new CommunityCenter(fromCenterId, "Community A", null, "Location A", 100, 85, List.of(fromResource1, fromResource2), List.of());
        CommunityCenter toCenter = new CommunityCenter(toCenterId, "Community B", null, "Location B", 100, 80, List.of(toResource1, toResource2), List.of());

        // Configurando o retorno dos mocks
        when(repository.findById(fromCenterId)).thenReturn(Optional.of(fromCenter));
        when(repository.findById(toCenterId)).thenReturn(Optional.of(toCenter));

        // Chamando o método do serviço e verificando a exceção
        Assertions.assertThrows(RuntimeException.class, () -> {
            communityCenterService.exchangeResources(fromCenterId, toCenterId, fromResources, toResources);
        });

        // Verificando as interações com os mocks
        verify(repository).findById(fromCenterId);
        verify(repository).findById(toCenterId);
        verify(repository, never()).save(any(CommunityCenter.class));
        verify(reportService, never()).addNegotiationsReport(any(NegotiationsReport.class));
    }

}