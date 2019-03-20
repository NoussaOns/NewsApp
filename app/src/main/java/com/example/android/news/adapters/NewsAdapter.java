package com.example.android.news.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.news.News;
import com.example.android.news.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PoliticsViewHolder> {
    private ArrayList<News> mNews;
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<News> news) {
        mNews = news;
        mContext = context;
    }

    @NonNull
    @Override
    public PoliticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PoliticsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PoliticsViewHolder holder, int position) {
        final News currentNews = mNews.get(position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.business);
        requestOptions.centerCrop();


        holder.mSubtitle.setText(currentNews.getContent());
        holder.mTitle.setText(currentNews.getTitle());
        holder.mDate.setText(reformatDate(currentNews.getDate()));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the online article url
                String url = currentNews.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
            }
        });

        //check if the news item has an author
        if (currentNews.getAuthor() == null){
            holder.mAuthor.setVisibility(View.GONE);
        } else {
            holder.mAuthor.setVisibility(View.VISIBLE);
            holder.mAuthor.setText(currentNews.getAuthor());
        }

        //check if the news item has an image
        if(currentNews.getImageUrl() == null){
            holder.mImageView.setVisibility(View.GONE);
        } else {
            holder.mImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(currentNews.getImageUrl()).centerCrop().into(holder.mImageView);
        }

        Log.e(getClass().getName(),"OnBindViewHolder Succeeded");
    }

    /**
     *  Clear all data (a list of {@link News} objects)
     */
    public void clearAll() {
        mNews.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public static class PoliticsViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mTitle, mSubtitle, mAuthor, mDate;
        private CardView mCardView;

        public PoliticsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.politics_img_main_card_1);
            mTitle = itemView.findViewById(R.id.politics_tv_card_main_1_title);
            mSubtitle = itemView.findViewById(R.id.politics_tv_card_main1_subtitle);
            mAuthor = itemView.findViewById(R.id.politics_tv_author);
            mDate = itemView.findViewById(R.id.politics_tv_date);
            mCardView = itemView.findViewById(R.id.news_card_view);
        }
    }

    /**
     * Reformat the date given as a parameter
     * @param dateToBeFormatted
     * @return the date after being formatted and converted to local time as desired
     */
    private String reformatDate(String dateToBeFormatted){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateToBeFormatted);
        } catch (ParseException e) {
            Log.e(getClass().getName(), "Error parsing date");
        }
        //reformat the date that we got in UTC
        SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm\ndd-MM-yyyy");
        String formattedDateUTC = newFormat.format(date);
        //convert from UTC to local time
        newFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date1 = null;
        try {
            date1 = newFormat.parse(formattedDateUTC);
            newFormat.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newFormat.format(date1);
    }
}
