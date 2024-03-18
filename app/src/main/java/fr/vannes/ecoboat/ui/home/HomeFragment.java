package fr.vannes.ecoboat.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import fr.vannes.ecoboat.R;
import fr.vannes.ecoboat.databinding.FragmentHomeBinding;
import fr.vannes.ecoboat.ui.nitrate.NitrateViewModel;
import fr.vannes.ecoboat.ui.ph.PhViewModel;
import fr.vannes.ecoboat.ui.temperature.TemperatureViewModel;

public class HomeFragment extends Fragment {

    // Link the HomeViewModel to the HomeFragment's layout
    private FragmentHomeBinding binding;

    /**
     * This method is called when the fragment is created.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        // Link the NitrateViewModel, TemperatureViewModel and PhViewModel to the HomeFragment's layout
        NitrateViewModel nitrateViewModel =
                new ViewModelProvider(this).get(NitrateViewModel.class);
        TemperatureViewModel temperatureViewModel =
                new ViewModelProvider(this).get(TemperatureViewModel.class);
        PhViewModel phViewModel =
                new ViewModelProvider(this).get(PhViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get the TextView from the layout
        final TextView textView = binding.textHome;

        // Observe the LiveData from the HomeViewModel
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Get the ProgressBar from the layout
        homeViewModel.getIndex().observe(getViewLifecycleOwner(), index -> {
            CircularProgressBar circularProgressBar = binding.circularProgressBar;
            circularProgressBar.setProgress(index);
        });

        // Get the water quality text from the layout using private method setWaterQualityText
        homeViewModel.getIndex().observe(getViewLifecycleOwner(), indexValue -> {
            if (indexValue <= 20) {
                setWaterQualityText(indexValue, String.valueOf(R.string.eau_mauvaise), "Mauvaise", Color.RED);
            } else if (indexValue <= 40) {
                setWaterQualityText(indexValue, String.valueOf(R.string.eau_moyenne), "Moyenne", Color.YELLOW);
            } else if (indexValue <= 60) {
                setWaterQualityText(indexValue, String.valueOf(R.string.eau_bonne), "Bonne", Color.GREEN);
            } else if (indexValue <= 80) {
                setWaterQualityText(indexValue, String.valueOf(R.string.tres_bonne), "Très bonne", Color.BLUE);
            } else {
                setWaterQualityText(indexValue, String.valueOf(R.string.eau_excellente), "Excellente", Color.MAGENTA);
            }
        });
        ;

        // Get the location from the layout
        homeViewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            TextView locationTextView = binding.locationText;
            locationTextView.setText(location);
        });

        // Observe the nitrate data using private method setNitrateText
        nitrateViewModel.getNitrate().observe(getViewLifecycleOwner(), nitrateList -> {
            if (nitrateList != null && !nitrateList.isEmpty()) {
                String firstNitrateValue = nitrateList.get(0);
                int nitrateIntValue = Integer.parseInt(firstNitrateValue);
                if (nitrateIntValue <= 10) {
                    setNitrateText(firstNitrateValue, Color.GREEN);
                } else if (nitrateIntValue <= 20) {
                    setNitrateText(firstNitrateValue, Color.YELLOW);
                } else if (nitrateIntValue <= 30) {
                    setNitrateText(firstNitrateValue, Color.RED);
                } else {
                    setNitrateText(firstNitrateValue, Color.BLACK);
                }
            } else {
                TextView nitrateTextView = binding.nitrateText;
                String nitrateText = getString(R.string.nitrate_text, "N/A");
                nitrateTextView.setText(nitrateText);
                nitrateTextView.setTextColor(Color.BLACK);
            }
        });


        // Observe the temperature data
        temperatureViewModel.getTemperature().observe(getViewLifecycleOwner(), temperatureList -> {
            if (temperatureList != null && !temperatureList.isEmpty()) {
                Map<String, String> firstTemperatureEntry = temperatureList.get(0);
                String temperatureValue = firstTemperatureEntry.get("temperature");
                TextView temperatureTextView = binding.temperatureText;
                // Use resource string with placeholder
                String temperatureText = getString(R.string.temperature_text, temperatureValue);
                temperatureTextView.setText(temperatureText);

                // Change the text color based on the temperature value
                assert temperatureValue != null;
                int temperatureIntValue = (int) Float.parseFloat(temperatureValue);
                if (temperatureIntValue <= 10) {
                    temperatureTextView.setTextColor(Color.GREEN);
                } else if (temperatureIntValue <= 20) {
                    temperatureTextView.setTextColor(Color.YELLOW);
                } else if (temperatureIntValue <= 30) {
                    temperatureTextView.setTextColor(Color.RED);
                } else {
                    temperatureTextView.setTextColor(Color.BLACK);
                }
            } else {
                TextView temperatureTextView = binding.temperatureText;
                String temperatureText = getString(R.string.temperature_text, "N/A");
                temperatureTextView.setText(temperatureText);
                temperatureTextView.setTextColor(Color.BLACK);
            }
        });

        // Observe the pH data
        phViewModel.getPh().observe(getViewLifecycleOwner(), phList -> {
            if (phList != null && !phList.isEmpty()) {
                Map<String, String> firstPhEntry = phList.get(0);
                String phValue = firstPhEntry.get("pH");
                TextView phTextView = binding.phText;
                // Use resource string with placeholder
                String phText = getString(R.string.ph_text, phValue);
                phTextView.setText(phText);

                // Change the text color based on the pH value
                assert phValue != null;
                float phFloatValue = Float.parseFloat(phValue);
                if (phFloatValue < 7) {
                    phTextView.setTextColor(Color.RED); // Acidic - Red
                } else if (phFloatValue == 7) {
                    phTextView.setTextColor(Color.GREEN); // Neutral - Green
                } else {
                    phTextView.setTextColor(Color.BLUE); // Alkaline - Blue
                }
            } else {
                TextView phTextView = binding.phText;
                String phText = getString(R.string.ph_text, "N/A");
                phTextView.setText(phText);
                phTextView.setTextColor(Color.BLACK);
            }
        });

        // Return the root view
        return root;
    }

    /**
     * Method to set the water quality text with a specific color
     *
     * @param indexValue        the index value
     * @param waterQualityKey   the water quality key
     * @param waterQualityValue the water quality value
     */
    private void setWaterQualityText(int indexValue, String waterQualityKey, String waterQualityValue, int color) {
        TextView waterQualityTextView = binding.waterQualityText;
        String waterQuality = getString(Integer.parseInt(waterQualityKey));
        SpannableString spannable = new SpannableString(waterQuality);
        int start = waterQuality.indexOf(waterQualityValue);
        int end = start + waterQualityValue.length();
        spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        waterQualityTextView.setText(spannable);
    }

    /**
     * Method to set the nitrate text with a specific color
     *
     * @param nitrateValue the nitrate value
     * @param colorWord    the color word
     * @param color        the color
     */
    private void setNitrateText(String nitrateValue, int color) {
        TextView nitrateTextView = binding.nitrateText;
        String nitrateText = getString(R.string.nitrate_text, nitrateValue);
        SpannableString spannable = new SpannableString(nitrateText);
        int start = nitrateText.indexOf(nitrateValue);
        if (start != -1) { // Only setSpan if nitrateValue is found in nitrateText
            int end = start + nitrateValue.length();
            spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        nitrateTextView.setText(spannable);
    }

    /**
     * This method is called when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}