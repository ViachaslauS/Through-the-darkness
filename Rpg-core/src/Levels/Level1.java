package Levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level1 {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public Level1() {
		
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("template.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public void render(OrthographicCamera camera) {
		renderer.setView(camera);
		renderer.render();
	}
}
