package com.jxc.core.util.json;

import java.util.HashSet;
import java.util.Set;

public class Node {
	private String id;
    private boolean leaf;
    private String qtip;
    private String text;
    Set<Node> children = new HashSet<Node>();
    
    public Node(){}
    
    public Node(String id,boolean leaf, String qtip,String text) {
        this.id=id;
        this.leaf=leaf;
        this.qtip=qtip;
        this.text=text;
    }
    
    public String toString() {
        return "{id:"+id+"," +
                "leaf:"+leaf+"," +
                "qtip:\""+qtip+"\"," +
                "text:\""+text+"\"}";
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Set<Node> getChildren() {
		return children;
	}

	public void setChildren(Set<Node> children) {
		this.children = children;
	}
    
	public void addNode(Node node) {
		this.getChildren().add(node);
	}

}
