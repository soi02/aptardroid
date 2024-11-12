package com.inhatc.android_fianl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardAdapter extends ArrayAdapter<BoardItem> {
    private Context context;
    private ArrayList<BoardItem> boardItems;

    public BoardAdapter(Context context, ArrayList<BoardItem> boardItems) {
        super(context, 0, boardItems);
        this.context = context;
        this.boardItems = boardItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoardItem boardItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.title_text_view);
        TextView contentTextView = convertView.findViewById(R.id.content_text_view);


        titleTextView.setText(boardItem.getWriter());
        contentTextView.setText(boardItem.getTitle());

        return convertView;
    }
}
