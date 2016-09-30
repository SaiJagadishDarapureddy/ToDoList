package com.example.admin.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> arraylistToDo;
    private ArrayAdapter<String> arrayadapterToDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arraylistToDo=new ArrayList<String>();
        arrayadapterToDo=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylistToDo);
        ListView listviewToDo = (ListView)findViewById(R.id.listViewToDo);
        listviewToDo.setAdapter(arrayadapterToDo);
        registerForContextMenu(listviewToDo);
        

        try{
            Log.i("ON CREATE", "Hi, the ON CREATE has occured");

            Scanner scanner =new Scanner(openFileInput("ToDo.txt"));
            while (scanner.hasNextLine()){
                String toDo= scanner.nextLine();
                arrayadapterToDo.add(toDo);
            }
            scanner.close();

        }catch (Exception e){

            Log.i("ON CREATE",e.getMessage());
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId()!=R.id.listViewToDo){
            return;
        }
        menu.setHeaderTitle("What would you like to do?");
        String[] options = {"Delete Task","Return"};

                for(String option: options){
                    menu.add(option);
                }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int SelectedIndex = info.position;
        if(item.getTitle().equals("Delete Task")){
            arraylistToDo.remove(SelectedIndex);
            arrayadapterToDo.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        try {
            Log.i("ON BACK PRESSED", "Hi the On back event has occured");
            PrintWriter pw= new PrintWriter(openFileOutput("ToDo.txt", Context.MODE_PRIVATE));

            for(String toDo: arraylistToDo ){
                pw.println(toDo);
            }
            pw.close();

        }catch (Exception e){
            Log.i("ON BACK PRESSED", e.getMessage());
        }
    }

    public void buttonAddClick(View view){
        EditText editTextToDo= (EditText)findViewById(R.id.editTextToDo);
        String toDo=editTextToDo.getText().toString().trim();
        if(toDo.isEmpty()){
            return;
        }

        arrayadapterToDo.add(toDo);
        editTextToDo.setText("");
    }
}
