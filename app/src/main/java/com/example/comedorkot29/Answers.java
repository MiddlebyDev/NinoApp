package com.example.comedorkot29;

public class Answers {
    int block_id;
    int shift_id;
    int question_id;
    String answer;

    public  Answers(int shift_id, int question_id, String answer){
        this.block_id = 1;
        this.shift_id = shift_id;
        this.question_id = question_id;
        this.answer = answer;
    }

    public int getBlock_id() { return block_id; }

    public void setBlock_id(int block_id) { this.block_id = block_id; }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
