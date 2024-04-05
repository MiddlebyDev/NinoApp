package com.example.comedorkot29;

import java.util.ArrayList;

public class QuestionsApiResponse {
    private boolean success;
    private String message;
    private ArrayList<Questions> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Questions> getData() {
        return data;
    }
}
