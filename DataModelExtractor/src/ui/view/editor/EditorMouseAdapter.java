package ui.view.editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EditorMouseAdapter implements MouseMotionListener, MouseListener
{
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			Editor editor = (Editor) e.getSource();
			EditorPopUpMenu menu = new EditorPopUpMenu(editor);
			menu.setSourceCoord(e.getPoint());
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		else if (e.getButton() == MouseEvent.BUTTON1) {
			
			Editor editor = (Editor) e.getSource();
			
			/*Remove any selected item*/
			editor.setSelectedShape(null);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{

	}

	@Override
	public void mouseDragged(MouseEvent e)
	{


	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		((Editor) e.getComponent()).drawConnector(e.getX(), e.getY());
	}

}
