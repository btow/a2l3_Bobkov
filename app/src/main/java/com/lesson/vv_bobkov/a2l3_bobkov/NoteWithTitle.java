package com.lesson.vv_bobkov.a2l3_bobkov;

/**
 * Created by samsung on 28.11.2017.
 */

class NoteWithTitle implements java.io.Serializable {

    private String mTitle, mAddress, mText;

    public NoteWithTitle(final String title, final String address, final String text) {
        mTitle = title;
        mAddress = address;
        mText = text;
    }

    public NoteWithTitle(int notesNo) {
        mTitle = App.getmApp().getString(notesNo);
        mAddress = "";
        mText = "";
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String mTitle) {
        this.mTitle = mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setText(final String mText) {
        this.mText = mText;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(final String address) {
        this.mAddress = address;
    }

    public void editNote(final NoteWithTitle noteWithTitle) {
        mTitle = noteWithTitle.getTitle();
        mAddress = noteWithTitle.getAddress();
        mText = noteWithTitle.getText();
    }
}
