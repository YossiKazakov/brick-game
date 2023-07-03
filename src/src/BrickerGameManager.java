package src;

import src.brick_strategies.BrickStrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.*;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    public static Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter brickCounter = new Counter();
    private Counter livesCounter = new Counter(NUM_OF_LIVES);

    //Features
    private static final Color COLOR = Color.CYAN;
    public static final float BORDER_WIDTH = 20;
    private static final float BALL_SIZE = 20;
    public static final int NUM_OF_LIVES = 15;
    private static final float BALL_SPEED = 300;
    public static final float PADDLE_WIDTH = 150;
    public static final float PADDLE_HEIGHT = 15;
    public static final float BRICK_HEIGHT = 15;
    public static final int EPSILON = 5;
    public static final float LIVES_FONT_SIZE = 30;
    private static final int NUM_OF_BRICK_ROWS = 5;
    private static final int NUM_BRICKS_PER_ROW = 8;
    private static final float SPACE_BRICKS_AND_WALLS = 5;
    private static final int SPACES_BETWEEN_BRICKS = NUM_BRICKS_PER_ROW - EPSILON;
    private static final float SPACE_BETWEEN_BRICK_AND_WALLS =
            SPACE_BRICKS_AND_WALLS * 2 + BORDER_WIDTH;
    private static final float SUM_OF_SPACES = SPACES_BETWEEN_BRICKS +
            SPACE_BETWEEN_BRICK_AND_WALLS;

    //Paths
    private static final String BALL_PATH = "assets/ball.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String BRICK_PATH = "assets/brick.png";
    private static final String PADDLE_PATH = "assets/paddle.png";
    private static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String HEART_PATH = "assets/heart.png";
    private static final String WIN_MSG = "You win! ";
    private static final String LOSE_MSG = "You lose! ";
    private static final String PLAY_AGAIN = "Play again?";


    /**
     * Constructor of the Game
     *
     * @param windowTitle,      the name of the game, appears in the title of the
     *                          window
     * @param windowDimensions, Vector representing the window size.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * This function is called by the initializeGame function and creates the ball
     *
     * @param imageReader  object that deals with images
     * @param soundReader, object that deals with sounds
     */
    private void creatingBall(ImageReader imageReader, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(BALL_PATH, true);
        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5F));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        this.gameObjects().addGameObject(ball);
    }


    /**
     * This function is called by the initializeGame function and creates the bricks
     *
     * @param imageReader object that deals with images
     */
    public void createBricks(ImageReader imageReader, BrickStrategyFactory brickStrategyFactory) {
        Renderable brickImage = imageReader.readImage(BRICK_PATH, false);
        float eachBrickWidth = (windowDimensions.x() - SUM_OF_SPACES) / NUM_BRICKS_PER_ROW;
        for (int i = 0; i < NUM_OF_BRICK_ROWS; i++) {
            for (int j = 0; j < NUM_BRICKS_PER_ROW; j++) {
                Vector2 topLeftCoordinates = new Vector2(
                        j * (eachBrickWidth + 1) + BORDER_WIDTH,
                        i * (BRICK_HEIGHT + 1) + BORDER_WIDTH);
                this.gameObjects().addGameObject(
                        new Brick(topLeftCoordinates,
                                new Vector2(eachBrickWidth, BRICK_HEIGHT), brickImage,
                                brickStrategyFactory.getStrategy(), brickCounter
                        ), Layer.STATIC_OBJECTS
                );
            }
        }
    }

    /**
     * This function is called by the initializeGame function and creates the paddle
     *
     * @param imageReader    object that deals with images
     * @param inputListener, object that deals with the keyboard input for movement
     */
    private void creatingPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(PADDLE_PATH, true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, (int) BORDER_WIDTH);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - BRICK_HEIGHT / 2));
        this.gameObjects().addGameObject(paddle);
    }

    /**
     * This function is called by the initializeGame function and creates the background
     * display of the game
     *
     * @param imageReader object that deals with images
     */
    private void creatingBackground(ImageReader imageReader) {
        GameObject background = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                imageReader.readImage(BACKGROUND_PATH, false));
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * This function is called by the initializeGame function and creates the walls:
     * a top wall, a left wall and a right wall
     */
    private void creatingWalls() {
        this.gameObjects().addGameObject( //Left wall
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        new RectangleRenderable(COLOR)
                ),
                Layer.STATIC_OBJECTS
        );
        this.gameObjects().addGameObject( //Right wall
                new GameObject(
                        new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                        new Vector2(BORDER_WIDTH, windowDimensions.y()),
                        new RectangleRenderable(COLOR)
                ),
                Layer.STATIC_OBJECTS
        );
        this.gameObjects().addGameObject( //Top wall
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), BORDER_WIDTH),
                        new RectangleRenderable(COLOR)
                ),
                Layer.STATIC_OBJECTS
        );

    }

    /**
     * This function is called by the initializeGame function and creates the numeric
     * lives counter
     */
    private void creatingNumericLivesCounter() {
        this.gameObjects().addGameObject(
                new NumericLifeCounter(livesCounter, Vector2.ZERO,
                        new Vector2(LIVES_FONT_SIZE, LIVES_FONT_SIZE),
                        this.gameObjects())
                , Layer.STATIC_OBJECTS
        );
    }

    /**
     * This function is called by the initializeGame function and creates the graphic
     * lives counter (the 'hearts')
     */
    private void creatingGraphicLivesCounter(ImageReader imageReader) {
        this.gameObjects().addGameObject(
                new GraphicLifeCounter(new Vector2(20, windowDimensions.y() - 20),
                        new Vector2(20, 20),
                        this.livesCounter,
                        imageReader.readImage(HEART_PATH, true),
                        this.gameObjects(),
                        NUM_OF_LIVES), Layer.FOREGROUND
        );
    }

    /**
     * The function that creates all the objects required for the game to run
     *
     * @param imageReader,      object that deals with images
     * @param soundReader,      object that deals with sounds
     * @param inputListener,    object that listen to the user's keyboard
     * @param windowController, object that creates the window of the game
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowController.setTargetFramerate(80);
        windowController.setTimeScale(1.0F);
        this.windowController = windowController;
        this.windowDimensions = windowController.getWindowDimensions();
        creatingBall(imageReader, soundReader);
        creatingPaddle(imageReader, inputListener);
        creatingWalls();
        creatingBackground(imageReader);
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(this.gameObjects(),
                this, imageReader,
                soundReader, inputListener, windowController, windowDimensions);
        createBricks(imageReader, brickStrategyFactory);
        creatingNumericLivesCounter();
        creatingGraphicLivesCounter(imageReader);

    }

    /**
     * This function responsible for fixing the ball placement every time it runs out of the
     * sideways of the screen
     */
    private void checkBallAgainstWalls() {
        float ballLeftCorner = ball.getTopLeftCorner().x();
        float ballRightCorner = ball.getTopLeftCorner().x() + ball.getDimensions().x();

        if (ballLeftCorner < BORDER_WIDTH) {
            ball.setTopLeftCorner(new Vector2(BORDER_WIDTH + EPSILON,
                    ball.getTopLeftCorner().y()));
        }
        if (ballRightCorner > windowDimensions.x()) {
            ball.setTopLeftCorner(new Vector2(windowDimensions.x() + BORDER_WIDTH + EPSILON + BALL_SIZE,
                    ball.getTopLeftCorner().y()));
        }
    }

    /**
     * The function checks for wins (when all the bricks are gone)
     * or loses (when the ball falls down). It then asks the user if he/she wants to proceed
     */
    private void checkGameStatus() {
        float ballHeight = ball.getCenter().y();
        String msg = "";
        if (brickCounter.value() == 0) {
            msg = WIN_MSG;
        }
        if (ballHeight > windowDimensions.y()) {
            this.setCamera(null);
            livesCounter.decrement();
            ball.setCenter(windowDimensions.mult(0.5F));
            if (livesCounter.value() == 0)
                msg = LOSE_MSG;
        }
        if (!msg.isEmpty()) {
            msg += PLAY_AGAIN;
            if (this.windowController.openYesNoDialog(msg)) {
                MockPaddle.isInstantiated = false;
                livesCounter.reset();
                brickCounter.reset();
                livesCounter.increaseBy(NUM_OF_LIVES);
                this.windowController.resetGame();
            } else {
                this.windowController.closeWindow();
            }
        }
    }

    /**
     * The function overrides 'update' so it will check for wins (when all the bricks are gone)
     * or loses (when the ball falls down)
     *
     * @param deltaTime time between calls
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkGameStatus();
        checkBallAgainstWalls();
        for (GameObject gameObject : gameObjects()) {
            if (gameObject.getDimensions().y() > windowDimensions.y()) {
                gameObjects().removeGameObject(gameObject);
            }
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(1000, 630)).run();
    }
}
