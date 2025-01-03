package com.arbeit.bachelor.controller;


import com.arbeit.bachelor.model.TreeNode;
import com.arbeit.bachelor.service.SBKService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tree")
public class TreeController {

    private final SBKService sbkService;

    public TreeController(SBKService sbkService) {
        this.sbkService = sbkService;
    }

    @GetMapping
    public String showTree(Model model) {
        List<TreeNode> tree = sbkService.buildTreeStructure();
        sbkService.fillLists(tree);
        sbkService.fillAnwenderFields(sbkService.allAnwender);
        model.addAttribute("tree", tree);
        return "sbk_tree";
    }
}

