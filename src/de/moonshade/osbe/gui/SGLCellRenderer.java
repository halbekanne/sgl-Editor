/*
 * 01/07/2009
 *
 * CCellRenderer.java - A cell renderer for C completions.
 * Copyright (C) 2008 Robert Futrell
 * robert_futrell at users.sourceforge.net
 * http://fifesoft.com/rsyntaxtextarea
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA.
 */
package de.moonshade.osbe.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.VariableCompletion;

/**
 * The cell renderer used for the SGL programming language.
 * 
 * @author Robert Futrell
 * @author Dominik Halfkann
 * @version 1.0
 */
class SGLCellRenderer extends CompletionCellRenderer {

	private Icon variableIcon;
	private Icon functionIcon;
	private Icon constructorIcon;
	private Icon emptyIcon;

	/**
	 * Constructor.
	 */
	public SGLCellRenderer() {
		variableIcon = getIcon("icons/var.png");
		functionIcon = getIcon("icons/function.png");
		constructorIcon = getIcon("icons/class.gif");
		emptyIcon = new EmptyIcon(16);
	}

	/**
	 * Returns an icon.
	 * 
	 * @param resource
	 *            The icon to retrieve. This should either be a file, or a
	 *            resource loadable by the current ClassLoader.
	 * @return The icon.
	 */
	private Icon getIcon(String resource) {
		ClassLoader cl = getClass().getClassLoader();
		URL url = cl.getResource(resource);
		if (url == null) {
			File file = new File(resource);
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException mue) {
				mue.printStackTrace(); // Never happens
			}
		}
		return url != null ? new ImageIcon(url) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void prepareForOtherCompletion(JList list, Completion c, int index, boolean selected,
			boolean hasFocus) {
		super.prepareForOtherCompletion(list, c, index, selected, hasFocus);
		setIcon(emptyIcon);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void prepareForVariableCompletion(JList list, VariableCompletion vc, int index,
			boolean selected, boolean hasFocus) {
		super.prepareForVariableCompletion(list, vc, index, selected, hasFocus);
		setIcon(variableIcon);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void prepareForFunctionCompletion(JList list, FunctionCompletion fc, int index,
			boolean selected, boolean hasFocus) {
		super.prepareForFunctionCompletion(list, fc, index, selected, hasFocus);
		if (fc.getName().equals("Sprite")) {
			setIcon(constructorIcon);
		} else {
			setIcon(functionIcon);
		}
	}

	/**
	 * An standard icon that doesn't paint anything. This can be used to take up
	 * an icon's space when no icon is specified.
	 * 
	 * @author Robert Futrell
	 * @version 1.0
	 */
	private static class EmptyIcon implements Icon, Serializable {

		private int size;

		public EmptyIcon(int size) {
			this.size = size;
		}

		public int getIconHeight() {
			return size;
		}

		public int getIconWidth() {
			return size;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
		}

	}

}