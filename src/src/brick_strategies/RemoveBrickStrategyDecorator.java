package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    private final CollisionStrategy toBeDecorated;

    /**
     * A decorator of strategies for a collision of a brick with a ball.
     *
     * @param toBeDecorated The strategy to be 'decorated' (to be added on)
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * On collision with a ball, calls the current strategy
     *
     * @param thisObj      A brick
     * @param otherObj     A ball
     * @param brickCounter Number of bricks left in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        toBeDecorated.onCollision(thisObj, otherObj, brickCounter);
    }

    /**
     * @return The game object collection
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
