package ru.myitschool.work.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.annotation.Nullable;

public class ProfileViewModel  extends ViewModel {
    private final MutableLiveData<StateInfo> mutableStateInfoLiveData = new MutableLiveData<>();
    public  final LiveData<StateInfo> stateInfoLiveData = mutableStateInfoLiveData;

    public class StateInfo {
        @Nullable
    }
}
