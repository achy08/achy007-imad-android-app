package ac.myblogapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ac.myblogapp.adapter.BlogEntryAdapter;
import ac.myblogapp.model.ErrorResponse;
import ac.myblogapp.network.CustomResponseListener;
import ac.myblogapp.viewHolder.BlogEntryViewHolder;
import ac.myblogapp.R;
import ac.myblogapp.model.BlogEntry;
import ac.myblogapp.network.APImanager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogEntryListActivity extends BaseActivity {

    RecyclerView recyclerView;
    BlogEntryAdapter blogListAdapter;

    public static void startActivity(Activity startingActivity) {
        Intent intent = new Intent(startingActivity, BlogEntryListActivity.class);
        startingActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);

        setTitle("Entry List");

        blogListAdapter = new BlogEntryAdapter(new BlogEntryAdapter.Helper(){
            @Override
            public void onEntryClicked(BlogEntry blogentry){
                BlogEntryDetailActivity.startActivity(BlogEntryListActivity.this, blogentry);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //Step 1 - create an adapter
        recyclerView.setAdapter(blogListAdapter);
        //Step 2 - set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showProgressDialog(true);
        APImanager.getApiInterface().getBlog()
                .enqueue(new BlogListResponseListener());
    }


    private class BlogListResponseListener extends CustomResponseListener<List<BlogEntry>> {
        @Override
        public void onSuccessfulResponse(List<BlogEntry> response) {
            showProgressDialog(false);
            blogListAdapter.setBlogEntryList(response);
        }

        @Override
        public void onFailedResponse(ErrorResponse errorResponse) {
            showProgressDialog(false);
            showAlert("Failed", errorResponse.getError());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_blog_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                showProgressDialog(true);
                APImanager.getApiInterface().logout()
                        .enqueue(new LogoutResponseListener());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LogoutResponseListener extends CustomResponseListener<Void> {
        @Override
        public void onSuccessfulResponse(Void response) {
            showProgressDialog(false);
            AuthenticationActivity.startActivity(BlogEntryListActivity.this);
        }

        @Override
        public void onFailedResponse(ErrorResponse errorResponse) {
            showProgressDialog(false);
            showAlert("You cannot leave", errorResponse.getError());

        }
    }
}
