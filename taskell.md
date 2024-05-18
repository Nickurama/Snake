## To Do

- Keypress documentation
- Auto update with clock
- Clock documentation
- Graphical overlays
- Graphical overlays documentation
- Update UML
- Fix UML dependencies on naming
- Update Javadoc
- Write Report

## Done recent

- Graphical stuff documentation
- Close window when stopping engine
- Detect keypress input

## Doing


## Done

- Do base GameObject
    * [x] Do GameObject class
    * [x] Do GameObject tests
    * [x] Do GameObject documentation
- Do GameEngineFlags
    * [x] Do GameEngineFlags class
    * [x] Do GameEngineFlags tests
    * [x] Do GameEngineFlags documentation
- Do base Scene class
    * [x] Do Scene class
    * [x] Do Scene tests
    * [x] Do Scene documentation
- Make stop() on GameEngine
- Make engine take input from stdin when in iterative mode
- Make engine process input when in iterative mode
- Make Stop command
- Make Step command
- Make IInputListener class
- Adjust scene class for IInputListener
- Scene updates id on GameObject when it's added to the scene
- A GameObject can only be instantiated in one scene
- GameObjects should have a reference to their scenes
- Make setScene() engine function
- Make Logger class
- Make Debug command
- Make RenderData class
- Make IRenderable interface
- Update scene class for IRenderable
- Make Circle
- Make Circle intersect with circle
- Make Circle IGeometricShape
- Make perpendicular Line generation
- Make Circle intersect with LineSegment
- Make Circle contains point
- Make Circle intersect with polygon (and vice versa)
- Make Polygon IGeometricShape
- Make Polygon contains point
- Make Circle contains IGeometricShape
- Make Polygon contains IGeometricShape
- Make contains an IGometricShape method?
- Make Renderer actually render
- Render singleton
- Render draw instead of make points
- Render actually render
- Rasterize scene
- Rasterize circle edge
- Rasterize circle
- Render overlay
- IOverlay
- Renderer overlay
- CollisionManager
- GameEngine singleton
- Score
- Scoreboard
- GameMap
- Decide on how game coordinates work
- Obstacle
- DynamicObstacle
- Overlay
- SnakeUnit
- FoodSquare without respawn
- FoodCircle without respawn
- Snake eat
- Snake grow
- Snake body
- Snake turn body
- Snake die when colliding with itself
- Do GameEngine
    * [ ] Do GameEngine class
    * [ ] Do GameEngine tests
    * [ ] Do GameEngine documentation
- Overlay split
- Overlay gameplay
- Overlay update gameplay values
- Overlay gameover
- Overlay highscores
- SnakeController
- GameManager
- Removing from scene should free the game object
- Random snake position
- GameManager tests
- GameManager builder
- only snake head should check for containing food
- Overlay GameManager.score instead of passing snake
- Render obstacle with O
- Food respawn
- change overlay when lose/win
- make player be able to submit highscore
- make replayable
- SnakeAI
- Easier input
- UML
- Geometry Documentation
    * [x] BoundingBox
    * [x] Circle
    * [x] GeometricException
    * [x] IGeometricShape
    * [x] Line
    * [x] LineSegment
    * [x] MathUtil
    * [x] Path
    * [x] Point
    * [x] Polygon
    * [x] Rectangle
    * [x] Square
    * [x] Triangle
    * [x] Vector
    * [x] VirtualPoint
- Geometry Report
    * [x] BoundingBox
    * [x] Circle
    * [x] GeometricException
    * [x] IGeometricShape
    * [x] Line
    * [x] LineSegment
    * [x] MathUtil
    * [x] Path
    * [x] Point
    * [x] Polygon
    * [x] Rectangle
    * [x] Square
    * [x] Triangle
    * [x] Vector
    * [x] VirtualPoint
- GameEngine Documentation
    * [x] CollisionManager
    * [x] GameEngine
    * [x] GameEngineException
    * [x] GameEngineFlags
    * [x] Colour
    * [x] GameObject
    * [x] ICollider
    * [x] IInputListener
    * [x] IOverlay
    * [x] IRenderable
    * [x] Logger
    * [x] RenderData
    * [x] Renderer
    * [x] Scene
    * [x] TextOverlay
    * [x] TextOverlayOutline
- GameEngine Report
    * [x] CollisionManager
    * [x] GameEngine
    * [x] GameEngineException
    * [x] GameEngineFlags
    * [x] Colour
    * [x] GameObject
    * [x] ICollider
    * [x] IInputListener
    * [x] IOverlay
    * [x] IRenderable
    * [x] Logger
    * [x] RenderData
    * [x] Renderer
    * [x] Scene
    * [x] TextOverlay
    * [x] TextOverlayOutline
- SnakeGame Documentation
    * [x] AISnakeController
    * [x] Direction
    * [x] DynamicObstacle
    * [x] FoodCircle
    * [x] FoodSquare
    * [x] FoodStats
    * [x] GameManager
    * [x] GameManagerBuilder
    * [x] GameMap
    * [x] GameoverOverlay
    * [x] GameplayOverlay
    * [x] HighscoresOverlay
    * [x] IFood
    * [x] IFoodStats
    * [x] IHighscoresReader
    * [x] IObstacle
    * [x] ISnakeController
    * [x] ISnakeStats
    * [x] ISpatialComponent
    * [x] InputSnakeController
    * [x] Score
    * [x] Scoreboard
    * [x] Snake
    * [x] SnakeController
    * [x] SnakeGameException
    * [x] SnakeStats
    * [x] SnakeUnit
    * [x] StaticObstacle
    * [x] Unit
- SnakeGame Report
    * [x] AISnakeController
    * [x] Direction
    * [x] DynamicObstacle
    * [x] FoodCircle
    * [x] FoodSquare
    * [x] FoodStats
    * [x] GameManager
    * [x] GameManagerBuilder
    * [x] GameMap
    * [x] GameoverOverlay
    * [x] GameplayOverlay
    * [x] HighscoresOverlay
    * [x] IFood
    * [x] IFoodStats
    * [x] IHighscoresReader
    * [x] IObstacle
    * [x] ISnakeController
    * [x] ISnakeStats
    * [x] ISpatialComponent
    * [x] InputSnakeController
    * [x] Score
    * [x] Scoreboard
    * [x] Snake
    * [x] SnakeController
    * [x] SnakeGameException
    * [x] SnakeStats
    * [x] SnakeUnit
    * [x] StaticObstacle
    * [x] Unit
- Report Notes/Design Patterns
- Javadoc
- Main examples presets
- Report Tutorial

## On Hold


## Scrapped

- GameObject should be class and have variable "hasStarted" (for events to know if they should execute)
- Make Start command
- Make engine execute command (class)
- Remove step() function from engine?
- Equals should see if gameObjects are in the same scene
- Write down the meaning of all of this
- Make Circle intersect with Line
- Stopping game engine should free the game object, and the scene shouldn't be active anymore
- GameManager builder
- Render food AND snake with #
- Report UML
