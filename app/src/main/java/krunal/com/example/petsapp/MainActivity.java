package krunal.com.example.petsapp;




import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import krunal.com.example.petsapp.Database.PetsEntity;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView mRecycleView;
    private MainActivityViewModel mMainActivityViewModel;
    private RecycleViewAdapter mRecycleViewAdapter;
    private ImageView mImageView;
    private TextView mTextView1,mTextView2;
    public static final String id = "id";
    // To get class name
    private static final String TAB1 = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mTextView1 = findViewById(R.id.textView);
        mTextView2 = findViewById(R.id.textView2);
        mImageView = findViewById(R.id.imageView);

        mTextView1.setVisibility(View.GONE);
        mTextView2.setVisibility(View.GONE);
        mImageView.setVisibility(View.GONE);

        // Initialization of MainActivityViewModel.
        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        mRecycleViewAdapter = new RecycleViewAdapter(this, petsModel -> {
            // Get id onClick and store in getid var.
            int getid = petsModel.getPet_Id();
            Log.i(TAB1,String.valueOf(getid));

            Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
            // put id in intent object.
            intent.putExtra(id,getid);
            //start UpdateActivity on click.
            startActivity(intent);
        });

        mRecycleView.setAdapter(mRecycleViewAdapter);

        fab =findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            // start AddActivity onClick.
            Intent intent =new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
        });

        // Get the List of Pets by name and Breed and observe.
        mMainActivityViewModel.getpetslist().observe(this, petsModels -> {
            // Show ic_empty_shelter.png image from drawable folder if there is no pets entry in Database.
            // And show text if there no pets entry.

            if (petsModels.size() == 0){
                mImageView.setVisibility(View.VISIBLE);
                mTextView1.setVisibility(View.VISIBLE);
                mTextView2.setVisibility(View.VISIBLE);
            }else if (petsModels.size() != 0){
                // if there is pet entry in database then hide imageView and TextView.
                mImageView.setVisibility(View.GONE);
                mTextView1.setVisibility(View.GONE);
                mTextView2.setVisibility(View.GONE);
            }

            // Add list pets name and Breed to RecycleView to display on ui.
            mRecycleViewAdapter.add(petsModels);

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_insert_dummy_data:
                Add();
                return true;
            case R.id.action_delete_all_entries:
                Delete();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    void Add(){
        String name = "joni";
        String Breed = "ldkf";
        int gender = 1;
        String weigth = "34";

        if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(Breed) && !TextUtils.isEmpty(weigth)){
            PetsEntity petsEntity = new PetsEntity(name,Breed,gender,weigth);
            mMainActivityViewModel.Add(petsEntity);
            Toast.makeText(this,getText(R.string.editor_insert_pet_successful),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,getText(R.string.editor_insert_pet_failed),Toast.LENGTH_SHORT).show();
        }
    }

    void Delete(){
        mMainActivityViewModel.DeleteAll();
        Toast.makeText(this,getText(R.string.editor_delete_pet_successful),Toast.LENGTH_SHORT).show();
    }


}
