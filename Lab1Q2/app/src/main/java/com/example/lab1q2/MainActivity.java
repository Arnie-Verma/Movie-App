package com.example.lab1q2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.Bundle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lab1q2.provider.FirebaseMovie;
import com.example.lab1q2.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import com.example.lab1q2.provider.Movie;


public class MainActivity extends AppCompatActivity {
    //**VARIABLE DECLARATION**//
    private EditText title, year, country, genre, cost, keywords;
    final String TAG = "DEBUG_LOGCAT";
    SharedPreferences sP;




    final String KEY_NAME0 = "KEY_NAME0", KEY_NAME1 = "KEY_NAME1", KEY_NAME2 = "KEY_NAME2", KEY_NAME3 = "KEY_NAME3", KEY_NAME4 = "KEY_NAME4", KEY_NAME5 = "KEY_NAME5", KEY_NAME6 = "KEY_NAME6";
    final String SP_FILE_NAME = "data.dat";
    final String Database_KEY="Database_KEY";

    //V5
    private DrawerLayout drawerlayout;
    private NavigationView navigationView;
    Toolbar toolbar;
    ListView listView;
    ArrayList<String> dataSource;
    ArrayAdapter<String> adapter;
    static int counter;

/////V6/////////
    ArrayList<Movie> dataBox;
    Gson gson = new Gson();
//V7//
    private MovieViewModel movieVM;
    private List<Movie> movie;

    //V10//
    View touchView;
    final String TAGs = "WEEK10";
   int x ,y;

   //V11//
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        title = findViewById(R.id.editTextTextPersonName16); //IDENTIFIES UI ELEMENT
        year = findViewById(R.id.editTextNumber3);
        country = findViewById(R.id.editTextTextPersonName20);
        genre = findViewById(R.id.editTextTextPersonName21);
        cost = findViewById(R.id.editTextNumber4);
        keywords = findViewById(R.id.editTextTextMultiLine);
        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        //WEEK 5
        drawerlayout = findViewById(R.id.drawer_layout_id);
        navigationView = findViewById(R.id.nav_id);
        toolbar = findViewById(R.id.toolbar_id);
        listView = findViewById(R.id.listview_id);
        setSupportActionBar(toolbar); // tells activity we want to use features of toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerlayout.addDrawerListener(toggle);//lets java know that you are adding to the drawer
        toggle.syncState();//tell java to update
        navigationView.setNavigationItemSelectedListener(new NavHandler());


        dataSource=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSource);
        listView.setAdapter(adapter);

        dataBox= new ArrayList<Movie>();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovieToList();
            }
        });

        movieVM= new ViewModelProvider(this).get(MovieViewModel.class);
        movieVM.getMovies().observe(this, newData -> {
            movie=newData;
        });


        //V8

        Log.d(TAG, "onCreate");//USED FOR DEBUGGING

/*        //V10

        touchView=findViewById(R.id.view);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x2, y2=0;
                Log.d(TAGs,"ADDMOVIE");
                //motionevent is an object used to report movement
                int action = event.getActionMasked(); // returns an integer number that represents the event
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) : //actiondown when the touch first occurs, check if actiondown matches action
                        x=(int) event.getX();
                        y=(int) event.getY();
                        return true; //touchevent handled
                    case (MotionEvent.ACTION_UP) : //actionup when the touch is lifted
                        if(event.getX()-x>150 && event.getY()-y<50){ //get absolute coordinates relative to view
                            displayMessage(view); //LEFT TO RIGHT
                        }
                        else if(event.getY()-y>150 &&event.getX()-x<50){
                            clearAll(view);   //TOP TO BOTTOM
                        }else if(x-event.getX()<150 && y-event.getY()>50){
                            setDefault();    //bottom to top
                        }else if(y-event.getY()<150 && x-event.getX()>50){
                            movieVM.deleteCostlyMovies();//right to left
                            Toast.makeText(MainActivity.this,"deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    default :
                        return false; //event not handled

            }
        }});*/



        gestureDetector=new GestureDetector(this, new GestureHandler());
        View touchView=findViewById(R.id.view);
        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });



    }

    class GestureHandler extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            setDefault();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            costIncrement();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onClear();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            moveTaskToBack(true);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(distanceX<distanceY){
                int tempsCost = Integer.parseInt(cost.getText().toString());
                int total;
                total = (int) (tempsCost-distanceX);
                String s = String.valueOf(total);
                cost.setText(s);
            } else if(distanceY<distanceX){
                int tempsCost = Integer.parseInt(cost.getText().toString());
                int total;
                total = (int) (tempsCost-distanceX);
                String s = String.valueOf(total);
                cost.setText(s);
            }
            return true;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.options_menu_clear_all_id){
            clearAllMovies();
        }
        return true;
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon*/

            StringTokenizer sT = new StringTokenizer(msg, ";");
            String titleToken = sT.nextToken();
            String yearToken = sT.nextToken();
            String durationToken = sT.nextToken();
            String countryToken = sT.nextToken();
            String genreToken = sT.nextToken();
            String costToken = sT.nextToken();
            String keywordsToken = sT.nextToken();

            int dur = Integer.parseInt(durationToken);
            int intYear = Integer.parseInt(yearToken);
            int total = dur+intYear;


            /* update the UI */
            title.setText(titleToken);
            year.setText("year:"+ yearToken + " duration: "+ durationToken + " new year: "+total);
            country.setText(countryToken);
            genre.setText(genreToken);
            cost.setText(costToken);
            keywords.setText(keywordsToken);
        }
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) { //SAVES VIEW DATA
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) { //RESTORES VIEW DATA
        super.onRestoreInstanceState(savedInstanceState);
        String titleCaps = title.getText().toString().toUpperCase(); //CHANGES TITLE TO UPPERCASE
        title.setText(titleCaps);
        String genreCaps = genre.getText().toString().toLowerCase(); //CHANGES GENRE TO LOWERCASE
        genre.setText(genreCaps);
        Log.d(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        sP = getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE); //SHARED PREFERENCES INITIALISATION
        String theTitle = sP.getString(KEY_NAME0, ""); //ACCESS SHARED PREFERENCES WITH KEY
        title.setText(theTitle); //SET THE TITLE WITH CONTENT FROM SHARED PREFERENCES
        String theYear = sP.getString(KEY_NAME1,"" );
        year.setText(theYear);
        String theCountry = sP.getString(KEY_NAME2,"");
        country.setText(theCountry);
        String theGenre = sP.getString(KEY_NAME3,"");
        genre.setText(theGenre);
        String theCost = sP.getString(KEY_NAME4,"");
        cost.setText(theCost);
        String theKeyword = sP.getString(KEY_NAME5,"");
        keywords.setText(theKeyword);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String aTitle = title.getText().toString(); //GETS TEXT FROM TITLE CONVERTS TO STRING AND PLACES IN VARIABLE
        String aYear = year.getText().toString();
        String aCountry = country.getText().toString();
        String aGenre = genre.getText().toString();
        String aCost = cost.getText().toString();
        String aKeyword = keywords.getText().toString();

        SharedPreferences.Editor editor = sP.edit(); //ALLOWS FOR EDITING
        editor.putString(KEY_NAME0, aTitle); //PUTS THE STRING INTO STORAGE
        editor.putString(KEY_NAME1, aYear);
        editor.putString(KEY_NAME2, aCountry);
        editor.putString(KEY_NAME3, aGenre);
        editor.putString(KEY_NAME4, aCost);
        editor.putString(KEY_NAME5, aKeyword);

        editor.apply();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    public void displayMessage(View view){ //USED FOR "ADD MOVIE" BUTTON
        String enteredTextByUser = title.getText().toString();
        Toast.makeText(getApplicationContext(), "Movie - "+enteredTextByUser+" - has been added", Toast.LENGTH_LONG).show();
        dataSource.add(title.getText().toString() + " | " +year.getText().toString());
        adapter.notifyDataSetChanged();


        String movieD = title.getText().toString();
        String yearD = year.getText().toString();
        String countryD = country.getText().toString();
        String genreD = genre.getText().toString();
        String costD = cost.getText().toString();
        String keywordD = keywords.getText().toString();
     //   String costUSD = String.valueOf(Float.valueOf(cost.getText().toString())*0.75);
        Movie movie = new Movie(movieD,yearD,countryD,genreD,costD,keywordD);
        movieVM.insertNewMovie(movie);


        //V8
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("/Movie/added Movie");
        reference.push().setValue(new FirebaseMovie(movieD,yearD,countryD,genreD,costD,keywordD));

        int tempCost = Integer.parseInt(costD);
        if(tempCost>40){
            database = FirebaseDatabase.getInstance();
            reference=database.getReference("/Movie/bigBudget");
            reference.push().setValue(new FirebaseMovie(movieD,yearD,countryD,genreD,costD,keywordD));

        }


    }


    public void clearAll (View view){ //USED FOR "CLEAR" BUTTON
        dataSource.clear();
        adapter.notifyDataSetChanged();

        title.getText().clear(); //ACCESSES TEXT FROM TEXT-BOX AND CLEARS IT
        year.getText().clear();
        country.getText().clear();
        genre.getText().clear();
        cost.getText().clear();
        keywords.getText().clear();

    }

    class NavHandler implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.drawer_layout_add_movie_id) {
                addMovieToList();
            } else if (id == R.id.drawer_layout_remove_last_movie_id) {
                removeLastMovie();
            }else if (id == R.id.drawer_layout_remove_all_movies_id) {
                removeAllMovies();
            } else if (id == R.id.drawer_layout_list_all_movies_id) {
                listAllMovies();
            }else if (id == R.id.drawer_layout_delete_old_movies) {
                deleteOldMovies();
            }else if (id == R.id.drawer_layout_delete_costly_movies) {
                deleteCostlyMovies();
            }

            // close the drawer
            drawerlayout.closeDrawers();
            // tell the OS
            return true;
        }
    }


    private void addMovieToList(){
        dataSource.add(title.getText().toString() + " | " +year.getText().toString());
        showSnackBar();
        adapter.notifyDataSetChanged();
    }

    private void removeLastMovie(){
        if (dataSource.size()>0){
            dataSource.remove(dataSource.size()-1);
            adapter.notifyDataSetChanged();
        }
    }
    private void removeAllMovies(){
            movieVM.deleteAll();
// V8
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.setValue(null);
        }


    private void listAllMovies(){
        String dataStr = gson.toJson(dataBox);
        SharedPreferences.Editor editor=sP.edit();
        editor.putString(Database_KEY, dataStr);
        editor.apply();
        Intent newIntent = new Intent(MainActivity.this, RecyclerActivity.class);
        newIntent.putExtra("JSON", dataStr);
        startActivity(newIntent);
    }

    private void deleteOldMovies(){
        dataSource.clear();
        adapter.notifyDataSetChanged();
        movieVM.deleteOldMovies();
    }
    private void deleteCostlyMovies(){
        dataSource.clear();
        adapter.notifyDataSetChanged();
        movieVM.deleteCostlyMovies();
    }



    private void clearAllMovies(){
        dataSource.clear();
        adapter.notifyDataSetChanged();

        title.getText().clear(); //ACCESSES TEXT FROM TEXT-BOX AND CLEARS IT
        year.getText().clear();
        country.getText().clear();
        genre.getText().clear();
        cost.getText().clear();
        keywords.getText().clear();
    }



    private void showSnackBar(){
        Snackbar.make(drawerlayout, "SnackBar", Snackbar.LENGTH_LONG).setAction("Defaults", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText(" "); //ACCESSES TEXT FROM TEXT-BOX AND CLEARS IT
                year.setText("0");
                country.setText(" ");
                genre.setText(" ");
                cost.setText("0");
                keywords.setText(" ");
                dataSource.clear();
                adapter.notifyDataSetChanged();
            }
        }).show();
    }

    public void setDefault() {
        title.setText("default"); //ACCESSES TEXT FROM TEXT-BOX AND CLEARS IT
        year.setText("0");
        country.setText("default");
        genre.setText("default");
        cost.setText("0");
        keywords.setText("default");
    }


    private void costIncrement(){
        int tempsCost = Integer.parseInt(cost.getText().toString());
        int total;
        total = tempsCost+150;
        String s = String.valueOf(total);
        cost.setText(s);
    }

    public void onClear() {
        title.setText(""); //ACCESSES TEXT FROM TEXT-BOX AND CLEARS IT
        year.setText("");
        country.setText("");
        genre.setText("");
        cost.setText("");
        keywords.setText("");
    }


}

