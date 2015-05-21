package ui.shapes;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import ui.view.editor.EditorStatus;

@SuppressWarnings("serial")
public class AttributeShape extends Shape
{
	public AttributeShape(String name, int id, ShapeType type)
	{
		super(name, id, type);
	}

	@Override
	protected void paintComponent(Graphics g)
	{

		Graphics2D g2 = (Graphics2D) g;

		/* Calculate and set required size for the shape */
		g2.setFont(EditorStatus.shapeFont);
		FontMetrics fm = g2.getFontMetrics();
		int width = fm.stringWidth(this.getDisplayName());
		int height = fm.getHeight();

		this.setSize(width + EditorStatus.shapeSizeOffset, height + EditorStatus.shapeSizeOffset);

		super.paintComponent(g);

		g2.setColor(EditorStatus.contrastColor);
		g2.setStroke(new BasicStroke((float) 1.5));

		// g2.drawRoundRect(1, 1, width + 18, height + 18, 20, 20);

		int innerPadding = EditorStatus.innerPadding;
		Insets ins = this.getInsets();
		g2.drawRoundRect(ins.left + innerPadding, ins.top + innerPadding, this.getSize().width
				- ins.right - innerPadding, this.getSize().height - ins.bottom - innerPadding, 20,
				20);

		g2.drawString(this.getDisplayName(), 10, height + 10 - fm.getDescent());

	}
}
