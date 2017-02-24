package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Game;
import com.phoenix.game.Scenes.Main_UI;
import com.phoenix.game.Tools.B2WorldCreator;

import java.awt.event.KeyEvent;

/**
 * Created by Alesander on 2/21/2017.
 */

public class GameScreen implements Screen {

    private Game game;
    private OrthographicCamera cam;
    private Viewport gamePort;
    private Main_UI UI;

    private MainCharacter mcharacter;
    private float MCSpeed = 4f; //Velocidad standard de movimiento del jugador

    //Variables relacionadas con el/los mapas
    private TmxMapLoader mapLoader;
    private TiledMap green_map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Variables box2D
    private World world;
    private Box2DDebugRenderer b2dr; //Dibuja las líneas de los polígonos para la colisión

    public GameScreen(Game game){
        this.game = game;
        //La cámara que seguirá a nuestro jugador
        this.cam = new OrthographicCamera();
        //FitViewPort se encarga de que el juego funciona en todas las resoluciones
        this.gamePort = new FitViewport(Game.WIDTH, Game.HEIGHT, cam);
        //Nueva Interfaz Gráfica general
        this.UI = new Main_UI(game.batch);

        //Carga el mapa hecho en Tiled
        mapLoader = new TmxMapLoader();
        green_map = mapLoader.load("maptest_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(green_map);
        //Centra la cámara cuando se ejecuta el juego al principio
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        //Inicializa variables box2D
        world = new World(new Vector2(0,0), true); //No hay gravedad, los cuerpos están "dormidos"
        b2dr = new Box2DDebugRenderer(); //Esto nos dibujará los cuadrados verdes de colisión

        //Crea el mundo Box2D con el mapa en cuestión
        new B2WorldCreator(world, green_map);

        //Crea el Body Box2D de nuestro personaje principal
        mcharacter = new MainCharacter(world);
    }

    @Override
    public void show() {

    }

    public void update(float delta){

        //Maneja lo que pulsa el usuario
        handleInput(delta);

        world.step(1/60f, 6, 2);

        //La cámara sigue al jugador
        cam.position.x = mcharacter.b2body.getPosition().x;
        cam.position.y = mcharacter.b2body.getPosition().y;

        //Actualiza las coordenadas de la cámara
        cam.update();
        //Solo dibujamos en la pantalla la parte del mundo que podemos ver
        mapRenderer.setView(cam);
    }

    public void handleInput(float delta){
        //TODO MEJORAR EL MOVIMIENTO EN DIAGONAL; TIENDE AL EJE X
        //Maneja el movimiento de las 4 flechas de dirección
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            mcharacter.b2body.applyLinearImpulse(new Vector2(MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            mcharacter.b2body.applyLinearImpulse(new Vector2(-MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            mcharacter.b2body.applyLinearImpulse(new Vector2(0, MCSpeed), mcharacter.b2body.getWorldCenter(), true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            mcharacter.b2body.applyLinearImpulse(new Vector2(0, -MCSpeed), mcharacter.b2body.getWorldCenter(), true);
        }
        else{
            mcharacter.b2body.setLinearVelocity(0,0); //Para al jugador
        }

    }
    @Override
    public void render(float delta) {
        //Actualizamos cada vez que dibujamos
        update(delta);

        //Limpia la pantalla con negro
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Dibuja el mapa
        mapRenderer.render();

        //Dibuja las líneas box2D
        b2dr.render(world, cam.combined);

        //El batch dibuja la UI con la cámara de la UI, que es estática
        game.batch.setProjectionMatrix(UI.stage.getCamera().combined);
        UI.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //Liberamos al sistema de los recursos que usa con estos elementos
        green_map.dispose();
        mapRenderer.dispose();
        world.dispose();
        b2dr.dispose();
        UI.dispose();
    }
}
