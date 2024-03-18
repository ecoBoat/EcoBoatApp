package fr.vannes.ecoboat.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
    private double nitrateValue;
    private double temperatureValue;
    private double phValue;


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
                this.nitrateValue = Double.parseDouble(nitrateList.get(0));
                homeViewModel.setNitrateValue(this.nitrateValue); // Set the value in the ViewModel
                if (nitrateValue <= 10) {
                    setNitrateText(String.valueOf(nitrateValue), Color.GREEN);
                } else if (nitrateValue <= 20) {
                    setNitrateText(String.valueOf(nitrateValue), Color.YELLOW);
                } else if (nitrateValue <= 30) {
                    setNitrateText(String.valueOf(nitrateValue), Color.RED);
                } else {
                    setNitrateText(String.valueOf(nitrateValue), Color.BLACK);
                }
            } else {
                TextView nitrateTextView = binding.nitrateText;
                String nitrateText = getString(R.string.nitrate_text, "N/A");
                nitrateTextView.setText(nitrateText);
                nitrateTextView.setTextColor(Color.BLACK);
            }

            // Update the water quality and the progress bar
            homeViewModel.updateWaterQuality();
        });


        // Observe the temperature data using private method setTemperatureText
        temperatureViewModel.getTemperature().observe(getViewLifecycleOwner(), temperatureList -> {
            if (temperatureList != null && !temperatureList.isEmpty()) {
                Map<String, String> firstTemperatureEntry = temperatureList.get(0);
                String temperatureValueString = firstTemperatureEntry.get("temperature");
                assert temperatureValueString != null;
                this.temperatureValue = Double.parseDouble(temperatureValueString);
                homeViewModel.setTemperatureValue(this.temperatureValue); // Set the value in the ViewModel
                if (temperatureValue <= 10) {
                    setTemperatureText(temperatureValueString, Color.GREEN);
                } else if (temperatureValue <= 20) {
                    setTemperatureText(temperatureValueString, Color.YELLOW);
                } else if (temperatureValue <= 30) {
                    setTemperatureText(temperatureValueString, Color.RED);
                } else {
                    setTemperatureText(temperatureValueString, Color.BLACK);
                }
            } else {
                TextView temperatureTextView = binding.temperatureText;
                String temperatureText = getString(R.string.temperature_text, "N/A");
                temperatureTextView.setText(temperatureText);
                temperatureTextView.setTextColor(Color.BLACK);
            }

            // Update the water quality and the progress bar
            homeViewModel.updateWaterQuality();
        });


        // Observe the pH data using private method setPhText
        phViewModel.getPh().observe(getViewLifecycleOwner(), phList -> {
            if (phList != null && !phList.isEmpty()) {
                Map<String, String> firstPhEntry = phList.get(0);
                String phValueString = firstPhEntry.get("pH");
                assert phValueString != null;
                this.phValue = Double.parseDouble(phValueString);
                homeViewModel.setPhValue(this.phValue); // Set the value in the ViewModel
                if (phValue < 7) {
                    setPhText(phValueString, Color.RED); // Acidic - Red
                } else if (phValue == 7) {
                    setPhText(phValueString, Color.GREEN); // Neutral - Green
                } else {
                    setPhText(phValueString, Color.BLUE); // Alkaline - Blue
                }
            } else {
                TextView phTextView = binding.phText;
                String phText = getString(R.string.ph_text, "N/A");
                phTextView.setText(phText);
                phTextView.setTextColor(Color.BLACK);
            }

            // Update the water quality and the progress bar
            homeViewModel.updateWaterQuality();
        });

        // Button to refresh the data
        binding.refreshButton.setBackgroundResource(R.drawable.cell_border);
        binding.refreshButton.setOnClickListener(v -> {
            try {
                Log.d("HomeFragment", "Refresh button clicked, fetching data");

                // Fetch nitrate data
                nitrateViewModel.fetchNitrate();

                // Fetch temperature data
                temperatureViewModel.fetchTemperature();

                // Fetch pH data
                phViewModel.fetchPh();

                // Update the water quality and the progress bar
                homeViewModel.updateWaterQuality();

            } catch (Exception e) {
                Log.e("HomeFragment", "Error fetching data: " + e);
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

    private void setTemperatureText(String temperatureValue, int color) {
        TextView temperatureTextView = binding.temperatureText;
        String temperatureText = getString(R.string.temperature_text, temperatureValue);
        SpannableString spannable = new SpannableString(temperatureText);
        int start = temperatureText.indexOf(temperatureValue);
        if (start != -1) { // Only setSpan if temperatureValue is found in temperatureText
            int end = start + temperatureValue.length();
            spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        temperatureTextView.setText(spannable);
    }

    /**
     * Method to set the pH text with a specific color
     *
     * @param phValue the pH value
     * @param color   the color
     */
    private void setPhText(String phValue, int color) {
        TextView phTextView = binding.phText;
        String phText = getString(R.string.ph_text, phValue);
        SpannableString spannable = new SpannableString(phText);
        int start = phText.indexOf(phValue);
        if (start != -1) { // Only setSpan if phValue is found in phText
            int end = start + phValue.length();
            spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        phTextView.setText(spannable);
    }

    /**
     * Getter for the temperature value
     *
     * @return the temperature value
     */
    public double getTemperatureValue() {
        return this.temperatureValue;
    }

    /**
     * Getter for the pH value
     *
     * @return the pH value
     */
    public double getPhValue() {
        return this.phValue;
    }

    /**
     * Getter for the nitrate value
     *
     * @return the nitrate value
     */
    public double getNitrateValue() {
        return this.nitrateValue;
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