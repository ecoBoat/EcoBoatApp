package fr.vannes.ecoboat.ui.temperature;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TemperatureViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TemperatureViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is temperature fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }


}