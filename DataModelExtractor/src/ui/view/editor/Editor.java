/**
 * Editor: This the actual diagram editor. User can add new entity, relations or
 * attributes and perform operations like rename and delete.
 * 
 * @author SureshSarda
 */

package ui.view.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import nlp.objects.Model;
import nlp.test.TestSentence;
import ui.shapes.Shape;
import ui.shapes.Shape.ShapeType;
import util.Tuple;

@SuppressWarnings("serial")
public class Editor extends JPanel
{
	protected EditorMouseAdapter mouseAdapter;
	TestSentence testSentence;
	int lookupObjectId;
	Model model;

	public DataModelAdapter data;
	Shape selectedShape;

	public Editor()
	{
		initComponents();
	}
	
	public void setTestSentence(TestSentence testSentence, int lookupObjectId)
	{

		this.lookupObjectId = lookupObjectId;
		this.testSentence = testSentence;
		if (lookupObjectId == -1)
		{
			model = null;
		}
		else
		{
			model = testSentence.getLookupResults().get(lookupObjectId).getDataModel();
		}

		this.setDataModel(model);

	}

	private void initComponents()
	{
		this.setVisible(true);
		this.setLayout(null);
		this.setBackground(Color.WHITE);

		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));

		mouseAdapter = new EditorMouseAdapter();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);

	}
	@Override
	public Component getComponentAt(Point p)
	{
		return findComponentAt(p);
	}

	public void setDataModel(Model model)
	{
		data = new DataModelAdapter(model);
		this.paintDataModel();
	}
	private void paintDataModel()
	{
		int rely = 0, enty = 0, atry = 0;

		for (Shape shape : data.shapes)
		{
			switch (shape.getType())
			{
				case ENTITY :
					// enty = enty + 50;
					shape.setLocation(150, enty);
					break;
				case RELATIONSHIP :
					// rely = rely + 50;
					shape.setLocation(20, rely);
					break;
				case ATTRIBUTE :
					// atry = atry + 50;
					shape.setLocation(300, atry);
					break;
			}
			shape.setSize(100, 100);
			this.add(shape);
		}
	}

	public boolean line = false;
	Shape source, dest;
	public void setSource(Shape source)
	{
		this.source = source;
		line = true;

	}

	public void setDest(Shape dest)
	{
		this.dest = dest;
		line = false;

		System.out.println(source.getClass());
		System.out.println(dest.getClass());

		if ((source.getType() == ShapeType.ATTRIBUTE && dest.getType() == ShapeType.ENTITY)
				|| (source.getType() == ShapeType.ENTITY && dest.getType() == ShapeType.RELATIONSHIP))
		{
			Shape temp;
			temp = source;
			source = dest;
			dest = temp;
		}

		Tuple<Integer, Integer> tuple = new Tuple<Integer, Integer>(source.getId(), dest.getId());
		data.connection.add(tuple);

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);

		g2.setColor(new Color(0, 131, 185));
		g2.setStroke(new BasicStroke((float) 1.5));

		for (Tuple<Integer, Integer> connection : data.connection)
		{
			Shape source = data.getShapeById(connection.first);
			Shape dest = data.getShapeById(connection.second);

			Point[] anchors = source.getAnchors(dest);

			g2.fillOval(anchors[0].x - 3, anchors[0].y - 3, 6, 6);
			for (int i = 1; i < anchors.length; i++)
			{
				g2.drawLine(anchors[i - 1].x, anchors[i - 1].y, anchors[i].x, anchors[i].y);
			}
			g2.fillOval(anchors[anchors.length - 1].x - 3, anchors[anchors.length - 1].y - 3, 6, 6);

		}
		this.getParent().repaint();
	}
	protected void drawConnector(int x, int y)
	{
		if (line == true)
		{
			Graphics2D g2 = (Graphics2D) this.getGraphics();

			RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHints(qualityHints);

			g2.setColor(new Color(0, 131, 185));
			g2.setStroke(new BasicStroke((float) 2));

			g2.drawLine(source.getX(), source.getY(), x, y);
		}
	}

	public void setSelectedShape(Shape shape)
	{
		/* remove already selected shape and */
		if (this.selectedShape != null)
			this.selectedShape.removeSelection();

		this.selectedShape = shape;

		/* set selection only if the provided shape is not null */
		if (shape != null)
			this.selectedShape.setSelection();
	}

	public Shape getSelectedShape()
	{
		return this.selectedShape;
	}

}
