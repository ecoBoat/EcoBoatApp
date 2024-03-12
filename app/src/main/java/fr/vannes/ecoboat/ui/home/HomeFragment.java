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

import fr.vannes.ecoboat.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    // Link the HomeViewModel to the HomeFragment's layout
    private FragmentHomeBinding binding;

    /**
     * This method is called when the fragment is created.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

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