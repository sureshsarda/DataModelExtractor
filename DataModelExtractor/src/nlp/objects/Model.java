package nlp.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Model implements Cloneable
{

	private List<Entity> Entities = new ArrayList<Entity>();
	private List<Relationship> Relationships = new ArrayList<Relationship>();

	/* Getters and setters */
	@XmlElementWrapper(name = "Entities")
	@XmlElement(name = "Entity")
	public List<Entity> getEntities()
	{
		return Entities;
	}
	public void setEntities(List<Entity> entities)
	{
		Entities = entities;
	}

	@XmlElementWrapper(name = "Relationnships")
	@XmlElement(name = "Relationship")
	public List<Relationship> getRelationships()
	{
		return Relationships;
	}
	public void setRelationships(List<Relationship> relationships)
	{
		Relationships = relationships;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ENTITIES");
		sb.append(System.lineSeparator());
		for (Entity entity : Entities)
		{
			sb.append(entity.toAbstractString());
			sb.append(System.lineSeparator());
		}
		sb.append("RELATIONSHIPS");
		sb.append(System.lineSeparator());
		for (Relationship relationship : Relationships)
		{
			sb.append(relationship.toAbstractString());
			sb.append(System.lineSeparator());
		}

		return sb.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{

		Model copy = (Model) super.clone();

		copy.Entities = new ArrayList<Entity>();
		for (Entity entity : Entities)
		{
			copy.Entities.add((Entity) entity.clone());
		}

		copy.Relationships = new ArrayList<Relationship>();
		for (Relationship relationship : Relationships)
		{
			copy.Relationships.add((Relationship) relationship.clone());
		}

		return copy;
	}

	/*
	 * GET OBJECTS AS STRING LISTS
	 */
	public List<String> getEntitiesAsStringList()
	{
		List<String> entities = new ArrayList<String>();
		for (Entity entity : this.Entities)
		{
			entities.add(entity.getLemmName());
		}
		return entities;
	}

	public List<String> getAttributesAsStringList()
	{
		List<String> attributes = new ArrayList<String>();
		for (Entity entity : Entities)
		{
			for (Attribute attribute : entity.getAttributes())
			{
				attributes.add(attribute.getLemmName());
			}
		}
		return attributes;
	}
	public List<String> getRelationshipsAsStringList()
	{
		List<String> relationships = new ArrayList<String>();
		for (Relationship relation : this.Relationships)
		{
			relationships.add(relation.getLemmName());
		}
		return relationships;
	}

	public Map<String, List<String>> getConnectorsAsStringMap()
	{

		Map<String, List<String>> connections = new HashMap<String, List<String>>();
		for (Entity entity : Entities)
		{
			List<String> attributes = new ArrayList<String>();
			for (Attribute attribute : entity.getAttributes())
			{
				attributes.add(attribute.getLemmName());
			}
			connections.put(entity.getLemmName(), attributes);
		}

		for (Relationship relation : Relationships)
		{
			List<String> rents = new ArrayList<String>();
			for (RelationEntity rent : relation.getConnects())
			{
				rents.add(rent.getLemmName());
			}
			connections.put(relation.getLemmName(), rents);
		}

		return connections;
	}

	/*
	 * SEARCHING METHODS by NAME by ID
	 */
	public Entity getEntityByName(String name)
	{
		for (Entity entity : Entities)
		{
			if (entity.getLemmName().equals(name) == true || entity.getName().equals(name) == true)
			{
				return entity;
			}
		}
		return null;
	}

	public Relationship getRelationshipByName(String name)
	{
		for (Relationship relation : Relationships)
		{
			if (relation.getLemmName().equals(name) == true
					|| relation.getName().equals(name) == true)
			{
				return relation;
			}
		}
		return null;
	}

	public Relationship getRelationshipById(int id)
	{
		for (Relationship relation : Relationships)
		{
			if (relation.getId() == id)
			{
				return relation;
			}
		}
		return null;
	}

	public Entity getEntityById(int id)
	{
		for (Entity entity : Entities)
		{
			if (entity.getId() == id)
			{
				return entity;
			}
		}
		return null;
	}

	public Model(List<Model> mods)
	{
		/*
		 * FIXME Pending work: Promote attributes to entities (recursively)
		 */
		int id = 0;
		try
		{
			for (int modelIndex = 0; modelIndex < mods.size(); modelIndex++)
			{
				Model model = mods.get(modelIndex);

				for (Entity entity : model.Entities)
				{
					Entity ent = this.getEntityByName(entity.getLemmName());
					if (ent == null)
					{
						Entity clone = (Entity) entity.clone();
						clone.setId(id++);
						this.Entities.add(clone);
					}
					else
					{
						for (Attribute attribute : entity.getAttributes())
						{
							Attribute clone = (Attribute) attribute.clone();
							clone.setId(id++);
							ent.getAttributes().add(clone);
						}
					}

				}
				for (Relationship relation : model.Relationships)
				{
					if (contains(relation) == false)
					{
						// It is not already present in the model
						// Either returned null or some things are different
						Relationship clone = (Relationship) relation.clone();
						clone.setId(id++);
						clone.Connects = new ArrayList<RelationEntity>();
						for (RelationEntity rent : relation.getConnects())
						{

							Entity ent = this.getEntityByName(rent.getLemmName());
							RelationEntity re = new RelationEntity();
							re.setName(ent.getName());
							re.setEntityId(ent.getId());
							clone.getConnects().add(re);
						}
						this.Relationships.add(clone);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Exception while merging models.");
		}
		
		promoteAttributes();

	}
	public Model()
	{
	}

	private boolean contains(Relationship relationship)
	{
		for (Relationship relation : Relationships)
		{
			if (relation.equals(relationship) == true)
			{
				return true;
			}
		}
		return false;
	}
	private boolean isEntity(String name)
	{
		return this.getEntitiesAsStringList().contains(name);
	}

	private void promoteAttributes()
	{
		//Modifying the entity object so, normal for loop is used
		for (int i = 0; i < Entities.size(); i++) {
			Entity entity = Entities.get(i);
			
			Iterator<Attribute> attributeItr = entity.getAttributes().iterator();
			while (attributeItr.hasNext())
			{
				Attribute attribute = attributeItr.next();
				if (this.isEntity(attribute.lemmName)) {
					attributeItr.remove();
				}
			}
		}

	}
}
