package com.example.educationproject2024.data;

import java.util.List;

public class Exercise {
    public String exerciseType;
    public String exerciseAnswerType;
    public String exerciseTitle;
    public List<SubtitleAndText> exerciseSubtitleAndText;
    public List<String> exerciseAnswerVariants;
    public List<String> exerciseRightAnswers;

    public Exercise() {
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getExerciseAnswerType() {
        return exerciseAnswerType;
    }

    public void setExerciseAnswerType(String exerciseAnswerType) {
        this.exerciseAnswerType = exerciseAnswerType;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String exerciseTitle) {
        this.exerciseTitle = exerciseTitle;
    }

    public List<SubtitleAndText> getExerciseSubtitleAndText() {
        return exerciseSubtitleAndText;
    }

    public void setExerciseSubtitleAndText(List<SubtitleAndText> exerciseSubtitleAndText) {
        this.exerciseSubtitleAndText = exerciseSubtitleAndText;
    }

    public List<String> getExerciseAnswerVariants() {
        return exerciseAnswerVariants;
    }

    public void setExerciseAnswerVariants(List<String> exerciseAnswerVariants) {
        this.exerciseAnswerVariants = exerciseAnswerVariants;
    }

    public List<String> getExerciseRightAnswers() {
        return exerciseRightAnswers;
    }

    public void setExerciseRightAnswers(List<String> exerciseRightAnswers) {
        this.exerciseRightAnswers = exerciseRightAnswers;
    }
}
