/**
 * EditorPanel: This is a wrapper for the editor and it contains the sentence
 * preview on the top, save diagram button at the bottom and the editor itself
 * in the center.
 * 
 * @author SureshSarda
 */

package ui.view.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nlp.objects.Model;

@SuppressWarnings("serial")
public class EditorPanel extends JPanel
{
	Editor editor;
	int lookupObjectIndex;
	EditorGroupPanel egpParent;

	public void setLookupObjectIndex(int lookupObjectIndex)
	{
		this.lookupObjectIndex = lookupObjectIndex;
		initComponents();
	}

	private void initComponents()
	{
		egpParent = this.getParentEditorGroupPanel();
		Model model = egpParent.sentence.getLookupResults().get(lookupObjectIndex).getDataModel();

		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setPreferredSize(new Dimension(300, 300));
		this.setMinimumSize(new Dimension(200, 200));

		/*
		 * Upper Sentence Text
		 */
		JLabel sentenceLabel = new JLabel();
		// sentenceLabel.setText("<html><body style='width: " + this.get.width +
		// "px'>" + sentence + "</body></html>");
		sentenceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		sentenceLabel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		sentenceLabel.setText(egpParent.sentence.getValue());
		this.add(sentenceLabel, BorderLayout.PAGE_START);

		/*
		 * Center Editor Panel
		 */
		editor = new Editor();
		editor.setModel(model);
		editor.setSize(300, 300);
		editor.setLocation(10, 10);
		this.add(editor, BorderLayout.CENTER);

		/*
		 * Lower Button Panel
		 */
		JPanel buttons = new JPanel();
		JButton saveButton = new JButton("Save this diagram");
		buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		saveButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{

				saveThisDiagram_click();

			}

		});
		buttons.add(saveButton);
		// buttons.add(new JButton("Reset"));
		buttons.setOpaque(false);
		this.add(buttons, BorderLayout.PAGE_END);

	}
	public void saveThisDiagram_click()
	{
		Model data = editor.data.getDataModel();
		egpParent.setDataModel(data);
		egpParent.sentence.setResults(null);
	}

	private EditorGroupPanel getParentEditorGroupPanel()
	{
		Container parent = this.getParent();
		while (parent.getClass() != EditorGroupPanel.class)
		{
			parent = parent.getParent();
		}
		return (EditorGroupPanel) parent;
	}
}
