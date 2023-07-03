package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class DoubleBehaviour implements CollisionStrategy {
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;

    /**
     * Constructor. A CollisionStrategy object that holds two collision strategies at once
     * @param strategy1
     * @param strategy2
     */
    public DoubleBehaviour(CollisionStrategy strategy1, CollisionStrategy strategy2) {
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    /**
     * On a collision, calls both strategies
     * @param thisObj      A brick
     * @param otherObj     A ball
     * @param brickCounter Number of bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        this.strategy1.onCollision(thisObj, otherObj, brickCounter);
        this.strategy2.onCollision(thisObj, otherObj, brickCounter);
    }

    /**
     * @return The game object collection
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return getGameObjectCollection();
    }
}
