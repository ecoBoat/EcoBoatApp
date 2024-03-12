package fr.vannes.ecoboat.ui.home;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mIndex;
    private final MutableLiveData<String> mWaterQualityText; // New LiveData for water quality text
    private final MutableLiveData<String> mLocation;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        String text = "Qualité de l'eau en ce moment :\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            text += " " + getCurrentDateFormatted();
        }
        mText.setValue(text);

        mIndex = new MutableLiveData<>();
        // TODO Change this value with the API value
        mIndex.setValue(30);

        mWaterQualityText = new MutableLiveData<>(); // Initialize the new LiveData

        // Update the water quality text when the index value changes
        Integer indexValue = mIndex.getValue();
        if (indexValue == null) {
            indexValue = 0; // Default value
        }

        if (indexValue <= 20) {
            mWaterQualityText.setValue("Qualité de l'eau : Mauvaise");
        } else if (indexValue <= 40) {
            mWaterQualityText.setValue("Qualité de l'eau : Moyenne");
        } else if (indexValue <= 60) {
            mWaterQualityText.setValue("Qualité de l'eau : Bonne");
        } else if (indexValue <= 80) {
            mWaterQualityText.setValue("Qualité de l'eau : Très bonne");
        } else {
            mWaterQualityText.setValue("Qualité de l'eau : Excellente");
        }

        mLocation = new MutableLiveData<>();

        // TODO Change this value with the API value
        mLocation.setValue("Vannes, Morbihan, France");

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getIndex() {
        return mIndex;
    }

    public LiveData<String> getWaterQualityText() { // Getter for the new LiveData
        return mWaterQualityText;
    }

    public LiveData<String> getLocation() {
        return mLocation;
    }

    /**
     * Get the current date formatted
     *
     * @return the current french date formatted as a string
     */
    private String getCurrentDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return sdf.format(new Date());
    }
}