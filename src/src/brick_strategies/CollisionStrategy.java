package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;


public interface CollisionStrategy {
    /**
     * General type for brick strategies,
     * part of decorator pattern implementation. All brick strategies implement this interface.
     * @param thisObj      A brick
     * @param otherObj     A ball
     * @param brickCounter Number of bricks left in the game
     */
    void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter);

    /**
     * @return The game object collection
     */
    GameObjectCollection getGameObjectCollection();
}
