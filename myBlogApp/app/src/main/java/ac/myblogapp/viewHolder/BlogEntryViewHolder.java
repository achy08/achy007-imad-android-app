package ac.myblogapp.viewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ac.myblogapp.R;

/**
 * Created by Home on 02-Sep-17.
 */

public class BlogEntryViewHolder extends RecyclerView.ViewHolder {

    public TextView articleName;
    public CardView cardView;

    public BlogEntryViewHolder(View itemView) {
        super(itemView);
        articleName = (TextView) itemView.findViewById(R.id.articleName);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
    }
}
