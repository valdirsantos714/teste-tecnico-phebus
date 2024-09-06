package com.valdirsantos714.communitycenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdirsantos714.communitycenter.model.Address;
import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadResponse;
import com.valdirsantos714.communitycenter.payload.ExchangeResourcesRequest;
import com.valdirsantos714.communitycenter.repository.AdressRepository;
import com.valdirsantos714.communitycenter.repository.CommunityCenterRepository;
import com.valdirsantos714.communitycenter.repository.ResourceRepository;
import com.valdirsantos714.communitycenter.service.AdressService;
import com.valdirsantos714.communitycenter.service.CommunityCenterService;
import com.valdirsantos714.communitycenter.service.NegotiationsReportService;
import com.valdirsantos714.communitycenter.service.ResourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CommunityCenterControllerTest {

    @MockBean
    private CommunityCenterRepository repository;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private ResourceRepository resourceRepository;

    @MockBean
    private AdressRepository adressRepository;

    @MockBean
    private AdressService adressService;

    @MockBean
    private NegotiationsReportService reportService;

    @MockBean
    private CommunityCenterService communityCenterService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CommunityCenterPayloadRequest> payloadRequest;

    @Autowired
    private JacksonTester<CommunityCenterPayloadResponse> payloadResponse;

    @Autowired
    private JacksonTester<List<CommunityCenterPayloadResponse>> payloadResponseList;

    @Test
    @DisplayName("Salva community center")
    void addCenter() throws Exception {

        // Criando um endereço de teste
        Address address = new Address("1", "12345", "Rua Teste", "Cidade Teste", 100, "Complemento", "Referencia");

        // Criando uma lista de recursos de teste
        var resources = new ArrayList<>(List.of(
                new Resource("2", "Medical Supplies", 10, 7)
        ));

        // Criando o objeto CommunityCenter de teste
        CommunityCenter communityCenter = new CommunityCenter("1", "Community Test", address, "Local Teste", 100, 10, resources, null);

        // Criando o payload request JSON esperado
        var communityCenterPayloadRequest = payloadRequest.write(
                new CommunityCenterPayloadRequest(communityCenter)).getJson();

        // Criando o JSON esperado para a resposta
        var jsonEsperado = payloadResponse.write(
                new CommunityCenterPayloadResponse(communityCenter)
        ).getJson();

        // Mockando as interações com os serviços
        when(adressRepository.save(address)).thenReturn(address);
        when(adressService.addAdress(any(Address.class))).thenReturn(address);
        when(resourceRepository.saveAll(resources)).thenReturn(resources);
        when(resourceService.addAllResources(anyList())).thenReturn(resources);
        when(repository.save(any(CommunityCenter.class))).thenReturn(communityCenter);

        var payload = new CommunityCenterPayloadRequest(communityCenter);
        when(communityCenterService.addCommunityCenter(payload)).thenReturn(communityCenter);

        // Executando a requisição POST e verificando a resposta
        var response = mvc.perform(post("/api/community-centers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(communityCenterPayloadRequest)
        ).andReturn().getResponse();

        // Verificando se a resposta foi criada corretamente (201 Created)
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        // Verificando se o JSON de resposta é o esperado
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());
    }

    @Test
    @DisplayName("Atualiza ocupação do centro comunitário")
    void updateOccupancy() throws Exception {
        // Criando um endereço de teste
        Address address = new Address("1", "12345", "Rua Teste", "Cidade Teste", 100, "Complemento", "Referencia");

        // Criando uma lista de recursos de teste
        var resources = new ArrayList<>(List.of(
                new Resource("2", "Medical Supplies", 10, 7)
        ));

        // Criando o objeto CommunityCenter de teste
        CommunityCenter communityCenter = new CommunityCenter("1", "Community Test", address, "Local Teste", 100, 10, resources, null);

        // Criando o payload request JSON esperado
        var communityCenterPayloadRequest = payloadRequest.write(
                new CommunityCenterPayloadRequest(communityCenter)).getJson();

        CommunityCenter communityCenterUpdated = new CommunityCenter("1", "Community Test", address, "Local Teste", 100, 90, resources, null);


        // Criando o JSON esperado para a resposta
        var jsonEsperado = payloadResponse.write(
                new CommunityCenterPayloadResponse(communityCenterUpdated)
        ).getJson();

        // Mockando as interações com os serviços
        when(adressRepository.save(address)).thenReturn(address);
        when(adressService.addAdress(any(Address.class))).thenReturn(address);
        when(resourceRepository.saveAll(resources)).thenReturn(resources);
        when(resourceService.addAllResources(anyList())).thenReturn(resources);
        when(repository.save(any(CommunityCenter.class))).thenReturn(communityCenter);
        when(communityCenterService.updateOccupancy("1", 90)).thenReturn(communityCenterUpdated);

        // Executando a requisição POST e verificando a resposta
        var response = mvc.perform(put("/api/community-centers/" + "1/occupancy?occupancy=" + 90)
                .contentType(MediaType.APPLICATION_JSON)
                .content(communityCenterPayloadRequest)
        ).andReturn().getResponse();

        // Verificando se a resposta foi criada corretamente (201 Created)
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

        // Verificando se o JSON de resposta é o esperado
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());

    }

    @Test
    @DisplayName("Realiza o intercâmbio de recursos entre dois centros comunitários.")
    void exchangeResources() throws Exception {

        // Criando um endereço de teste
        Address address = new Address("1", "12345", "Rua Teste", "Cidade Teste", 100, "Complemento", "Referencia");

        // Criando uma lista de recursos de teste
        List<Resource> fromResources = List.of(
                new Resource("1", "Doctor", 5, Resource.DOCTOR_POINTS),
                new Resource("2", "Volunteer", 10, Resource.VOLUNTEER_POINTS)
        );

        List<Resource> toResources = List.of(
                new Resource("3", "Medical Supplies", 20, Resource.MEDICAL_SUPPLIES_POINTS),
                new Resource("4", "Transport Vehicle", 3, Resource.TRANSPORT_VEHICLE_POINTS)
        );

        // Criando o payload request JSON esperado
        var exchangeRequest = new ExchangeResourcesRequest(fromResources, toResources);

        var jsonRequest = new ObjectMapper().writeValueAsString(exchangeRequest);

        // Mockando o comportamento dos serviços
        doNothing().when(communityCenterService).exchangeResources("1", "2", fromResources, toResources);

        // Executando a requisição POST e verificando a resposta
        var response = mvc.perform(post("/api/community-centers/exchangeResources?fromCenterId=1&toCenterId=2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
        ).andReturn().getResponse();

        // Verificando se a resposta foi criada corretamente (204 No Content)
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    @DisplayName("Retorna a média de recursos por centro comunitário.")
    void getAverageResources() throws Exception {

        // Simulando a resposta esperada do serviço
        Map<String, Double> averageResourcesMock = Map.of(
                "Doctor", 5.0,
                "Volunteer", 7.5,
                "Medical Supplies", 12.0
        );

        // Mockando o comportamento do serviço
        when(communityCenterService.getAverageResourcesPerCenter()).thenReturn(averageResourcesMock);

        // Executando a requisição GET e verificando a resposta
        var response = mvc.perform(get("/api/community-centers/average-resources")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Verificando se a resposta foi criada corretamente (200 OK)
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

        // Convertendo a resposta para Map e comparando com o valor esperado
        var responseBody = new ObjectMapper().readValue(response.getContentAsString(), Map.class);
        Assertions.assertEquals(averageResourcesMock, responseBody);
    }

    @Test
    @DisplayName("Retorna os centros comunitários com alta ocupação.")
    void getHighOccupancyCenters() throws Exception {

        // Criando objetos CommunityCenter de teste
        CommunityCenter communityCenter1 = new CommunityCenter("1", "Community Test 1", null, "Local Teste 1", 100, 98, null, null);
        CommunityCenter communityCenter2 = new CommunityCenter("2", "Community Test 2", null, "Local Teste 2", 100, 95, null, null);
        CommunityCenter communityCenter3 = new CommunityCenter("3", "Community Test 3", null, "Local Teste 3", 100, 5, null, null);

        // Criando uma lista de respostas esperada (somente os centros com alta ocupação)
        List<CommunityCenterPayloadResponse> listCommunityHighOccupancy = new ArrayList<>(List.of(
                new CommunityCenterPayloadResponse(communityCenter1),
                new CommunityCenterPayloadResponse(communityCenter2)
        ));

        var list = new ArrayList<>(List.of(
           communityCenter1, communityCenter2
        ));

        // Criando o JSON esperado para a resposta
        var jsonEsperado = payloadResponseList.write(listCommunityHighOccupancy).getJson();

        // Mockando as interações com os serviços
        when(communityCenterService.getHighOccupancyCenters()).thenReturn(list);

        // Executando a requisição GET e verificando a resposta
        var response = mvc.perform(get("/api/community-centers/high-occupancy")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Verificando se a resposta foi criada corretamente (200 OK)
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

        // Verificando se o JSON de resposta é o esperado
        Assertions.assertEquals(jsonEsperado, response.getContentAsString());
    }
}