package com.chemander.mydemo.data.model.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.chemander.mydemo.data.model.GetStoriesInformation;
import com.chemander.mydemo.data.model.StoryInformation;
import com.chemander.mydemo.data.remote.StoryService;
import com.chemander.mydemo.utils.ApiUtils;
import com.chemander.mydemo.utils.SettingsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryInformationNewsListDataSource extends PageKeyedDataSource<Integer, StoryInformation> {
    private StoryService storyService;

    public StoryInformationNewsListDataSource(){
        this.storyService = ApiUtils.getStoryService();
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, StoryInformation> callback) {
        storyService = ApiUtils.getStoryService();
        storyService.getNewsStories(1, SettingsManager.SIZE_OF_PAGE).enqueue(new Callback<GetStoriesInformation>() {
            @Override
            public void onResponse(Call<GetStoriesInformation> call, Response<GetStoriesInformation> response) {
                Log.d("Hung", "Log.d(\"Hung\", \"Cannot load data\"); load data");
                List<StoryInformation> storyInformations = new ArrayList<>();
                if(response.isSuccessful()){
                    GetStoriesInformation getStoriesInformation = response.body();
                    if(getStoriesInformation != null && getStoriesInformation.getStories() != null){
                        storyInformations = (ArrayList<StoryInformation>)getStoriesInformation.getStories();
                        callback.onResult(storyInformations, null, 2);
                    }
                }else{
                    Log.d("Hung", "Cannot load data");
                }
            }

            @Override
            public void onFailure(Call<GetStoriesInformation> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, StoryInformation> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, StoryInformation> callback) {
        storyService = ApiUtils.getStoryService();
        storyService.getNewsStories(params.key, SettingsManager.SIZE_OF_PAGE).enqueue(new Callback<GetStoriesInformation>() {
            @Override
            public void onResponse(Call<GetStoriesInformation> call, Response<GetStoriesInformation> response) {
                Log.d("Hung", "loadAfter");
                List<StoryInformation> storyInformations = new ArrayList<>();
                if(response.isSuccessful()){
                    GetStoriesInformation getStoriesInformation = response.body();
                    if(getStoriesInformation != null && getStoriesInformation.getStories() != null){
                        storyInformations = (ArrayList<StoryInformation>)getStoriesInformation.getStories();
                        callback.onResult(storyInformations, params.key+1);
                    }
                }else{
                    Log.d("Hung", "Cannot load data");
                }
            }

            @Override
            public void onFailure(Call<GetStoriesInformation> call, Throwable t) {

            }
        });
    }
}
