package fr.vannes.ecoboat.ui.nitrate;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NitrateViewModel extends ViewModel {
    private final MutableLiveData<List<String>> mNitrateDataList;

    private final Handler handler = new Handler();
    private final String[] nitrateLevels = {"9mg/L", "12mg/L", "7mg/L", "3mg/L", "21mg/L", "35mg/L", "19mg/L", "10mg/L", "48mg/L", "68mg/L", "14mg/L", "11mg/L",};
    private final String[] timeSlots = {"02h00", "04h00", "06h00", "08h00", "10h00", "12h00", "14h00", "16h00", "18h00", "20h00", "22h00", "00h00"};
    private int currentIndex = 0;
    private final List<String> nitrateDataList = new ArrayList<>(); // Liste pour stocker les données

    private final Runnable updateNitrateRunnable = new Runnable() {
        @Override
        public void run() {
            String nitrateLevel = nitrateLevels[currentIndex];
            String timeSlot = timeSlots[currentIndex];
            String newValue = timeSlot + " : " + nitrateLevel; // Nouvelle valeur
            nitrateDataList.add(newValue); // Ajoutez la nouvelle valeur à la liste
            currentIndex++;

            // Mettre à jour mNitrateDataList
            mNitrateDataList.setValue(new ArrayList<>(nitrateDataList));

            // Vérifiez si vous avez atteint la fin des tableaux
            if (currentIndex < nitrateLevels.length) {
                // Si non, réexécutez ce Runnable après 5 secondes
                handler.postDelayed(this, TimeUnit.SECONDS.toMillis(1));
            }
        }
    };

    public NitrateViewModel() {
        mNitrateDataList = new MutableLiveData<>();
        mNitrateDataList.setValue(new ArrayList<>());

        handler.post(updateNitrateRunnable);
    }

    public LiveData<List<String>> getNitrateDataList() {
        return mNitrateDataList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        handler.removeCallbacks(updateNitrateRunnable);
    }
}