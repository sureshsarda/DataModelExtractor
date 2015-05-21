package nlp.objects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Relationship extends Type implements Cloneable
{

	List<RelationEntity> Connects = new ArrayList<RelationEntity>();

	@XmlElementWrapper(name = "Connects")
	@XmlElement(name = "Entity")
	public List<RelationEntity> getConnects()
	{
		return Connects;
	}
	public void setConnects(List<RelationEntity> connects)
	{
		Connects = connects;
	}

	@Override
	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" [");
		for (RelationEntity entity : Connects)
		{
			sb.append(entity.toString() + ",");
		}
		sb.append("]");

		return sb.toString();
	}
	@Override
	public String toAbstractString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append(super.toAbstractString());
		sb.append(" [");
		for (RelationEntity entity : Connects)
		{
			sb.append(entity.toAbstractString() + ",");
		}
		sb.append("]");

		return sb.toString();
	}
	public boolean equals(Relationship relation)
	{
		// If LemmNames are same
		if (this.lemmName.equals("issue")) {
			System.out.println();
		}
		if (this.getLemmName().equals(relation.getLemmName()) == true)
		{
			// And the sizes are also same
			if (this.Connects.size() == relation.Connects.size())
			{
				// Then try to find a different entity
				for (RelationEntity relationEntity : Connects)
				{

					if (relation.contains(relationEntity) == false)
					{
						// different entity found
						return false;
					}
				}
				// No different entity found
				return true;
			}
			else
			{
				//The sizes of sublists are not same.
				return false;
			}
		}
		else
		{
			//The lemmNames are not same.
			return false;
		}

	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		Relationship copy = (Relationship) super.clone();
		copy.Connects = new ArrayList<RelationEntity>();
		for (RelationEntity relationEntity : Connects)
		{
			copy.Connects.add((RelationEntity) relationEntity.clone());
		}

		return copy;
	}
	
	public boolean contains(RelationEntity rent) {
		for (RelationEntity relationEntity : Connects)
		{
			if (rent.equals(relationEntity)) {
				return true;
			}
		}
		return false;
	}
}