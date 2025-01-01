package com.arbeit.bachelor.service;

import com.arbeit.bachelor.model.Bewirtschafter;
import com.arbeit.bachelor.model.Organisationseinheit;
import com.arbeit.bachelor.model.SBK;
import com.arbeit.bachelor.model.TreeNode;
import com.arbeit.bachelor.repository.BehoerdeRepository;
import com.arbeit.bachelor.repository.BewirtschafterRepository;
import com.arbeit.bachelor.repository.OrganisationseinheitRepository;
import com.arbeit.bachelor.repository.SBKRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SBKService {

    private final SBKRepository sbkRepository;
    private final BewirtschafterRepository bewirtschafterRepository;
    private final OrganisationseinheitRepository organisationseinheitRepository;
    private final BehoerdeRepository behoerdeRepository;
    private List<SBK> allSbks;
    private List<Bewirtschafter> allBewirtschafter;
    private List<Organisationseinheit> allOrganisationseinheit;


    public SBKService(SBKRepository sbkRepository, BewirtschafterRepository bewirtschafterRepository, OrganisationseinheitRepository organisationseinheitRepository, BehoerdeRepository behoerdeRepository) {
        this.sbkRepository = sbkRepository;
        this.bewirtschafterRepository = bewirtschafterRepository;
        this.organisationseinheitRepository = organisationseinheitRepository;
        this.behoerdeRepository = behoerdeRepository;

        allSbks = sbkRepository.findAll();
        allBewirtschafter = bewirtschafterRepository.findAll();
        allOrganisationseinheit = organisationseinheitRepository.findAll();
    }

    public List<TreeNode> buildTreeStructure() {

        Map<String, TreeNode> nodeMap = createNodeMap(allSbks);

        List<TreeNode> rootNodes = buildParentChildReferences(allSbks, nodeMap);

        for (TreeNode node : rootNodes) {
            printTree(node, 6);
        }

        return rootNodes;
    }

    private Map<String, TreeNode> createNodeMap(List<SBK> allSbks) {
        Map<String, TreeNode> nodeMap = new HashMap<>();
        for (SBK sbk : allSbks) {
            nodeMap.put(sbk.getId(), new TreeNode(sbk));
        }
        return nodeMap;
    }

    private List<TreeNode> buildParentChildReferences(List<SBK> allSbks, Map<String, TreeNode> nodeMap) {
        List<TreeNode> rootNodes = new ArrayList<>();
        for (SBK sbk : allSbks) {
            TreeNode currentNode = nodeMap.get(sbk.getId());
            if (sbk.getParent() != null) {
                TreeNode parentNode = nodeMap.get(sbk.getParent().getId());
                parentNode.addChild(currentNode);
            } else {
                rootNodes.add(currentNode);
            }
        }
        return rootNodes;
    }

    public void printTree(TreeNode node, int level) {
        System.out.println("  ".repeat(level) + node.getData().getId());
        for (TreeNode child : node.getChildren()) {
            printTree(child, level + 1);
        }
    }

    public void fillLists(List<TreeNode> treeNodes){
        for(Bewirtschafter bewirtschafter : allBewirtschafter){
            List<TreeNode> list = new ArrayList<>();
            bewirtschafter.setSbks(list);
            fillSBKLists(bewirtschafter, treeNodes);
        }
        for (Organisationseinheit organisationseinheit : allOrganisationseinheit){
            organisationseinheit.setBewirtschafter(fillBewirtschafterListsOrga(organisationseinheit));
        }
        //fill Behoerde Lists method
    }
    private void fillSBKLists(Bewirtschafter bewirtschafter, List<TreeNode> treeNodes){
/*** need to FIX this so that each Node is traversed***/

            upwardTreeTraversal(treeNodes,bewirtschafter);

        /*
        for (SBK sbk : allSbks) {
            if(sbk.getBewirtschafter().getName().equals(bewirtschafter.getName())){
                counter++;
            }
        }
        List<SBK> list = new ArrayList<>(counter);
        for (SBK sbk : allSbks) {
            if(sbk.getBewirtschafter().getName().equals(bewirtschafter.getName())){
                list.add(sbk);
            }
        }*/
    }
    private void upwardTreeTraversal(List<TreeNode> list, Bewirtschafter bewirtschafter){
        for(TreeNode node : list){
            if(node.getData().getBewirtschafter().getName().equals(bewirtschafter.getName())){
                bewirtschafter.getSbks().add(node);
                System.out.println("help");
            }
            if(node.getChildren()!=null){
                upwardTreeTraversal(node.getChildren(),bewirtschafter);
            }

        }
    }
    private List<Bewirtschafter> fillBewirtschafterListsOrga(Organisationseinheit organisationseinheit){
        int counter=0;

        for (Bewirtschafter bewirtschafter : allBewirtschafter){
            if(bewirtschafter.getOrganisationseinheit()!=null) {
                if (bewirtschafter.getOrganisationseinheit().getName().equals(organisationseinheit.getName())) {
                    counter++;
                }
            }
        }
        List<Bewirtschafter> list = new ArrayList<>(counter);
        for (Bewirtschafter bewirtschafter : allBewirtschafter){
            if(bewirtschafter.getOrganisationseinheit()!=null) {
                if (bewirtschafter.getOrganisationseinheit().getName().equals(organisationseinheit.getName())) {
                    list.add(bewirtschafter);
                }
            }
        }
        return list;
    }
    // STILL NEED TO COUPLE THESE TO THE TREE, RIGHT NOW IT ONLY REFERENCES INSTANCES NEED COUPLING TO TREE NODES


}


