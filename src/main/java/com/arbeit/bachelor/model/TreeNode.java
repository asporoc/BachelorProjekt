package com.arbeit.bachelor.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private SBK data;
    private List<TreeNode> children;

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


    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}
