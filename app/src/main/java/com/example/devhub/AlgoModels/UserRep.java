package com.example.devhub.AlgoModels;

import android.os.Build;


import androidx.annotation.RequiresApi;

import com.example.devhub.Models.Repositories;

import java.util.HashMap;
import java.util.List;

public class UserRep {

    HashMap<String, Integer> ProgrammingLanguage;

    String experience;

    public UserRep(HashMap<String, Integer> programmingLanguage, String Experience) {
        ProgrammingLanguage = programmingLanguage;
        experience = Experience;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void innitializeDominantLanguage(List<Repositories> repos){

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

}
