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

    private static ArrayList<NoteWithTitle> mNoteWithTitleList = null;
    private static HashMap<Integer, NoteWithTitle> mSelectedItems;
    private static Menu mMenu;
    private static App mApp;

    public App() {
        mApp = this;
    }

    public static App getApp() {
        return mApp;
    }

    public static void setNoteWithTitleList(ArrayList<NoteWithTitle> noteWithTitleList) {
        App.mNoteWithTitleList = noteWithTitleList;
    }

    public static List<NoteWithTitle> getNoteWithTitleList() {
        return mNoteWithTitleList;
    }

    public static boolean noteWithTitleListIsEmpty() {

        if (mNoteWithTitleList == null ||
                (mNoteWithTitleList.size() == 1 &&
                        mNoteWithTitleList.get(0).getTitle().equals(
                                mApp.getResources().getString(R.string.notes_no)
                        ))) return true;
        return false;
    }

    public static void addNewNoteToNoteWithTitleList(NoteWithTitle noteWithTitle) {
        mNoteWithTitleList.add(noteWithTitle);
    }

    public static void remNoteFromNoteWithTitleList(int position) {
        mNoteWithTitleList.remove(position);
    }

    public static void editNoteFromNoteWithTitleList(int position, NoteWithTitle noteWithTitle) {
        mNoteWithTitleList.get(position).editNote(noteWithTitle);
    }

    public static boolean selectedItemsIsEmpty() {

        if (mSelectedItems == null ||
                mSelectedItems.isEmpty()) {
            return true;
        }
        return false;
    }

    public static HashMap<Integer, NoteWithTitle> getSelectedItems() {
        return mSelectedItems;
    }

    public static void createSelectedItems() {
        App.mSelectedItems = new HashMap<>();
    }

    public static NoteWithTitle getSelectedItem(Integer positoin) {
        return App.mSelectedItems.get(positoin);
    }

    public static void addSelectedItem(Integer positoin, NoteWithTitle noteWithTitle) {
        App.mSelectedItems.put(positoin, noteWithTitle);
    }

    public static void prepareMenu(Menu menu) {

        if (App.noteWithTitleListIsEmpty() || App.selectedItemsIsEmpty()) {

            for (int i = 0; i < menu.size(); i++) {

                MenuItem menuItem = menu.getItem(i);

                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                menuItem.setVisible(false);
            }
        } else if (App.getSelectedItems().size() > 1) {

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

    public static Menu getMenu() {
        return mMenu;
    }

    public static void setMenu(Menu menu) {
        App.mMenu = menu;
    }
}
