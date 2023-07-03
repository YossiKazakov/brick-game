package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import danogl.util.Vector2;


public class TransformPaddle implements CollisionStrategy {

    private final GameObjectCollection gameObjects;
    private final float resizingFactor;

    /**
     * Constructor. Creates the strategy of a status definer object that changes the size of a paddle
     * @param gameObjects
     * @param resizingFactor
     */
    public TransformPaddle(GameObjectCollection gameObjects, float resizingFactor) {
        this.gameObjects = gameObjects;
        this.resizingFactor = resizingFactor;
    }

    /**
     * On a collision with a paddle, it resizing it. Does nothing if the other object is not a paddle
     * @param thisObj The status definer
     * @param otherObj The other object of the collision
     * @param brickCounter Does not change
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        if (thisObj.shouldCollideWith(otherObj)) {
            if (otherObj.getDimensions().x() < 700) {
                otherObj.setDimensions(new Vector2(otherObj.getDimensions().x() * resizingFactor,
                        otherObj.getDimensions().y()));
            }
        }
        gameObjects.removeGameObject(thisObj);
    }

    /**
     * @return The object collection of the game
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjects;
    }
}
