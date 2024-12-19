package com.arbeit.bachelor.controller;



import com.arbeit.bachelor.model.SBK;
import com.arbeit.bachelor.repository.SBKRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SBKController {
    private final SBKRepository sbkRepository;

    public SBKController(SBKRepository sbkRepository) {
        this.sbkRepository = sbkRepository;
    }

    @GetMapping("/sbk")
    public String showSBKs(Model model) {
        List<SBK> sbks = sbkRepository.findAll();
        model.addAttribute("sbks", sbks);
        return "sbk_list";
    }
}
