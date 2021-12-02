package com.example.lib.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Đơn hàng");
    }

    public LiveData<String> getText() {
        return mText;
    }
}