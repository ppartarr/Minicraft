package ac.novel.common.level.tile;

import java.io.Serializable;

import ac.novel.common.entity.AirWizard;
import ac.novel.common.entity.Entity;
import ac.novel.common.gfx.Screen;
import ac.novel.common.level.Level;

public class InfiniteFallTile extends Tile implements Serializable {
	public InfiniteFallTile(int id) {
		super(id);
	}

	public void render(Screen screen, Level level, int x, int y) {
	}

	public void tick(Level level, int xt, int yt) {
	}

	public boolean mayPass(Level level, int x, int y, Entity e) {
		if (e instanceof AirWizard) return true;
		return false;
	}
}
