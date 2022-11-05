package com.ramaivan.utils;

import static com.ramaivan.utils.Utiles.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Utiles {
	
	public final static float PPM = 32;
	
	
	public static Body createBox(int x, int y, int w, int h, boolean isStatic, World world) {
		Body pBody;
		BodyDef def = new BodyDef();
		if (isStatic) {
			def.type = BodyType.StaticBody;
		} else {
			def.type = BodyType.DynamicBody;
		}
		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();

		
		System.out.println(w);
		System.out.println(PPM);


		shape.setAsBox(w / 2 / PPM, h / 2 / PPM);

		pBody.createFixture(shape, 1);
		shape.dispose();

		return pBody;
	}
	
}	
