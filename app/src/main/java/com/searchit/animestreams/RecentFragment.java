package com.searchit.animestreams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.searchit.animestreams.Room.database.AnimeDatabase;
import com.searchit.animestreams.Room.entities.Anime;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {
    private ArrayList<Anime> mAnimeList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.animefinder,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        return view;
    }
    @Override
    public void onResume() {
        getAnimeList();
        super.onResume();
    }
    private void getAnimeList(){
        mAnimeList.clear();
//        SQLiteDatabase recent=Objects.requireNonNull(getContext()).openOrCreateDatabase("recent",Context.MODE_PRIVATE,null);
//        Cursor  resultSet = recent.rawQuery("Select * from anime",null);
//        resultSet.moveToLast();
//        for(int i=resultSet.getCount()-1;i>=0;i--) {
//            Anime anime =new Anime();
//
//            anime.setName(resultSet.getString(0));
//            anime.setEpisodeNo(resultSet.getString(1));
//            anime.setLink(resultSet.getString(2));
//            anime.setImageLink(resultSet.getString(3));
//            Log.d("imagelinkis","soja "+ anime.getImageLink());
//
//            mAnimeList.add(anime);
//
//            resultSet.move(-1);
//        }
        AnimeDatabase animeDatabase = AnimeDatabase.getInstance(getContext());
        List<Anime> animeList =  animeDatabase.animeDao().getAnimeList();
        AnimeDataAdapter mDataAdapter = new AnimeDataAdapter(getContext(), animeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(mDataAdapter);
    }
}
