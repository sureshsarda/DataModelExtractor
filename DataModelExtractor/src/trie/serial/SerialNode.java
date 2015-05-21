package trie.serial;

import javax.xml.bind.annotation.XmlValue;

import nlp.objects.Tag;

public class SerialNode implements Comparable<SerialNode>{
	
	Tag tag;

	public SerialNode() {}
	public SerialNode(String tag) {
		setTag(tag);
	}

	
	/*
	 * Constructors
	 */
	
	public Tag getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = new Tag(tag);
	}
	
	@XmlValue
	public String getTagString() {
		return tag.toString();
	}
	public void setTagString(String tag) {
		setTag(tag);
	}
	
	
	@Override
	public String toString() {
		return tag.toString();
	}

	@Override
	public int compareTo(SerialNode node) {
		return tag.compareTo(node.tag);
	}
	
}
