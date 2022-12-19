package com.politecnicomalaga.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Serpiente {

    //Atributos
    ArrayList<Cuadrado> miCuerpo;
    Cuadrado.Direccion miDireccion;
    Texture imagenCuadrado;
    Texture imagenCabeza;

    //MÃ©todos
    public Serpiente() {
        miCuerpo = new ArrayList<Cuadrado>();
        miDireccion = Cuadrado.Direccion.ARRIBA;

        imagenCuadrado = queColorTieneLaSerpiente();
        imagenCabeza = new Texture("Gorilla.png");

        Cuadrado cabeza = new Cuadrado(200, 200, 20, imagenCabeza);
        miCuerpo.add(cabeza);

    }

    //moverse
    public void moverse() {
        this.crecer();
        Cuadrado elemento = miCuerpo.remove(miCuerpo.size() - 1);
        elemento.dispose();
    }


    //crecer
    public void crecer() {
        Cuadrado nuevaCabeza, cabeza;


        cabeza = miCuerpo.get(0);
        nuevaCabeza = new Cuadrado(cabeza, imagenCabeza);
        nuevaCabeza.moverse(miDireccion);

        miCuerpo.add(0, nuevaCabeza);
        if (miCuerpo.size() - 1 >= 1) {
            miCuerpo.get(1).setImg(imagenCuadrado);
        }


    }

    public void pintarse(SpriteBatch sb) {
        for (Cuadrado c : miCuerpo) {
            c.pintarse(sb);
        }
    }

    public boolean colisiona() {
        Cuadrado cabeza = miCuerpo.get(0);
        for (int i = 4; i < miCuerpo.size(); i++) {
            if (cabeza.colisiona(miCuerpo.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean snakeHitPowerUp(PowerUp powerUp){
        Cuadrado cabeza = miCuerpo.get(0);
        if(cabeza.hitPowerUp(powerUp)){
            return true;
        }
        return false;
    }


    public boolean estaDentro(int limiteMinX, int limiteMaxX, int limiteMinY, int limiteMaxY) {
        Cuadrado cabeza = miCuerpo.get(0);
        return (cabeza.getPosX() > limiteMinX && cabeza.getPosX() < limiteMaxX - cabeza.getLado() &&
                cabeza.getPosY() > limiteMinY && cabeza.getPosY() < limiteMaxY - cabeza.getLado());
    }

    public float getCabezaX() {
        return miCuerpo.get(0).getPosX();
    }


    public float getCabezaY() {
        return miCuerpo.get(0).getPosY();
    }

    public void cambiaDir(Cuadrado.Direccion nuevaDir) {
        miDireccion = nuevaDir;
    }

    public void dispose() {
        for (Cuadrado c : miCuerpo) {
            c.dispose();
        }
    }

    public Texture queColorTieneLaSerpiente() {
        Texture textureDefinido;
        String pathTexture = "hola";
        int numeroColor = (int) (Math.random() * 3 + 1);
        //System.out.println(numeroColor); Era para probar que salen numeros aleatorios
        if (numeroColor == 1) {
            //Color azul
            pathTexture = "Gorilla.png";
        } else if (numeroColor == 2) {
            //Color rojo
            pathTexture = "cuadrado.jpg";
        } else {
            //El normal
            pathTexture = "cuadrado.jpg";
        }
        textureDefinido = new Texture(pathTexture);
        return textureDefinido;
    }
}
