package com.politecnicomalaga.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxSnake extends ApplicationAdapter {
	SpriteBatch batch;
	Texture imgInicio, imgJugando, imgFinal;

	enum Pantallas {INICIAL, JUGANDO, FINAL}

	;

	Pantallas miPantallaActiva;
	Serpiente vibora;
	PowerUp powerUp;
	int iPasos, secondsIterator;
	boolean inmortal,visible;

	@Override
	public void create() {
		batch = new SpriteBatch();
		imgInicio = new Texture("badlogic.jpg");
		imgJugando = new Texture("fondo.jpg");
		imgFinal = new Texture("muerte.jpg");
		miPantallaActiva = Pantallas.INICIAL;
		vibora = new Serpiente();
		powerUp = new PowerUp();
		iPasos = 0;
		secondsIterator = 0;
		inmortal = false;
		visible=true;
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		switch (miPantallaActiva) {
			case INICIAL:
				pantallaInicial();
				break;
			case JUGANDO:
				pantallaJugando();
				break;
			case FINAL:
				pantallaFinal();
				break;
		}

	}

	//MÃ©todos de trabajo de cada una de las pantallas
	private void pantallaInicial() {
		batch.begin();
		batch.draw(imgInicio, 0, 0);
		batch.end();

		if (Gdx.input.justTouched()) {
			miPantallaActiva = Pantallas.JUGANDO;
			vibora.dispose();
			vibora = new Serpiente();
		}
	}

	private void pantallaJugando() {
		int pixelX, pixelY; //Los pixeles "clicados" en la pantalla
		float cabX, cabY;
		//Jugando

		//Comprobamos entrada
		if (Gdx.input.justTouched()) {
			//han tocado la pantalla
			pixelX = Gdx.input.getX();
			pixelY = Gdx.graphics.getHeight() - Gdx.input.getY();

			//Calculamos con respecto a la cabeza a donde nos dirigimos
			cabX = vibora.getCabezaX();
			cabY = vibora.getCabezaY();

			if (Math.abs(pixelX - cabX) > Math.abs(pixelY - cabY)) {
				//horizontal
				if (pixelX > cabX) {
					vibora.cambiaDir(Cuadrado.Direccion.DERECHA);
				} else {
					vibora.cambiaDir(Cuadrado.Direccion.IZQUIERDA);
				}
			} else {
				//vertical
				if (pixelY > cabY) {
					vibora.cambiaDir(Cuadrado.Direccion.ARRIBA);
				} else {
					vibora.cambiaDir(Cuadrado.Direccion.ABAJO);
				}
			}

		}
		secondsIterator++;
		iPasos++;
		if (secondsIterator%1200==0 && visible==true){
			powerUp.setPosY(Gdx.graphics.getHeight()+1000);
		}
		if (secondsIterator % 2400 == 0) {
			powerUp.setPosX((int) ((Math.random() * (Gdx.graphics.getWidth()-100)) + 1));
			powerUp.setPosY((int) ((Math.random() * (Gdx.graphics.getHeight()-100)) + 1));
			visible=true;
		}

		if (secondsIterator % 600 ==0){
			inmortal=false;
		}
		//Simulamos el mundo
		if (iPasos % 10 == 0) {
			vibora.moverse();
			if (vibora.snakeHitPowerUp(powerUp) == true) {
				inmortal = true;
				powerUp.setPosY(Gdx.graphics.getHeight()+1000);
				visible=false;
				System.out.println("Siiii");
			}


			//Nos hemos estrellado???
			if(!vibora.estaDentro(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight())){
				miPantallaActiva= Pantallas.FINAL;
			}
			if ((vibora.colisiona()) && inmortal == false) {
				miPantallaActiva = Pantallas.FINAL;
			}

		}
		if (iPasos == 29) {
			iPasos = 0;
			vibora.crecer();
			//Nos hemos estrellado???
			if(!vibora.estaDentro(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight())){
				miPantallaActiva= Pantallas.FINAL;
			}
			if ((vibora.colisiona()) && inmortal == false) {
				miPantallaActiva = Pantallas.FINAL;
			}
		}


		//Pintamos
		vibora.pintarse(batch);
		powerUp.pintarse(batch);


	}

	private void pantallaFinal() {
		batch.begin();
		batch.draw(imgFinal, 0, 0);
		batch.end();

		if (Gdx.input.justTouched()) {
			miPantallaActiva = Pantallas.INICIAL;
		}

	}


	@Override
	public void dispose() {
		batch.dispose();
		imgInicio.dispose();
		imgJugando.dispose();
		imgFinal.dispose();
		vibora.dispose();
		powerUp.dispose();
	}
}
