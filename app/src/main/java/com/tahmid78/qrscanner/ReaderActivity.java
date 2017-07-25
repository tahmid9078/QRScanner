package com.tahmid78.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    private TextView items;
    private Button send_btn;
    private ArrayList<String> selected_items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn=(Button) findViewById(R.id.scan_btn);
        items = (TextView) findViewById(R.id.items_view);
        final Activity activity=this;
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("scan");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });
        for (int i=0; i<selected_items.size(); i++){
            items.setText(selected_items.get(0));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

         if(result != null){
             if (result.getContents() == null){
                 Toast.makeText(this,"scanning is cancelled", Toast.LENGTH_LONG).show();
             }
             else {
                 Toast.makeText(this,result.getContents(), Toast.LENGTH_LONG).show();
                 selected_items.add(result.getContents());
                 String s="";
                 for (int i=0; i<selected_items.size(); i++){
                     s=s+selected_items.get(i)+"\n";

                 }
                 //showing the selected barcodeed items in the middle part
                 items.setText(s);
                 //sending the selected items to the next activity
                 send_btn=(Button) findViewById(R.id.send_btn);
                 final Intent intent=new Intent(this,BluetoothActivity.class);
                 intent.putExtra("myItems", selected_items);
                 send_btn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         startActivity(intent);
                     }
                 });
             }
         }

        else {
             super.onActivityResult(requestCode, resultCode, data);
         }
    }

}
