package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

public class PuckStrategy extends RemoveBrickStrategyDecorator {
    private static final float PUCK_SPEED = 230;
    private static final int NUM_PUCK_BALLS = 3;
    private final ImageRenderable puckImage;
    private final Sound puckSound;

    /**
     * Constructor. Creates a strategy which upon collision of a brick with a ball, creates a given amount
     * of balls(pucks)
     * @param toBeDecorated The strategy to be 'decorated' (to be added on)
     * @param imageReader
     * @param soundReader
     */
    public PuckStrategy(CollisionStrategy toBeDecorated,
                        danogl.gui.ImageReader imageReader,
                        danogl.gui.SoundReader soundReader) {
        super(toBeDecorated);
        this.puckImage = imageReader.readImage("assets/mockBall.png", true);
        this.puckSound = soundReader.readSound("assets/blop_cut_silenced.wav");
    }

    /**
     * On a collision with a ball, creates 3 more balls(pucks) with random velocity
     * @param thisObj      A brick
     * @param otherObj     A ball
     * @param brickCounter Number of bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        super.onCollision(thisObj, otherObj, brickCounter);
        for (int i = 0; i < NUM_PUCK_BALLS; i++) {
            Puck puck = new Puck(Vector2.ZERO, Vector2.ONES.mult(20), puckImage, puckSound);
            puck.setCenter(thisObj.getCenter());
            float ballVelX = PUCK_SPEED;
            float ballVelY = PUCK_SPEED;
            Random rand = new Random();
            if (rand.nextBoolean()) {
                ballVelX *= -1;
            }
            if (rand.nextBoolean()) {
                ballVelY *= -1;
            }
            puck.setVelocity(new Vector2(ballVelX, ballVelY));
            this.getGameObjectCollection().addGameObject(puck);
        }

    }
}
