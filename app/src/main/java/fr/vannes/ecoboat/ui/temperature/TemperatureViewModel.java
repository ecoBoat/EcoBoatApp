package fr.vannes.ecoboat.ui.temperature;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import fr.vannes.ecoboat.utils.APIUtils;

/**
 * ViewModel for the temperature fragment.
 */
public class TemperatureViewModel extends ViewModel {
    // Text attribute to display the fragment title
    private final MutableLiveData<String> mText;
    // Temperature attribute to display the temperature
    private final MutableLiveData<List<Map<String, String>>> mTemperature;

    /**
     * Constructor for the TemperatureViewModel
     */
    public TemperatureViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Derniers relevés de température");

        mTemperature = new MutableLiveData<>();
        fetchTemperature();
    }

    /**
     * Method to get the text attribute
     * @return The text attribute
     */
    public MutableLiveData<String> getText() {
        return mText;
    }

    /**
     * Method to get the temperature attribute
     * @return The temperature attribute
     */
    public MutableLiveData<List<Map<String, String>>> getTemperature() {
        return mTemperature;
    }

    /**
     * Method to fetch the temperature from the API
     * with the APIUtils getTemperature method
     */
    private void fetchTemperature() {
    // Create a new thread to fetch the temperature from the API
    new Thread(() -> {
        APIUtils apiUtils = new APIUtils();
        try {
            // Get the temperature from the API
            List<Map<String, String>> temperature = apiUtils.getTemperature();

            // Create a new Handler to update the LiveData on the main thread
            new Handler(Looper.getMainLooper()).post(() -> {
                // Update the LiveData with the temperature
                mTemperature.setValue(temperature);
                Log.d("TemperatureViewModel", "Temperature data fetched: " + temperature);
            });
        } catch (Exception e) {
            Log.e("TemperatureViewModel", "Error fetching temperature data", e);
        }
    }).start();
}


}