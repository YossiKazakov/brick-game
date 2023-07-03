package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.Sound;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.StatusDefiner;

import java.util.Random;

public class StatusDefinerStrategy extends RemoveBrickStrategyDecorator {

    private final ImageRenderable buffNarrow;
    private final float NARROWING_FACTOR = 0.8F;
    private final float WIDDENING_FACTOR = 1.2F;
    private final ImageRenderable buffWiden;
    private final Sound sound;

    /**
     * Constructor of the strategy
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader
     * @param soundReader
     */
    public StatusDefinerStrategy(CollisionStrategy toBeDecorated,
                                 danogl.gui.ImageReader imageReader, danogl.gui.SoundReader soundReader) {
        super(toBeDecorated);
        this.buffNarrow = imageReader.readImage("assets/buffNarrow.png", false);
        this.buffWiden = imageReader.readImage("assets/buffWiden.png", false);
        this.sound = soundReader.readSound("assets/Bubble5_4.wav");
    }

    /**
     * Creates the StatusDefiner object when the brick breaks. Chooses randomly between narrowing and widening
     * @param thisObj The brick
     * @param otherObj The ball
     * @param brickCounter Responsibly for counting the bricks left
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        super.onCollision(thisObj, otherObj, brickCounter);
        Random random = new Random();
        if (random.nextBoolean()) {
            StatusDefiner widenBuffer = new StatusDefiner(Vector2.ZERO,
                    thisObj.getDimensions(),
                    buffWiden, new TransformPaddle(getGameObjectCollection(), WIDDENING_FACTOR), sound);
            widenBuffer.setCenter(thisObj.getCenter());
            getGameObjectCollection().addGameObject(widenBuffer);
        } else {
            StatusDefiner narrowBuffer = new StatusDefiner(Vector2.ZERO,
                    thisObj.getDimensions(),
                    buffNarrow, new TransformPaddle(getGameObjectCollection(), NARROWING_FACTOR), sound);
            narrowBuffer.setCenter(thisObj.getCenter());
            getGameObjectCollection().addGameObject(narrowBuffer);
        }
    }
}
