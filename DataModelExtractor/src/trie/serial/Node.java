package trie.serial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nlp.objects.Model;

/**
 * Node - Represent a node in the trie To init the class, pass a Part Of Speech
 * tag, which cannot be modified later.
 *
 * @author Suresh Sarda
 *
 */
public class Node {
	
	/* Cannot modify once new object is created */
	private String Tag;

	private List<Node> Children;

	private LeafNode LeafInformation;

	public LeafNode getLeafInformation() {
		return LeafInformation;
	}

	public void setLeafInformation(LeafNode leafInformation) {
		LeafInformation = leafInformation;
	}

	public void setLeafInformation(Model dataModel) {
		LeafInformation = new LeafNode(dataModel);
	}

	public Model getLeafInformation(Model dataModel) {
		return LeafInformation.getDataModel();
	}

	public Node(String tag, String word) {
		Children = new ArrayList<Node>();
		this.Tag = new String(tag);
	}

	public void addChild(Node child) {
		Children.add(child);
	}

	public Node findChild(String post) {
		Iterator<Node> itr = Children.iterator();
		while (itr.hasNext()) {
			Node current = itr.next();
			if (current.getTag().compareTo(post) == 0) {
				return current;
			}
		}
		return null;
	}

	public List<Node> getChildren() {
		return Children;
	}

	public String getTag() {
		return Tag;
	}


	@Override
	public String toString() {
		return this.Tag;
	}
}
