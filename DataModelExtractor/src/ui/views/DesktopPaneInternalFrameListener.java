package ui.views;

import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import trie.serial.SerialTrie;

public class DesktopPaneInternalFrameListener implements InternalFrameListener
{

	@Override
	public void internalFrameActivated(InternalFrameEvent arg0)
	{
		

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent arg0)
	{
		

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent arg0)
	{
		int retVal = JOptionPane
				.showConfirmDialog(
						null,
						"You have requested to make changes to the model. Do you wish to update model?",
						"Update Model", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		
		
		//Save the model if user returns YES
		if (retVal == JOptionPane.YES_OPTION)
		{
			Feedback caller = (Feedback)arg0.getSource();
			SerialTrie.updateTrie("data/model.xml", caller.original);
		}
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0)
	{

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0)
	{
		

	}

	@Override
	public void internalFrameIconified(InternalFrameEvent arg0)
	{
		

	}

	@Override
	public void internalFrameOpened(InternalFrameEvent arg0)
	{
		

	}

}
