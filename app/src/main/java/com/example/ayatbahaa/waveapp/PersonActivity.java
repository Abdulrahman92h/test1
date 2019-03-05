package com.example.ayatbahaa.waveapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ayatbahaa.waveapp.activity.PersonRegistrationActivity;
import com.example.ayatbahaa.waveapp.database.Person;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.example.ayatbahaa.waveapp.activity.PersonRegistrationActivity.EXTRA_DOCUMENT_ID;

/**
 * database keys are hardcoded from views id
 *
 */


abstract public class PersonActivity extends AppCompatActivity implements PersonItemCallback.Listener {
    private static final String TAG= "PersonActivity";
    public static final int RC_NEW_PERSON = 1;
    private static final int RC_PERSON_EDIT = 2;
    public static final String EXTRA_PERSON= "extraPerson";


    private PersonAdapter mPersonAdapter;
    private CollectionReference mPersonReference;
    private List<Person> mPersonList;
    private int mLastEditedPosition;
    private FirebaseAuth mFirebaseAuth;
    private Uri mPictureUri;
    TextView activityLabel;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        AndroidThreeTen.init(this);
        activityLabel= findViewById(R.id.activityLabel);

        // TODO: add search
        //searchView = findViewById(R.id.search_person);
        mFirebaseAuth= FirebaseAuth.getInstance();
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO: delete this
        mFirebaseAuth.signInWithEmailAndPassword("test@test.com", "baghdad")
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                });

        mPersonList = new ArrayList<>();
        initializeRecyclerView();
        getFirestoreData();
    }

    abstract CollectionReference getCollectionRef();
    abstract Person getPerson(DocumentChange dc);
    abstract Intent getProfileIntent(Person person);
    abstract Intent getRegistrationIntent();

    private void getFirestoreData() {
        mPersonReference = getCollectionRef();
        mPersonReference.orderBy("name")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null){
                            return;
                        }

                        for(DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    addPerson(dc);
                                    break;
                                case MODIFIED:
                                    updatePerson(dc);
                                    break;
                                case REMOVED:
                                    mPersonList.remove(dc.getOldIndex());
                                    mPersonAdapter.notifyItemRemoved(dc.getOldIndex());
                                    break;
                            }
                        }
                    }
                });
    }

    private void updatePerson(DocumentChange dc) {
        Person Person= getPerson(dc);
        Person.setDocumentId(dc.getDocument().getId());

        if(mPictureUri != null){
            Person.setPictureUri(mPictureUri);
        }
        // update position
        mPersonList.remove(dc.getOldIndex());
        mPersonAdapter.notifyItemRemoved(dc.getOldIndex());

        mPersonList.add(dc.getNewIndex(), Person);
        mPersonAdapter.notifyItemInserted(dc.getNewIndex());
    }

    private void addPerson(DocumentChange dc) {
        Person person = getPerson(dc);
        person.setDocumentId( dc.getDocument().getId());

        if(mPictureUri != null){
            person.setPictureUri(mPictureUri);
        }
        mPersonList.add(dc.getNewIndex(), person);
        mPersonAdapter.notifyItemInserted(dc.getNewIndex());
    }

    private void initializeRecyclerView() {
        RecyclerView personRecyclerView = findViewById(R.id.personRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        personRecyclerView.setLayoutManager(layoutManager);
        personRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if(mFirebaseAuth.getCurrentUser() != null) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                    new PersonItemCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this));
            itemTouchHelper.attachToRecyclerView(personRecyclerView);
        }

        mPersonAdapter = new PersonAdapter(mPersonList, this);
        personRecyclerView.setAdapter(mPersonAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);
        if(mFirebaseAuth.getCurrentUser() != null) {
            menu.getItem(0).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addPersonMenuItem:
                mPictureUri= null;
                Intent intent = getRegistrationIntent();
                startActivityForResult(intent, RC_NEW_PERSON);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == RC_PERSON_EDIT){
            mPersonAdapter.notifyItemChanged(mLastEditedPosition);
        }

        if(data != null) {
            mPictureUri = data.getParcelableExtra(PersonRegistrationActivity.EXTRA_PICTURE_URI);
        }
    }

    @Override
    public void deletePerson(int position) {
        String documentId = mPersonList.get(position).getDocumentId();
        mPersonReference.document(documentId).delete();
    }

    @Override
    public void editPerson(int position) {
        mPictureUri= null;
        mLastEditedPosition = position;
        Person Person = mPersonList.get(position);
        String documentId = Person.getDocumentId();

        Intent intent = getRegistrationIntent();
        intent.putExtra(EXTRA_DOCUMENT_ID, documentId);
        if (Person.getPictureUri() != null) {
            intent.putExtra(PersonRegistrationActivity.EXTRA_PICTURE_URI, Person.getPictureUri());
        }
        startActivityForResult(intent, RC_PERSON_EDIT);
    }
}
