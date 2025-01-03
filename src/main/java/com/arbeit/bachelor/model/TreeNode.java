package com.arbeit.bachelor.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private SBK data;
    private List<TreeNode> children;
    private TreeNode parent;

    public TreeNode(SBK data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public SBK getData() {
        return data;
    }

    public void setData(SBK data) {
        this.data = data;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}
