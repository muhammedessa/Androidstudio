package org.codeforiraq.myvolley.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.codeforiraq.myvolley.MainActivity;
import org.codeforiraq.myvolley.Model.Book;
import org.codeforiraq.myvolley.Model.User;
import org.codeforiraq.myvolley.R;
import org.codeforiraq.myvolley.SERVER.URLs;
import org.codeforiraq.myvolley.UI.EditData;
import org.codeforiraq.myvolley.UI.ShowActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    private Context context;
    private List<Book> bookList;



    public Adapter(Context context, List<Book> list ) {
        this.context = context;
        bookList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_content,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
       final Book book = bookList.get(position);
       holder.name.setText(book.getName());
       holder.timestamp.setText(formatDate(book.getCreated_at()));
       holder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               deleteData(position, book.getId() );
           }
       });
       holder.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(context,String.valueOf(book.getId()),Toast.LENGTH_LONG).show();

               Intent intent = new Intent(context, EditData.class);
               intent.putExtra("id",book.getId());
               intent.putExtra("name",book.getName());
               intent.putExtra("author",book.getAuthor());

               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name,timestamp;
        public ImageView delete,edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.nameTextView);
            timestamp = itemView.findViewById(R.id.timeTextView);
            delete  = itemView.findViewById(R.id.deleteIcon);
            edit   = itemView.findViewById(R.id.editIcon);
        }

        @Override
        public void onClick(View v) {
//            int position = getAdapterPosition();
//            Book item = bookList.get(position);
//
//            Toast.makeText(context, URLs.URL_UPDATE_DATA+item.getId()
//                 , Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(context, ShowActivity.class);
//            intent.putExtra("id",item.getId());
//            intent.putExtra("name",item.getName());
//            intent.putExtra("author",item.getAuthor());
//
//            context.startActivity(intent);
        }
    }




    private String formatDate(String dateStr){
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut  = new SimpleDateFormat("MMM d");
            return  fmtOut.format(date);
        } catch (ParseException e) {
            Log.e("error", e.getMessage());
        }
        return "";
    }


    private  void deleteData(int position, int id){

//        Toast.makeText(context, URLs.URL_DELETE_DATA+id
//                , Toast.LENGTH_SHORT).show();

      final String  token = SessionManager.getInstance(context).getToken().getToken() ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URLs.URL_DELETE_DATA+id, (JSONObject) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    if(response.getBoolean("success")){
                        Log.d("message", response.getString("message"));
                    }else {
                        Log.d("message", "sorry");
                    }



                }catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer   "+token);
                return params;
            }
        };



        //  queue.add(jsonObjectRequest);
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        bookList.remove(position);
        MainActivity.notifyAdapter();
    }



    }




