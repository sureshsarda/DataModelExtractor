/**
 * <b>ImportWizardPageTemplate</b> is a template for ImportWizard steps. It
 * provides implements basic contents of the page like the header bar which
 * contains information about the current step and a footer bar which has "Next"
 * and "Cancel" buttons.
 * 
 * @author SureshSarda
 */

package importWizard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;

public class ImportWizardPageTemplate extends JPanel implements ActionListener
{
	JButton nextButton;
	JButton cancelButton;
	JLabel title;

	JPanel top;
	JPanel center;
	JPanel bottom;

	public ImportWizardPageTemplate()
	{
		this.setSize(new Dimension(600, 450));
		this.setMinimumSize(new Dimension(300, 300));
		this.setVisible(true);
		this.setLayout(new BorderLayout());

	}

	void initComponents()
	{
		/*
		 * Create Top Panel for Page Headers
		 */
		top = new JPanel();
		top.setBackground(Color.WHITE);
		top.setMinimumSize(new Dimension(300, 75));
		top.setPreferredSize(new Dimension(600, 75));
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		top.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
				BorderFactory.createEmptyBorder(12, 12, 12, 12)));

		title = new JLabel();
		title.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		top.add(title);

		this.add(top, BorderLayout.PAGE_START);

		/*
		 * Center Panel - Blank (since page specific
		 */
		center = new JPanel();
		center.setMinimumSize(new Dimension(300, 150));
		center.setPreferredSize(new Dimension(600, 300));
		this.add(center, BorderLayout.CENTER);

		/*
		 * Bottom Panel - Page Footer (Next Button and Cancel Button)
		 */
		bottom = new JPanel();
		bottom.setMinimumSize(new Dimension(300, 75));
		bottom.setPreferredSize(new Dimension(600, 75));
		bottom.setLayout(new FlowLayout(FlowLayout.TRAILING));
		bottom.setBorder(new CompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY), BorderFactory
						.createEmptyBorder(12, 12, 12, 12)));

		nextButton = new JButton("Next");
		nextButton.setMnemonic(KeyEvent.VK_N);
		nextButton.setActionCommand("Next");
		nextButton.addActionListener(this);
		nextButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		nextButton.setPreferredSize(new Dimension(90, 25));
		nextButton.setEnabled(false);
		bottom.add(nextButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic(KeyEvent.VK_C);
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
		cancelButton.setPreferredSize(new Dimension(90, 25));
		cancelButton.setEnabled(true);
		bottom.add(cancelButton);

		this.add(bottom, BorderLayout.PAGE_END);

	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		ImportWizard parent = (ImportWizard) SwingUtilities.getWindowAncestor(this);
		switch (arg0.getActionCommand())
		{
			case "Next" :
				parent.next();
				break;
			case "Cancel" :
				parent.cancelImport();
				break;
		}

	}

	void enableNextButton()
	{
		nextButton.setEnabled(true);
	}
	void disableNextButton()
	{
		nextButton.setEnabled(false);
	}

}
