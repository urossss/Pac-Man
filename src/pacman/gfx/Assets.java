package pacman.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    private static final int WIDTH = 8, HEIGHT = 8;

    private static final int DIGIT_SIZE = 9, LETTER_SIZE = 9, GHOST_SIZE = 16, PLAYER_SIZE = 16;

    public static BufferedImage title, player;
    public static BufferedImage[] digits, letters;
    public static BufferedImage[] ghost_red, ghost_pink, ghost_blue, ghost_orange,
            ghost_scared, ghost_eaten;
    public static BufferedImage[] player_eaten;
    public static BufferedImage world1, world2, food, powerFood;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));

        title = sheet.crop(0, 0, 182, 46);
        
        letters = loadArray(sheet, 0, 293, 26, LETTER_SIZE);
        digits = loadArray(sheet, 0, 302, 10, DIGIT_SIZE);
        
        ghost_red = loadArray(sheet, 0, 124, 8, GHOST_SIZE);
        ghost_pink = loadArray(sheet, 0, 124 + GHOST_SIZE, 8, GHOST_SIZE);
        ghost_blue = loadArray(sheet, 0, 124 + GHOST_SIZE * 2, 8, GHOST_SIZE);
        ghost_orange = loadArray(sheet, 0, 124 + GHOST_SIZE * 3, 8, GHOST_SIZE);
        ghost_scared = loadArray(sheet, 0, 124 + GHOST_SIZE * 4, 4, GHOST_SIZE);
        ghost_eaten = loadArray(sheet, GHOST_SIZE * 4, 124 + GHOST_SIZE * 4, 4, GHOST_SIZE);
        
        player_eaten = loadArray(sheet, 0, 258, 14, PLAYER_SIZE);
        
        world1 = sheet.crop(202, 4, 164, 212);
        world2 = sheet.crop(370, 4, 164, 212);
        
        food = sheet.crop(0, 78, 8, 8);
        powerFood = sheet.crop(8, 78, 9, 9);
        
        //player = sheet.crop()
    }
    
    private static BufferedImage[] loadArray(SpriteSheet sheet, int x, int y, int n, int SIZE) {
        BufferedImage[] arr = new BufferedImage[n];
        
        for (int i = 0; i < n; i++) {
            arr[i] = sheet.crop(x + i * SIZE, y, SIZE, SIZE);
        }
        
        return arr;
    }

}