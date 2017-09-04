package ac.myblogapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import ac.myblogapp.R;
import ac.myblogapp.model.BlogEntry;

public class BlogEntryDetailActivity extends AppCompatActivity {

    private static final String ENTRY_KEY = "entryKey";

    public static void startActivity(Activity startingActivity, BlogEntry blogEntry){
        Intent intent = new Intent(startingActivity, BlogEntryDetailActivity.class);
        intent.putExtra(ENTRY_KEY, blogEntry);
        startingActivity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        TextView entryTitle = (TextView) findViewById(R.id.entryTitle);
        TextView entryContent = (TextView) findViewById(R.id.entryContent);

        if(getIntent()!= null){
            BlogEntry blogEntry = getIntent().getParcelableExtra(ENTRY_KEY);
            entryTitle.setText(blogEntry.getTitle());
            entryContent.setText(blogEntry.getContent());

            setTitle(blogEntry.getTitle());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
