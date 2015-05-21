package ui.shapes;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import ui.view.editor.Editor;
import ui.view.editor.EditorStatus;

//FIXME Shapes should not move out of editable area

public class ShapeMouseAdapter implements MouseMotionListener, MouseListener
{
	protected static ShapeMouseAdapter shapeMouseAdapter = new ShapeMouseAdapter();
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Shape thisShape = (Shape) e.getComponent();
		Editor ed = (Editor) thisShape.getParent();
		
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			/* Complete a connection if there is an ongoing connection */
			if (ed.line == true)
			{
				ed.setDest(thisShape);
			}
			else
			{
				ed.setSelectedShape(thisShape);
			}
		}
		else if (e.getButton() == MouseEvent.BUTTON3)
		{
			/*This is the new selected shape*/
			ed.setSelectedShape(thisShape);
			
			/* Show pop up menu */
			ShapePopUpMenu menu = new ShapePopUpMenu();
			menu.setCaller((Shape) e.getComponent());
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		else
		{
			/* Invalid Button Pressed */

		}

	}
	@Override
	public void mouseEntered(MouseEvent e)
	{
		// JPanel thisComponent = (JPanel) e.getComponent();
		// thisComponent.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// JPanel thisComponent = (JPanel) e.getComponent();
		// thisComponent.setBorder(BorderFactory.createEmptyBorder());

	}

	boolean flag = false;
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		flag = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		flag = false;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (flag == true)
		{
			Component compo = e.getComponent();
			Point p = e.getPoint();
			p = SwingUtilities.convertPoint(compo, p, compo.getParent());

			// System.out.println("Draged" + this.getLocation() + e.getPoint());

			int newX = p.x - (compo.getWidth() / 2);
			int newY = p.y - (compo.getHeight() / 2);

			// set the calculated new location
			compo.setLocation(newX, newY);
			e.getComponent().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{

	}
}
