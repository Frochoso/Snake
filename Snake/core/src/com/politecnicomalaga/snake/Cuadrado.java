package com.politecnicomalaga.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cuadrado {

    public enum Direccion {ARRIBA, ABAJO, DERECHA, IZQUIERDA}

    ;

    //private static final String IMAGEN = "cuadrado.png";

    //Atributos
    protected Texture img;
    protected float posX, posY;
    protected int lado;

    //MÃ©todos
    public Cuadrado(float X, float Y, int l, Texture IMAGEN) {
        posX = X;
        posY = Y;
        lado = l;
        img = IMAGEN;
    }

    public Cuadrado(Cuadrado otro, Texture imagenCuerpo) {
        posX = otro.getPosX();
        posY = otro.getPosY();
        lado = otro.getLado();
        img = imagenCuerpo;
    }

    //Pintarse
    public void pintarse(SpriteBatch sb) {
        sb.begin();
        sb.draw(img, posX, posY, lado, lado);
        sb.end();
    }


    //Moverse
    public void moverse(Direccion dir) {
        switch (dir) {
            case ABAJO:
                posY = posY - lado;
                break;
            case ARRIBA:
                posY = posY + lado;
                break;
            case DERECHA:
                posX = posX + lado;
                break;
            case IZQUIERDA:
                posX = posX - lado;
                break;
        }
    }

    //Colisiona
    public boolean colisiona(Cuadrado otro) {
        return (otro.getPosX() == posX && otro.getPosY() == posY);
    }

    public boolean hitPowerUp(Cuadrado otro){
        boolean resultado,colisionX,colisionY;
        int HalfSquare=this.getLado()/2;
        colisionX = (Math.abs(posX - otro.getPosX()) <= (HalfSquare + HalfSquare));
        colisionY = (Math.abs(posY - otro.getPosY()) <= (HalfSquare + HalfSquare));
        resultado = colisionX && colisionY;

        return resultado;
    }


    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public int getLado() {
        return lado;
    }

    public void setImg(Texture img) {
        this.img = img;
    }
    public void setPosX(int posicionX){
        this.posX=posicionX;

    }
    public void setPosY(int posicionY){
        this.posY=posicionY;
    }

    public void dispose() {
//Aqui he borrado si la imagen es null
    }
}
