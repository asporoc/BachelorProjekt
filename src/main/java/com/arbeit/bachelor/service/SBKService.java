package com.arbeit.bachelor.service;

import com.arbeit.bachelor.model.*;
import com.arbeit.bachelor.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SBKService {

    private SBKRepository sbkRepository;
    private BewirtschafterRepository bewirtschafterRepository;
    private OrganisationseinheitRepository organisationseinheitRepository;
    private BehoerdeRepository behoerdeRepository;
    private AnwenderRepository anwenderRepository;
    private List<SBK> allSbks;
    private List<Bewirtschafter> allBewirtschafter;
    private List<Organisationseinheit> allOrganisationseinheit;
    private List<Behoerde> allBehoerde;
    public List<Anwender> allAnwender;


    public SBKService(SBKRepository sbkRepository, BewirtschafterRepository bewirtschafterRepository, OrganisationseinheitRepository organisationseinheitRepository, BehoerdeRepository behoerdeRepository, AnwenderRepository anwenderRepository) {
        this.sbkRepository = sbkRepository;
        this.bewirtschafterRepository = bewirtschafterRepository;
        this.organisationseinheitRepository = organisationseinheitRepository;
        this.behoerdeRepository = behoerdeRepository;
        this.anwenderRepository = anwenderRepository;

        allSbks = sbkRepository.findAll();
        allBewirtschafter = bewirtschafterRepository.findAll();
        allOrganisationseinheit = organisationseinheitRepository.findAll();
        allBehoerde = behoerdeRepository.findAll();
        allAnwender = anwenderRepository.findAll();

    }

    public List<TreeNode> buildTreeStructure() {

        Map<String, TreeNode> nodeMap = createNodeMap(allSbks);

        List<TreeNode> rootNodes = buildParentChildReferences(allSbks, nodeMap);


        return rootNodes;
    }

    private Map<String, TreeNode> createNodeMap(List<SBK> allSbks) {
        Map<String, TreeNode> nodeMap = new HashMap<>();
        for (SBK sbk : allSbks) {
            nodeMap.put(sbk.getId(), new TreeNode(sbk));
        }
        return nodeMap;
    }
    public void fillAnwenderFields(List<Anwender> anwenderList) {
        for (Anwender anwender : anwenderList) {
            linkAnwender(anwender);
        }
    }

    private List<TreeNode> buildParentChildReferences(List<SBK> allSbks, Map<String, TreeNode> nodeMap) {
        List<TreeNode> rootNodes = new ArrayList<>();
        for (SBK sbk : allSbks) {
            TreeNode currentNode = nodeMap.get(sbk.getId());
            if (sbk.getParent() != null) {
                TreeNode parentNode = nodeMap.get(sbk.getParent().getId());
                parentNode.addChild(currentNode);
                currentNode.setParent(parentNode);

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
        for (Behoerde behoerde : allBehoerde){
            behoerde.setBewirtschafter(fillBewirtschafterListsBehoerde(behoerde));
        }
        for (Behoerde behoerde : allBehoerde){
            behoerde.setOrganisationseinheiten(fillOrgaListsBehoerde(behoerde));
        }

    }
    private void fillSBKLists(Bewirtschafter bewirtschafter, List<TreeNode> treeNodes){
            DownwardTreeTraversal(treeNodes,bewirtschafter);
    }
    private void DownwardTreeTraversal(List<TreeNode> list, Bewirtschafter bewirtschafter){
        for(TreeNode node : list){
            if(node.getData().getBewirtschafter().getName().equals(bewirtschafter.getName())){
                bewirtschafter.getSbks().add(node);
                node.getData().setBewirtschafter(bewirtschafter); // update node Bewirtschafter so that the Bewirtschafter in the node is the same as the one that holds the node in its sbks list
            }
            if(node.getChildren()!=null){
                DownwardTreeTraversal(node.getChildren(),bewirtschafter);
            }

        }
    }
    private List<Bewirtschafter> fillBewirtschafterListsOrga(Organisationseinheit organisationseinheit){
        List<Bewirtschafter> list = new ArrayList<>();
        for (Bewirtschafter bewirtschafter : allBewirtschafter){
            if(bewirtschafter.getOrganisationseinheit()!=null) {
                if (bewirtschafter.getOrganisationseinheit().getName().equals(organisationseinheit.getName())) {
                    list.add(bewirtschafter);
                }
            }
        }
        return list;
    }

    private List<Bewirtschafter> fillBewirtschafterListsBehoerde(Behoerde behoerde){
        List<Bewirtschafter> list = new ArrayList<>();
        for (Bewirtschafter bewirtschafter : allBewirtschafter){
            if(bewirtschafter.getBehoerde()!=null) {
                if (bewirtschafter.getBehoerde().getName().equals(behoerde.getName())) {
                    list.add(bewirtschafter);
                }
            }
        }
        return list;
    }

    private List<Organisationseinheit> fillOrgaListsBehoerde(Behoerde behoerde){
        List<Organisationseinheit> list = new ArrayList<>();
        for (Organisationseinheit organisationseinheit : allOrganisationseinheit){
            if(organisationseinheit.getBehoerde()!=null) {
                if (organisationseinheit.getBehoerde().getName().equals(behoerde.getName())) {
                    list.add(organisationseinheit);
                }
            }
        }
        return list;
    }
    public void linkAnwender(Anwender anwender) {
        if(anwender.getBewirtschafter() != null) {
            for (Bewirtschafter bewirtschafter : allBewirtschafter) {
                if (bewirtschafter.getName().equals(anwender.getBewirtschafter().getName())) {
                    anwender.setBewirtschafter(bewirtschafter);
                    break;
                }
            }
        }
        if(anwender.getOrganisationseinheit() != null) {
            for (Organisationseinheit orga : allOrganisationseinheit) {
                if (orga.getName().equals(anwender.getOrganisationseinheit().getName())) {
                    anwender.setOrganisationseinheit(orga);
                    break;
                }
            }
        }

        for (Behoerde behoerde : allBehoerde) {
            if (behoerde.getName().equals(anwender.getBehoerde().getName())) {
                anwender.setBehoerde(behoerde);
                break;
            }
        }
    }

    public Map<SBK,Permissions> generateBfdHACL(Anwender anwender){
        Map<SBK,Permissions> acl = new HashMap<>();
        List<TreeNode> bList = new ArrayList<>();
        if (anwender.getBehoerde().getOrganisationseinheiten().size()>0 && anwender.getBehoerde().getOrganisationseinheiten() != null ) {
            for (Organisationseinheit orga : anwender.getBehoerde().getOrganisationseinheiten()) {
                for (Bewirtschafter bewirtschafter : orga.getBewirtschafter()) {
                    bList.addAll(bewirtschafter.getSbks());
                }
            }
        }else{
            for (Bewirtschafter bewirtschafter : anwender.getBehoerde().getBewirtschafter()) {
                bList.addAll(bewirtschafter.getSbks());
            }

        }
        return getSbkPermissionsMap(acl, bList);

    }

    private Map<SBK, Permissions> getSbkPermissionsMap(Map<SBK, Permissions> acl, List<TreeNode> bList) {
        for(TreeNode node : bList){
            acl.put(node.getData(),Permissions.B);
            if(node.getChildren() != null ) {
                downTree(node.getChildren(), acl, bList);
            }
            if(node.getParent() != null) {
                upTree(node.getParent(),acl);
            }
        }

        return acl;
    }

    public void downTree(List<TreeNode> nodes, Map<SBK,Permissions> acl, List <TreeNode> bList){
        for(TreeNode node : nodes){
            if(!bList.contains(node.getParent())) { //check if parent is in the List of the Buchungs SBKs, as acl entries were overwritten.
                acl.put(node.getData(), Permissions.L);
            }
            if(node.getChildren() != null){
                downTree(node.getChildren(),acl, bList);
            }
        }
    }
    public void upTree(TreeNode node, Map<SBK,Permissions> acl){
            acl.put(node.getData(), Permissions.L);
            if(node.getParent() != null){
                upTree(node.getParent(),acl);
            }
        }

    public Map<SBK,Permissions> generateAnweisendeAndAoBACL(Anwender anwender){
        Map<SBK,Permissions> acl = new HashMap<>();
        List<TreeNode> bList = anwender.getBewirtschafter().getSbks();
        return getSbkPermissionsMap(acl, bList);
    }

    public void printAclMap(Map<SBK, Permissions> acl) {
        for (Map.Entry<SBK, Permissions> entry : acl.entrySet()) {
            if(entry.getValue().equals(Permissions.B))
            System.out.println(entry.getKey().getId() + " => "+ entry.getValue().toString());
        }
    }
}


