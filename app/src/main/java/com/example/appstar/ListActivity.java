package com.example.appstar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstar.adapter.TeamAdapter;
import com.example.appstar.beans.Team;
import com.example.appstar.services.ServiceTeam;

public class ListActivity extends AppCompatActivity {

    private TeamAdapter teamAdapter = null;
    private ServiceTeam service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        service = ServiceTeam.getInstance();
        init();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        teamAdapter = new TeamAdapter(service.findAll(), this);
        recyclerView.setAdapter(teamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ///////////////////////

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Toast.makeText(getApplicationContext(), "ff"+viewHolder.getAdapterPosition(), Toast.LENGTH_LONG).show();
                service.delete(service.findById(2));
                teamAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerView);
    }

    public void init(){
        service.create(new Team("Man united","https://logoeps.com/wp-content/uploads/2011/08/manchester-united-logo-vector.png","Premier League","Manchester United Football Club is a professional football club based in Old Trafford, Greater Manchester, England, that competes in the Premier League, the top flight of English football. Nicknamed the Red Devils, the club was founded as Newton Heath LYR Football Club in 1878, but changed its name to Manchester United in 1902. The club moved from Newton Heath to its current stadium, Old Trafford, in 1910",4.5f));
        service.create(new Team("Man city","https://seeklogo.com/images/M/manchester-city-fc-new-logo-4C45133019-seeklogo.com.png","Premier League","Manchester City Football Club is an English football club based in Manchester that competes in the Premier League, the top flight of English football. Founded in 1880 as St. Mark's (West Gorton), it became Ardwick Association Football Club in 1887 and Manchester City in 1894. The club's home ground is the Etihad Stadium in east Manchester, to which it moved in 2003, having played at Maine Road since 1923. The club adopted their sky blue home shirts in 1894 in the first season of the club's current iteration, that have been used ever since.[4] In terms of trophies won, it is the fifth-most successful club in English football",5f));
        service.create(new Team("Liverpool","https://logoeps.com/wp-content/uploads/2011/08/liverpool-logo-vector.png","Premier League","Liverpool Football Club is a professional football club based in Liverpool, England, that competes in the Premier League, the top tier of English football. Domestically, the club has won nineteen League titles, seven FA Cups, a record nine League Cups and fifteen FA Community Shields. In international competitions, the club has won six European Cups, more than any other English club, three UEFA Cups, four UEFA Super Cups, and one FIFA Club World Cup. In terms of trophies won, it is the joint-most successful club in English football",5f));
        service.create(new Team("Chelsea","https://logoeps.com/wp-content/uploads/2011/08/chelsea-logo-vector.png","Premier League","Chelsea Football Club is an English professional football club based in Fulham, West London. Founded in 1905, the club competes in the Premier League, the top division of English football. Domestically, the club has won six league titles, eight FA Cups, five League Cups, and four FA Community Shields. Internationally, Chelsea are the only English team to have won the three active major UEFA trophies. They have won two European Cups, two UEFA Cup Winners' Cups, two UEFA Cups, two UEFA Super Cups, and one FIFA Club World Cup. In terms of trophies won, it is the fourth-most successful club in English football",4.75f));
        service.create(new Team("NewCastle united","https://logoeps.com/wp-content/uploads/2011/08/newcastle-united-fc-logo.jpg","Premier League","Arsenal was the first club from the South of England to join the Football League in 1893, and they reached the First Division in 1904. Relegated only once, in 1913, they continue the longest streak in the top division,[6] and have won the second-most top-flight matches in English football history.",3.5f));
        service.create(new Team("Spurs","https://logoeps.com/wp-content/uploads/2012/02/tottenham-hotspur-fc-logo-vector.jpg","Premier League","Arsenal was the first club from the South of England to join the Football League in 1893, and they reached the First Division in 1904. Relegated only once, in 1913, they continue the longest streak in the top division,[6] and have won the second-most top-flight matches in English football history.",3.5f));
        service.create(new Team("Aston villa","https://logoeps.com/wp-content/uploads/2011/06/77ab52085ff2cda2808f8aaeedd6b01d-200x200.jpg","Premier League","Arsenal was the first club from the South of England to join the Football League in 1893, and they reached the First Division in 1904. Relegated only once, in 1913, they continue the longest streak in the top division,[6] and have won the second-most top-flight matches in English football history.",2.5f));
        service.create(new Team("West Brom","https://logoeps.com/wp-content/uploads/2012/10/west-brom-logo-vector.png","Premier League","Arsenal was the first club from the South of England to join the Football League in 1893, and they reached the First Division in 1904. Relegated only once, in 1913, they continue the longest streak in the top division,[6] and have won the second-most top-flight matches in English football history.",1f));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if (teamAdapter != null){
                    teamAdapter.getFilter().filter(newText);
                }
                return true;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Premier League's Teams";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Premier League's Teams")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }



}