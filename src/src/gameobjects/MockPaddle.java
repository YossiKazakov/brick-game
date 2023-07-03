package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle {
    public static boolean isInstantiated = false;
    private int numCollisionsToDisappear;
    private GameObjectCollection gameObjectCollection;

    /**
     * Construct a new GameObject instance: a mock paddle
     * @param topLeftCorner       Position of the object,
     *                            in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner
     *                            of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object.
     *                            Can be null, in which case
     * @param inputListener       gets what the user push
     * @param windowDimensions    a vector of 2 elements of the size of the window
     * @param minDistanceFromEdge
     */
    public MockPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions, Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions,
                      GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        isInstantiated = true;
    }

    /**
     * On a collision with an object, it decrements the number of collision left for the mock
     * paddle before disappearing. When it does disappear, it removes it and sets a false flag
     * for a new mock paddle to appear again.
     * @param other Can be any other object of the game
     * @param collision a collision that has occurred
     */
    public void onCollisionEnter(danogl.GameObject other, danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);
        numCollisionsToDisappear--;
        if (numCollisionsToDisappear == 0){
            isInstantiated = false;
            gameObjectCollection.removeGameObject(this);
        }
    }
}
