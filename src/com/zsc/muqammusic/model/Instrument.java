package com.zsc.muqammusic.model;

public class Instrument {

	private String name;
	private String introduce;
	private int images[];
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public int[] getImages() {
		return images;
	}
	public void setImages(int[] images) {
		this.images = images;
	}
	
}
