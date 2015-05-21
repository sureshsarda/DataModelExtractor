package ui.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FeedbackRibbon extends JPanel
{
	public FeedbackRibbon() {
		this.setPreferredSize(new Dimension(50, 100));
		this.setVisible(true);
		this.setBackground(Color.GRAY);
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		this.add(createRibbonButton("Attribute", "assets/50x50/Attribute.png"));
		this.add(createRibbonButton("Entity", "assets/50x50/Entity.png"));
		
	}
	
	private JPanel createRibbonButton(String name, String icon) {
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(50, 65));
		panel.setMaximumSize(new Dimension(70, 65));
		panel.setMinimumSize(new Dimension(70, 65));
		panel.setVisible(true);
		
		JButton but = new JButton(new ImageIcon(icon));
		but.setPreferredSize(new Dimension(50, 50));
		but.setMaximumSize(new Dimension(70, 50));
		but.setMinimumSize(new Dimension(70, 50));
		panel.add(but, BorderLayout.CENTER);
		
		JLabel lab = new JLabel("<html><center>" + name + "</center></html>");
		lab.setPreferredSize(new Dimension(50, 15));
		lab.setMaximumSize(new Dimension(70, 15));
		lab.setMinimumSize(new Dimension(70, 15));

		panel.add(lab, BorderLayout.PAGE_END);
		
//		Color col = but.getBackground();
//		lab.setHorizontalAlignment(SwingConstants.CENTER);
//		lab.setVerticalAlignment(SwingConstants.CENTER);
//		lab.setAlignmentX(CENTER_ALIGNMENT);
//		but.setAlignmentX(CENTER_ALIGNMENT);
		
//		panel.setBackground(col);
		return panel;
	}
}
