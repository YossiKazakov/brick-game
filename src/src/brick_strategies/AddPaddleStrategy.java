package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    private static final float MOCK_PADDLE_WIDTH = 100;
    private static final float MOCK_PADDLE_HEIGHT = 15;
    private static final int MIN_DIST_FROM_EDGE = 12;
    private static final int NUM_COLLISIONS_TO_DISAPPEAR = 3;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final ImageRenderable mockPaddleImage;

    /**
     * Constructor. Creates a strategy which upon collision of a brick with a ball, creates another paddle
     * in the middle of the screen.
     *
     * @param toBeDecorated    The strategy to be 'decorated' (to be added on)
     * @param imageReader
     * @param inputListener
     * @param windowDimensions
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             danogl.gui.ImageReader imageReader,
                             danogl.gui.UserInputListener inputListener,
                             danogl.util.Vector2 windowDimensions) {
        super(toBeDecorated);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.mockPaddleImage = imageReader.readImage("assets/paddle.png", true);

    }

    /**
     * On a collision of a brick with a ball, creates a strategy which upon collision of a brick with a ball,
     * creates another paddle
     * in the middle of the screen.
     *
     * @param thisObj      A brick
     * @param otherObj     A ball
     * @param brickCounter Number of bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        if (!MockPaddle.isInstantiated) {
            MockPaddle mockPaddle = new MockPaddle(Vector2.ZERO,
                    new Vector2(MOCK_PADDLE_WIDTH, MOCK_PADDLE_HEIGHT),
                    mockPaddleImage, inputListener, windowDimensions,
                    this.getGameObjectCollection(), MIN_DIST_FROM_EDGE, NUM_COLLISIONS_TO_DISAPPEAR);
            mockPaddle.setCenter(windowDimensions.mult(0.5F));
            this.getGameObjectCollection().addGameObject(mockPaddle);
        }
        super.onCollision(thisObj, otherObj, brickCounter);
    }

}
