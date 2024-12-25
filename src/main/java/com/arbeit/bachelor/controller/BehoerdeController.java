package com.arbeit.bachelor.controller;

import com.arbeit.bachelor.model.Behoerde;
import com.arbeit.bachelor.repository.BehoerdeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BehoerdeController {
    private final BehoerdeRepository behoerdeRepository;

    public BehoerdeController(BehoerdeRepository behoerdeRepository) {this.behoerdeRepository = behoerdeRepository;}

    @GetMapping("/behoerden")
    public String showbehoerden(Model model) {
        List<Behoerde> behoerden = behoerdeRepository.findAll();
        model.addAttribute("behoerden", behoerden);
        return "behoerde_list";
    }
}