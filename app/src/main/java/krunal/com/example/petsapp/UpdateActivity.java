package krunal.com.example.petsapp;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import krunal.com.example.petsapp.Database.PetsEntity;
import krunal.com.example.petsapp.Database.Repository;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAB5 = UpdateActivity.class.getSimpleName();
    private EditText mUpdateEditName, mUpdateEditBreed, mUpdateEditWeight;
    private Spinner mSpinner;
    private UpdateViewModel mUpdateViewModel;
    private static int getId;
    private boolean mPetViewTouch = false;
    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mPetHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = (view, motionEvent) -> {
        mPetViewTouch = true;
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mUpdateEditName = findViewById(R.id.update_edit_pet_name);
        mUpdateEditBreed = findViewById(R.id.update_edit_pet_breed);
        mUpdateEditWeight = findViewById(R.id.update_edit_pet_weight);
        mSpinner = findViewById(R.id.update_spinner_gender);

        // setup setOnTouchListener to check if user have touch the ui element or not.
        mUpdateEditName.setOnTouchListener(mTouchListener);
        mUpdateEditBreed.setOnTouchListener(mTouchListener);
        mUpdateEditWeight.setOnTouchListener(mTouchListener);
        mSpinner.setOnTouchListener(mTouchListener);

        // Get the Intent and get id from the intent store in getid var.
        getId = getIntent().getIntExtra(MainActivity.id, -1);
        Log.i(TAB5, String.valueOf(getId));

        // setupSpinner for Gender.
        SetupSpinner();

        // Initialization UpdateActivity.
        mUpdateViewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);
        // Query by id from Pet_tbl.
        // This run on BackGroung Thread.
        mUpdateViewModel.getDetails(getId);

        try {
            // make main thread to sleep for 300 millis.
            // so that we can wait for responce that is coming from BackGround Thread.
            Thread.sleep(300);
            // Update the ui.
            mUpdateEditName.setText(UpdateViewModel.Name);
            mUpdateEditBreed.setText(UpdateViewModel.Breed);
            int getGender = UpdateViewModel.gender;
            Log.i(TAB5, String.valueOf(getGender));
            switch (getGender) {
                case Repository.GENDER_MALE:
                    mSpinner.setSelection(1);
                    break;
                case Repository.GENDER_FEMALE:
                    mSpinner.setSelection(2);
                    break;
                default:
                    mSpinner.setSelection(0);
                    break;
            }
            mUpdateEditWeight.setText(UpdateViewModel.weigth);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    /**
     * setupSpinner for Gender.
     */
    private void SetupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mSpinner.setAdapter(genderSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        UpdateViewModel.GENDER = Repository.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        UpdateViewModel.GENDER = Repository.GENDER_FEMALE;
                    } else {
                        UpdateViewModel.GENDER = Repository.GENDER_UNKNOWN;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UpdateViewModel.GENDER = Repository.GENDER_UNKNOWN;
            }
        });

    }

    /**
     * Create the Menu from update_menu.xml file.
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    /**
     * On Back button press.
     */
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mPetViewTouch) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                (dialogInterface, i) -> {
            Log.i(TAB5,"discardButtonClickListener call from onBackPressed");
                    // User clicked "Discard" button, close the current activity.
                    finish();
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    /**
     * Triggered the action base upon the menu option click.
     * @param item
     * @return true.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Update_action_save:
                Update();
                finish();
                return true;
            case R.id.single_action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mPetViewTouch){
                    Log.i(TAB5,"Petview not touch");
                    NavUtils.navigateUpFromSameTask(UpdateActivity.this);
                    return true;
                }
                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        (dialogInterface, i) -> {
                            Log.i(TAB5,"discardButtonClickListener call from home");
                            // User clicked "Discard" button, navigate to parent activity.
                            NavUtils.navigateUpFromSameTask(UpdateActivity.this);
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update pets by id.
     */
    void Update() {

        String name = mUpdateEditName.getText().toString().trim();
        String Breed = mUpdateEditBreed.getText().toString().trim();
        int gender = UpdateViewModel.GENDER;
        String weigth = mUpdateEditWeight.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(Breed) && !TextUtils.isEmpty(weigth)) {
            PetsEntity petsEntity = new PetsEntity(getId, name, Breed, gender, weigth);
            mUpdateViewModel.Update(petsEntity);
            Toast.makeText(this, "Update Suceessfully", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, getString(R.string.editor_update_pet_failed), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Delete Pet by id.
     */
    void Delete() {

        String name = mUpdateEditName.getText().toString().trim();
        String Breed = mUpdateEditBreed.getText().toString().trim();
        int gender = UpdateViewModel.GENDER;
        String weigth = mUpdateEditWeight.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(Breed) && !TextUtils.isEmpty(weigth)) {
            PetsEntity petsEntity = new PetsEntity(getId, name, Breed, gender, weigth);
            mUpdateViewModel.Delete(petsEntity);
            Toast.makeText(this, getString(R.string.editor_delete_pet_successful), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.editor_delete_pet_failed), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Create AlertDialog and display when you click on delete options from the menu.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, (dialog, id) -> {
            // User clicked the "Delete" button, so delete the pet.
            Delete();
            finish();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            // User clicked the "Cancel" button, so dismiss the dialog
            // and continue editing the pet.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Create AlertDialog to show Unsaved Changes.
     * @param discardButtonClickListener
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, (dialog, id) -> {
            // User clicked the "Keep editing" button, so dismiss the dialog
            // and continue editing the pet.
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
