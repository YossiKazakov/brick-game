package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    private CollisionStrategy collisionStrategy;
    private Counter brickCounter;

    /**
     * Constructor of the object that create a new brick in the game
     *
     * @param topLeftCorner,     Vector of 2 values of the top left corner
     * @param dimensions,        Vector of 2 values of the dimension of the object
     * @param renderable,        the render of the object, a brick image
     * @param collisionStrategy, an object the responsible on the collision
     *                           strategy
     * @param brickCounter,      number of bricks currently in the game
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter brickCounter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.brickCounter = brickCounter;
        brickCounter.increment();
    }

    /**
     * This function responsible on the collision between 2 objects, calls to
     * the collisionStrategy
     * @param other     the other object that this object collied with
     * @param collision a collision object in DanoGameLab
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, brickCounter);
    }
}
