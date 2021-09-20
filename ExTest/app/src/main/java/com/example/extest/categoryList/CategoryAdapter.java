package com.example.extest.categoryList;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryItemViewHolder>
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
    ArrayList<CategoryItem> items = new ArrayList<CategoryItem>();

    public class CategoryItemViewHolder extends RecyclerView.ViewHolder
    {

        protected TextView categoryName;
        protected ImageView categoryImage;

        public CategoryItemViewHolder(View view){
            super(view);
            categoryName = view.findViewById(R.id.categoryName1);
            categoryImage = view.findViewById(R.id.categoryImage);
        }

    }

    public CategoryAdapter(Context context){
      this.context = context;
    }

    public void addItem(CategoryItem item){
        items.add(item);
    }
    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
      View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_category_list_view, viewGroup, false);
        CategoryItemViewHolder viewHolder = new CategoryItemViewHolder(view);

      return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder viewholder, int position) {
        viewholder.categoryName.setText(items.get(position).getCategoryName());
        viewholder.categoryImage.setImageResource(items.get(position).getResId());

        final int num = position;

        viewholder.categoryImage.setOnClickListener(new View.OnClickListener(){
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
