package ui.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import nlp.test.TestParagraph;
import trie.serial.SerialTrie;
import ui.Util;

public class RootFrame extends JFrame
{
	public static RootFrame rootFrame;
	public static JDesktopPane desktopPane;
	public static SerialTrie trie;
	public static String trieFile = "E:/Workspace/EclipseWorkspaces/DefaultWorkspace/Trainer/data/model.xml";

	public RootFrame()
	{
		rootFrame = this;
		Util.setCurrentSystemLookAndFeel();

		createMenuBar();

		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(640, 480));
		this.setTitle("Data Model Extractor");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setVisible(true);

	}

	public SerialTrie getTrie()
	{
		if (trie == null)
		{
			trie = SerialTrie.loadFromXml(trieFile);
		}
		return trie;
	}
	public void addNewFrame(TestParagraph para)
	{
		Feedback frame = new Feedback(para);
		frame.setResizable(true);
		desktopPane.add(frame);
		frame.moveToFront();
		frame.setVisible(true);
	}

	private void createMenuBar()
	{

		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();

		/*
		 * FILE MENU
		 */
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		menuItem = new JMenuItem("New", new ImageIcon("assets/32x32/appbar.add.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand("NewFile");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Open", new ImageIcon("assets/32x32/appbar.book.open.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand("OpenFile");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Save", new ImageIcon("assets/32x32/appbar.save.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.setActionCommand("Save");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menuItem = new JMenuItem("Save As", new ImageIcon("assets/32x32/appbar.page.copy.png"));
		menuItem.setActionCommand("SaveAs");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Exit", new ImageIcon("assets/32x32/appbar.close.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		menuItem.setActionCommand("Exit");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		/*
		 * DIAGRAM MENU
		 */
		menu = new JMenu("Diagram");
		menu.setMnemonic(KeyEvent.VK_D);

		menuBar.add(menu);

		menuItem = new JMenuItem("Format", new ImageIcon("assets/32x32/appbar.text.size.png"));
		menuItem.setMnemonic(KeyEvent.VK_F);
		menuItem.setActionCommand("DiagramFormat");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		/*
		 * IMPORT MENU
		 */

		menu = new JMenu("Import/Export");
		menu.setMnemonic(KeyEvent.VK_I);
		menuBar.add(menu);

		menuItem = new JMenuItem("Import From Text File", new ImageIcon(
				"assets/32x32/appbar.download.png"));
		menuItem.setMnemonic(KeyEvent.VK_T);
		menuItem.setActionCommand("ImportText");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem("Export as Jpeg", new ImageIcon(
				"assets/32x32/appbar.image.export.png"));
		menuItem.setMnemonic(KeyEvent.VK_J);
		menuItem.setActionCommand("ExportJpeg");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menuItem = new JMenuItem("Export as Png", new ImageIcon(
				"assets/32x32/appbar.image.export.png"));
		menuItem.setMnemonic(KeyEvent.VK_P);
		menuItem.setActionCommand("ExportPng");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		/*
		 * HELP MENU
		 */

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);

		menuItem = new JMenuItem("Getting Started", new ImageIcon(
				"assets/32x32/appbar.book.perspective.help.png"));
		menuItem.setMnemonic(KeyEvent.VK_G);
		menuItem.setActionCommand("HelpGettingStarted");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		menuItem = new JMenuItem("About", new ImageIcon("assets/32x32/appbar.information.png"));
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setActionCommand("HelpAbout");
		menuItem.addActionListener(new MenuBarActionListener());
		menu.add(menuItem);

		setJMenuBar(menuBar);
	}

	public static void main(String[] args)
	{

		/*
		 * Desktop Pane and initialization
		 */
		new RootFrame();
		desktopPane = new JDesktopPane();
		rootFrame.add(desktopPane, BorderLayout.CENTER);

		/*
		 * Add the getting started/help page
		 */
		GettingStarted gettingStarted = new GettingStarted();
		gettingStarted.setSize(desktopPane.getSize());
		desktopPane.add(gettingStarted);

		// FIXME Look and feel of internal frames

	}

}
