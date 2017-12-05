package com.lesson.vv_bobkov.a2l3_bobkov;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lesson.vv_bobkov.a2l3_bobkov.R.color.colorLightGray;
import static com.lesson.vv_bobkov.a2l3_bobkov.R.color.colorWithe;

/**
 * Created by samsung on 28.11.2017.
 */

class NoteWithTitleAdapter extends BaseAdapter {

    private Context mCxt;
    private LayoutInflater layoutInflater;

    @BindView(R.id.tvNotesTitle)
    TextView tvNotesTitle;
    @BindView(R.id.llRowse)
    LinearLayout llRowse;

    NoteWithTitleAdapter(Context cxt) {

        mCxt = cxt;
        layoutInflater = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (App.noteWithTitleListIsEmpty()) {
            App.setNoteWithTitleList(new ArrayList<NoteWithTitle>());
            App.addNewNoteToNoteWithTitleList(
                    new NoteWithTitle(R.string.notes_no));
        }
    }

    @Override
    public int getCount() {
        return App.getNoteWithTitleList().size();
    }

    @Override
    public Object getItem(int i) {
        return App.getNoteWithTitleList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_view_item, viewGroup, false);
        }

        ButterKnife.bind(this, view);

        if (App.getSelectedItems().containsKey(Integer.valueOf(i))) {
            llRowse.setBackgroundColor(mCxt.getResources().getColor(colorLightGray));
            tvNotesTitle.setBackgroundColor(mCxt.getResources().getColor(colorLightGray));
        } else {
            llRowse.setBackgroundColor(mCxt.getResources().getColor(colorWithe));
            tvNotesTitle.setBackgroundColor(mCxt.getResources().getColor(colorWithe));
        }
        tvNotesTitle.setText(App.getNoteWithTitleList().get(i).getTitle());
        tvNotesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvNotesTitle.getText().toString().equals(mCxt.getString(R.string.notes_no))) {
                    return;
                }
                if (App.getSelectedItems().containsKey(Integer.valueOf(i))) {
                    llRowse.setBackgroundColor(mCxt.getResources().getColor(colorWithe));
                    v.setBackgroundColor(mCxt.getResources().getColor(colorWithe));
                    App.getSelectedItems().remove(Integer.valueOf(i));
                } else {
                    llRowse.setBackgroundColor(mCxt.getResources().getColor(colorLightGray));
                    v.setBackgroundColor(mCxt.getResources().getColor(colorLightGray));
                    App.addSelectedItem(
                            Integer.valueOf(i), App.getNoteWithTitleList().get(i)
                    );
                }
                notifyDataSetChanged();
                App.prepareMenu(App.getMenu());
            }
        });
        return view;
    }

    public void remNoteFromNoteWithTitleList(int position) {
        App.remNoteFromNoteWithTitleList(position);

        if (App.getNoteWithTitleList().size() == 0) {
            App.addNewNoteToNoteWithTitleList(new NoteWithTitle(R.string.notes_no));
        }
        notifyDataSetChanged();
    }
}
