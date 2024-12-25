package com.arbeit.bachelor.controller;

import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.SBK;
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

    public SBKManagementController(SBKRepository sbkRepository) {
        this.sbkRepository = sbkRepository;
    }

    @GetMapping
    public String viewSBKManagementPage(Model model) {
        List<SBK> allSbks = sbkRepository.findAll();
        model.addAttribute("sbks", allSbks);

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

        List<SBK> filteredSbks = sbkRepository.findByBewirtschafter_Name(
                selectedSbk.getBewirtschafter().getName()
        );

        model.addAttribute("sbks", filteredSbks);
        model.addAttribute("selectedSbk", selectedSbk);
        return "sbk_management";
    }
}

