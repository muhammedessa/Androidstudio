package org.codeforiraq.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.Listitem;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Listitem> listitems;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         listitems = new ArrayList<>();
        Listitem  item1 = new Listitem("Muhammed Essa" , "Java Developer", "36");
        Listitem  item2 = new Listitem("Ahmed Essa" , "Android Developer", "26");
        Listitem  item3 = new Listitem("Hassan Essa" , "Python Developer", "46");
        Listitem  item4 = new Listitem("Osama Essa" , "Ruby Developer", "44");
        Listitem  item5 = new Listitem("Omer Essa" , "Rail Developer", "22");
        Listitem  item6 = new Listitem("Khalid Essa" , "Flutter Developer", "55");
        Listitem  item7 = new Listitem("Ali Essa" , "Angular Developer", "25");
        Listitem  item8 = new Listitem("Wisam Essa" , "Kotlin Developer", "37");
        Listitem  item9 = new Listitem("Islam Essa" , "PHP Developer", "66");

        listitems.add(item1);
        listitems.add(item2);
        listitems.add(item3);
        listitems.add(item4);
        listitems.add(item5);
        listitems.add(item6);
        listitems.add(item7);
        listitems.add(item8);
        listitems.add(item9);

        adapter = new MyAdapter(this,listitems);
        recyclerView.setAdapter(adapter);
    }
}




////        for(int x =1 ; x< 12 ; x++){
////            Listitem listitem = new Listitem(
////                    "Muhammed" + (x+1),
////                    "Details",
////                    "23"
////            );

//            listitems.add(listitem);
//        }

