/**
 * ImportWizard: This wizard guides the user for extracting Entity-Relationship
 * information from a file. There are 2 steps for this dialog box.
 * <p>
 * Step 1: Select File (FileSelctionPane.java)
 * <p>
 * Step 2: Extraction (ProgressPane.java)
 * 
 * 
 * @author SureshSarda
 */

package importWizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ui.Util;
import ui.views.Feedback;
import ui.views.RootFrame;

@SuppressWarnings("serial")
public class ImportWizard extends JFrame
{
	ImportWizardPageTemplate current;

	public ImportWizard()
	{
		RootFrame.rootFrame.setEnabled(false);

		setBackground(Color.WHITE);
		this.setTitle("Import Wizard");
		this.setLocationRelativeTo(RootFrame.rootFrame);
		this.setSize(new Dimension(640, 480));
		this.setMaximumSize(new Dimension(640, 480));
		this.setMinimumSize(new Dimension(640, 480));
		setResizable(false);

		this.setVisible(true);

		Util.setLocationToCenter(this);

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				RootFrame.rootFrame.setEnabled(true);
			}
		});

		/* Navigate to first page on load */
		FileSelectionPane fileSelectionPane = new FileSelectionPane();
		current = fileSelectionPane;
		this.add(fileSelectionPane, BorderLayout.CENTER);

	}

	void cancelImport()
	{
		// Close frame
		this.dispose();
		RootFrame.rootFrame.setEnabled(true);
		RootFrame.rootFrame.setVisible(true);

	}

	void next()
	{
		if (current.getClass().equals(FileSelectionPane.class) == true)
		{
			this.invalidate();
			this.getContentPane().removeAll();

			ProgressPane prog = new ProgressPane();
			prog.text = ((FileSelectionPane) current).fileContentTextArea.getText();
			current = prog;

			this.getContentPane().add(prog, BorderLayout.CENTER);
			this.validate();
		}
		else
		{
			/* Open a new feedback window and close import wizard */
			ProgressPane prog = (ProgressPane) current;
			Feedback fb = new Feedback(prog.getTestParagraph());
			RootFrame.desktopPane.add(fb);
			fb.moveToFront();

			cancelImport();
		}

	}

}
