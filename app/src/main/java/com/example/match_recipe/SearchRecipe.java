package com.example.match_recipe;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//create hashmap userinput with boolean values
public class SearchRecipe {
    static String search(HashMap<String,Object> database, HashMap<String,Boolean> userInputs){

        String result="";
        for(String input: userInputs.keySet()){
            if(!userInputs.get(input))
                result=url(database,userInputs,input);
        }
        return result;
    }
    static String url (Object database, HashMap userInputs,String node){
        String result = "";
        if(database instanceof String)
            result= (String) database;
        else{
            HashMap<String, Object> newDatabase = (HashMap<String, Object>) database;
            Iterator<String> it = newDatabase.keySet().iterator();
            while(it.hasNext()) {
                String ingredient=it.next();
                if(userInputs.containsKey(ingredient)) {
                    userInputs.put(ingredient,true);
                    return url(newDatabase.get(ingredient),userInputs,ingredient);
                }
                else {
                    result="Sorry. We couldn't find matching recipe";
                }
            }
        }
        return result;
    }
}
