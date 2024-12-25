package com.arbeit.bachelor.controller;


import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.repository.BewirtschafterRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BewirtschafterController {
    private final BewirtschafterRepository bewirtschafterRepository;

    public BewirtschafterController(BewirtschafterRepository bewirtschafterRepository) {
        this.bewirtschafterRepository = bewirtschafterRepository;
    }

    @GetMapping("/bewirtschafter")
    public String showBewirtschafter(Model model) {
        List<Bewirtschafter> bewirtschafter = bewirtschafterRepository.findAll();
        model.addAttribute("bewirtschafter", bewirtschafter);
        return "bewirtschafter_list";
    }
}
