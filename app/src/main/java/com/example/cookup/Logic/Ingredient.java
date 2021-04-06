package com.example.cookup.Logic;


import com.example.cookup.Logic.Enums.Type;

import lombok.Data;

@Data
public class Ingredient {
    private String ingredient;
    private int amount;
    private Type type;

}
