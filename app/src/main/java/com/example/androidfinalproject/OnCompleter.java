package com.example.androidfinalproject;

// Interface that is called when an async task is completed
public interface OnCompleter<T> {
    public void OnCompleted(T t);
}
