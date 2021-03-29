package com.hitsz.eazytime.ui.focus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FocusViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FocusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is focus fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}