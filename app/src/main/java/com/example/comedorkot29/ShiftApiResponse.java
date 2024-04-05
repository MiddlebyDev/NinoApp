package com.example.comedorkot29;

import java.util.ArrayList;

public class ShiftApiResponse {
    private boolean success;
    private String message;
    private ArrayList<Shifts> data;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Shifts> getData() {
        return data;
    }
}
