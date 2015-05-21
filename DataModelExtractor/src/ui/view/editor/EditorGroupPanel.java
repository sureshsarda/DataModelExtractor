/**
 * EditorGroupPanel This is the outer TabbedPane which contains all the possible
 * outputs and the editor panel itself. This panel is absent when there are not
 * LookupObjects since there are no choices.
 * 
 * @author SureshSarda
 */

package ui.view.editor;

import java.awt.Container;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import nlp.objects.Model;
import nlp.test.LookupResultObject;
import nlp.test.TestSentence;
import ui.views.Feedback;

@SuppressWarnings("serial")
public class EditorGroupPanel extends JTabbedPane
{
	TestSentence sentence;

	public void setTestSentence(TestSentence sentence)
	{
		this.setTabPlacement(TOP);
		updateLayout(sentence);
	}

	public void updateLayout(TestSentence sent)
	{
		this.sentence = sent;

		this.removeAll();

		int optionCount = 0;
		for (int i = 0; i < sentence.getLookupResults().size(); i++)
		{
			EditorPanel pan = new EditorPanel();

			this.addTab("Option: " + i, pan);
			pan.setLookupObjectIndex(i);
		}
	}

	public void setDataModel(Model model)
	{
		sentence.setDataModel(model);

		Container obj = this;
		while (!obj.getClass().equals(Feedback.class))
			obj = obj.getParent();

		Feedback parent = (Feedback) obj;
		parent.dataModelUpdated(sentence);
	}

	public void updateTabImages()
	{
		invalidate();
		for (int i = 0; i < getTabCount(); i++)
		{
			EditorPanel pan = (EditorPanel) getTabComponentAt(i);
			BufferedImage img = new BufferedImage(pan.getPreferredSize().width,
					pan.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
			ImageIcon icon = new ImageIcon(img);
			removeTabAt(i);
			addTab("Option", icon, pan);
		}
		validate();
	}
	// @Override
	// protected void paintComponent(Graphics g)
	// {
	// super.paintComponent(g);
	//
	// BufferedImage img = new BufferedImage(pan.getPreferredSize().width,
	// pan.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
	//
	// pan.paint(img.getGraphics());
	//
	// ImageIcon icon = new ImageIcon(img);
	// }

}