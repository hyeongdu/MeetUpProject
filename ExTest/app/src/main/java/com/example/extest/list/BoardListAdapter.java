package com.example.extest.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extest.R;

import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.SingerItemViewHolder>
{
    //아이템 클릭시 실행 함수
    public interface ItemClick{
        public void onClick(View view, int position);
    }
    private ItemClick itemClick;

    //아이템 클릭시 실행 함수 등록
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    Context context;
    ArrayList<BoardListItem> items = new ArrayList<BoardListItem>();

    public class SingerItemViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView textView1;
        protected TextView textView2;
        protected TextView textView3;
        protected TextView textView4;
        protected ImageView imageView1;

        public SingerItemViewHolder(View view){
            super(view);
            textView1 = view.findViewById(R.id.Title);
            textView2 = view.findViewById(R.id.date);
            textView3 = view.findViewById(R.id.star);
            textView4 = view.findViewById(R.id.tid);
            imageView1 = view.findViewById(R.id.imageView);
        }

    }

    public BoardListAdapter(Context context){
      this.context = context;
    }

    public void addItem(BoardListItem item){
        items.add(item);
    }
    @Override
    public SingerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
      View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_board_item_view, viewGroup, false);
      SingerItemViewHolder viewHolder = new SingerItemViewHolder(view);

      return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SingerItemViewHolder viewholder, int position) {
        viewholder.textView1.setText(items.get(position).getTitle());
        viewholder.textView2.setText(items.get(position).getStartdate());
        viewholder.textView3.setText(items.get(position).getEnddate());
        viewholder.textView4.setText(items.get(position).getTid());
        viewholder.imageView1.setImageResource(items.get(position).getTfile());

        final int num = position;

        viewholder.imageView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(itemClick != null){
                    itemClick.onClick(v, num);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }


}
