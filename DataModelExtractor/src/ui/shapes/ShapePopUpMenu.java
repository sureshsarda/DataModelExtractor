package ui.shapes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import ui.view.editor.Editor;
import ui.view.editor.EditorStatus;

@SuppressWarnings("serial")
public class ShapePopUpMenu extends JPopupMenu
{
	JMenuItem rename;
	JMenuItem delete;
	JMenuItem connect;

	Shape thisShape;

	public ShapePopUpMenu()
	{

		this.add(rename = new JMenuItem("Rename"));
		this.add(delete = new JMenuItem("Delete"));
		this.add(connect = new JMenuItem("Connect"));

		delete.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Editor ed = (Editor) thisShape.getParent();
				ed.remove(thisShape);
				ed.data.remove(thisShape);
				
			}

		});

		connect.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				((Editor)thisShape.getParent()).setSource(thisShape);
			}

		});
		
		rename.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Editor parent = (Editor)thisShape.getParent();
				
				JTextField rename = new JTextField();
				rename.setText(thisShape.getDisplayName());
				rename.setBorder(BorderFactory.createStrokeBorder(EditorStatus.connectorStroke));
				rename.setFont(EditorStatus.shapeFont);
				rename.setSize(thisShape.getSize());
				rename.setLocation(thisShape.getLocation());
				rename.setVisible(true);
				rename.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent a)
					{
						thisShape.setDisplayName(rename.getText());
						rename.setVisible(false);
						thisShape.setVisible(true);

						updateData(parent, thisShape);
						/* Checks if the new name is in the sentence */
						/*
						 * if (((EditorGroupPanel)
						 * thisComponent.getParent().getParent
						 * ().getParent()).sentence
						 * .getValue().contains(rename.getText()) == true) {
						 * thisComponent.setName(rename.getText());
						 * rename.setVisible(false);
						 * 
						 * thisComponent.setVisible(true); } else {
						 * rename.setText("Error"); }
						 */
					}
				});

				thisShape.setVisible(false);
				thisShape.getParent().add(rename);
				
			}
			
			private void updateData(Editor ed, Shape thisShape)
			{
				if (thisShape.getClass() == EntityShape.class)
					ed.data.addEntity(thisShape.getDisplayName());
				else if (thisShape.getClass() == AttributeShape.class)
					ed.data.addAttribute(thisShape.getDisplayName());
				else if (thisShape.getClass() == RelationshipShape.class)
					ed.data.addRelationship(thisShape.getDisplayName());
				else
					;
			}
			
		});

	}

	public void setCaller(Shape component)
	{
		this.thisShape = component;
	}

}
