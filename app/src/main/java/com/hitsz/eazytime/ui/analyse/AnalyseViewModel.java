package com.hitsz.eazytime.ui.analyse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnalyseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AnalyseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is analyse fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}