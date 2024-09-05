package com.valdirsantos714.communitycenter.controller;

import com.valdirsantos714.communitycenter.model.Address;
import com.valdirsantos714.communitycenter.model.CommunityCenter;
import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.model.Resource;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadRequest;
import com.valdirsantos714.communitycenter.payload.CommunityCenterPayloadResponse;
import com.valdirsantos714.communitycenter.repository.CommunityCenterRepository;
import com.valdirsantos714.communitycenter.service.AdressService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CommunityCenterControllerTest {

    @MockBean
    private CommunityCenterRepository repository;

    @MockBean
    private ResourceService resourceService;

    @MockBean
    private AdressService adressService;

    @MockBean
    private NegotiationsReportService reportService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CommunityCenterPayloadRequest> payloadRequest;

    @Autowired
    private JacksonTester<CommunityCenterPayloadResponse> payloadResponse;

    @Test
    @DisplayName("Salva community center")
    void save() throws Exception {

        // Criando um endereço de teste
        Address address = new Address(null, "12345", "Rua Teste", "Cidade Teste", 100, "Complemento", "Referência");

        // Criando uma lista de recursos de teste
        var resources = new ArrayList<>(List.of(
                new Resource(null, "Medical Supplies", 10, 7)
        ));

        // Criando o objeto CommunityCenter de teste
        CommunityCenter communityCenter = new CommunityCenter("1", "Community Test", address, "Local Teste", 100, 10, resources, null);

        // Criando o payload request JSON esperado
        var communityCenterPayloadRequest = payloadRequest.write(
                new CommunityCenterPayloadRequest(communityCenter)).getJson();

        // Criando o JSON esperado para a resposta
        var jsonEsperado = payloadRequest.write(
                new CommunityCenterPayloadRequest("Community Test", address, "Local Teste", 100, 10, resources, null)
        ).getJson();

        // Mockando as interações com os serviços
        when(adressService.addAdress(any(Address.class))).thenReturn(address);
        when(resourceService.addAllResources(anyList())).thenReturn(resources);
        when(repository.save(any(CommunityCenter.class))).thenReturn(communityCenter);

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
}