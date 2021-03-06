package com.lesson.vv_bobkov.a2l3_bobkov;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.etNotesTitle)
    EditText etNotesTitle;
    @BindView(R.id.etNotesAddress)
    EditText etNotesAddress;
    @BindView(R.id.etNotesText)
    EditText etNotesText;
    @BindView(R.id.tvTitleOfNote)
    TextView tvTitleOfNote;
    @BindView(R.id.tvAddressOfNote)
    TextView tvAddressOfNote;
    @BindView(R.id.tvNotesText)
    TextView tvNotesText;
    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    private App mApp = App.getmApp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ButterKnife.bind(this);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (App.NOTES_MODE) {
            etNotesTitle.setVisibility(View.GONE);
            etNotesAddress.setVisibility(View.GONE);
            etNotesText.setVisibility(View.GONE);
            btnOk.setVisibility(View.GONE);

            for (Map.Entry<Integer, NoteWithTitle> currentSelectedItem:
                 mApp.getmSelectedItems().entrySet()) {

                tvTitleOfNote.setVisibility(View.VISIBLE);
                tvTitleOfNote.setText(currentSelectedItem.getValue().getTitle());

                if (!currentSelectedItem.getValue().getAddress().equals("")) {

                    tvAddressOfNote.setVisibility(View.VISIBLE);
                    tvAddressOfNote.setText(currentSelectedItem.getValue().getAddress());
                }
                tvNotesText.setVisibility(View.VISIBLE);
                tvNotesText.setText(currentSelectedItem.getValue().getText());
            }
        } else {

            if (!mApp.selectedItemsIsEmpty()) {
                for (Map.Entry<Integer, NoteWithTitle> selectedItem :
                        mApp.getmSelectedItems().entrySet()) {

                    etNotesTitle.setText(selectedItem.getValue().getTitle());
                    etNotesAddress.setText(selectedItem.getValue().getAddress());
                    etNotesText.setText(selectedItem.getValue().getText());
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);

        switch (view.getId()) {

            case R.id.btnOk:

                if (!App.NOTES_MODE) {

                    if (mApp.mNoteWithTitleListIsEmpty()) mApp.getmNoteWithTitleList().clear();

                    if (mApp.selectedItemsIsEmpty()) {

                        mApp.addNewNoteToNoteWithTitleList(
                                new NoteWithTitle(
                                        etNotesTitle.getText().toString(),
                                        etNotesAddress.getText().toString(),
                                        etNotesText.getText().toString()
                                )
                        );

                        try {
                            mApp.getmFilesController().saveToFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        for (Map.Entry<Integer, NoteWithTitle> currentSelectedItem :
                                mApp.getmSelectedItems().entrySet()) {
                            currentSelectedItem.getValue().setTitle(etNotesTitle.getText().toString());
                            currentSelectedItem.getValue().setAddress(etNotesAddress.getText().toString());
                            currentSelectedItem.getValue().setText(etNotesText.getText().toString());
                            break;
                        }
                    }
                }
            case R.id.btnCancel:
                startActivity(intent);
                break;
        }
    }

    public void onClickMap(View view) {
        // Define string for URI scheme
        String geo = "geo:0,0?z=20&q=" + tvAddressOfNote.getText().toString();
        // Create intent object
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geo));
        // You could specify package for use the GoogleMaps app, only
        //intent.setPackage("com.google.android.apps.maps");
        // Start maps activity
        try {
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            mApp.createAttentionDialog(this, e.getMessage(), App.WHITOUT_BUTTON).show();
        }
    }
}
