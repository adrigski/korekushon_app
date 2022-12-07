package com.example.korekushon_app.ui.saved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SavedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Bookmark_Page");
    }

    public LiveData<String> getText() {
        return mText;
    }
}