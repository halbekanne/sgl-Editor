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

package de.moonshade.osbe.gui;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * @author Dominik Halfkann
 * 
 */
public class OldGUI {

	/* Variables for the GUI */
	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private RTextScrollPane fileContentScroll = null;
	private RSyntaxTextArea fileContentArea = null;
	private JPanel StatusBar = null;
	private JLabel labelBPM = null;
	private JMenuItem openMenuItem = null;
	private JPanel toolBar = null;
	private JSlider toolTimeSetSlider = null;
	private JTextField toolTimeSetField = null;
	private JLabel labelms = null;
	private JButton toolNextButton = null;
	private JMenu optionsMenu = null;
	private JMenuItem bpmMenuItem = null;
	private JTextField toolMeasureSetField = null;
	private JLabel labelMeasure = null;
	private JDialog bpmDialog = null;
	private JPanel bpmContentPane = null;
	private JTextField bpmSetField = null;
	private JLabel labelSetBPM = null;
	private JButton bpmOkButton = null;
	private JLabel labeltickInfo = null;
	private JMenuItem undoMenuItem = null;
	private JMenuItem redoMenuItem = null;
	private JMenuItem saveAsMenuItem = null;
	private JLabel aboutCopyrightLabel = null;

	/* Other Variables */
	private float bpm = 100;
	private File currentFile = null; // @jve:decl-index=0:
	private String lastPath = System.getProperty("user.home");
	boolean saved = false;
	String openFile = null;

	public OldGUI() {

	}

	public OldGUI(String file) {
		// Cunstructor, if the Application should open a file on startup
		openFile = file;
	}

	// Here comes the swing-stuff, it's more or less unsorted cause it was made
	// by a wysiwyg sort of gui editor

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	public JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(800, 600);
			jFrame.setLocationByPlatform(true);
			jFrame.setContentPane(getJContentPane());

			jFrame.setTitle("osu! Storyboard Script Editor");
		}

		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(2);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.add(getFileContentScroll(), BorderLayout.CENTER);
			jContentPane.add(getStatusBar(), BorderLayout.SOUTH);
			jContentPane.add(getToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getOptionsMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getSaveAsMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getUndoMenuItem());
			editMenu.add(getRedoMenuItem());
			editMenu.addSeparator();
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(getJFrame().getWidth() / 2 - 20, getJFrame()
							.getHeight() / 2 - 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog
	 * 
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			GridLayout gridLayout1 = new GridLayout();
			gridLayout1.setRows(2);
			gridLayout1.setVgap(2);
			aboutCopyrightLabel = new JLabel();
			aboutCopyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
			aboutCopyrightLabel.setText("� by MoonShade (2011)");

			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(gridLayout1);
			aboutContentPane.add(getAboutVersionLabel(), null);
			aboutContentPane.add(aboutCopyrightLabel, null);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel
					.setText("    osu! Storyboard Script Editor v. 1.0.1    ");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
			cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fileContentArea.cut();
				}
			});
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
			copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fileContentArea.copy();
				}
			});
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
			pasteMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							fileContentArea.paste();
						}
					});
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (currentFile == null) {
						// Show save-dialog
						JFileChooser fc = new JFileChooser(lastPath);
						int rc = fc.showDialog(getJFrame(), "Save");
						if (rc == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							try {
								currentFile = file;
								lastPath = file.getParentFile()
										.getAbsolutePath();
								// Saves the file
								FileOutputStream fstream = new FileOutputStream(
										currentFile);
								fstream.write(fileContentArea.getText()
										.getBytes());
								fstream.close();
								jFrame.setTitle("osu! Storyboard Script Editor - "
										+ file.getName() + " - saved");
								// Unlocks the file
								file.setReadable(true);
								file.setWritable(true);
								saved = true;
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					} else {
						try {
							// Saves the file directly
							FileOutputStream fstream = new FileOutputStream(
									currentFile);
							fstream.write(fileContentArea.getText().getBytes());
							fstream.close();
							jFrame.setTitle("osu! Storyboard Script Editor - "
									+ currentFile.getName() + " - saved");
							// Unlocks the file
							currentFile.setReadable(true);
							currentFile.setWritable(true);
							saved = true;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			});
		}
		return saveMenuItem;
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

			// If the application should open a file on startup
			if (openFile != null) {
				FileReader fr = null;
				try {
					fr = new FileReader(openFile);
					fileContentArea.read(fr, null);
					File file = new File(openFile);
					currentFile = file;
					lastPath = file.getParentFile().getAbsolutePath();
					jFrame.setTitle("osu! Storyboard Script Editor - "
							+ file.getName());
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			fileContentArea.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyTyped(java.awt.event.KeyEvent e) {
					// Just to delete (saved) in the title, if changes were made
					if (saved) {
						jFrame.setTitle("osu! Storyboard Script Editor - "
								+ currentFile.getName());
						saved = false;
					}
				}
			});
		}

		return fileContentArea;

	}

	/**
	 * This method initializes StatusBar
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getStatusBar() {
		if (StatusBar == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			gridLayout.setHgap(10);
			gridLayout.setVgap(0);
			gridLayout.setColumns(5);
			labelBPM = new JLabel();
			labelBPM.setText("BPM: 100 (default)");
			StatusBar = new JPanel();
			StatusBar.setLayout(gridLayout);
			StatusBar.add(labelBPM, null);

		}
		return StatusBar;
	}

	/**
	 * This method initializes openMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					Event.CTRL_MASK, true));

			openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Show the open dialog
					JFileChooser fc = new JFileChooser(lastPath);
					int rc = fc.showDialog(getJFrame(), "Open");
					if (rc == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						lastPath = file.getParentFile().getAbsolutePath();
						try {
							// Read the file
							fileContentArea.read(new FileReader(file), null);
							currentFile = file;
							jFrame.setTitle("osu! Storyboard Script Editor - "
									+ file.getName());
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}
			});
		}
		return openMenuItem;
	}

	/**
	 * This method initializes toolBar
	 * 
	 * @return JPanel
	 */
	private JPanel getToolBar() {
		if (toolBar == null) {
			labeltickInfo = new JLabel();
			labeltickInfo.setText("");
			labelMeasure = new JLabel();
			labelMeasure.setText("measures");
			labelms = new JLabel();
			labelms.setText("ms / ");
			toolBar = new JPanel();
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));
			toolBar.add(getToolTimeSetSlider(), getToolTimeSetSlider()
					.getName());
			toolBar.add(getToolTimeSetField());
			toolBar.add(labelms);
			toolBar.add(getToolMeasureSetField(), null);
			toolBar.add(labelMeasure, null);
			toolBar.add(getToolNextButton());
			toolBar.add(labeltickInfo, null);

		}
		return toolBar;
	}

	/**
	 * This method initializes toolTimeSetSlider
	 * 
	 * @return javax.swing.JSlider
	 */
	private JSlider getToolTimeSetSlider() {
		if (toolTimeSetSlider == null) {
			toolTimeSetSlider = new JSlider(-10, 10);
			toolTimeSetSlider.setPreferredSize(new Dimension(300, 35));
			toolTimeSetSlider.setPaintTicks(true);
			toolTimeSetSlider.setMinorTickSpacing(1);
			toolTimeSetSlider.setSnapToTicks(true);

			toolTimeSetSlider
					.addChangeListener(new javax.swing.event.ChangeListener() {
						@Override
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							// If the slider changed, make some calculation and
							// show ms/measures
							float measure = getMeasureFromSlider(toolTimeSetSlider
									.getValue());
							toolMeasureSetField.setText(String.valueOf(measure));
							int ms = convertMeasuresToMs(measure);
							toolTimeSetField.setText(String.valueOf(ms));
						}

					});

		}
		return toolTimeSetSlider;
	}

	/**
	 * This method initializes toolTimeSetField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getToolTimeSetField() {
		if (toolTimeSetField == null) {
			toolTimeSetField = new JTextField();
			toolTimeSetField.setPreferredSize(new Dimension(80, 24));
			toolTimeSetField.setHorizontalAlignment(SwingConstants.RIGHT);
			toolTimeSetField.setText("0");
			toolTimeSetField.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyReleased(java.awt.event.KeyEvent e) {
					// If the ms-field content changed, make some calculation
					// and show measures
					int ms = Integer.parseInt(toolTimeSetField.getText());
					float measure = convertMsToMeasures(ms);
					DecimalFormat df = new DecimalFormat("0.####");
					toolMeasureSetField.setText(df.format(measure));
				}

			});
		}
		return toolTimeSetField;
	}

	/**
	 * This method initializes toolNextButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getToolNextButton() {
		if (toolNextButton == null) {
			toolNextButton = new JButton();
			toolNextButton.setText("Shift");
			toolNextButton.setToolTipText("Shift the marked Lines in Time");
			toolNextButton
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (fileContentArea.getSelectedText() != null) {
								// Shift marked lines on click
								shiftLines();
							}
						}

					});
		}
		return toolNextButton;
	}

	/**
	 * This method initializes optionsMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getOptionsMenu() {
		if (optionsMenu == null) {
			optionsMenu = new JMenu();
			optionsMenu.setText("Options");
			optionsMenu.add(getBpmMenuItem());
		}
		return optionsMenu;
	}

	/**
	 * This method initializes bpmMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getBpmMenuItem() {
		if (bpmMenuItem == null) {
			bpmMenuItem = new JMenuItem();
			bpmMenuItem.setText("Set Map's BPM");
			bpmMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					bpmDialog = getBpmDialog();
					bpmDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(getJFrame().getWidth() / 2 - 20, getJFrame()
							.getHeight() / 2 - 20);
					bpmDialog.setLocation(loc);
					bpmDialog.setVisible(true);
				}
			});
		}
		return bpmMenuItem;
	}

	/**
	 * This method initializes toolMeasureSetField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getToolMeasureSetField() {
		if (toolMeasureSetField == null) {
			toolMeasureSetField = new JTextField();
			toolMeasureSetField.setPreferredSize(new Dimension(60, 24));
			toolMeasureSetField.setHorizontalAlignment(SwingConstants.RIGHT);
			toolMeasureSetField.setText("0");
			toolMeasureSetField.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyReleased(java.awt.event.KeyEvent e) {
					// If the measure-field content changed, make some
					// calculation and show ms
					float measure = Float.parseFloat(toolMeasureSetField
							.getText());
					int ms = convertMeasuresToMs(measure);
					toolTimeSetField.setText(String.valueOf(ms));
				}

			});
		}
		return toolMeasureSetField;
	}

	/**
	 * This method initializes bpmDialog
	 * 
	 * @return javax.swing.JPanel
	 */
	private JDialog getBpmDialog() {
		if (bpmDialog == null) {
			bpmDialog = new JDialog(getJFrame(), true);
			bpmDialog.setTitle("Set Map's BPM");
			bpmDialog.setSize(200, 74);
			bpmDialog.setContentPane(getBpmContentPane());
		}
		return bpmDialog;
	}

	/**
	 * This method initializes bpmContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getBpmContentPane() {
		if (bpmContentPane == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(7);
			flowLayout.setVgap(6);
			labelSetBPM = new JLabel();
			labelSetBPM.setText("BPM");
			bpmContentPane = new JPanel();
			bpmContentPane.setLayout(flowLayout);
			bpmContentPane.add(getBpmSetField(), null);
			bpmContentPane.add(labelSetBPM, null);
			bpmContentPane.add(getBpmOkButton(), null);
		}
		return bpmContentPane;
	}

	/**
	 * This method initializes bpmSetField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBpmSetField() {
		if (bpmSetField == null) {
			bpmSetField = new JTextField();
			bpmSetField.setPreferredSize(new Dimension(60, 25));
			bpmSetField.setText(Float.toString(bpm));
			bpmSetField.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return bpmSetField;
	}

	/**
	 * This method initializes bpmOkButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBpmOkButton() {
		if (bpmOkButton == null) {
			bpmOkButton = new JButton();
			bpmOkButton.setText("Save");
			bpmOkButton.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						bpm = Float.parseFloat(bpmSetField.getText());
						labelBPM.setText("BPM: " + bpm);
						bpmDialog.dispose();
					} catch (NumberFormatException e2) {
						JOptionPane
								.showMessageDialog(
										null,
										"Number must be in Format XXX or XXX.XXX, e.g. 120 or 120.354",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return bpmOkButton;
	}

	/**
	 * This method initializes undoMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getUndoMenuItem() {
		if (undoMenuItem == null) {
			undoMenuItem = new JMenuItem();
			undoMenuItem.setText("Undo");
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					Event.CTRL_MASK, true));
			undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fileContentArea.undoLastAction();
				}
			});
		}
		return undoMenuItem;
	}

	/**
	 * This method initializes redoMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getRedoMenuItem() {
		if (redoMenuItem == null) {
			redoMenuItem = new JMenuItem();
			redoMenuItem.setText("Redo");
			redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
					Event.CTRL_MASK, true));
			redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fileContentArea.redoLastAction();
				}
			});
		}
		return redoMenuItem;
	}

	/**
	 * This method initializes saveAsMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText("Save As...");
			saveAsMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// Show save-dialog
							JFileChooser fc = new JFileChooser(lastPath);
							int rc = fc.showDialog(getJFrame(), "Save");
							if (rc == JFileChooser.APPROVE_OPTION) {
								File file = fc.getSelectedFile();
								lastPath = file.getParentFile()
										.getAbsolutePath();
								try {
									// Saves the file
									fileContentArea.write(new FileWriter(
											currentFile));
									currentFile = file;
									jFrame.setTitle("osu! Storyboard Script Editor - "
											+ file.getName() + " - saved");
									saved = true;
									// Unlocks the file
									file.setReadable(true);
									file.setWritable(true);
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							}

						}
					});

		}
		return saveAsMenuItem;
	}

	/**
	 * Launches this application
	 */
	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				OldGUI application;
				try {
					// If a file should be opened with this application
					application = new OldGUI(args[0]);
				} catch (Exception ex) {
					// Normal startup (I know this isn't the best way to do it
					// ;)
					application = new OldGUI();
				}

				application.getJFrame().setVisible(true);
			}
		});

	}

	/* Other Functions */

	/**
	 * Show the amount of ticks, returns the amount of measures
	 */
	private float getMeasureFromSlider(int slider) {
		labeltickInfo.setText("");
		switch (slider) {
		case 0:
			return 0;
		case 1:
			labeltickInfo.setText("Shift by 1 blue tick");
			return 0.0625f;
		case 2:
			labeltickInfo.setText("Shift by 1 red tick");
			return 0.125f;
		case 3:
			labeltickInfo.setText("Shift by 1 white tick");
			return 0.25f;
		case 4:
			labeltickInfo.setText("Shift by 1.5 white ticks");
			return 0.375f;
		case 5:
			labeltickInfo.setText("Shift by 2 white ticks");
			return 0.5f;
		case 6:
			labeltickInfo.setText("Shift by 3 white ticks");
			return 0.75f;
		case -1:
			labeltickInfo.setText("Shift by -1 blue tick");
			return -0.0625f;
		case -2:
			labeltickInfo.setText("Shift by -1 red tick");
			return -0.125f;
		case -3:
			labeltickInfo.setText("Shift by -1 white tick");
			return -0.25f;
		case -4:
			labeltickInfo.setText("Shift by -1.5 white ticks");
			return -0.375f;
		case -5:
			labeltickInfo.setText("Shift by -2 white ticks");
			return -0.5f;
		case -6:
			labeltickInfo.setText("Shift by -3 white ticks");
			return -0.75f;
		}

		if (slider >= 7) {
			labeltickInfo.setText("Shift by " + (slider - 6) * 4
					+ " white ticks");
			return slider - 6;
		}
		labeltickInfo.setText("Shift by " + (slider + 6) * 4 + " white ticks");
		return slider + 6;
	}

	/**
	 * Converts measures to milliseconds
	 * 
	 * @param measures
	 * @return ms
	 */
	private int convertMeasuresToMs(float measure) {
		double temp = 60000 * 4 * measure;
		return (int) Math.round(temp / bpm);
	}

	/**
	 * Converts milliseconds to measures
	 * 
	 * @param ms
	 * @return measures
	 */
	private float convertMsToMeasures(int ms) {
		// TODO Auto-generated method stub
		return ms * 1.0f * bpm / (60000 * 4);
	}

	private void shiftLines() {
		int ms = Integer.parseInt(toolTimeSetField.getText());
		String shiftedCommands = shiftCommands(ms);
		// fileContentArea.setText(newtext);
		int selstart = fileContentArea.getSelectionStart();
		int selend = fileContentArea.getSelectionEnd();
		fileContentArea.replaceRange(shiftedCommands,
				fileContentArea.getSelectionStart(),
				fileContentArea.getSelectionEnd());
		fileContentArea.select(selstart, selend);
	}

	private String shiftCommands(int ms) {
		String text = fileContentArea.getSelectedText();
		String newtext = "";

		String[] lines = text.split("\n");
		for (int a = 0; a < lines.length; a++) {
			// F�r jede Zeile:
			String[] parts = lines[a].split(",");
			boolean loop = false;
			boolean loopInner = false;
			if (parts[0].startsWith("  ") || parts[0].startsWith("__")) {
				loopInner = true;
			}
			for (int b = 0; b < parts.length; b++) {
				// F�r jedes Element
				boolean shift = false;
				if (!loopInner) {
					if (b == 0 && parts[b].endsWith("L")) {
						loop = true;

					} else if (b == 0 && parts[b].matches("[0-9]+")) {
						shift = true;
					} else if (b == 1 && loop) {
						shift = true;

					} else if ((b == 2 || b == 3) && !loop) {
						shift = true;
					}
				}
				if (shift) {
					try {
						parts[b] = String
								.valueOf((Integer.parseInt(parts[b]) + ms));
					} catch (Exception ex) {
						// not interesting
					}
				}
				newtext += parts[b];
				newtext += ",";
			}
			newtext = newtext.substring(0, newtext.length() - 1);
			newtext += "\n";
		}
		newtext = newtext.substring(0, newtext.length() - 1);
		return newtext;
	}

}
