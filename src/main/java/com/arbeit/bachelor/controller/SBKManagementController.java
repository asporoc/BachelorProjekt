package com.arbeit.bachelor.controller;

import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.SBK;
import com.arbeit.bachelor.repository.BewirtschafterRepository;
import com.arbeit.bachelor.repository.SBKRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manage-sbks")
public class SBKManagementController {

    private final SBKRepository sbkRepository;
    private final BewirtschafterRepository bewirtschafterRepository;

    public SBKManagementController(SBKRepository sbkRepository, BewirtschafterRepository bewirtschafterRepository) {
        this.sbkRepository = sbkRepository;
        this.bewirtschafterRepository = bewirtschafterRepository;
    }

    @GetMapping
    public String viewSBKManagementPage(Model model) {
        List<SBK> allSbks = sbkRepository.findAll(); // Fetch all SBKs
        List<Bewirtschafter> bewirtschafterList = bewirtschafterRepository.findAll();
        model.addAttribute("sbks", allSbks);
        model.addAttribute("bewirtschafterList", bewirtschafterList);

        SBK defaultSbk = new SBK();
        defaultSbk.setBewirtschafter(new Bewirtschafter());
        model.addAttribute("selectedSbk", defaultSbk);

        return "sbk_management";
    }

    @PostMapping
    public String filterSBKs(@ModelAttribute("selectedSbk") SBK selectedSbk, Model model) {
        if (selectedSbk.getBewirtschafter() == null || selectedSbk.getBewirtschafter().getName() == null) {
            model.addAttribute("error", "Invalid Bewirtschafter.");
            return "sbk_management";
        }

        // Filter SBKs based on the selected Bewirtschafter's name
        List<SBK> filteredSbks = sbkRepository.findByBewirtschafter_Name(
                selectedSbk.getBewirtschafter().getName()
        );

        List<Bewirtschafter> bewirtschafterList = bewirtschafterRepository.findAll();
        model.addAttribute("sbks", filteredSbks);
        model.addAttribute("bewirtschafterList", bewirtschafterList);
        model.addAttribute("selectedSbk", selectedSbk);

        return "sbk_management";
    }
}

