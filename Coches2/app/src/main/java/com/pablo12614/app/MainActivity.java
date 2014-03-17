package com.pablo12614.app;

import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {
    private Menu menu;
    private ListView lista;
    private ImageButton b_add;
    int indice=0;
    String[] values;
    Date[] fechas;
    Cursor cursor_1;
    bd bd_1 = new bd(this);
    int locked = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lista = (ListView) findViewById(R.id.listView);
        lista.setLongClickable(true);
        //this.b_add = (ImageButton) findViewById(R.id.button);

        registerForContextMenu(lista);
        RefrescarConBD();

        /*lista.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String itemValue = (String) ((TextView) view).getText();
                //String itemValue = (String) lista.getAdapter().getItemAtPosition(1);

                // Show Alert
                //Toast.makeText(getApplicationContext(),"Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
                bd_1.borrarNombre(itemValue);
                Date now = new Date();
                bd_1.insertarUsuario(1,itemValue);
                Toast.makeText(getApplicationContext()," "+itemValue+" ha sido enviado a la última posición." , Toast.LENGTH_SHORT).show();

                //bd_1.updateFechaActual(itemValue);
                RefrescarConBD();
            }

        });*/



        /*b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ed = (EditText) findViewById(R.id.editText);
                String lname = ed.getText().toString();
                Toast.makeText(getApplicationContext(), " " + lname + " ha sido añadido a la lista.", Toast.LENGTH_SHORT).show();
                ed.setText("");
                bd_1.insertarUsuario(1, lname);
                RefrescarConBD();
            }
        });*/
    }
        /*
        if (sa == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        */

    public void myClickHandler(View v)
    {
        if(locked == 0)
        {
            Button bt = (Button) v;
            String nombre = (String) bt.getText();
            bd_1.borrarNombre(nombre);
            Date now = new Date();
            bd_1.insertarUsuario(1,nombre);
            Toast.makeText(getApplicationContext()," "+nombre+" ha sido enviado a la última posición." , Toast.LENGTH_SHORT).show();

            //bd_1.updateFechaActual(itemValue);
            RefrescarConBD();
        }
        else
            Toast.makeText(getApplicationContext(),"La lista se encuentra bloqueada" , Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.listView)
        {
            AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle("Acciones");

            inflater.inflate(R.menu.menu_ctx_etiqueta, menu);
        }
        //inflater.inflate(R.menu.menu_ctx_etiqueta, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String campo = (String) ((TextView) info.targetView).getText();
        if(locked == 0)
        {
            switch (item.getItemId()) {
                case R.id.CtxLblOpc1:
                    bd_1.borrarNombre(campo);
                    Date now = new Date();
                    bd_1.insertarUsuario(1,campo);
                    Toast.makeText(getApplicationContext()," "+campo+" ha sido enviado a la última posición." , Toast.LENGTH_SHORT).show();
                    RefrescarConBD();
                    return true;
                case R.id.CtxLblOpc2:
                    bd_1.borrarNombre(campo);
                    Toast.makeText(getApplicationContext()," "+campo+" ha sido borrado de la lista." , Toast.LENGTH_SHORT).show();
                    RefrescarConBD();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        else

            Toast.makeText(getApplicationContext(),"La lista se encuentra bloqueada" , Toast.LENGTH_SHORT).show();
        return true;
    }

    private void Refrescar()
    {
        this.lista = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);
        lista.setAdapter(adapter);
    }
    private void RefrescarConBD()
    {
        /*SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);*/
        cursor_1 = bd_1.leerLista();
        startManagingCursor(cursor_1);
        values = new String[]{"nombre"};
        int[] to = new int[]{R.id.text};
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor_1,values,to);
        lista.setAdapter(cursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.main, menu);
        return true;*/

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_settings:
            {

                return true;
            }
            case R.id.action_share:
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ObtenerListado());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            }
            case R.id.action_new:
            {
                if(locked == 0)
                {
                    EditText ed = (EditText) findViewById(R.id.editText);
                    String lname = ed.getText().toString();
                    Toast.makeText(getApplicationContext(), " " + lname + " ha sido añadido a la lista.", Toast.LENGTH_SHORT).show();
                    ed.setText("");
                    bd_1.insertarUsuario(1, lname);
                    RefrescarConBD();
                }
                else
                    Toast.makeText(getApplicationContext(),"La lista se encuentra bloqueada" , Toast.LENGTH_SHORT).show();

            }
            case R.id.action_secure:
                if(locked == 0)
                {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_secure));
                    locked = 1;
                }
                else
                {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_action_not_secure));
                    locked = 0;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String ObtenerListado()
    {
        ArrayList<String> arrlist=new ArrayList<String>();
        ListAdapter la = lista.getAdapter();
        String texto="";


        if (cursor_1 != null ) {
            if  (cursor_1.moveToFirst()) {
                texto+= "Orden de conductores:\n";
                texto+= "-------------------\n";
                do {
                    String firstName = cursor_1.getString(cursor_1.getColumnIndex("nombre"));
                    texto += cursor_1.getPosition()+1+". "+firstName+"\n";
                }while (cursor_1.moveToNext());
            }
            texto+= "-------------------";
        }
        else
            texto = "No existen datos en la lista";
        return texto;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
