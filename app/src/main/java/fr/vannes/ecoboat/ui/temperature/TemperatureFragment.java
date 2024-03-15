package fr.vannes.ecoboat.ui.temperature;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.vannes.ecoboat.databinding.FragmentTemperatureBinding;

/**
 * Fragment to display the temperature.
 */
public class TemperatureFragment extends Fragment {

    // The binding for the fragment
    private FragmentTemperatureBinding binding;

    /**
     * Method to create a new instance of the TemperatureFragment
     * @return The new instance of the TemperatureFragment
     */
    public static TemperatureFragment newInstance() {
        return new TemperatureFragment();
    }

    /**
     * Method to create the view of the fragment
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return The view of the fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Create a TemperatureViewModel
        TemperatureViewModel temperatureViewModel =
                new ViewModelProvider(this).get(TemperatureViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentTemperatureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observe the LiveData from the TemperatureViewModel
        final TextView textView = binding.textTemperature;
        temperatureViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Create the LineChart
        final LineChart lineChart = binding.chart;

        // Observe the LiveData from the TemperatureViewModel
        temperatureViewModel.getTemperature().observe(getViewLifecycleOwner(), temperature -> {
            // Create the entries for the LineChart
            List<Entry> entries = new ArrayList<>();
            // For each temperature, create an entry
            for (Map<String, String> temp : temperature) {
                // If the Android version is Oreo or higher
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    // Get the date of the temperature
                    String dateString = Objects.requireNonNull(temp.get("created"));
                    // Create an Instant from the date
                    Instant instantCreated = Instant.parse(dateString);
                    // Get the current date
                    Instant instantNow = Instant.now();
                    // Calculate the difference in seconds between the current date and the date of the temperature
                    long differenceInSeconds = instantNow.getEpochSecond() - instantCreated.getEpochSecond();
                    // Calculate the difference in minutes
                    float differenceInMinutes = differenceInSeconds / 60.0f;
                    // Get the temperature value
                    float yValue = Float.parseFloat(Objects.requireNonNull(temp.get("temperature")));
                    // Add the entry to the entries list
                    entries.add(new Entry(differenceInMinutes, yValue));
                }
            }

            Log.d("TemperatureFragment", "Temperature entries created: " + entries);

            // Create the LineDataSet and LineData
            LineDataSet lineDataSet = new LineDataSet(entries, "Temperature");
            LineData lineData = new LineData(lineDataSet);
            // Set the LineData to the LineChart
            lineChart.setData(lineData);
            lineChart.invalidate();

            Log.d("TemperatureFragment", "Chart data set and invalidated");
        });

        // Return the view of the fragment
        return root;
    }

    /**
     * This method is called when the fragment is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}