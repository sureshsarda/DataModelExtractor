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

import ui.views.Feedback;
import nlp.objects.Model;
import nlp.test.TestSentence;

@SuppressWarnings("serial")
public class EditorPanel extends JPanel
{
	Editor editor;
	TestSentence testSentence;
	int lookupObjectId;
	EditorGroupPanel egpParent;

	public void setTestSentence(TestSentence sentence, int lookupObjectId)
	{
		this.testSentence = sentence;
		this.lookupObjectId = lookupObjectId;
		initComponents();
	}

	private void initComponents()
	{
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
		sentenceLabel.setText(testSentence.getValue());
		this.add(sentenceLabel, BorderLayout.PAGE_START);

		/*
		 * Center Editor Panel
		 */
		editor = new Editor();
		editor.setTestSentence(this.testSentence, this.lookupObjectId);
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
		this.testSentence.setResults(null);
		this.testSentence.setDataModel(data);
		
		Container obj = this;
		while (!obj.getClass().equals(Feedback.class))
			obj = obj.getParent();

		Feedback parent = (Feedback) obj;
		parent.dataModelUpdated(testSentence);
	}

	private EditorGroupPanel getParentEditorGroupPanel()
	{
		if (egpParent == null)
		{

			Container parent = this.getParent();
			while (parent.getClass() != EditorGroupPanel.class)
			{
				parent = parent.getParent();
			}
			egpParent = (EditorGroupPanel) parent;
		}
		return egpParent;
	}
}
