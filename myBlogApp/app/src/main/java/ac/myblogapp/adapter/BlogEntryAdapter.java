package ac.myblogapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ac.myblogapp.R;
import ac.myblogapp.model.BlogEntry;
import ac.myblogapp.viewHolder.BlogEntryViewHolder;

/**
 * Created by Home on 04-Sep-17.
 */

public class BlogEntryAdapter extends RecyclerView.Adapter<BlogEntryViewHolder>{

    List<BlogEntry> blogEntryList = new ArrayList<>();
    Helper helper;


    //// TODO: 02-Sep-17 connect to articleName in DB
    //Mock up data
        /*String blogEntryList[] = {
                "Article 1",
                "Article 2",
                "Article 3",
                "Article 4",
        };*/

    public interface Helper{
        void onEntryClicked(BlogEntry blogEntry);
    }

    public BlogEntryAdapter (Helper helper){
        this.helper = helper;
    }

    @Override
    public BlogEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating view - layout_blogItem.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blog_item,parent, false);
        return new BlogEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BlogEntryViewHolder holder, final int position) {
        //setting the data
        holder.articleName.setText(blogEntryList.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.onEntryClicked(blogEntryList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogEntryList.size();
    }

    public void setBlogEntryList(List<BlogEntry> data){
        this.blogEntryList = data;
        this.notifyDataSetChanged();
    }
}