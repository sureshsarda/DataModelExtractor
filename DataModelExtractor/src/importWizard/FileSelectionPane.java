/**
 * FileSelectionPane: Panel to ease import of text files for extraction. This
 * panel is designed to be placed in import wizard in a
 * ImportWizardPageTemplate. This panel has a TextBox for the file path and and
 * browse button which opens file chooser. There is second text box "content"
 * which previews the file for the user before clicking "Next" button.
 * 
 * This is the first step of importing a file.
 * 
 * @author SureshSarda
 */
package importWizard;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class FileSelectionPane extends ImportWizardPageTemplate implements ActionListener
{
	JTextArea filePathTextArea;
	JTextArea fileContentTextArea;

	JButton browseButton;

	public FileSelectionPane()
	{
		super();

		initComponents();

	}

	void initComponents()
	{
		super.initComponents();

		titleLabelMessage("", "");

		center.setLayout(new GridBagLayout());
		GridBagConstraints leftGb = new GridBagConstraints();
		GridBagConstraints centerGb = new GridBagConstraints();
		GridBagConstraints rightGb = new GridBagConstraints();

		leftGb.anchor = GridBagConstraints.NORTHWEST;
		leftGb.fill = GridBagConstraints.NONE;
		leftGb.gridx = 0;
		leftGb.gridwidth = 1;
		leftGb.weightx = 0.1;
		leftGb.insets = new Insets(12, 12, 12, 12);

		centerGb.anchor = GridBagConstraints.CENTER;
		centerGb.fill = GridBagConstraints.BOTH;
		centerGb.gridx = 1;
		centerGb.gridwidth = 1;
		centerGb.weightx = 0.8;
		centerGb.insets = new Insets(12, 12, 12, 12);

		rightGb.anchor = GridBagConstraints.LINE_END;
		rightGb.fill = GridBagConstraints.HORIZONTAL;
		rightGb.gridx = 2;
		rightGb.gridwidth = 1;
		rightGb.weightx = 0.1;
		rightGb.insets = new Insets(12, 12, 12, 12);

		JLabel label = new JLabel("File Path:");
		leftGb.gridy = 0;
		center.add(label, leftGb);

		filePathTextArea = new JTextArea();
		filePathTextArea.setLineWrap(true);
		filePathTextArea.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0.5f),
				Color.GRAY));
		centerGb.gridy = 0;
		center.add(filePathTextArea, centerGb);

		browseButton = new JButton("Browse");
		rightGb.gridy = 0;
		browseButton.setMnemonic(KeyEvent.VK_B);
		browseButton.setActionCommand("Browse");
		browseButton.addActionListener(this);
		browseButton.setPreferredSize(new Dimension(90, 25));
		center.add(browseButton, rightGb);

		// File Content Text Area
		label = new JLabel("File Contents");
		leftGb.gridy = 1;
		center.add(label, leftGb);

		fileContentTextArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(fileContentTextArea);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.setMinimumSize(new Dimension(20, 200));
		scroll.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(0.5f), Color.GRAY));
		centerGb.gridy = 1;
		fileContentTextArea.setWrapStyleWord(true);
		fileContentTextArea.setLineWrap(true);
		fileContentTextArea.setRows(10);
		InputTextAreaDocumentListener changeListener = new InputTextAreaDocumentListener();
		changeListener.parent = this;
		fileContentTextArea.getDocument().addDocumentListener(changeListener);
		center.add(scroll, centerGb);

	}
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String command = arg0.getActionCommand();

		switch (command)
		{
			case "Browse" :
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text Files Only", new String[]{
						"txt", "text"}));
				int retVal = chooser.showOpenDialog(this);
				if (retVal == JFileChooser.APPROVE_OPTION)
				{
					filePathTextArea.setText(chooser.getSelectedFile().getPath());
					fileContentTextArea.setText(readFileContent(chooser.getSelectedFile()));
					revalidate();
				}
				break;

		}

		super.actionPerformed(arg0);

	}

	private String readFileContent(File file)
	{
		try
		{
			return new String(Files.readAllBytes(file.toPath()));
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this, "Invalid Selection");
			e.printStackTrace();
			return "File Parsing Error";
		}
	}

	enum InputTextErrorCode
	{
		ContainsNumbers, ContainsSpecialCharacter, ParagraphFormation
	};
	String containsNumberErrorMessage = "Data contains numerical character. Program can continue but errors may occur.";
	String containsSpecialCharacterErrorMessage = "Data contains special characters. Program can continue but errors may occur.";
	String paragraphFormationErrorMessage = "Paragraph is not formed correctly.";
	void invalidInput(InputTextErrorCode code)
	{
		switch (code)
		{
			case ContainsNumbers :
				titleLabelMessage(containsNumberErrorMessage, "Warning");
				enableNextButton();
				break;
			case ContainsSpecialCharacter :
				titleLabelMessage(containsSpecialCharacterErrorMessage, "Warning");
				enableNextButton();
				break;
			case ParagraphFormation :
				titleLabelMessage(paragraphFormationErrorMessage, "Error");
				disableNextButton();
				break;
			default :
				break;

		}
	}
	void validInput()
	{
		titleLabelMessage("Click next to continue", "");
		enableNextButton();
	}

	private void titleLabelMessage(String message, String type)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		sb.append("<b>Select File</b>");
		sb.append("<p>Import wizard lets you extract entity-relation diagram from a text document. Browse for a file or start typing text.</p>");
		sb.append("<br>");
		sb.append("<font color=\"#FF2222\"><b>");

		sb.append(message);

		sb.append("</b></font>");
		sb.append("</body></html>");

		title.setText(sb.toString());
	}

}
