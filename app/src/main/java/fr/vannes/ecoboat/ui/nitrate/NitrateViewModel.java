package fr.vannes.ecoboat.ui.nitrate;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ViewModel for the nitrate fragment.
 */
public class NitrateViewModel extends ViewModel {
    // LiveData to store the nitrate data list
    private final MutableLiveData<List<String>> mNitrateDataList;
    // Defining a Handler to manage messages and tasks on the main (UI) thread.
    private final Handler handler = new Handler();
    // Array of nitrate levels mock data
    private final String[] nitrateLevels = {"9mg/L", "12mg/L", "7mg/L", "3mg/L", "21mg/L", "35mg/L", "19mg/L", "10mg/L", "48mg/L", "68mg/L", "14mg/L", "11mg/L",};
    // Array of time slots mock data
    private final String[] timeSlots = {"02h00", "04h00", "06h00", "08h00", "10h00", "12h00", "14h00", "16h00", "18h00", "20h00", "22h00", "00h00"};
    // Index to iterate over the nitrate levels and time slots
    private int currentIndex = 0;
    // List to store the data
    private final List<String> nitrateDataList = new ArrayList<>(); // Liste pour stocker les données
    // Runnable to update the nitrate level every 5 seconds
    private final Runnable updateNitrateRunnable = new Runnable() {
        @Override
        public void run() {
            String nitrateLevel = nitrateLevels[currentIndex];
            String timeSlot = timeSlots[currentIndex];
            String newValue = timeSlot + " : " + nitrateLevel; // new value to add to the list
            nitrateDataList.add(newValue); // add the new value to the list
            currentIndex++; // increment the index

            // Update the LiveData with the new list
            mNitrateDataList.setValue(new ArrayList<>(nitrateDataList));

            // If the index is less than the length of the nitrate levels array
            if (currentIndex < nitrateLevels.length) {
                // Post the Runnable to the Handler to run it again after 5 seconds
                handler.postDelayed(this, TimeUnit.SECONDS.toMillis(5));
            }
        }
    };

    /**
     * Constructor for the NitrateViewModel
     */
    public NitrateViewModel() {
        mNitrateDataList = new MutableLiveData<>();
        mNitrateDataList.setValue(new ArrayList<>());

        handler.post(updateNitrateRunnable);
    }

    /**
     * Getter for the nitrate data list
     *
     * @return the LiveData of the nitrate data list
     */
    public LiveData<List<String>> getNitrateDataList() {
        return mNitrateDataList;
    }

    /**
     * Method to clear the Runnable when the ViewModel is cleared
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        handler.removeCallbacks(updateNitrateRunnable);
    }
}