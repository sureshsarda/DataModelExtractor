package ui.view.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nlp.objects.Attribute;
import nlp.objects.Entity;
import nlp.objects.Model;
import nlp.objects.RelationEntity;
import nlp.objects.Relationship;
import ui.shapes.AttributeShape;
import ui.shapes.EntityShape;
import ui.shapes.RelationshipShape;
import ui.shapes.Shape;
import ui.shapes.Shape.ShapeType;
import util.Tuple;

public class DataModelAdapter
{
	protected List<Shape> shapes;
	protected List<Tuple<Integer, Integer>> connection;
	protected int id = 0;

	public DataModelAdapter(Model data)
	{
		shapes = new ArrayList<Shape>();
		connection = new ArrayList<Tuple<Integer, Integer>>();
		if (data == null)
		{
			data = new Model();
		}
		for (Entity entity : data.getEntities())
		{
			EntityShape ent = new EntityShape(entity.getLemmName(), id++, ShapeType.ENTITY);
			shapes.add(ent);

			for (Attribute attribute : entity.getAttributes())
			{
				AttributeShape atrb = new AttributeShape(attribute.getLemmName(), id++,
						ShapeType.ATTRIBUTE);
				shapes.add(atrb);

				Tuple<Integer, Integer> tuple = new Tuple<Integer, Integer>(ent.getId(),
						atrb.getId());
				connection.add(tuple);
			}
		}

		for (Relationship relation : data.getRelationships())
		{
			RelationshipShape rel = new RelationshipShape(relation.getLemmName(), id++,
					ShapeType.RELATIONSHIP);
			shapes.add(rel);

			for (RelationEntity rent : relation.getConnects())
			{

				int entId;
				Entity ent;
				String entName;
				Shape shape;
				try
				{
					entId = rent.getEntityId();
					ent = data.getEntityById(entId);
					entName = ent.getLemmName();
					shape = getShapeByName(entName);

					Tuple<Integer, Integer> tuple = new Tuple<Integer, Integer>(rel.getId(),
							shape.getId());
					connection.add(tuple);
				}
				catch (NullPointerException e)
				{

					e.printStackTrace();
				}

			}
		}

	}
	public void remove(Shape shape)
	{
		shapes.remove(shape);

		Iterator<Tuple<Integer, Integer>> itr = connection.iterator();
		while (itr.hasNext())
		{
			Tuple<Integer, Integer> tuple = itr.next();
			if (tuple.first == shape.getId() || tuple.second == shape.getId())
			{
				itr.remove();
			}
		}
	}

	public Model getDataModel()
	{
		Model model = new Model();
		for (Shape shape : shapes)
		{
			if (shape.isValid() == true)
				if (shape.getType() == ShapeType.ENTITY)
				{
					Entity ent = new Entity();
					ent.setName(shape.getDisplayName());
					ent.setId(shape.getId());
					model.getEntities().add(ent);
				}
				else if (shape.getType() == ShapeType.RELATIONSHIP)
				{
					Relationship rel = new Relationship();
					rel.setName(shape.getDisplayName());
					rel.setId(shape.getId());
					model.getRelationships().add(rel);
				}
				else
					;
		}

		for (Tuple<Integer, Integer> tuple : connection)
		{
			Shape source = getShapeById(tuple.first);
			Shape dest = getShapeById(tuple.second);
			System.out.println(tuple);
			System.out.println(shapes);

			if (source.getType() == ShapeType.ENTITY)
			{
				Attribute attribute = new Attribute();
				attribute.setName(dest.getDisplayName());
				attribute.setId(dest.getId());
				Entity ent =model.getEntityById(source.getId()); 
				if (ent == null)
				{
					System.out.println("Null Pointer Exception");
					System.out.println(model.toString());
					System.out.println("Trying to fetch: " + source);
				}
				else
				{
					ent.getAttributes().add(attribute);
				}
			}
			else if (source.getType() == ShapeType.RELATIONSHIP)
			{
				RelationEntity rent = new RelationEntity();
				rent.setName(dest.getDisplayName());
				rent.setEntityId(dest.getId());
				Relationship relation = model.getRelationshipById(source.getId());
				if (relation == null)
				{
					System.out.println("Null Pointer Exception");
					System.out.println(model.toString());
					System.out.println("Trying to fetch: " + source);
				}
				else
				{
					relation.getConnects().add(rent);
				}

			}
			else
				;
		}
		return model;
	}

	public Shape addEntity(String name)
	{
		Shape shape = new EntityShape(name, id++, ShapeType.ENTITY);
		shapes.add(shape);
		return shape;
	}

	public Shape addAttribute(String name)
	{
		Shape shape = new AttributeShape(name, id++, ShapeType.ATTRIBUTE);
		shapes.add(shape);
		return shape;
	}

	public Shape addRelationship(String name)
	{
		Shape shape = new RelationshipShape(name, id++, ShapeType.RELATIONSHIP);
		shapes.add(shape);
		return shape;
	}

	public Shape getShapeByName(String name)
	{
		for (Shape shape : shapes)
		{
			if (shape.getDisplayName().equals(name))
				return shape;
		}
		return null;
	}

	public Shape getShapeById(int id)
	{
		for (Shape shape : shapes)
		{
			if (shape.getId() == id)
				return shape;
		}
		return null;
	}
}
