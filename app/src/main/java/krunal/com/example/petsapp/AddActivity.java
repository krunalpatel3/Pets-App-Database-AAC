package krunal.com.example.petsapp;


import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import krunal.com.example.petsapp.Database.PetsEntity;
import krunal.com.example.petsapp.Database.Repository;


public class AddActivity extends AppCompatActivity {

    private EditText mEditName,mEditBreed,mEditWeight;
    private AddActivityViewModel mAddActivityViewModel;
    private Spinner mGender;
    // To get class name
    private static final String TAB = AddActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mGender = findViewById(R.id.spinner_gender);
        mEditName = findViewById(R.id.edit_pet_name);
        mEditBreed = findViewById(R.id.edit_pet_breed);
        mEditWeight = findViewById(R.id.edit_pet_weight);

        // Initialization of Spinner for Gender.
        SetupSpinner();

        // Initialization of AddActivityViewModel
        mAddActivityViewModel = ViewModelProviders.of(this).get(AddActivityViewModel.class);


    }

    /**
     * Menu is Created from add_menu.xml file.
     * @param menu
     * @return true.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    /**
     * Triggered the action base upon the menu option click.
     * @param item
     * @return true.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save:
                SavePet();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * SetupSpinner for Pets Gender.
     */
    private void SetupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGender.setAdapter(genderSpinnerAdapter);
        mGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)){
                    if (selection.equals(getString(R.string.gender_male))){
                        AddActivityViewModel.GENDER = Repository.GENDER_MALE;
                    }else if (selection.equals(getString(R.string.gender_female))){
                        AddActivityViewModel.GENDER = Repository.GENDER_FEMALE;
                    }else {
                        AddActivityViewModel.GENDER = Repository.GENDER_UNKNOWN;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AddActivityViewModel.GENDER = Repository.GENDER_UNKNOWN;
            }
        });

    }

    /**
     * save Pet into the database in Pets_tbl.
     */
    void SavePet(){


        String petname = mEditName.getText().toString().trim();
        String petBreed = mEditBreed.getText().toString().trim();
        String petWeight = mEditWeight.getText().toString().trim();

        if (!TextUtils.isEmpty(petname) && !TextUtils.isEmpty(petBreed)
                && !TextUtils.isEmpty(petWeight)){

            Log.i(TAB,String.valueOf(AddActivityViewModel.GENDER));

            PetsEntity petdata = new PetsEntity(petname,petBreed,AddActivityViewModel.GENDER,
                    petWeight);
            mAddActivityViewModel.Insert(petdata);

            Toast.makeText(this,getString(R.string.editor_insert_pet_successful),Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed), Toast.LENGTH_LONG).show();
        }

    }




}
