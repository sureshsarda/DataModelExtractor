package ui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import nlp.objects.Model;
import nlp.test.TestParagraph;
import nlp.test.TestSentence;
import trie.serial.SerialTrie;
import ui.Util;
import ui.view.editor.Editor;
import ui.view.editor.EditorGroupPanel;

@SuppressWarnings("serial")
public class Feedback extends JInternalFrame
{
	public TestParagraph original;
	public List<String> pending = new ArrayList<String>();
	public List<String> complete = new ArrayList<String>();

	DesktopPaneInternalFrameListener dpInternalFrameListener = new DesktopPaneInternalFrameListener();

	/*
	 * Components
	 */
	JComboBox<String> sentenceCombo;
	EditorGroupPanel groupEditor;
	Editor paragraphEditor;
	JPanel leftPanel;
	JPanel rightPanel;
	JSplitPane splitPane;

	
	public Feedback(TestParagraph para)
	{
		super("Untitled", true, true, true, true);
		this.setSize(600, 500);
		Util.setCurrentSystemLookAndFeel();

		this.setResizable(true);
		this.setVisible(true);

		addInternalFrameListener(dpInternalFrameListener);
		
		if (para == null)
		{
			Editor ed = new Editor();
			ed.setTestSentence(null, -1);
			this.add(ed, BorderLayout.CENTER);
		}
		else
		{
			this.original = para;
			pending.addAll(para.getSentenceList());
			initComponents();
		}
		// add(new FeedbackRibbon(), BorderLayout.PAGE_START);

	}

	
	private void initComponents()
	{
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(450);
		splitPane.setVisible(true);

		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setMinimumSize(new Dimension(20, 400));
		leftPanel.setVisible(true);

		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setMinimumSize(new Dimension(400, 400));
		rightPanel.setVisible(true);

		JScrollPane rscroll = new JScrollPane(rightPanel);

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rscroll);
		/*
		 * COMBO BOX
		 */
		sentenceCombo = new JComboBox<String>();
		sentenceCombo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (sentenceCombo.getSelectedIndex() >= 0)
					loadGroupEditor((String) sentenceCombo.getSelectedItem());
			}
		});
		updateComboBoxItems();

		leftPanel.add(sentenceCombo, BorderLayout.PAGE_START);
		loadGroupEditor(pending.get(0));

		leftPanel.revalidate();

		/*
		 * RIGHT PANEL
		 */
		updateRightPane();

		add(splitPane, BorderLayout.CENTER);

		// TODO FIX THE STATUS BAR
		JPanel statusBar = new JPanel();
		statusBar.setPreferredSize(new Dimension(200, 20));
		statusBar.setVisible(true);
		add(statusBar, BorderLayout.PAGE_END);

	}
	private void loadGroupEditor(String sentence)
	{
		leftPanel.invalidate();

		if (groupEditor != null)
			leftPanel.remove(groupEditor);
		groupEditor = new EditorGroupPanel();
		groupEditor.setTestSentence(original.getSentenceByValue(sentence));
		leftPanel.add(groupEditor, BorderLayout.CENTER);
		// groupEditor.updateTabImages();

		leftPanel.validate();
	}

	public void dataModelUpdated(TestSentence sent)
	{
		original.updateSentenceModel(sent.getValue(), sent.getDataModel());
		System.out.println("Sentence: " + sent.getValue());
		System.out.println("New Model:\n" + sent.getDataModel());
		original.getSentenceByValue(sent.getValue()).setResults(null);
		pending.remove(sent.getValue());
		complete.add(sent.getValue());

		updateComboBoxItems();
		updateRightPane();
	}

	private void updateRightPane()
	{
		rightPanel.invalidate();

		if (paragraphEditor != null)
			rightPanel.remove(paragraphEditor);

		Model paraModel = new Model(original.getModelAsList());
		System.out.println("New Paragraph Model:\n" + paraModel.toString());
		paragraphEditor = new Editor();
		paragraphEditor.setDataModel(paraModel);
		rightPanel.add(paragraphEditor, BorderLayout.CENTER);

		rightPanel.validate();
	}
	private void updateComboBoxItems()
	{
		sentenceCombo.removeAllItems();
		for (String string : pending)
		{
			sentenceCombo.addItem(string);
		}
		if (sentenceCombo.getItemCount() == 0)
		{
			splitPane.setLeftComponent(null);
		}
	}

	// @Override
	// public Insets getInsets()
	// {
	// return new Insets(12, 12, 12, 12);
	// }
}
