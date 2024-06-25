package com.example.educationproject2024.data;

public class SubtitleAndText {
    public String exerciseSubtitle;
    public String exerciseText;

    public SubtitleAndText() {
    }

    public String getExerciseSubtitle() {
        return exerciseSubtitle;
    }

    public void setExerciseSubtitle(String exerciseSubtitle) {
        this.exerciseSubtitle = exerciseSubtitle;
    }

    public String getExerciseText() {
        return exerciseText;
    }

    public void setExerciseText(String exerciseText) {
        this.exerciseText = exerciseText;
    }

    @Override
    public String toString() {
        return "SubtitleAndText{" +
                "exerciseSubtitle='" + exerciseSubtitle + '\'' +
                ", exerciseText='" + exerciseText + '\'' +
                '}';
    }
}
