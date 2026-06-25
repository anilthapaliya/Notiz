package com.bca.notiz;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bca.notiz.broadcasts.AirplaneModeReceiver;
import com.bca.notiz.recyclers.Note;
import com.bca.notiz.recyclers.NoteAdapter;
import com.bca.notiz.utils.NotificationUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotesActivity extends AppCompatActivity {

    ImageView imgProfile;
    FloatingActionButton btnAdd;
    RecyclerView recyclerNotes;
    ProgressBar progressBar;
    NoteAdapter noteAdapter;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);

        viewBinding();
        registerEvents();
        checkNotificationPermission();

        // Prepare notes adapter
        notes = new ArrayList<>();
        noteAdapter = new NoteAdapter(notes);
        recyclerNotes.setAdapter(noteAdapter);
    }

    private final int NOTIFICATION_CODE = 102;
    private void checkNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_CODE);
            }
        }
    }

    void viewBinding() {

        imgProfile = findViewById(R.id.imgProfile);
        btnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);
        recyclerNotes = findViewById(R.id.recyclerNotes);
    }

    void registerEvents() {

        imgProfile.setOnClickListener(v -> {
            startActivity(new Intent(NotesActivity.this, ProfileActivity.class));
        });

        btnAdd.setOnClickListener(v -> addNote());

        recyclerNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    void addNote() {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_add_note, null);
        dialog.setContentView(view);

        MaterialCardView imgClose = view.findViewById(R.id.imgClose);
        MaterialButton btnAdd = view.findViewById(R.id.btnAdd);
        TextInputEditText etTitle = view.findViewById(R.id.etTitle);
        TextInputEditText etMessage = view.findViewById(R.id.etMessage);

        btnAdd.setOnClickListener(v -> {
            // simulate heavy tasks.
            String title = etTitle.getText().toString();
            String message = etMessage.getText().toString();
            //new UploadNote(title, message, this).start();
            //new Thread(new UploadTask(title, message)).start();

            /*Handler uiHandler = new Handler(Looper.getMainLooper()); // gives UI thread
            uiHandler.post(() -> {
                // runs immediately
                // runs in background.
                // don't run long running operations, tasks, otherwise ANR
            });
            uiHandler.postDelayed(() -> {
                // execute some tasks
            }, 4000);

            HandlerThread bgThread = new HandlerThread("bg_thread");
            bgThread.start();

            Handler bgHandler = new Handler(bgThread.getLooper()); // gives bgThread
            bgHandler.post(() -> {
               // runs in background
               // don't ever try to run UI tasks here.
                uiHandler.post(() -> {
                   // runs in main thread.  eg. show progress
                });
                // perform bg tasks
                uiHandler.post(() -> {
                    // runs in main thread.  eg. hide progress
                });
            });

            bgThread.quitSafely();*/

            dialog.dismiss();
        });
        imgClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    class PerformTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            // UI thread
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // BG thread
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            // UI thread
        }
    }

    class UploadNote extends Thread {
        String title, message;
        Context context;
        UploadNote(String title, String message, Context context) {
            this.title = title;
            this.message = message;
            this.context = context;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(7000);
                NotificationUtils.showNotesUpdates(context, title, message);
            } catch (InterruptedException e) {
                Log.d("Exception","Interrupted");
            }
        }
    }

    class UploadTask implements Runnable {
        String title, message;
        UploadTask(String title, String message) {
            this.title = title;
            this.message = message;
        }
        @Override
        public void run() {
            try {
                runOnUiThread(() -> showProgress(true));
                Thread.sleep(7000);
                // API calls-perform actual work
                runOnUiThread(() -> {
                    loadNote(new Note(title, message));
                    showProgress(false);
                });
            } catch (InterruptedException e) {
                Log.d("Exception","Interrupted");
                runOnUiThread(() -> showProgress(false));
            }
        }
    }

    AirplaneModeReceiver receiver;
    @Override
    protected void onStart() {
        super.onStart();
        // register the broadcast receiver
        receiver = new AirplaneModeReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister
        if (receiver != null) unregisterReceiver(receiver);
    }

    void showProgress(boolean value) {
        progressBar.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    void loadNote(Note note) {
        notes.add(note);
        noteAdapter.notifyItemInserted(notes.size() - 1);
    }

}
