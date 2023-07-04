package com.sunmi.printerhelper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import com.sunmi.printerhelper.R;

public class Main extends AppCompatActivity {
    public static AlertDialog changeipdialog;
    AlertDialog.Builder changeipbuilder;
    View changeipview;
    EditText ip;
    String ipaddress;
    Button enter_pos;
    String default_ip="http://10.34.23.228:8069/web/login";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        changeipbuilder= new AlertDialog.Builder(Main.this,R.style.DialogipTheme);
        enter_pos=(Button) findViewById(R.id.enter_pos);
        changeipview = getLayoutInflater().inflate(R.layout.changeip, null);
        ip=(EditText) changeipview.findViewById(R.id.edit_text_ip);
        changeipbuilder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                changeipdialog.cancel();

            }
        });
        enter_pos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
               // Toast.makeText(getApplicationContext(),ipaddress,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Main.this,AllActivity.class);
                Bundle b=new Bundle();
                if(ipaddress != null){
                    intent.putExtra("ip",ipaddress);
                }else{
                    intent.putExtra("ip",default_ip);
                    }

                startActivity(intent);

            }
        });
        changeipbuilder.setPositiveButton("Change IP", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ipaddress = ip.getText().toString();
               // Toast.makeText(getApplicationContext(),ipaddress,Toast.LENGTH_SHORT).show();
                changeipdialog.cancel();

            }
        });
        changeipbuilder.setView(changeipview);
        changeipdialog= changeipbuilder.create();
        changeipdialog.setTitle("Change Ip Address");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.ip:
                changeipdialog.show();
        }
        return true;
    }
}