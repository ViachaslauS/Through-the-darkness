package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RPG  extends Game{
	
	public SpriteBatch batch;
	public BitmapFont font;    //font

	public static int WINDOW_WIDTH = 1280;
	public static int WINDOW_HEIGHT = 720;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("menu_font.fnt"),Gdx.files.internal("menu_font.png"),false);
		this.setScreen(new LevelLoading(this,new MainMenuScreen(this)));
	}
	@Override
	public void render() {
		super.render();
	}
	
	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}
}
