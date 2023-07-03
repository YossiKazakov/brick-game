package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.CollisionStrategy;

public class StatusDefiner extends GameObject {
    private final Sound collisionSound;
    private CollisionStrategy collisionStrategy;
    private final float STATUS_DEFINER_SPEED = 300;

    /**
     * Constructor of the object. This object falls from a brick (which has the StatusDefinerStrategy)
     * on a collision with a ball. If it collides with a pedal, it is widening\narrowing it.
     * @param topLeftCorner Position of the object,
     *                      in window coordinates (pixels).
     *                      Note that (0,0) is the
     *                      top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     *                      Can be null, in which case
     * @param collisionStrategy Either to widen or to narrow the paddle
     * @param sound A sound that is play on a collision
     */
    public StatusDefiner(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                                    CollisionStrategy collisionStrategy, Sound sound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = sound;
        this.collisionStrategy = collisionStrategy;
        this.setVelocity(Vector2.DOWN.mult(STATUS_DEFINER_SPEED));
    }

    /**
     * Return true if the other object in the collision is a paddle
     * @param other object of the collision
     * @return boolean
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }

    /**
     * Calls OnCollision function of the chosen strategy, and plays the sound of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        collisionStrategy.onCollision(this, other, null);
        collisionSound.play();
        super.onCollisionEnter(other, collision);
    }
}
