package ui.view.editor;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ui.shapes.Shape;

@SuppressWarnings("serial")
public class EditorPopUpMenu extends JPopupMenu implements ActionListener
{
	JMenuItem addEntity = new JMenuItem("Add Entity");
	JMenuItem addAttribute = new JMenuItem("Add Attribute");
	JMenuItem addRelationship = new JMenuItem("Add Relationship");
	
	Point sourceCoord;
	Editor parentEditor;

	public EditorPopUpMenu(Editor editor)
	{
		parentEditor = editor;
		
		add(addEntity);
		add(addAttribute);
		add(addRelationship);
		
		addEntity.setActionCommand("AddEntity");
		addAttribute.setActionCommand("AddAttribute");
		addRelationship.setActionCommand("AddRelationship");
		
		addEntity.addActionListener(this);
		addAttribute.addActionListener(this);
		addRelationship.addActionListener(this);

	}

	public void setSourceCoord(Point p)
	{
		sourceCoord = p;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (parentEditor != null) {
			Shape shape = null;
			switch (arg0.getActionCommand())
			{
				case "AddEntity":
					shape = parentEditor.data.addEntity("new entity");
					break;
				case "AddAttribute":
					shape = parentEditor.data.addAttribute("new attribute");
					break;
				case "AddRelationship":
					shape = parentEditor.data.addRelationship("new relationship");
					break;
			}
			shape.setSize(50, 50);
			shape.setLocation(sourceCoord);
			shape.setVisible(true);
			
			parentEditor.add(shape);
		}
		
	}
}
