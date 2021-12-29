package com.example.e_commerce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lib.Model.CommentModel;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private List<CommentModel> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CommentAdapter(Context aContext,  List<CommentModel> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View viewComment;
        if (convertView == null) {
            viewComment = View.inflate(parent.getContext(), R.layout.comment_item_item, null);
        } else viewComment = convertView;

        //Bind sữ liệu phần tử vào View
        CommentModel commentt = listData.get(i);
        ((TextView) viewComment.findViewById(R.id.name_comment)).setText(commentt.getName());
        ((TextView) viewComment.findViewById(R.id.content_comment)).setText(commentt.getContent());
        System.out.println("Đay là i: " + i);
        return viewComment;
    }
}
