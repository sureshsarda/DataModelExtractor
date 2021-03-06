package nlp.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import nlp.processing.StanfordProcessor;

public class Type implements Comparable<Type>, Cloneable
{
	String name;
	String lemmName;
	int id;
	int wordIndex;
	int length;

	@XmlAttribute(name = "Id")
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@XmlAttribute(name = "WordId")
	public int getWordIndex()
	{
		return wordIndex;
	}

	public void setWordIndex(int wordIndex)
	{
		/*
		 * Required to subtract 1, if the training data is directly taken from
		 * tagger.
		 */
		this.wordIndex = wordIndex;
	}

	@XmlAttribute(name = "Length")
	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	@XmlElement(name = "Name")
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
		this.lemmName = StanfordProcessor.getInstance().lemmatiseString(this.name).toLowerCase();
	}

	public String getLemmName()
	{
		return lemmName;
	}

	@Override
	public String toString()
	{
		return String.format("%d %s WI=%d L=%d", id, lemmName, wordIndex, length);
	}

	public String toAbstractString()
	{
		return toString();
		// return String.format("WI=%d L=%d", wordIndex, length);
	}

	@Override
	public int compareTo(Type type)
	{
		if (length == type.length)
		{
			return lemmName.compareTo(type.lemmName);
		}
		else
		{
			if (this.length < type.length)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Type copy = (Type) super.clone();
		copy.name = new String(name != null ? name : "");
		copy.lemmName = new String(lemmName != null ? lemmName : "");
		copy.wordIndex = new Integer(wordIndex);
		copy.length = new Integer(length);
		copy.id = new Integer(id);
		return (Object) copy;
	}

}
