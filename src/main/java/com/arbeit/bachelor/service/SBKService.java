package com.arbeit.bachelor.service;

import com.arbeit.bachelor.model.SBK;
import com.arbeit.bachelor.model.TreeNode;
import com.arbeit.bachelor.repository.SBKRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SBKService {

    private final SBKRepository sbkRepository;

    public SBKService(SBKRepository sbkRepository) {
        this.sbkRepository = sbkRepository;
    }

    /**
     * Builds a hierarchical tree structure of SBKs using custom TreeNode objects.
     *
     * @return A list of root TreeNodes representing the top-level hierarchy.
     */
    public List<TreeNode> buildTreeStructure() {
        List<SBK> allSbks = sbkRepository.findAll();

        //map SBK ID to TreeNode
        Map<String, TreeNode> nodeMap = new HashMap<>();
        for (SBK sbk : allSbks) {
            nodeMap.put(sbk.getId(), new TreeNode(sbk));
        }

        // link children to parents
        List<TreeNode> rootNodes = new ArrayList<>();
        for (SBK sbk : allSbks) {
            TreeNode currentNode = nodeMap.get(sbk.getId());
            if (sbk.getParent() != null) {
                // Attach to parent TreeNode
                TreeNode parentNode = nodeMap.get(sbk.getParent().getId());
                parentNode.addChild(currentNode);
            } else {

                rootNodes.add(currentNode);
            }
        }
        for(TreeNode nodes : rootNodes){
            printTree(nodes,6);
        }
        return rootNodes;
    }


    public void printTree(TreeNode node, int level) {
        System.out.println("  ".repeat(level) + node.getData().getId());
        for (TreeNode child : node.getChildren()) {
            printTree(child, level + 1);
        }
    }
}


