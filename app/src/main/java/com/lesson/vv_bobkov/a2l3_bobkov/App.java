package com.lesson.vv_bobkov.a2l3_bobkov;

import android.app.Application;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samsung on 28.11.2017.
 */

public class App extends Application {

    static final boolean
            NOTES_MODE_OPEN = true,
            NOTES_MODE_EDIT = false;
    static boolean NOTES_MODE = false;

    private static App mApp;
    private static Menu mMenu;
    private static FilesController mFilesController;

    private static ArrayList<NoteWithTitle> mNoteWithTitleList = null;
    private static HashMap<Integer, NoteWithTitle> mSelectedItems;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApp == null) {
            mApp = this;
        }
        mFilesController = new FilesController(mApp);
    }

    public static App getmApp() {
        return mApp;
    }

    public Menu getmMenu() {
        return mMenu;
    }

    public void setmMenu(Menu menu) {
        App.mMenu = menu;
    }

    public FilesController getmFilesController() {
        return mFilesController;
    }

    public void setmFilesController(FilesController mFilesController) {
        App.mFilesController = mFilesController;
    }

    public void setmNoteWithTitleList(ArrayList<NoteWithTitle> noteWithTitleList) {
        App.mNoteWithTitleList = noteWithTitleList;
    }

    public List<NoteWithTitle> getmNoteWithTitleList() {
        return mNoteWithTitleList;
    }

    public boolean mNoteWithTitleListIsEmpty() {

        if (mNoteWithTitleList == null ||
                (mNoteWithTitleList.size() == 1 &&
                        mNoteWithTitleList.get(0).getTitle().equals(
                                mApp.getResources().getString(R.string.notes_no)
                        ))) return true;
        return false;
    }

    public void addNewNoteToNoteWithTitleList(NoteWithTitle noteWithTitle) {
        mNoteWithTitleList.add(noteWithTitle);
    }

    public void remNoteFromNoteWithTitleList(int position) {
        mNoteWithTitleList.remove(position);
    }

    public void editNoteFromNoteWithTitleList(int position, NoteWithTitle noteWithTitle) {
        mNoteWithTitleList.get(position).editNote(noteWithTitle);
    }

    public boolean selectedItemsIsEmpty() {

        if (mSelectedItems == null ||
                mSelectedItems.isEmpty()) {
            return true;
        }
        return false;
    }

    public HashMap<Integer, NoteWithTitle> getmSelectedItems() {
        return mSelectedItems;
    }

    public void createmSelectedItems() {
        App.mSelectedItems = new HashMap<>();
    }

    public NoteWithTitle getmSelectedItem(Integer positoin) {
        return App.mSelectedItems.get(positoin);
    }

    public void addSelectedItem(Integer positoin, NoteWithTitle noteWithTitle) {
        App.mSelectedItems.put(positoin, noteWithTitle);
    }

    public void prepareMenu(Menu menu) {

        if (mApp.mNoteWithTitleListIsEmpty() || mApp.selectedItemsIsEmpty()) {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);

                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                menuItem.setVisible(false);
            }
        } else if (mApp.getmSelectedItems().size() > 1) {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);

                menuItem.setVisible(true);

                if (menuItem.getTitle().equals(mApp.getResources().getString(R.string.remove))) {
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                } else {
                    menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                    menuItem.setVisible(false);
                }
            }
        } else {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
                menuItem.setVisible(true);
            }
        }
    }
}
