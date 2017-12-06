package com.lesson.vv_bobkov.a2l3_bobkov;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by bobkov-vv on 06.12.2017.
 */

public class FilesController {

    private App mApp;
    private String mFileName;

    FilesController(final App app) {
        this.mApp = app;
        mFileName = mApp.getmApp().getFilesDir() + mApp.getmApp().getString(R.string.file_name);
    }

    public void saveToFile() throws Exception {
        try {
            File file = new File(mFileName);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(mApp.getmNoteWithTitleList());

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getClass().equals(InvalidClassException.class)) {
                throw new InvalidClassException(e.getMessage());
            } else if (e.getClass().equals(NotSerializableException.class)) {
                throw new NotSerializableException(e.getMessage());
            } else if (e.getClass().equals(IOException.class)) {
                throw new IOException(e.getMessage());
            }
        }
    }

    public void readeFromFile() throws Exception {
        try {
            FileInputStream fileInputStream = new FileInputStream(mFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            mApp.setmNoteWithTitleList((ArrayList<NoteWithTitle>) objectInputStream.readObject());

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getClass().equals(ClassNotFoundException.class)) {
                throw new ClassNotFoundException(e.getMessage());
            } else if (e.getClass().equals(InvalidClassException.class)) {
                throw new InvalidClassException(e.getMessage());
            } else if (e.getClass().equals(StreamCorruptedException.class)) {
                throw new StreamCorruptedException(e.getMessage());
            } else if (e.getClass().equals(OptionalDataException.class)) {
                throw e;
            } else {
                throw new IOException(e.getMessage());
            }
        }
    }
}
