package pacman.entities;

import pacman.Handler;
import pacman.tiles.Tile;

/**
 * Creature class.
 * 
 * Pacman and Ghosts are going to be our creatures (Player and Ghost class will extend
 * this class). Here we implemented some methods that mostly regard the creature movement
 * and will be used by both Pacman and Ghosts.
 * 
 * @author uross
 */

public abstract class Creature extends Entity {

    public static enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected Direction currentDirection = Direction.RIGHT, nextDirection = null;

    protected float speed = 2.0f;
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        xMove = 0;
        yMove = 0;
    }

    public void move() {
        if (!canMove()) {
            currentDirection = nextDirection;
            nextDirection = null;
        } else {
            x += xMove;
            y += yMove;
            
            if (currentDirection == Direction.UP || currentDirection == Direction.DOWN) {
                x = getXTile() * Tile.TILE_WIDTH;
            } else {
                y = getYTile() * Tile.TILE_HEIGHT;
            }
        }
    }

    public boolean canMove() {
        if (currentDirection == null) {
            return false;
        }
        switch (currentDirection) {
            case UP:
                if (collisionWithTile(getXTile(), getYTile() - 1)) {
                    if (y + yMove >= getYTile() * Tile.TILE_HEIGHT) {
                        return true;
                    } else {
                        y = getYTile() * Tile.TILE_HEIGHT;
                    }
                    return false;
                }
                break;
            case DOWN:
                if (collisionWithTile(getXTile(), getYTile() + 1)) {
                    if (y + yMove <= getYTile() * Tile.TILE_HEIGHT) {
                        return true;
                    } else {
                        y = getYTile() * Tile.TILE_HEIGHT;
                    }
                    return false;
                }
                break;
            case LEFT:
                if (collisionWithTile(getXTile() - 1, getYTile())) {
                    if (x + xMove >= getXTile() * Tile.TILE_WIDTH) {
                        return true;
                    } else {
                        x = getXTile() * Tile.TILE_WIDTH;
                    }
                    return false;
                }
                break;
            case RIGHT:
                if (collisionWithTile(getXTile() + 1, getYTile())) {
                    if (x + xMove <= getXTile() * Tile.TILE_WIDTH) {
                        return true;
                    } else {
                        x = getXTile() * Tile.TILE_WIDTH;
                    }
                    return false;
                }
                break;
        }
        return true;
    }
    
    protected void changeDirection() {
        if (nextDirection == null) {
            return;
        }
        switch (nextDirection) {
            case UP:
                if (!collisionWithTile(getXTile(), getYTile() - 1)) {
                    switch (currentDirection) {
                        case RIGHT:
                            if (getXTile() * Tile.TILE_WIDTH - x > speed) {
                                break;
                            }
                        case LEFT:
                            if (x - getXTile() * Tile.TILE_WIDTH > speed) {
                                break;
                            }
                        default:
                            currentDirection = Direction.UP;
                            nextDirection = null;
                            break;
                    }
                }
                break;
            case DOWN:
                if (!collisionWithTile(getXTile(), getYTile() + 1)) {
                    switch (currentDirection) {
                        case RIGHT:
                            if (getXTile() * Tile.TILE_WIDTH - x > speed) {
                                break;
                            }
                        case LEFT:
                            if (x - getXTile() * Tile.TILE_WIDTH > speed) {
                                break;
                            }
                        default:
                            currentDirection = Direction.DOWN;
                            nextDirection = null;
                            break;
                    }
                }
                break;
            case LEFT:
                if (!collisionWithTile(getXTile() - 1, getYTile())) {
                    switch (currentDirection) {
                        case UP:
                            if (y - getYTile() * Tile.TILE_HEIGHT > speed) {
                                break;
                            }
                        case DOWN:
                            if (getYTile() * Tile.TILE_HEIGHT - y > speed) {
                                break;
                            }
                        default:
                            currentDirection = Direction.LEFT;
                            nextDirection = null;
                            break;
                    }
                }
                break;
            case RIGHT:
                if (!collisionWithTile(getXTile() + 1, getYTile())) {
                    switch (currentDirection) {
                        case UP:
                            if (y - getYTile() * Tile.TILE_HEIGHT > speed) {
                                break;
                            }
                        case DOWN:
                            if (getYTile() * Tile.TILE_HEIGHT - y > speed) {
                                break;
                            }
                        default:
                            currentDirection = Direction.RIGHT;
                            nextDirection = null;
                            break;
                    }
                }
                break;
        }
    }

    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    protected void checkBounds() {
        if (getYTile() != 14) {
            return;
        }
        if (x > -Tile.TILE_WIDTH && x < handler.getWidth()) {
            return;
        }
        if (x <= -Tile.TILE_WIDTH) {
            x = handler.getWidth();
            return;
        }
        if (x >= handler.getWidth()) {
            x = -Tile.TILE_WIDTH;
            return;
        }
    }
    
    protected void setMoves() {
        if (currentDirection == null) {
            return;
        }
        switch (currentDirection) {
            case UP:
                xMove = 0;
                yMove = -speed;
                break;
            case DOWN:
                xMove = 0;
                yMove = speed;
                break;
            case LEFT:
                xMove = -speed;
                yMove = 0;
                break;
            case RIGHT:
                xMove = speed;
                yMove = 0;
                break;
        }
    }

    // Getters and Setters
    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
