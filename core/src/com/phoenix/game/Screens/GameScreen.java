package com.phoenix.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.phoenix.game.Entities.MovingBlock;
import com.phoenix.game.Entities.Orc;
import com.phoenix.game.Entities.Skeleton;
import com.phoenix.game.Game;
import com.phoenix.game.Scenes.Main_UI;
import com.phoenix.game.Tools.B2WorldCreator;
import com.phoenix.game.Tools.Controller;
import com.phoenix.game.Tools.ScreenHandler;
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
    private float spawnX;
    private float spawnY;
    private final float MCSpeed = 0.1f; //Velocidad standard de movimiento del jugador
    private final float TOP_SPEED = 1;
    private final float JUMP_STR = 4.8f;
    private boolean fbLock; //Está la fireball en cooldown?
    private boolean jumpLock;
    private final long JMPCD = 900000000;
    private final long FBCD = 500000000; //CD en nanosegundos de la bola de fuego
    private long startTime = 0;

    //Variables relacionadas con el/los mapas
    private TmxMapLoader mapLoader;
    private TiledMap green_map;
    private OrthogonalTiledMapRenderer mapRenderer;

    //Variables box2D
    private World world;
    private Box2DDebugRenderer b2dr; //Dibuja las líneas de los polígonos para la colisión

    //Objetos con los que se puede interaccionar

    private B2WorldCreator b2wc;

    private boolean dungeonFlag = false;
    private boolean greenMapFlag = false;
    private boolean sideScrollFlag = false;
    private boolean cityFlag = false;
    private boolean tpFlag = false;
    private boolean repositionFlag = false;

    private boolean sideScroll = false;
    public boolean ladder = false;

    public Controller controller;

    public GameScreen(Game game, String map, boolean gravity){
        this.game = game;
        //La cámara que seguirá a nuestro jugador
        this.cam = new OrthographicCamera();
        //FitViewPort se encarga de que el juego funciona en todas las resoluciones
        this.gamePort = new FitViewport(Game.WIDTH / Game.PPM, Game.HEIGHT / Game.PPM, cam);

        //Carga el mapa hecho en Tiled
        mapLoader = new TmxMapLoader();
        green_map = mapLoader.load(map);
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
        //initializeEnemies(world);

        //Nueva Interfaz Gráfica general
        this.UI = new Main_UI(game.batch, this.mcharacter);

        //Controles
        this.controller = new Controller(game.batch);

        //Listener para todas nuestras colisiones
        world.setContactListener(new WorldContactListener());

        this.fbLock = false;
        this.jumpLock = false;

    }

    public GameScreen(Game game, String map, MainCharacter mc, boolean gravity){
        this.game = game;
        //La cámara que seguirá a nuestro jugador
        this.cam = new OrthographicCamera();
        //FitViewPort se encarga de que el juego funciona en todas las resoluciones
        this.gamePort = new FitViewport(Game.WIDTH / Game.PPM, Game.HEIGHT / Game.PPM, cam);

        //Carga el mapa hecho en Tiled
        mapLoader = new TmxMapLoader();
        green_map = mapLoader.load(map);
        mapRenderer = new OrthogonalTiledMapRenderer(green_map, 1 / Game.PPM);
        //Centra la cámara cuando se ejecuta el juego al principio
        cam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        //Inicializa variables box2D
        if (!gravity){
            world = new World(new Vector2(0,0), true); //No hay gravedad, los cuerpos están "dormidos"
        }
        else{
            world = new World(new Vector2(0, -10), true);
            sideScroll = true;
        }
        b2dr = new Box2DDebugRenderer(); //Esto nos dibujará los cuadrados verdes de colisión

        //Crea el mundo Box2D con el mapa en cuestión
        b2wc = new B2WorldCreator(world, green_map, this);

        //Crea el Body Box2D de nuestro personaje principal
        mcharacter = new MainCharacter(world, this, mc);

        //Nueva Interfaz Gráfica general
        this.UI = new Main_UI(game.batch, this.mcharacter);

        this.controller = new Controller(game.batch);

        //Listener para todas nuestras colisiones
        world.setContactListener(new WorldContactListener());

        this.fbLock = false;
        this.jumpLock = false;

    }
    @Override
    public void show() {

    }

    public void update(float delta){

        //Maneja lo que pulsa el usuario
        if(sideScroll)
            handleSCInput(delta);
        else
            handleInput(delta);

        world.step(1f/60f, 6, 2);

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
        for(MovingBlock mb : b2wc.getMbArray()){
            mb.update(delta);
        }
        for(Skeleton sk : b2wc.getSkeletonArray()){
            sk.update(delta);
        }
        for(Orc orc : b2wc.getOrcArray()){
            orc.update(delta);
        }
    }

    public World getWorld(){
        return this.world;
    }

    public void handleInput(float delta){

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)|| Gdx.input.isTouched()) {//Maneja el movimiento de las 4 flechas de dirección
            if (controller.isRightPressed() && mcharacter.b2body.getLinearVelocity().x < TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
            }
            if (controller.isLeftPressed() && mcharacter.b2body.getLinearVelocity().x > -TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(-MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
            }
            if (controller.isUpPressed() && mcharacter.b2body.getLinearVelocity().y < TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(0, MCSpeed), mcharacter.b2body.getWorldCenter(), true);
            }
            if (controller.isDownPressed() && mcharacter.b2body.getLinearVelocity().y > -TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(0, -MCSpeed), mcharacter.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !fbLock) {
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

    public void handleSCInput(float delta){
        if (controller.isRightPressed() && mcharacter.b2body.getLinearVelocity().x < TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
        }
        if (controller.isLeftPressed() && mcharacter.b2body.getLinearVelocity().x > -TOP_SPEED) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(-MCSpeed, 0), mcharacter.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && jumpLock == false) {
                mcharacter.b2body.applyLinearImpulse(new Vector2(0, JUMP_STR), mcharacter.b2body.getWorldCenter(), true);
                startTime = TimeUtils.nanoTime();
                jumpLock = true;
        }
        if (controller.isUpPressed() && mcharacter.b2body.getLinearVelocity().y < 1 && ladder) {
            mcharacter.b2body.applyLinearImpulse(new Vector2(0, 1.0f), mcharacter.b2body.getWorldCenter(), true);
        }
        if(jumpLock){
            lockJump(startTime);
        }
    }
    @Override
    public void render(float delta) {
        //Actualizamos cada vez que dibujamos
        update(delta);

        //Dibuja al personaje siguiendo nuestra cámara
        mcharacter.update(delta);

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
        if(this.sideScroll) {
            for (MovingBlock mb : b2wc.getMbArray()) {
                mb.draw(game.batch);
            }
        }
        for(Skeleton sk : b2wc.getSkeletonArray()){
            sk.draw(game.batch);
        }
        for(Orc orc : b2wc.getOrcArray()){
            orc.draw(game.batch);
        }
        //El batch dibuja la UI con la cámara de la UI, que es estática
        game.batch.setProjectionMatrix(UI.stage.getCamera().combined);

        game.batch.end();

        UI.stage.draw();

        controller.draw();

        if(dungeonFlag){
            ScreenHandler.getScreenHandler().setDungeonScreen(mcharacter);
        }
        if(greenMapFlag){
            ScreenHandler.getScreenHandler().setGameScreenBack(mcharacter);
        }
        if(sideScrollFlag){
            ScreenHandler.getScreenHandler().setSideScrollScreen(mcharacter);
        }
        if(cityFlag){
            ScreenHandler.getScreenHandler().setCityScreen(mcharacter);
        }
        if(tpFlag){
            mcharacter.teleport(1, 1);
            tpFlag = false;
            mcharacter.decreaseLife(200);
            UI.updateLife(mcharacter);
        }
        if(repositionFlag){
            setSpawn();
            mcharacter.teleport(spawnX, spawnY);
            repositionFlag = false;
        }
    }

    private void lockFireBall(long startTime){ //Pone la bola de fuego en CD
        if(TimeUtils.timeSinceNanos(startTime) > FBCD){
            fbLock = false;
        }

    }
    private void lockJump(long startTime){
        if(TimeUtils.timeSinceNanos(startTime) > JMPCD){
            jumpLock = false;
        }
    }
    private void setSpawn(){
        if(this.green_map.getProperties().get("name").equals("map_1")){
            if(ScreenHandler.getScreenHandler().getPreviousMap().equals("none")){

            }
            else if(ScreenHandler.getScreenHandler().getPreviousMap().equals("dungeon_1")){
                spawnX = 30;
                spawnY = 51.5f;
            }
        }
        else if(this.green_map.getProperties().get("name").equals("dungeon_1")){
            if(ScreenHandler.getScreenHandler().getPreviousMap().equals("map_1")){
                spawnX = 15f;
                spawnY = 1.5f;
            }
            else if(ScreenHandler.getScreenHandler().getPreviousMap().equals("city_1")){
                spawnX = 1;
                spawnY = 14;
            }
        }
        else if(this.green_map.getProperties().get("name").equals("sidescroll_1")){
            if(ScreenHandler.getScreenHandler().getPreviousMap().equals("city_1")){
                spawnX = 1;
                spawnY = 0.7f;
            }
            else if(ScreenHandler.getScreenHandler().getPreviousMap().equals("")){

            }
        }
        else if(this.green_map.getProperties().get("name").equals("city_1")){
            if(ScreenHandler.getScreenHandler().getPreviousMap().equals("dungeon_1")){ //Cambiar
                spawnX = 2.5f;
                spawnY = 1;
            }
            else if(ScreenHandler.getScreenHandler().getPreviousMap().equals("sidescroll_1")){
                spawnX = 6.1f;
                spawnY = 9.4f;
            }
        }
    }

    public void setDungeonFlag(){
        dungeonFlag = true;
    }

    public void setGreenMapFlag(){
        greenMapFlag = true;
    }

    public void setSideScrollFlag(){
        sideScrollFlag = true;
    }

    public void setCityFlag(){
        cityFlag = true;
    }

    public void setTpFlag(){ tpFlag = true; }

    public void setRepositionFlag(){ repositionFlag = true; }

    public void setLadder(boolean value){ ladder = value; }

    public TiledMap getMap(){return green_map;}

    public com.badlogic.gdx.Game getGame(){
        return game;
    }

    public GameScreen getScreen(){
        return this;
    }

    public MainCharacter getMcharacter(){ return mcharacter; }

    public Main_UI getUI(){
        return UI;
    }
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
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
