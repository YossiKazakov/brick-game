package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

public class NumericLifeCounter extends GameObject {
    private int numOfLives;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final TextRenderable text;
    private GameObject textObject;


    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param livesCounter         The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection Collection of game objects
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter, danogl.util.Vector2 topLeftCorner,
                              danogl.util.Vector2 dimensions,
                              danogl.collisions.GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.dimensions = dimensions;
        this.topLeftCorner = topLeftCorner;
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        numOfLives = livesCounter.value();
        text = new TextRenderable(Integer.toString(livesCounter.value()));
        text.setColor(Color.RED);
        textObject = new GameObject(topLeftCorner, dimensions, text);
        gameObjectCollection.addGameObject(textObject, Layer.FOREGROUND);
    }

    /**
     * Updates the numeric counter of lives left on the top left of the screen
     * @param deltaTime the time between calls
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() < numOfLives) {
            gameObjectCollection.removeGameObject(textObject, Layer.FOREGROUND);
            text.setString(Integer.toString(livesCounter.value()));
            textObject = new GameObject(topLeftCorner, dimensions, text);
            gameObjectCollection.addGameObject(textObject, Layer.FOREGROUND);
            numOfLives--;
        }

    }
}

