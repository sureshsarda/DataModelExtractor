/**
 * InputTextAreaDocumentListener: Provides an action listener for the FileContet
 * text area in the first step of import wizard. Basic processing and
 * identifying problems in the file are to be done in this.
 * 
 * @author SureshSarda
 */

package importWizard;

import importWizard.FileSelectionPane.InputTextErrorCode;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class InputTextAreaDocumentListener implements DocumentListener
{

	FileSelectionPane parent;
	@Override
	public void changedUpdate(DocumentEvent e)
	{
		PlainDocument doc = (PlainDocument) e.getDocument();
		process(doc);
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		PlainDocument doc = (PlainDocument) e.getDocument();
		process(doc);
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		PlainDocument doc = (PlainDocument) e.getDocument();
		process(doc);
	}

	private void process(PlainDocument doc)
	{
		try
		{
			parent.validInput();
			String text = doc.getText(0, doc.getLength());

			if (text.matches(".*[0-9].*") == true)
			{
				parent.invalidInput(InputTextErrorCode.ContainsNumbers);
			}

			else if (text.matches(".*[!@#$%^&*\\(\\)\\{\\}\\[\\]\\+].*") == true)
			{
				parent.invalidInput(InputTextErrorCode.ContainsSpecialCharacter);
			}

			char[] ch = text.toCharArray();
			for (int i = 0; i < ch.length - 1; i++)
			{
				if (ch[i] == '.' && ch[i + 1] != ' ')
				{
					// problem
					parent.invalidInput(InputTextErrorCode.ParagraphFormation);
					break;
				}
			}

		}
		catch (ClassCastException cce)
		{

		}
		catch (BadLocationException ble)
		{

		}

	}

}
