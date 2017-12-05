package com.lesson.vv_bobkov.a2l3_bobkov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.lvNotes)
    ListView lvNotes;

    private NoteWithTitleAdapter mNoteWithTitleAdapter;
    private AbsListView.MultiChoiceModeListener mMultiChoiceModeListener
            = new AbsListView.MultiChoiceModeListener() {

        private Menu actionModeMenu;

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            actionModeMenu = menu;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            App.prepareMenu(menu);
            return true;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            if (App.noteWithTitleListIsEmpty()) {
                return;
            }
            if (checked) {
                App.addSelectedItem(
                        position, App.getNoteWithTitleList().get(position)
                );
            } else {
                App.getSelectedItems().remove(position);
            }
            App.prepareMenu(actionModeMenu);
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return onClickMenuItem(item);
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            supportInvalidateOptionsMenu();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (App.selectedItemsIsEmpty()) App.createSelectedItems();

        ButterKnife.bind(this);
        mNoteWithTitleAdapter = new NoteWithTitleAdapter(getApplicationContext());
        lvNotes.setAdapter(mNoteWithTitleAdapter);
        lvNotes.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvNotes.setMultiChoiceModeListener(mMultiChoiceModeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        App.setMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        App.prepareMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return onClickMenuItem(item);
    }

    private boolean onClickMenuItem(MenuItem item) {

        Intent intent = new Intent(this, NoteActivity.class);

        switch (item.getItemId()) {

            case R.id.actionRem:

                if (!App.selectedItemsIsEmpty()) {

                    for (Iterator<HashMap.Entry<Integer, NoteWithTitle>> iterator =
                            App.getSelectedItems().entrySet().iterator();
                            iterator.hasNext();) {

                        HashMap.Entry<Integer, NoteWithTitle> selectNote = iterator.next();

                        mNoteWithTitleAdapter.remNoteFromNoteWithTitleList(selectNote.getKey());
                        iterator.remove();
                        invalidateOptionsMenu();
                    }
                    return true;
                }
                return false;

            case R.id.actionOpen:

                if (!App.selectedItemsIsEmpty()) {

                    App.NOTES_MODE = App.NOTES_MODE_OPEN;
                    startActivity(intent);
                    return true;
                }
                return false;

            case R.id.actionEdit:

                if (!App.selectedItemsIsEmpty()) {

                    App.NOTES_MODE = App.NOTES_MODE_EDIT;
                    startActivity(intent);
                    return true;
                }
        }
        return false;
    }

    public void onClickAdd(View view) {

        Intent intent = new Intent(this, NoteActivity.class);
        App.NOTES_MODE = App.NOTES_MODE_EDIT;

        if (App.selectedItemsIsEmpty()) {
            App.createSelectedItems();
        } else {
            App.getSelectedItems().clear();
        }
        startActivity(intent);
    }
}
