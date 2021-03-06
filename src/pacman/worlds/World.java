package pacman.worlds;

import java.awt.Graphics;

import pacman.Handler;
import pacman.entities.EntityManager;
import pacman.gfx.Assets;
import pacman.tiles.Tile;
import pacman.utils.Utils;

/**
 * World. This is a map for our game.
 * 
 * The map is loaded to a 2d matrix here which is later used to check whether a tile
 * is eatable or solid etc.
 * 
 * We keep track of the food left so we can know when the level is completed.
 * 
 * lockCage() and unlockCage() methods are used in the beginning of the game to let
 * the ghosts out of the cage one by one.
 * 
 * @author uross
 */

public class World {

    private Handler handler;
    private int width, height;
    private int[][] tiles;

    private int foodLeft = 0;

    // Entities
    private EntityManager entityManager;

    public World(Handler handler, String path) {
        this.handler = handler;

        createEntityManager();

        loadWorld(path);
    }

    public void tick() {
        entityManager.tick();
    }

    public void render(Graphics g, boolean e) {
        // Background:
        g.drawImage(Assets.world1, 0, 0, width * Tile.TILE_WIDTH, height * Tile.TILE_HEIGHT, null);

        // Board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                getTile(x, y).render(g, x * Tile.TILE_WIDTH, y * Tile.TILE_HEIGHT);
            }
        }

        // Entities (player and ghosts)
        if (e) {
            entityManager.render(g);
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            if (y == 14) {
                return Tile.emptyTile;
            }
            return Tile.wallTile;
        }

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null) {
            return Tile.emptyTile;
        }
        return t;
    }
    
    public int eatTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return 0;
        }

        int score = Tile.tiles[tiles[x][y]].getScore();

        tiles[x][y] = 0;    // empty tile
        foodLeft--;

        return score;
    }
    
    public void unlockCage() {
        for (int x = 12; x < 16; x++) {
            tiles[x][12] = 0;
        }
    }
    
    public void lockCage() {
        for (int x = 10; x < 18; x++) {
            tiles[x][12] = 1;
        }
    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);

        foodLeft = 0;

        tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Utils.parseInt(tokens[2 + (y * width + x)]);
                if (Tile.tiles[tiles[x][y]].isEatable()) {
                    foodLeft++;
                }
            }
        }
    }

    public boolean isCompleted() {
        return foodLeft <= 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void createEntityManager() {
        entityManager = new EntityManager(handler);
        handler.setEntityManager(entityManager);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}
