package com.example.devhub.AlgoModels;

import android.os.Build;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.devhub.Models.Repositories;
import com.example.devhub.network.ApiClient;
import com.example.devhub.network.ApiService;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRep {

    HashMap<String, Integer> ProgrammingLanguage;

    String experience;

    List<Repositories> repos;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserRep(List<Repositories> repos) {
        ProgrammingLanguage = new HashMap<String, Integer>();
        experience = ParseUser.getCurrentUser().getString("Experience");
        innitializeDominantLanguage(repos);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void innitializeDominantLanguage(List<Repositories> repos){

        for(Repositories repo : repos){
            String language = repo.getLanguage();

            if (language != null) {

                if (ProgrammingLanguage.containsKey(language)) {
                    int newValue = ProgrammingLanguage.get(language) + 1;
                    ProgrammingLanguage.replace(language, newValue);

                }else{
                    ProgrammingLanguage.put(language, 1);
                }
            }
        }

    }
    public String getDominantLanguage(){

        String language = "";
        int maxCount = 0;

        for (String lang : ProgrammingLanguage.keySet()){
            if(ProgrammingLanguage.get(lang) >= maxCount){
                language = lang;
            }
        }
        return  language;
    }

    public String getExperience(){
        return experience;
    }


}
