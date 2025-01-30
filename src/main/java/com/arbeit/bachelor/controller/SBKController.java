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

    private SBKRepository sbkRepository;
    private SBKService sbkService;

    public SBKController(SBKRepository sbkRepository, SBKService sbkService) {
        this.sbkRepository = sbkRepository;
        this.sbkService = sbkService;
        List<TreeNode> tree = sbkService.buildTreeStructure();
        sbkService.fillLists(tree);
        sbkService.fillAnwenderFields(sbkService.allAnwender);
    }

    @GetMapping("/sbk")
    public String showSBKs(Model model) {
        List<SBK> sbks = sbkRepository.findAll();
        model.addAttribute("sbks", sbks);
        return "sbk_list";
    }

    @GetMapping("/")
    public String showSBKTreeAndAnwenderAcl(Model model) {
        model.addAttribute("anwenderList", sbkService.allAnwender); // Anwender list for dropdown
        return "sbk_tree";
    }

    //return ACL Map for selected Anwender
    @GetMapping("/acl/{anwender}")
    @ResponseBody
    public Map<String, String> getAclForAnwender(@PathVariable String anwender) {

        List<TreeNode> tree = sbkService.buildTreeStructure();
        sbkService.fillLists(tree);
        sbkService.fillAnwenderFields(sbkService.allAnwender);
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
    /*** Gibt min, max und avg antwortzeit für eine Anwender abfrage an ***/
    @GetMapping("/test/acl-performance/{anwender}")
    @ResponseBody
    public Map<String, Object> testAclPerformance(@PathVariable String anwender) {
        final int iterations = 100;
        long totalExecutionTime = 0;
        long maxTime = Long.MIN_VALUE;
        long minTime = Long.MAX_VALUE;
        Map<String, String> lastResult = null;

        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();

        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();

            lastResult = getAclForAnwender(anwender);

            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;

            totalExecutionTime += executionTime;
            maxTime = Math.max(maxTime, executionTime);
            minTime = Math.min(minTime, executionTime);
        }

        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = endMemory - startMemory;

        Map<String, Object> results = new HashMap<>();
        results.put("averageTimeMs", totalExecutionTime / (iterations * 1_000_000.0));
        results.put("maxTimeMs", maxTime / 1_000_000.0);
        results.put("minTimeMs", minTime / 1_000_000.0);
        results.put("memoryUsedKb", memoryUsed / 1024.0);
        results.put("lastResult", lastResult);

        return results;
    }

    /*** git min, max und avg antwortzeit für eine query jedes users an ***/
    @GetMapping("/test/acl-performance/all")
    @ResponseBody
    public Map<String, Object> testAclPerformanceForAllAnwenders() {
        final int iterations = 100;
        Runtime runtime = Runtime.getRuntime();

        Map<String, Map<String, Object>> performanceMetrics = new HashMap<>();


        for (Anwender anwender : sbkService.allAnwender) {
            String anwenderName = anwender.getBezeichnung();
            long totalExecutionTime = 0;
            long maxTime = Long.MIN_VALUE;
            long minTime = Long.MAX_VALUE;
            long memoryUsed = 0;
            Map<String, String> lastResult = null;

            long startMemory = runtime.totalMemory() - runtime.freeMemory();

            for (int i = 0; i < iterations; i++) {
                long startTime = System.nanoTime();

                lastResult = getAclForAnwender(anwenderName);

                long endTime = System.nanoTime();
                long executionTime = endTime - startTime;

                totalExecutionTime += executionTime;
                maxTime = Math.max(maxTime, executionTime);
                minTime = Math.min(minTime, executionTime);
            }

            long endMemory = runtime.totalMemory() - runtime.freeMemory();
            memoryUsed = endMemory - startMemory;

            Map<String, Object> metrics = new HashMap<>();
            metrics.put("averageTimeMs", totalExecutionTime / (iterations * 1_000_000.0));
            metrics.put("maxTimeMs", maxTime / 1_000_000.0);
            metrics.put("minTimeMs", minTime / 1_000_000.0);
            metrics.put("memoryUsedKb", memoryUsed / 1024.0);
            metrics.put("lastResult", lastResult);

            performanceMetrics.put(anwenderName, metrics);
        }

        //results
        Map<String, Object> result = new HashMap<>();
        result.put("totalUsersTested", sbkService.allAnwender.size());
        result.put("metrics", performanceMetrics);

        return result;
    }

    /*** Gibt die durchschnittliche längste, kürzeste und durchschnittliche Antwortzeit eines API Endpoints an ***/
    @GetMapping("/test/acl-performance")
    @ResponseBody
    public Map<String, Object> testAclPerformanceForAllUsers() {
        List<Anwender> allAnwender = sbkService.allAnwender;


        long totalExecutionTime = 0;
        long maxExecutionTime = Long.MIN_VALUE;
        long minExecutionTime = Long.MAX_VALUE;
        int totalRequests = allAnwender.size();

        for (Anwender anwender : allAnwender) {
            String anwenderName = anwender.getBezeichnung();
            long startTime = System.nanoTime();


            sbkService.allAnwender.stream()
                    .filter(a -> a.getBezeichnung().equals(anwenderName))
                    .findFirst()
                    .ifPresent(selectedAnwender -> {
                        if (selectedAnwender.getRolle().equals(Rolle.Beauftragte_fuer_den_Haushalt)) {
                            sbkService.generateBfdHACL(selectedAnwender);
                        } else {
                            sbkService.generateAnweisendeAndAoBACL(selectedAnwender);
                        }
                    });

            long elapsedTime = System.nanoTime() - startTime;

            totalExecutionTime += elapsedTime;
            maxExecutionTime = Math.max(maxExecutionTime, elapsedTime);
            minExecutionTime = Math.min(minExecutionTime, elapsedTime);
        }


        double avgExecutionTimeMs = (double) totalExecutionTime / totalRequests / 1_000_000;
        double maxExecutionTimeMs = maxExecutionTime / 1_000_000.0;
        double minExecutionTimeMs = minExecutionTime / 1_000_000.0;


        Map<String, Object> response = new HashMap<>();
        response.put("totalRequests", totalRequests);
        response.put("averageExecutionTimeMs", avgExecutionTimeMs);
        response.put("maxExecutionTimeMs", maxExecutionTimeMs);
        response.put("minExecutionTimeMs", minExecutionTimeMs);

        return response;
    }
}
