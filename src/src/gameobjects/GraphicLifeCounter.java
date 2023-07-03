package src.gameobjects;

import src.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private int numOfLives;
    private Counter livesCounter;
    private GameObject[] heartsArray;
    private GameObjectCollection gameObjects;

    /**
      * Constructor of the lives pictures
      * @param widgetTopLeftCorner, top left corner of the first heart image.
      * @param widgetDimensions, Vector of the dimensions of the heart image
      * @param livesCounter, reference to the outside counter of lives
      * @param widgetRenderable, a render object of the heart image
      * @param gameObjectsCollection, all the game objects in the game
      * @param numOfLives, int of number of lives
      */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner,
                              Vector2 widgetDimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.gameObjects = gameObjectsCollection;
        heartsArray = new GameObject[numOfLives];
        for (int i = 0; i < this.numOfLives; i++) {
            heartsArray[i] = new GameObject(
                    new Vector2(BrickerGameManager.BORDER_WIDTH + i * (1 + widgetDimensions.x()),
                            widgetTopLeftCorner.y()),
                    widgetDimensions, widgetRenderable);
            this.gameObjects.addGameObject(heartsArray[i], Layer.FOREGROUND);
        }
    }

    /**
      * This function updates the number of heart images in the game according to the lives left
      * @param deltaTime time between calls
      */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() < numOfLives) {
            gameObjects.removeGameObject(
                    heartsArray[numOfLives - 1], Layer.FOREGROUND);
            numOfLives--;
        }
    }
}
