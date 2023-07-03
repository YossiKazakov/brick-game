package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.BrickerGameManager;


import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private final float minDistanceFromEdge;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance: a paddle
     * @param topLeftCorner    Position of the object,
     *                         in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner
     *                         of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object.
     *                         Can be null, in which case
     * @param inputListener    gets what the user push
     * @param windowDimensions a vector of 2 elements of the size of the window
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener,
                  Vector2 windowDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = (float) minDistanceFromEdge;
    }

    /**
     * This function updates the paddle object accordingly to the user's movement
     * @param deltaTime time between calls
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDirection = Vector2.ZERO;

//        if (BrickerGameManager.ball.getCenter().x() >= this.getCenter().x()) {
//            movementDirection = movementDirection.add(Vector2.RIGHT);
//        }
//        if (BrickerGameManager.ball.getCenter().x() < this.getCenter().x()){
//            movementDirection = movementDirection.add(Vector2.LEFT);
//        }
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDirection = movementDirection.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));
        if (getTopLeftCorner().x() < minDistanceFromEdge) {
            transform().setTopLeftCornerX(minDistanceFromEdge);
        }
        if (getTopLeftCorner().x() > windowDimensions.x() - minDistanceFromEdge - getDimensions().x()) {
            transform().setTopLeftCornerX(windowDimensions.x() - minDistanceFromEdge - getDimensions().x());
        }
    }
}
