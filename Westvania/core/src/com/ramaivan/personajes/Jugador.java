package com.ramaivan.personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.ramaivan.Managers.Animacion;
import com.ramaivan.utils.Utiles;

public class Jugador extends InputAdapter {

	private Animacion animacionIdleWithGun;
	private Animacion animacionWalkWithGun;
	private Animacion animacionSaltando;

	private Body pBody;

	private float elapsedTime;
	private Vector2 position;
	private boolean saltando = false;
	private boolean enMov = false;
	private final int WidthResizeFactor = (int) Utiles.PPM;
	private final int HeightResizeFactor = (int) Utiles.PPM;

//	private Rectangle pRectangle = new Rectangle();

	public int getWidthResizeFactor() {
		return WidthResizeFactor;
	}

	public int getHeightResizeFactor() {
		return HeightResizeFactor;
	}

	public Jugador(World world) {

		animacionIdleWithGun = new Animacion("personajesAssests/idleWithGun.png", 2, 1, 100f);
		animacionWalkWithGun = new Animacion("personajesAssests/walkWithGun.png", 3, 1, 15f);
		animacionWalkWithGun.getAnimacion().setPlayMode(PlayMode.LOOP_PINGPONG);
		animacionSaltando = new Animacion("personajesAssests/Cowboy2_jump_with_gun.png", 1, 1, 1f);
		position = new Vector2(20, 20);
		pBody = Utiles.createBox(8, 10, (int) animacionIdleWithGun.getAnchoImagenRegion() / 2,
				(int) animacionIdleWithGun.getAlturaImagenRegion() / 2, false, world);
		// pRectangle.width = Math.round(animacionIdleWithGun.getAnchoImagenRegion());
		// pRectangle.height = Math.round(animacionIdleWithGun.getAlturaImagenRegion());
		// pRectangle.setPosition(position.x, position.y);
	}

	public Animacion getAnimacionIdleWithGun() {
		return animacionIdleWithGun;
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	public boolean isEnMov() {
		return enMov;
	}

	public void renderAnimacionActual(SpriteBatch batch) {

		if (isSaltando()) {
			animacionSaltando.render(batch, 1,
					pBody.getPosition().x * Utiles.PPM - ((int) animacionIdleWithGun.getAnchoImagenRegion() / 4),
					pBody.getPosition().y * Utiles.PPM - ((int) animacionIdleWithGun.getAnchoImagenRegion() / 4),
					WidthResizeFactor, HeightResizeFactor);
		} else if (enMov) {
			animacionWalkWithGun.render(batch, 1,
					pBody.getPosition().x * Utiles.PPM - ((int) animacionIdleWithGun.getAnchoImagenRegion() / 4),
					pBody.getPosition().y * Utiles.PPM - ((int) animacionIdleWithGun.getAnchoImagenRegion() / 4),
					WidthResizeFactor, HeightResizeFactor);
		} else if (!enMov) {
			animacionIdleWithGun.render(batch, 1,
					pBody.getPosition().x * Utiles.PPM - ((int) animacionIdleWithGun.getAnchoImagenRegion() / 4),
					pBody.getPosition().y * Utiles.PPM - ((int) animacionIdleWithGun.getAnchoImagenRegion() / 4),
					WidthResizeFactor, HeightResizeFactor);
		}

	}

	public void mover() {
		saltando = false;
		enMov = false;
		int horizontalForce = 0;
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			System.out.println("D");
			horizontalForce += 1;
			enMov = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			System.out.println("A");
			horizontalForce -= 1;
			// pRectangle.setPosition(position.x, position.y);
			enMov = true;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			System.out.println("SALTO");
			pBody.applyForceToCenter(0, 150, false);
			saltando = true;
		}

		pBody.setLinearVelocity(horizontalForce * 5, pBody.getLinearVelocity().y);
	}

	public Animacion getAnimacionWalkWithGun() {
		return animacionWalkWithGun;
	}

	public boolean isSaltando() {
		return saltando;
	}

	public Animacion getAnimacionSaltando() {
		return animacionSaltando;
	}

}
