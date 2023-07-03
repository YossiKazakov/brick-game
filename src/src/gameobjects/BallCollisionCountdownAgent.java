package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject {
    private final Ball ball;
    private final ChangeCameraStrategy owner;
    private final int countDownValue;

    /**
     * An invisible object of the game that is responsible for counting X collisions of a ball
     * with a brick when the ball is on CameraStrategy mode.
     * After X collisions, it resets the camera.
     */
    public BallCollisionCountdownAgent(Ball ball,
                                       ChangeCameraStrategy owner,
                                       int countDownValue) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
    }

    /**
     * Overrides parent's update function. Checks for X collision, then turns off the camera effect.
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        if (ball.getCollisionCount() == countDownValue) {
            owner.turnOffCameraChange();
        }
        super.update(deltaTime);
    }
}
