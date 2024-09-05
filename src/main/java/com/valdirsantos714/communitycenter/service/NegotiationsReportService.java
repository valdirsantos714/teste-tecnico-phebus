package com.valdirsantos714.communitycenter.service;

import com.valdirsantos714.communitycenter.model.NegotiationsReport;
import com.valdirsantos714.communitycenter.repository.NegotiationsReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NegotiationsReportService {

    @Autowired
    private NegotiationsReportRepository repository;

    public NegotiationsReport addNegotiationsReport(NegotiationsReport NegotiationsReport) {
        return repository.save(NegotiationsReport);
    }

    public List<NegotiationsReport> addAllNegotiationsReports(List<NegotiationsReport> NegotiationsReport) {
        return repository.saveAll(NegotiationsReport);
    }

    public NegotiationsReport getNegotiationsReportById(String NegotiationsReportId) {
        var NegotiationsReport = repository.findById(NegotiationsReportId).orElseThrow(() -> new RuntimeException("Erro! NegotiationsReport não encontrado!"));
        return NegotiationsReport;
    }
    
    public void deleteNegotiationsReport(String NegotiationsReportId) {
        repository.deleteById(NegotiationsReportId);
    }

    public List<NegotiationsReport> getAllNegotiationsReports() {
        return repository.findAll();
    }

    public List<NegotiationsReport> getNegotiationsReportsForGivenPeriod(LocalDateTime oldDate) {
        var list = getAllNegotiationsReports().stream().filter(n -> n.getNegotiationDate().isAfter(oldDate)).collect(Collectors.toList());
        return list;
    }
}