package com.arbeit.bachelor.service;

import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.Organisationseinheit;
import com.arbeit.bachelor.model.SBK;
import com.arbeit.bachelor.repository.BehoerdeRepository;
import com.arbeit.bachelor.repository.OrganisationseinheitRepository;
import com.arbeit.bachelor.repository.SBKRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private final SBKRepository sbkRepository;
    private final OrganisationseinheitRepository organisationseinheitRepository;
    private final BehoerdeRepository behoerdeRepository;

    public DataService(SBKRepository sbkRepository,
                       OrganisationseinheitRepository organisationseinheitRepository,
                       BehoerdeRepository behoerdeRepository) {
        this.sbkRepository = sbkRepository;
        this.organisationseinheitRepository = organisationseinheitRepository;
        this.behoerdeRepository = behoerdeRepository;
    }

    public List<SBK> getSBKsByBewirtschafter(String bewirtschafterName) {
        return sbkRepository.findByBewirtschafter_Name(bewirtschafterName);
    }

    public List<Bewirtschafter> getBewirtschaftersByOrganisationseinheit(String organisationseinheitName) {
        return organisationseinheitRepository.findBewirtschaftersByName(organisationseinheitName);
    }

    public List<Organisationseinheit> getOrganisationseinheitenByBehoerde(String behoerdeName) {
        return behoerdeRepository.findOrganisationseinheitenByName(behoerdeName);
    }
}
