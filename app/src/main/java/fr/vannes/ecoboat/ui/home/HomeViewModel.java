package fr.vannes.ecoboat.ui.home;

import android.os.Build;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * ViewModel for the home fragment.
 */
public class HomeViewModel extends ViewModel {

    // Live data to store the text
    private final MutableLiveData<String> mText;
    // Live data to store the index
    private final MutableLiveData<Integer> mIndex;
    // LiveData for water quality text
    private final MutableLiveData<String> mWaterQualityText;
    // LiveData for location
    private final MutableLiveData<String> mLocation;
    // Defining a Handler to manage messages and tasks on the main (UI) thread.
    private final Handler handler = new Handler();
    // Runnable to update the date every minute
    private final Runnable updateDateRunnable = new Runnable() {
        @Override
        public void run() {
            updateDate();
            handler.postDelayed(this, TimeUnit.MINUTES.toMillis(1)); // Update the date every minute
        }
    };


    /**
     * Constructor for the HomeViewModel
     */
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        String text = "Qualité de l'eau en ce moment :\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            text += " " + getCurrentDateFormatted();
        }
        mText.setValue(text);

        mIndex = new MutableLiveData<>();
        // TODO Change this value with the API value
        mIndex.setValue(75);

        mWaterQualityText = new MutableLiveData<>(); // Initialize the new LiveData

        // Update the water quality text when the index value changes
        Integer indexValue = mIndex.getValue();
        if (indexValue == null || indexValue < 0 || indexValue > 100) {
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

        handler.post(updateDateRunnable);

    }

    /**
     * Getter for the text
     * @return the LiveData of the text
     */
    public LiveData<String> getText() {
        return mText;
    }

    /**
     * Getter for the index
     * @return the LiveData of the index
     */
    public LiveData<Integer> getIndex() {
        return mIndex;
    }

    /**
     * Getter for the water quality text
     * @return the LiveData of the water quality text
     */
    public LiveData<String> getWaterQualityText() { // Getter for the new LiveData
        return mWaterQualityText;
    }

    /**
     * Getter for the location
     * @return the LiveData of the location
     */
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

    /**
     * Update the date in the LiveData
     */
    private void updateDate() {
        String text = "Qualité de l'eau en ce moment :\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            text += " " + getCurrentDateFormatted();
        }
        mText.setValue(text);
    }

    /**
     * Stop the updates when the ViewModel is cleared
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        handler.removeCallbacks(updateDateRunnable); // Stop the updates when the ViewModel is cleared
    }
}