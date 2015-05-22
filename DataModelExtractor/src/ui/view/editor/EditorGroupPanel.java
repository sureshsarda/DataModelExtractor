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
	TestSentence testSentence;

	public void setTestSentence(TestSentence sentence)
	{
		this.setTabPlacement(TOP);
		updateLayout(sentence);
	}

	public void updateLayout(TestSentence sent)
	{
		this.testSentence = sent;
		this.removeAll();

		for (int i = 0; i < testSentence.getLookupResults().size(); i++)
		{
			EditorPanel pan = new EditorPanel();
			pan.setTestSentence(this.testSentence, i);
			
			this.addTab("Option: " + i, pan);
		}
	}

	public void setDataModel(Model model)
	{
		testSentence.setDataModel(model);

		Container obj = this;
		while (!obj.getClass().equals(Feedback.class))
			obj = obj.getParent();

		Feedback parent = (Feedback) obj;
		parent.dataModelUpdated(testSentence);
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
}