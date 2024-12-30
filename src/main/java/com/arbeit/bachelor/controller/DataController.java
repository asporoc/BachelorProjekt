package com.arbeit.bachelor.controller;

import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.Organisationseinheit;
import com.arbeit.bachelor.model.SBK;
import com.arbeit.bachelor.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/sbks-by-bewirtschafter/{id}")
    public List<SBK> getSBKs(@PathVariable String id) {
        return dataService.getSBKsByBewirtschafter(id);
    }

    @GetMapping("/bewirtschafters-by-organisationseinheit/{id}")
    public List<Bewirtschafter> getBewirtschafters(@PathVariable String id) {
        return dataService.getBewirtschaftersByOrganisationseinheit(id);
    }

    @GetMapping("/organisationseinheiten-by-behoerde/{id}")
    public List<Organisationseinheit> getOrganisationseinheiten(@PathVariable String id) {
        return dataService.getOrganisationseinheitenByBehoerde(id);
    }
}

