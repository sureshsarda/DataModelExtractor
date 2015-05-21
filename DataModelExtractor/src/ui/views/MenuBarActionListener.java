package ui.views;

import importWizard.ImportWizard;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarActionListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent arg)
	{
		String actionCommand = arg.getActionCommand();

		switch (actionCommand)
		{
			case "NewFile" :
				RootFrame.rootFrame.addNewFrame(null);
				break;
			case "OpenFile" :
				break;
			case "Save" :
				break;
			case "SaveAs" :
				break;
			case "Exit" :
				System.exit(0);
				break;

			case "DiagramFormat" :
				break;
				
			case "ImportText" :
				ImportWizard imp = new ImportWizard();
				break;
				
			case "ExportJpeg":

				break;
			case "ExportPng":
				break;

			case "HelpGettingStarted" :
				break;
			case "HelpAbout" :
				break;
		}

	}

}
