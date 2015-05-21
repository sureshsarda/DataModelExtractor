package ui.shapes;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import ui.view.editor.EditorStatus;

@SuppressWarnings("serial")
public class EntityShape extends Shape
{
	public EntityShape(String name, int id, ShapeType type) {
		super(name, id, type);
	}

	@Override
	protected void paintComponent(Graphics g)
	{

		Graphics2D g2 = (Graphics2D) g;

		g2.setFont(EditorStatus.shapeFont);
		FontMetrics fm = g2.getFontMetrics();
		int width = fm.stringWidth(this.getDisplayName());
		int height = fm.getAscent();

		this.setSize(width + 30, height + 30);

		super.paintComponent(g);

		g2.setColor(EditorStatus.contrastColor);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		g2.setColor(EditorStatus.foregroundColor);
		g2.drawString(this.getDisplayName(), 15, height + 15 - fm.getDescent());

		this.getParent().repaint();

	}

}
