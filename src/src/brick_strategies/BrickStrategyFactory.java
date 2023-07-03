package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

public class BrickStrategyFactory {
    enum Strategies {CHANGE_CAMERA, ADD_PADDLE, STATUS_DEFINER, PUCK, DEFAULT, DUAL}

    private static final int NUM_OF_DUALS_ALLOWED = 3;
    private Counter dualsCalled = new Counter();
    private final GameObjectCollection gameObjects;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    int num_strategies = Strategies.values().length;
    private boolean flag = false;

    /**
     * Constructor. Creates the factory and initializes the necessary fields which are needed for
     * creating a given strategy
     *
     * @param gameObjectCollection
     * @param gameManager
     * @param imageReader
     * @param soundReader
     * @param inputListener
     * @param windowController
     * @param windowDimensions
     */
    public BrickStrategyFactory(danogl.collisions.GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager, danogl.gui.ImageReader imageReader,
                                danogl.gui.SoundReader soundReader,
                                danogl.gui.UserInputListener inputListener,
                                danogl.gui.WindowController windowController,
                                danogl.util.Vector2 windowDimensions) {
        this.gameObjects = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    /**
     * @return A randomly picked collision strategy. If a double-strategy is picked, then neither of them can be
     * the default one.
     */
    public CollisionStrategy getStrategy() {
        Random random = new Random();
        int random_index;
        if (flag) {
            random_index = (int) ((Math.random() * (2 - 7)) + 2);
        } else {
            random_index = random.nextInt(num_strategies - 1); // Exclude "DEFAULT"
        }
        Strategies strategy = Strategies.values()[random_index];
        RemoveBrickStrategy defaultStrategy = new RemoveBrickStrategy(gameObjects);
        switch (strategy) {
            case CHANGE_CAMERA:
                return new ChangeCameraStrategy(defaultStrategy, windowController, gameManager);
            case ADD_PADDLE:
                return new AddPaddleStrategy(defaultStrategy, imageReader, inputListener, windowDimensions);
            case PUCK:
                return new PuckStrategy(defaultStrategy, imageReader, soundReader);
            case STATUS_DEFINER:
                return new StatusDefinerStrategy(defaultStrategy, imageReader, soundReader);
            case DUAL: {
                if (dualsCalled.value() >= NUM_OF_DUALS_ALLOWED) {
                    this.dualsCalled.reset();
                    return defaultStrategy;
                }
                flag = true;
                this.dualsCalled.increment();
                CollisionStrategy collisionStrategy = new DoubleBehaviour(getStrategy(), getStrategy());
                flag = false;
                return collisionStrategy;
            }
            default:
                return defaultStrategy;
        }
    }
}
