package de.moonshade.osbe.oop;

import de.moonshade.osbe.oop.exception.GeneratorException;

public class SpriteVariable implements Variable {

	private String name;
	private String path;
	private String layer;
	private String origin;
	private String storyboard = "";
	
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
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the layer
	 */
	public String getLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(String layer) {
		this.layer = layer;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Override
	public String getStringValue() throws GeneratorException {
		// TODO Auto-generated method stub
		throw new GeneratorException(null,-1,"The variable relates to a sprite, it's impossible to parse it in this expression");
	}

	public void addStoryboard(String storyboard) {
		this.storyboard = this.storyboard + storyboard + "\n";
	}

	public String getStoryboard() {
		return storyboard;
	}

}
