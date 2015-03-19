package com.example.kdiaziglesias.calendariofepv2;

import android.content.ClipData;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class JuegodeColocacion extends ActionBarActivity implements View.OnTouchListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegode_colocacion);

        findViewById(R.id.imagenView).setOnTouchListener(this);
        findViewById(R.id.imagenView2).setOnTouchListener(this);
        findViewById(R.id.imagenView6).setOnTouchListener(this);
        findViewById(R.id.imageView0).setOnTouchListener(this);
        findViewById(R.id.imageView5).setOnTouchListener(this);
        findViewById(R.id.imagenView3).setOnTouchListener(this);
        findViewById(R.id.imageView11).setOnTouchListener(this);
        findViewById(R.id.imageView8).setOnTouchListener(this);
        findViewById(R.id.imageView9).setOnTouchListener(this);
        findViewById(R.id.imageView10).setOnTouchListener(this);




        View superior = findViewById(R.id.sup);
        View inferior = findViewById(R.id.inf);
        View fuego = findViewById(R.id.cataluña);
        View agua = findViewById(R.id.andalucia);
        View electrico = findViewById(R.id.asturias);
        View planta = findViewById(R.id.galicia);
        superior.setOnDragListener(new MiDrag());
        inferior.setOnDragListener(new MiDrag());
        fuego.setOnDragListener(new MiDrag());
        agua.setOnDragListener(new MiDrag());
        electrico.setOnDragListener(new MiDrag());
        planta.setOnDragListener(new MiDrag());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juegode_colocacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onTouch(View objeto, MotionEvent evento) {

        if (evento.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData datos = ClipData.newPlainText("", "");
            ;
            View.DragShadowBuilder objeto_sombra = new View.DragShadowBuilder(objeto);
            objeto.startDrag(datos, objeto_sombra, objeto, 0);
            objeto.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }

    final class MiDrag implements View.OnDragListener {


        public boolean onDrag(View contenedor_nuevo, DragEvent evento) {
            switch (evento.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View objeto = (View) evento.getLocalState();
                    ViewGroup papa_objeto = (ViewGroup) objeto.getParent();
                    papa_objeto.removeView(objeto);

                    LinearLayout contenedor_nuevo2 = (LinearLayout) contenedor_nuevo;
                    contenedor_nuevo2.addView(objeto);

                    objeto.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }


    }


}
