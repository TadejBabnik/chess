package com.game;

import javafx.scene.image.Image;

public class Helper {
    public static Image loadImage(String resource, int width, int height) {
		return new Image(Helper.class.getResourceAsStream(resource), width, height, true, true);
	}
}
