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

package de.moonshade.osbe.oop;

import de.moonshade.osbe.oop.exception.GeneratorException;

public class SpriteVariable implements Variable {

	private String name;
	private String path;
	private String layer;
	private String origin;
	// Da wir evt. viele Zeilen Code hinzufügen wollen, ist ein StringBuilder performanter
	private StringBuilder storyboard = new StringBuilder();

	private int x = 320;
	private int y = 240;
	private float opacity = 1;
	private float scaleX = 1;
	private float scaleY = 1;
	private float rotation = 0;
	private int red = 255;
	private int green = 255;
	private int blue = 255;
	private boolean loop = false;
	private int loopTime = 0;
	

	public SpriteVariable(String name, String path) {
		this.name = name;
		this.path = path;
		this.layer = "Foreground";
		this.origin = "Centre";
	}

	public SpriteVariable(String name, String path, String layer) {
		this.name = name;
		this.path = path;
		this.layer = layer;
		this.origin = "Centre";
	}

	public SpriteVariable(String name, String path, String layer, String origin) {
		this.name = name;
		this.path = path;
		this.layer = layer;
		this.origin = origin;
	}

	public void addStoryboard(String storyboard) {
		if (loop) {
			this.storyboard.append(" " + storyboard + "\n");
		} else {
			this.storyboard.append(storyboard + "\n");
		}
	}

	/**
	 * @return the layer
	 */
	public String getLayer() {
		return layer;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * @return the opacity
	 */
	public float getOpacity() {
		return opacity;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the rotation
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @return the scale
	 */
	public float getScaleX() {
		return scaleX;
	}
	
	public float getScaleY() {
		return scaleY;
	}

	public StringBuilder getStoryboard() {
		return storyboard;
	}

	@Override
	public String getStringValue() throws GeneratorException {
		// TODO Auto-generated method stub
		throw new GeneratorException(null, -1,
				"The variable relates to a sprite, it's impossible to parse it in this expression");
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param layer
	 *            the layer to set
	 */
	public void setLayer(String layer) {
		this.layer = layer;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	/**
	 * @param opacity
	 *            the opacity to set
	 */
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param rotation
	 *            the rotation to set
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * @param scale
	 *            the scale to set
	 */
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}
	
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	/**
	 * @param storyboard
	 *            the storyboard to set
	 */
	public void setStoryboard(StringBuilder storyboard) {
		this.storyboard = storyboard;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	public boolean getLoop() {
		return loop;
	}

	public void setLoopTime(int loopTime) {
		this.loopTime = loopTime;
	}

	public int getLoopTime() {
		return loopTime;
	}

}
