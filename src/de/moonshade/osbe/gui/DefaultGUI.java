/*
 * Copyright (c) 2011 Dominik Halfkann.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Dominik Halfkann
 */

/**
 * 
 */
package de.moonshade.osbe.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import de.moonshade.osbe.Main;
import de.moonshade.osbe.menuhandler.Action;
import de.moonshade.osbe.menuhandler.MenuHandler;
import de.moonshade.osbe.serializable.Options;

/**
 * Default graphical user interface.
 * <p>
 * This Class contains all the Information about anything the user sees, like
 * windows. Designed using Swing.
 * 
 * @author Dominik Halfkann
 * @since 1.6
 */
public class DefaultGUI implements GUI {

	// Some variables
	private String title;
	private Main main;
	private Main windowClosingHandler = null;
	private MenuHandler handler = null;
	private Options options = null;

	// Swing object variables
	private JFrame mainFrame = null;
	private JPanel mainContent = null;
	private JMenuBar menuBar = null;
	private RTextScrollPane fileContentScroll = null;
	private RSyntaxTextArea fileContentArea = null;
	private ArrayList<JMenu> menuList = new ArrayList<JMenu>();

	public DefaultGUI(Main main, Options options) {
		this.main = main;
		this.options = options;
	}
	
	@Override
	public void init(String title) {
		if (mainFrame == null) {
			mainFrame = new JFrame();
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setSize(options.getLastWidth(), options.getLastHeight());
			mainFrame.setLocation(options.getLastLocation());
			mainFrame.setTitle(title);
			this.title = title;

			mainFrame.setJMenuBar(getMenuBar());
			mainFrame.setContentPane(getMainContent());
		}
	}

	private JPanel getMainContent() {
		if (mainContent == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(2);
			mainContent = new JPanel();
			mainContent.setLayout(borderLayout);
			mainContent.add(getFileContentScroll(), BorderLayout.CENTER);
			/*
			 * mainContent.add(getStatusBar(), BorderLayout.SOUTH);
			 * mainContent.add(getToolBar(), BorderLayout.NORTH);
			 */
		}
		return mainContent;
	}

	/**
	 * This method initializes fileContentScroll
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private RTextScrollPane getFileContentScroll() {
		if (fileContentScroll == null) {
			fileContentScroll = new RTextScrollPane();
			fileContentScroll.setViewportView(getFileContentArea());
			fileContentScroll.setLineNumbersEnabled(true);
		}
		return fileContentScroll;
	}

	/**
	 * This method initializes fileContentArea
	 * 
	 * @return org.fife.ui.rsyntaxtextarea.RSyntaxTextArea
	 */
	private RSyntaxTextArea getFileContentArea() {
		if (fileContentArea == null) {
			fileContentArea = new RSyntaxTextArea();
			/*
			 * Note: At this point, I set my syntax highlighter for osu files.
			 * It was created with the TokenTokenMaker, so it's not that
			 * perfect.
			 */
			fileContentArea
					.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_OSU);

		}
		return fileContentArea;
	}

	private JMenuBar getMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();

		}
		return menuBar;
	}

	@Override
	public void start() {
		mainFrame.setVisible(true);

	}

	@Override
	public void onClose(Main handler) {
		windowClosingHandler = handler;
		mainFrame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				windowClosingHandler.onGUIClosed();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return mainFrame.getHeight();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return mainFrame.getWidth();
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return mainFrame.getLocation();
	}

	@Override
	public void setMenuHandler(MenuHandler handler) {
		// TODO Auto-generated method stub
		this.handler = handler;
	}

	@Override
	public void createMenu(String title) {
		JMenu jmenu = new JMenu(title);
		menuBar.add(jmenu);
		menuList.add(jmenu);
	}

	@Override
	public void createMenuItem(String title, int id, final Action action) {
		JMenuItem jmenuitem = new JMenuItem(title);
		final GUI gui = this;
		jmenuitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handler.doAction(action, gui);
			}
		});
		menuList.get(id).add(jmenuitem);

	}

	@Override
	public RSyntaxTextArea getContentArea() {
		return fileContentArea;
	}

	@Override
	public File showFileChooser(String title) {
		System.out.print("clicked");
		JFileChooser fc = new JFileChooser(options.getLastPath());
		fc.setFileFilter(new OsuFileFilter());
		fc.setPreferredSize(new Dimension(640,480));
		AbstractButton button = SwingUtils.getDescendantOfType(
				AbstractButton.class, fc, "Icon",
				UIManager.getIcon("FileChooser.detailsViewIcon"));
		button.doClick();
		// fc.setApproveButtonText(title);

		if (fc.showDialog(mainFrame, title) == JFileChooser.APPROVE_OPTION) {
			options.setLastPath(fc.getSelectedFile().getParentFile().getAbsolutePath());
			return fc.getSelectedFile();
		}

		return null;
	}

}
