package ac.myblogapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ac.myblogapp.viewHolder.BlogEntryViewHolder;
import ac.myblogapp.R;
import ac.myblogapp.model.BlogEntry;
import ac.myblogapp.network.APImanager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogEntryListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BlogListAdapter blogListAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);
        blogListAdapter = new BlogListAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //Step 1 - create an adapter
        recyclerView.setAdapter(blogListAdapter);
        //Step 2 - set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Entries");
        progressDialog.show();
        APImanager.getApiInterface().getBlog()
                .enqueue(new Callback<List<BlogEntry>>() {
                    @Override
                    public void onResponse(Call<List<BlogEntry>> call, Response<List<BlogEntry>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            blogListAdapter.setBlogEntryList(response.body());
                        } else{
                            //Todo: create and show alert
                            Toast.makeText(BlogEntryListActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BlogEntry>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(BlogEntryListActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public class BlogListAdapter extends RecyclerView.Adapter<BlogEntryViewHolder>{

        List<BlogEntry> blogEntryList = new ArrayList<>();


        //// TODO: 02-Sep-17 connect to articleName in DB
        //Mock up data
        /*String blogEntryList[] = {
                "Article 1",
                "Article 2",
                "Article 3",
                "Article 4",
        };*/

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
                    //Todo: Add navigateToDetail()
                    Toast.makeText(BlogEntryListActivity.this, "Entry Clicked:" + blogEntryList.get(position).getTitle(),
                            Toast.LENGTH_SHORT).show();

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

    //Todo: Create Navigate to Entry Detail Activity
    private void navigateToDetail(){
        Intent intent = new Intent(this, BlogEntryDetailActivity.class);
        startActivity(intent);
    }
}
