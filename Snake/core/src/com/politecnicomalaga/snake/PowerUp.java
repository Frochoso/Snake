package com.politecnicomalaga.snake;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerUp extends Cuadrado{
    protected Texture img;
    static float posX=(float)(Math.random()*850+1),posY=(float)(Math.random()*850+1);
    public PowerUp(){
        super(posX,posY,20,new Texture("obstaculo.png"));
    }
    @Override
    public void dispose(){



    }
}
