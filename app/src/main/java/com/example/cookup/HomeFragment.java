package com.example.cookup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookup.Logic.Enums.Type;
import com.example.cookup.Logic.Ingredient;
import com.example.cookup.Logic.Preparation;
import com.example.cookup.Logic.Recipe;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;



public class HomeFragment extends Fragment {

    private ArrayList<Recipe> recipes = new ArrayList<>();

    private ArrayList<String> rec = new ArrayList<>();

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    ListView Recipe;

    ArrayAdapter<String> recipeadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("recipes")){
            recipes.addAll((ArrayList<Recipe>) intent.getSerializableExtra("recipes"));
        }else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            createRecipefromDocument(document);
                        }
                    }
                }
            });

        }

        Recipe = view.findViewById(R.id.RecipeList);

        recipeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , rec);
        Recipe.setAdapter(recipeadapter);

        Recipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe recipe = recipes.get(position);

                System.out.println(recipe.getServings());

                Intent i = new Intent(getActivity(),RecipeViewActivity.class);
                i.putExtra("recipe", recipe);
                startActivity(i);
            }
        });


        return view;
    }

    @SneakyThrows
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 5){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == getActivity().RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                System.out.println("Success");
            }else{
                System.out.println("Fail");
                getActivity().finish();
            }
        }

    }

    public void createRecipefromDocument(QueryDocumentSnapshot document){
        Recipe recipe = new Recipe(document.get("name").toString());
        recipe.setDishtype(document.get("dishtype").toString());
        recipe.setFoodtype(document.get("foodtype").toString());
        recipe.setDescription(document.get("description").toString());
        recipe.setServings(Integer.parseInt(document.get("servings").toString()));
        ArrayList<Ingredient> ingrlist = new ArrayList<>();
        ArrayList<Preparation> preplist = new ArrayList<>();
        ArrayList<Map<String, Object>> ingr = (ArrayList<Map<String, Object>>) document.get("ingredients");
        ArrayList<Map<String, Object>> prep = (ArrayList<Map<String, Object>>) document.get("preparations");
        for(int i = 0; i < ingr.size(); i++){
            Ingredient ingredient = new Ingredient(ingr.get(i).get("ingredient").toString(), Integer.parseInt(ingr.get(i).get("amount").toString()),setType(ingr.get(i).get("type").toString()));
            System.out.println(ingredient);
            ingrlist.add(ingredient);
        }
        for(int i = 0; i < prep.size(); i++){
            Preparation preparation = new Preparation(prep.get(i).get("pass").toString());
            preplist.add(preparation);
        }
        recipe.setIngredients(ingrlist);
        recipe.setPreparations(preplist);
        recipes.add(recipe);
        rec.add(recipe.toString());
        LoadRecipeAdapter();
    }

    public Type setType(String type){
        if(type.equals(Type.cuchara_sopera.toString())){
            return Type.cuchara_sopera;
        }else if(type.equals(Type.cucharilla.toString())){
            return Type.cucharilla;
        }else if(type.equals(Type.gr.toString())){
            return Type.gr;
        }else{
            return Type.ml;
        }
    }

    public void LoadRecipeAdapter(){
        recipeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1 , rec);
        Recipe.setAdapter(recipeadapter);
    }
}
