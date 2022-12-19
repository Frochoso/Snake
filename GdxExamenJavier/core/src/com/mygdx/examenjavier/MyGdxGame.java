package com.mygdx.examenjavier;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

	static public final int PANTALLA_ANCHO = 800;
	static public final int PANTALLA_ALTO = 600;
	SpriteBatch batch;
	Texture img;
	Texture fondo;
	Texture imagen;
	Texture roto;
	float velJugador=0.5f;
	float velEnemigo=-1f;
	float velFondo=-2f;
	float velBroken=-2f;
	int Nenemigo= (int) (Math.random() * 4);
	int PosEnemigo= (int) (Math.random() * 4);
	int probabilidad;
	Array<ObjetoVolador> Enemigos;
	Array<ObjetoVolador> Rotos;
	String[] coches = {"warcar1.png", "warcar2.png", "warcar3.png"};
	ObjetoVolador jugador;
	ObjetoVolador enemigo;
	ObjetoVolador carretera;
	ObjetoVolador broken;
	PanelNumeros pn;


	@Override
	public void create() {
		probabilidad=0;
		fondo=new Texture("road.png");
		roto=new Texture("broken.png");
		batch = new SpriteBatch();
		img = new Texture(Gdx.files.internal("interceptor.png"));
		jugador = new ObjetoVolador(img.getWidth()/2.0f, PANTALLA_ALTO/2-32, 0, 4f, img);
		imagen = new Texture(coches[0]);
		enemigo = new ObjetoVolador(PANTALLA_ANCHO, PosEnemigo- 32, -1f, 0f, imagen);
		Enemigos=new Array<ObjetoVolador>();
		Enemigos.add(enemigo);
		carretera=new ObjetoVolador(PANTALLA_ANCHO,PANTALLA_ALTO,-4f,0f,fondo);

		broken=new ObjetoVolador(PANTALLA_ANCHO,PosEnemigo-32,-2f,0f,roto);
		Rotos=new Array<ObjetoVolador>();
		Rotos.add(broken);
		Enemigos.add(broken);
		pn=new PanelNumeros(0,0,50);


	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		pn.pintarse(batch);
		pantallaJuego();

	}


	public void pantallaJuego() {

		int posBroken=(int)(Math.random()*(600-20)+20);


		carretera.moverse(velFondo,0);
		carretera.pintarFondo(batch);



		if (Gdx.input.justTouched()) {
			int pixelY = Gdx.input.getY();

			if (Math.abs(pixelY-img.getHeight()) < Math.abs(Gdx.graphics.getHeight()*0.3)) {
				velJugador=-2f;
				jugador.moverse(0,velJugador);
			} else {
				velJugador=2f;
				jugador.moverse(0,velJugador);
			}
		}

		//colisiones
		for(ObjetoVolador f:Enemigos) {

			if (jugador.colisiona(f)){
				Enemigos.removeValue(f,true);
				pn.reduce(1);
			}
		}



		int spawn= (int) (Math.random() * 100);
		if(spawn>97.5f){
			Nenemigo= (int) (Math.random() * 3);
			PosEnemigo=(int) (Math.random() * PANTALLA_ANCHO);
			imagen = new Texture(coches[Nenemigo]);
			Enemigos.add(new ObjetoVolador(PANTALLA_ANCHO, PosEnemigo- 32, -1f, 0f, imagen));

		}

//

		jugador.moverse(0,velJugador);
		jugador.pintarse(batch);


		for(ObjetoVolador f:Enemigos){
			f.moverse(velEnemigo,0);
			f.pintarse(batch);
		}

		probabilidad=(int)(Math.random()*100);
		if(probabilidad==20f){
			Rotos.add(new ObjetoVolador(PANTALLA_ANCHO,PosEnemigo-32,velBroken,0f,roto));
		}
		for(ObjetoVolador r:Rotos){
			r.moverse(velBroken,0);
			r.pintarse(batch);
		}



	}

	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
