package com.example.notessafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notessafety.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    EditText editText_title, editText_notes;
    ImageView imageView_save;
    Notes notes;
    boolean isOldNote = false;
    ImageView imageView_upload;
    ImageView uploaded_image;
    WebView uploaded_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);
        imageView_upload = findViewById(R.id.imageView_upload);
        uploaded_image = findViewById(R.id.uploaded_image);
        uploaded_pdf = findViewById(R.id.uploaded_pdf);
        
         notes = new Notes();
         try {
             notes = (Notes) getIntent().getSerializableExtra("old_note");
             editText_title.setText(notes.getTitle());
             editText_notes.setText(notes.getNotes());
             isOldNote = true;
         }
         catch (Exception e) {
             e.printStackTrace();
         }

        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if(description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please add some notes!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat  formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if(!isOldNote) {
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);      //as we have implemented Serializable so this wont give error
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

         imageView_upload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showMenu(imageView_upload);
             }
         });
    }

    private void showMenu(ImageView imageView) {
        PopupMenu popupMenu = new PopupMenu(this, imageView);
        popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        popupMenu.inflate(R.menu.img_pdf_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        switch (item.getItemId()) {
            case R.id.image_upload:
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 103);
                setResult(Activity.RESULT_OK, intent);
                return true;

            case R.id.pdf_upload:
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 104);
                setResult(Activity.RESULT_OK, intent);
                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==103) {
            if(resultCode==Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                if(data==null) {
                    return;
                }
                uploaded_image.setImageURI(selectedImageUri);
            }
        }
        else if(requestCode==104) {
            if(resultCode==Activity.RESULT_OK) {
                String FilePath = data.getData().getPath();
                String FileName = data.getData().getLastPathSegment();
                int lastPos = FilePath.length() - FileName.length();
                String Folder = FilePath.substring(0, lastPos);
                uploaded_pdf.setWebViewClient(new WebViewClient());
                uploaded_pdf.loadUrl(FileName);
                WebSettings webSettings = uploaded_pdf.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setPluginState(WebSettings.PluginState.ON);
            }
        }
    }
}