package ui.view.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class EditorStatus
{
	public static Font shapeFont = new Font("Times New Roman", Font.PLAIN, 20);
	public static Color contrastColor = new Color(0, 131, 185);
	public static Color connectorColor = new Color(0, 131, 185);
	public static BasicStroke connectorStroke = new BasicStroke((float) 1.5);
	public static Color foregroundColor = Color.WHITE;
	
	public static Color selectionBorderColor = new Color(0, 0, 0);
	public static Border selectionBorder = BorderFactory.createDashedBorder(selectionBorderColor, 3f, 2f, 3f, false);

	public static int shapeSizeOffset = 30;
	public static int innerPadding = 3;
	
	public static float zoomFactor = 1.0f;
}
