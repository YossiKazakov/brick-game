package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjects;

    /**
     * Constructor. This is the basic strategy on a collision - it does nothing.
     * @param gameObjects The game object collection
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * @return The game object collection
     */
    @Override
    public GameObjectCollection getGameObjectCollection(){
        return this.gameObjects;
    }

    /**
     * On a collision with a ball, removes the brick and decrements the bricks counter
     * @param thisObj The brick
     * @param otherObj A ball
     * @param brickCounter Number of bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        if (this.gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            brickCounter.decrement();
        }
    }
}
