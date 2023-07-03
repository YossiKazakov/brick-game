package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.BallCollisionCountdownAgent;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private static final int COUNT_DOWN_VALUE = 4;

    /**
     * Constructor. Creates a strategy which upon a collision of a brick with -the- ball, sets the camera to
     * follow the ball, for a given amount of collisions until it resets back
     * @param toBeDecorated The strategy to be 'decorated' (to be added on)
     * @param windowController
     * @param gameManager
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                 danogl.gui.WindowController windowController,
                                 BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * On a collision of a brick with -the- ball, sets the camera to
     * follow the ball, for a given amount of collisions until it resets back
     * @param thisObj      A brick
     * @param otherObj     A ball
     * @param brickCounter Number of bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        if (gameManager.getCamera() == null && BrickerGameManager.ball == otherObj)
        {
            int currCollisions = BrickerGameManager.ball.getCollisionCount();
            BallCollisionCountdownAgent ballCollisionCountdownAgent =
                    new BallCollisionCountdownAgent(BrickerGameManager.ball,
                    this, currCollisions + COUNT_DOWN_VALUE);
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);
            gameManager.setCamera(
                    new Camera(BrickerGameManager.ball,		//object to follow
                            Vector2.ZERO, 	//follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions()   //share the window dimensions
                    )
            );
        }
        super.onCollision(thisObj, otherObj, brickCounter);
    }

    /**
     * Resets the camera
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);
    }

}
