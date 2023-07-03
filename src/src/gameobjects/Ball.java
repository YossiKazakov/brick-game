package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private Sound collisionSound;
    private Counter collisionCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object,
     *                      in window coordinates (pixels).
     *                      Note that (0,0) is the
     *                      top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     *                      Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionCounter = new Counter();
        this.collisionSound = collisionSound;
    }

    /**
     * This function responsible on the sound and the change in speed of
     * the ball on a collision
     *
     * @param other     the other object that this object collied with
     * @param collision a collision object in DanoGameLab
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter.increment();
    }

    /**
     * @return The number of collision the ball has made with objects during the game
     */
    public int getCollisionCount() {
        return collisionCounter.value();
    }
}
