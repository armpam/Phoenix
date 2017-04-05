package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.phoenix.game.Entities.Coin;
import com.phoenix.game.Entities.MainCharacter;
import com.phoenix.game.Entities.MainFireball;
import com.phoenix.game.Entities.Orc;
import com.phoenix.game.Entities.Skeleton;
import com.phoenix.game.Game;
import com.phoenix.game.Scenes.Main_UI;
import com.phoenix.game.Tools.B2WorldCreator;
import com.phoenix.game.Tools.WorldContactListener;


/**
 * Created by Alesander on 2/21/2017.
 */

public class GameScreen implements Screen {

    private Game game;
    private OrthographicCamera cam;
    private Viewport gamePort;
    private Main_UI UI;

    private MainCharacter mcharacter;
    private final float MCSpeed = 0.1f; //Velocidad standard de movimiento del jugador
    private final float TOP_SPEED = 1;
    private boolean fbLock; //Está la fireball en cooldown?
    private final long FBCD = 500000000; //CD en nanosegundos de la bola de fuego
    private long startTime = 0;

    //Variables relacionadas con los enemigos
    private final int numberOfSkeletons = 20; //Número total de Skeletons
    private final int numberOfOrcs = 20; //Número total de Orcos
    private final int dActiveEnemies = 5; //Distancia al personaje a la que se activan/desactivan los enemigos

    private Skeleton[] skeleton; //Declaramos los enemigos de tipo Skeleton
    private Orc[] orc; // Declaramos los enemigos tipo Orc

    //Variables relacionadas con el/los mapas
    private TmxMapLoader mapLoader;
    private TiledMap green_map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Variables box2D
    private World world;
    private Box2DDebugRenderer b2dr; //Dibuja las líneas de los polígonos para la colisión

    private Music OWtheme;

    //Objetos con los que se puede interaccinar

    private B2WorldCreator b2wc;

    public GameScreen(Game game){
        this.game = game;
        //La cámara que seguirá a nuestro jugador
        this.cam = new OrthographicCamera();
        //FitViewPort se encarga de que el juego funciona en todas las resoluciones
        this.gamePort = new FitViewport(Game.WIDTH / Game.PPM, Game.HEIGHT / Game.PPM, cam);

        //Carga el mapa hecho en Tiled
        mapLoader = new TmxMapLoader();
        green_map = mapLoader.load("maptest_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(green_map, 1 / Game.PPM);
        //Centra la cámara cuando se ejecuta el juego al principio
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        //Inicializa variables box2D
        world = new World(new Vector2(0,0), true); //No hay gravedad, los cuerpos están "dormidos"
        b2dr = new Box2DDebugRenderer(); //Esto nos dibujará los cuadrados verdes de colisión

        //Crea el mundo Box2D con el mapa en cuestión
         b2wc = new B2WorldCreator(world, green_map, this);

        //Crea el Body Box2D de nuestro personaje principal
        mcharacter = new MainCharacter(world, this);

        //Crea el Body Box2D de los enemigos
        initializeEnemies(world);

        //Nueva Interfaz Gráfica general
        this.UI = new Main_UI(game.batch, this.mcharacter);

        //Listener para todas nuestras colisiones
        world.setContactListener(new WorldContactListener());

        //OWtheme = Game.assetManager.get("audio/themes/overworld.ogg", Music.class);
        //OWtheme.setLooping(true);
        //OWtheme.play();

        this.fbLock = false;

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

        //Actualizamos objetos del juego
        for(Coin coin : b2wc.getCoinArray() ){
            coin.update(delta);
            if (coin.isDestroyed()){
                b2wc.getCoinArray().removeValue(coin, true);
            }
        }
    }

    public World getWorld(){
        return this.world;
    }

    public void initializeEnemies(World world){
        //Inicializamos el número de enemigos declarados en las variables finales de cada enemigo.
        skeleton = new Skeleton[numberOfSkeletons];
        orc = new Orc[numberOfOrcs];
        for (int i = 0; (i < numberOfSkeletons); i++) {
            skeleton[i] = new Skeleton(world);
        }
        for (int i = 0; (i < numberOfOrcs); i++) {
            orc[i] = new Orc(world);
        }
    }

    public void enemiesMovement(MainCharacter mcharacter){
        //Declaramos la variable distance, la cual obtiene  la distancia del enemigo al Mcharacter
        Vector2 player = mcharacter.b2body.getPosition();
        Vector2 enemy;
        float distance;
        //Movemos cada enemigo uno a uno
        for (int i = 0; i < numberOfSkeletons; i++) { //Movimiento de los Skeletons
            enemy = skeleton[i].b2body.getPosition();
            distance = enemy.dst(player); //Obtenemos la distancia del personaje al enemigo
            if ((distance) < dActiveEnemies) { //Si la distancia es menor que "dActiveEnemies" activamos al enemigo y su moviento
                skeleton[i].b2body.setActive(true);
                skeleton[i].enemyMovement(mcharacter);
            } else { //Si la distancia es mayor entonces desactivamos al enemigo
                skeleton[i].b2body.setActive(false);
            }
        }
        for (int i = 0; i < numberOfOrcs; i++) { //Movimiento de los Orcs
            enemy = orc[i].b2body.getPosition();
            distance = enemy.dst(player); //Obtenemos la distancia del personaje al enemigo
            if (distance < dActiveEnemies) { //Si la distancia es menor que "dActiveEnemies" activamos al enemigo y su moviento
                orc[i].b2body.setActive(true);
            } else { //Si la distancia es mayor entonces desactivamos al enemigo
                orc[i].b2body.setActive(false);
            }
        }
    }

    public void handleInput(float delta){

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {//Maneja el movimiento de las 4 flechas de dirección
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mcharacter.b2body.getLinearVelocity().x < TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mcharacter.b2body.getLinearVelocity().x > -TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(-MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && mcharacter.b2body.getLinearVelocity().y < TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(0, MCSpeed), mcharacter.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && mcharacter.b2body.getLinearVelocity().y > -TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(0, -MCSpeed), mcharacter.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && fbLock == false) {
                mcharacter.fire(); //Dispara una bola de fuego
                startTime = TimeUtils.nanoTime();
                fbLock = true;
            }
        }
        else{
            mcharacter.b2body.setLinearVelocity(0,0);
        }

        //No disparar si la bola de fuego está en CD
        if(fbLock){
            lockFireBall(startTime);
        }

    }
    @Override
    public void render(float delta) {
        //Actualizamos cada vez que dibujamos
        update(delta);

        //Dibuja al personaje siguiendo nuestra cámara
        mcharacter.update(delta);

        //Consigue que los enemigos persigan al jugador
        enemiesMovement(mcharacter);

        //Limpia la pantalla con negro
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Dibuja el mapa
        mapRenderer.render();

        //Dibuja las líneas box2D
        b2dr.render(world, cam.combined);

        //Dibujar al jugador - Él sólo dibuja sus animaciones por extender Sprite mediante(setRegion())
        game.batch.setProjectionMatrix(cam.combined);
        game.batch.begin();
        mcharacter.draw(game.batch);
        //Dibuja las bolas de fuego
        for(MainFireball fireball : mcharacter.getFireballs()){
            fireball.draw(game.batch);
        }
        for(Coin coin : b2wc.getCoinArray()){
            coin.draw(game.batch);
        }
        //Actualiza la vida
        UI.updateLifeLabel(mcharacter); //Actualiza el nivel de vida del personaje

        game.batch.end();

        //El batch dibuja la UI con la cámara de la UI, que es estática
        game.batch.setProjectionMatrix(UI.stage.getCamera().combined);
        UI.stage.draw();
    }

    private void lockFireBall(long startTime){ //Pone la bola de fuego en CD
        if(TimeUtils.timeSinceNanos(startTime) > FBCD){
            fbLock = false;
        }

    }

    public GameScreen getScreen(){
        return this;
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
