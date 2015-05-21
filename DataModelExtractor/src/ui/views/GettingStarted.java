package ui.views;

import java.io.File;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;


@SuppressWarnings("serial")
public class GettingStarted extends JInternalFrame
{
	public GettingStarted() {
		super("Getting Started", true, true, true, true);
		setVisible(true);
		
		
		JEditorPane pane = new JEditorPane();
		pane.setEditable(false);
		pane.setContentType("text/html");
		
		try
		{
			File htmlFile = new File("assets\\GettingStarted.html");
			pane.setPage(htmlFile.toURI().toURL());
		}
		catch (IOException e)
		{
			
			pane.setText("<html>Could not display help</html>");
			e.printStackTrace();
		}
		
		add(pane);
	}
}
