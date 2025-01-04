package com.arbeit.bachelor.controller;

import com.arbeit.bachelor.model.*;
import com.arbeit.bachelor.service.SBKService;
import com.arbeit.bachelor.repository.SBKRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SBKController {

    private final SBKRepository sbkRepository;
    private final SBKService sbkService;

    public SBKController(SBKRepository sbkRepository, SBKService sbkService) {
        this.sbkRepository = sbkRepository;
        this.sbkService = sbkService;
        List<TreeNode> tree = sbkService.buildTreeStructure();
        sbkService.fillLists(tree);
        sbkService.fillAnwenderFields(sbkService.allAnwender);
    }

    // Existing method for SBK list
    @GetMapping("/sbk")
    public String showSBKs(Model model) {
        List<SBK> sbks = sbkRepository.findAll();
        model.addAttribute("sbks", sbks);
        return "sbk_list";
    }

    // New method for SBK Tree and Anwender ACL View
    @GetMapping("/")
    public String showSBKTreeAndAnwenderAcl(Model model) {
        model.addAttribute("anwenderList", sbkService.allAnwender); // Anwender list for dropdown
        return "sbk_tree";
    }

    // API endpoint to return ACL Map for selected Anwender
    @GetMapping("/acl/{anwender}")
    @ResponseBody
    public Map<String, String> getAclForAnwender(@PathVariable String anwender) {
        Anwender selectedAnwender = sbkService.allAnwender.stream()
                .filter(a -> a.getBezeichnung().equals(anwender))
                .findFirst()
                .orElse(null);

        if (selectedAnwender != null && selectedAnwender.getRolle().equals(Rolle.Beauftragte_fuer_den_Haushalt)) {
            Map<SBK, Permissions> acl = sbkService.generateBfdHACL(selectedAnwender);
            // Convert the Map<SBK, Permissions> to a simple JSON-friendly Map
            Map<String, String> response = new HashMap<>();
            for (Map.Entry<SBK, Permissions> entry : acl.entrySet()) {
                response.put(entry.getKey().getId(), entry.getValue().toString());
            }

            return response;
        }else if(selectedAnwender != null) {
            Map<SBK, Permissions> acl = sbkService.generateAnweisendeAndAoBACL(selectedAnwender);
            Map<String, String> response = new HashMap<>();
            for (Map.Entry<SBK, Permissions> entry : acl.entrySet()) {
                response.put(entry.getKey().getId(), entry.getValue().toString());

            }
        return response;
        }
        return Collections.emptyMap();
    }
}
