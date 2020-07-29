package com.example.devhub.AlgoModels;

import com.example.devhub.Models.Repositories;

import java.util.HashMap;
import java.util.List;

public class UserRep {

    HashMap<String, Integer> ProgrammingLanguage;

    List<String> Experience;

    public UserRep(HashMap<String, Integer> programmingLanguage, List<String> experience) {
        ProgrammingLanguage = programmingLanguage;
        Experience = experience;
    }

    public static String innitializeDominantLanguage(List<Repositories> repos){

        for(Repositories repo : repos){
            String language = repo.getLanguage();

        }

    }

}
