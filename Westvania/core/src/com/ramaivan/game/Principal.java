package com.ramaivan.game;

import static com.ramaivan.utils.Utiles.PPM;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ramaivan.personajes.Jugador;
import com.ramaivan.utils.Utiles;

public class Principal extends ApplicationAdapter {
	private boolean DEBUG = false;

	private OrthographicCamera camera;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player, platform;

	private SpriteBatch batch;
	
	private Jugador personaje;
	
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / 2, h / 2);

		world = new World(new Vector2(0, -9.8f), false);
		b2dr = new Box2DDebugRenderer();

		player = Utiles.createBox(8, 10, 32, 32, false, world);
		platform = Utiles.createBox(0, 0, 64, 32, true, world);

		
		personaje = new Jugador(world);
		
		batch = new SpriteBatch();

	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());

		ScreenUtils.clear(0, 0, 0, 1);

		b2dr.render(world, camera.combined.scl(PPM));

		batch.begin();

		personaje.renderAnimacionActual(batch);
		
		batch.end();

	}

	public void resize(int width, int height) {
		camera.setToOrtho(false, width / 2, height / 2);
	}

	@Override
	public void dispose() {
		b2dr.dispose();
		world.dispose();
		batch.dispose();

	}

	public void update(float deltaTime) {
		world.step(1 / 60f, 6, 2);

		//inputUpdate(deltaTime);
		personaje.mover();
		cameraUpdate(deltaTime);
		batch.setProjectionMatrix(camera.combined);

	}

	private void inputUpdate(float deltaTime) {
		int horizontalForce = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			horizontalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			horizontalForce += 1;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			player.applyForceToCenter(0, 300, false);
		}
		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
	}

	private void cameraUpdate(float deltaTime) {
		Vector3 position = camera.position;

		position.x = 0;
		position.y = 0;

		camera.position.set(position);

		camera.update();

	}

	
}
