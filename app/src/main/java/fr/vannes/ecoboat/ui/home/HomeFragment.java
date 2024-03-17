package fr.vannes.ecoboat.ui.home;

import android.os.Bundle;
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

        // Get the water quality text from the layout
        homeViewModel.getWaterQualityText().observe(getViewLifecycleOwner(), waterQualityText -> {
            TextView waterQualityTextView = binding.waterQualityText;
            waterQualityTextView.setText(waterQualityText);
        });

        // Get the location from the layout
        homeViewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            TextView locationTextView = binding.locationText;
            locationTextView.setText(location);
        });

        // Observe the nitrate data
        nitrateViewModel.getNitrate().observe(getViewLifecycleOwner(), nitrateList -> {
            if (nitrateList != null && !nitrateList.isEmpty()) {
                String firstNitrateValue = nitrateList.get(0);
                TextView nitrateTextView = binding.nitrateText;
                // Use resource string with placeholder
                String nitrateText = getString(R.string.nitrate_text, firstNitrateValue);
                nitrateTextView.setText(nitrateText);
            } else {
                TextView nitrateTextView = binding.nitrateText;
                String nitrateText = getString(R.string.nitrate_text, "N/A");
                nitrateTextView.setText(nitrateText);
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
            } else {
                TextView temperatureTextView = binding.temperatureText;
                String temperatureText = getString(R.string.temperature_text, "N/A");
                temperatureTextView.setText(temperatureText);
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
            } else {
                TextView phTextView = binding.phText;
                String phText = getString(R.string.ph_text, "N/A");
                phTextView.setText(phText);
            }
        });

        // Return the root view
        return root;
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