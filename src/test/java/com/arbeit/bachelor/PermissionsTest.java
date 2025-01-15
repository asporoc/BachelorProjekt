package com.arbeit.bachelor;

import com.arbeit.bachelor.model.*;
import com.arbeit.bachelor.service.SBKService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PermissionsTest {

    @Autowired
    private SBKService sbkService;

    private static final String CSV_FILE_PATH = "src/test/resources/TestSuite.csv";


    private static Map<String, Map<String, String>> cachedRoleMap;
    private static Map<String, Map<String, String>> loadCsvData(String filePath) throws IOException {
        Map<String, Map<String, String>> roleMap = new LinkedHashMap<>();


        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))) {
            if (cachedRoleMap != null) {
                return cachedRoleMap;
            }
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("The CSV file is empty.");
            }
            String[] headers = headerLine.split(",");
            List<String> roles = new ArrayList<>();
            for (int i = 1; i < headers.length; i++) {
                roles.add(headers[i].trim());
                roleMap.put(headers[i].trim(), new LinkedHashMap<>());
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.equals(",,,,,,,,,,,,,,,,,,,,,,,,,,,")){
                    break;
                }
                try {

                    String[] values = line.split(",");
                    if (values[0].equals("")){
                        break;
                    }
                    String account = values[0].trim();
                    for (int i = 1; i < values.length; i++) {
                        String role = roles.get(i - 1);
                        String permission = values[i].trim();

                        if (!permission.equals(".")) {
                            if(permission.equals("(L)")) {
                            permission = "L";
                            }
                            roleMap.get(role).put(account, permission);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line);
                    e.printStackTrace();
                    throw e; // Re-throw after logging, if needed
                }
            }

        }

        return roleMap;

    }

    static Stream<Arguments> csvColumns() throws IOException {
        Map<String, Map<String, String>> rolePermissionsMap = loadCsvData(CSV_FILE_PATH);
        if (rolePermissionsMap == null || rolePermissionsMap.isEmpty()) {
            throw new IllegalArgumentException("CSV file does not contain role data.");
        }

        return rolePermissionsMap.entrySet().stream()
                .map(entry -> org.junit.jupiter.params.provider.Arguments.of(
                        entry.getKey(),
                        entry.getValue()
                ));
    }

    @ParameterizedTest
    @MethodSource("csvColumns")
    @DisplayName("Validate each role's permissions in the Access Matrix")
    @Transactional
    void testRoleColumn(String roleName, Map<String, String> permissions) {
        List<TreeNode> tree = sbkService.buildTreeStructure();
        sbkService.fillLists(tree);
        sbkService.fillAnwenderFields(sbkService.allAnwender);
        Anwender testSubject = sbkService.allAnwender.stream()
                .filter(anwender -> anwender.getBezeichnung().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));

        if (testSubject.getRolle().equals(Rolle.Beauftragte_fuer_den_Haushalt)) {
            testSubject.setAcl(sbkService.generateBfdHACL(testSubject));
        } else {
            testSubject.setAcl(sbkService.generateAnweisendeAndAoBACL(testSubject));
        }
        assertTrue(isSubset(permissions,testSubject.getAcl()));


    }
    public static boolean isSubset(Map<String, String> mapA, Map<SBK, Permissions> mapB) {
        for (Map.Entry<String, String> entryA : mapA.entrySet()) {
            String keyA = entryA.getKey().trim();
            String valueA = entryA.getValue().trim();
            valueA = valueA.trim();
            String idB = "";
            String valueB = "";
            Map<String,String> stringStringMap = new HashMap<>();
            boolean foundMatch = false;

            for (Map.Entry<SBK, Permissions> entryB : mapB.entrySet()) {
                idB = entryB.getKey().getId().trim();
                valueB = entryB.getValue().toString().trim();
                stringStringMap.put(idB,valueB);
                if (keyA.trim().equals(idB) && valueA.strip().equals(valueB)) {
                    foundMatch = true;
                    break;
                }

            }

            if (!foundMatch) {
                System.out.println(stringStringMap);
                return false;
            }
        }
        return true;
    }
}






