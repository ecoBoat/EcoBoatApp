package fr.vannes.ecoboat.ui.ph;

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

import fr.vannes.ecoboat.R;
import fr.vannes.ecoboat.databinding.FragmentPhBinding;

public class PhFragment extends Fragment {

    // The binding for the fragment
    private FragmentPhBinding binding;

    /**
     * Method to create a new instance of the PhFragment
     *
     * @return The new instance of the PhFragment
     */
    public static PhFragment newInstance() {
        return new PhFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Call the ViewModelProvider to create a new instance of the PhViewModel
        PhViewModel phViewModel =
                new ViewModelProvider(this).get(PhViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentPhBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observe the LiveData from the PhViewModel
        final TextView textView = binding.textPh;
        phViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final TextView subtitleView = binding.chartLabel;
        phViewModel.getSubtitle().observe(getViewLifecycleOwner(), subtitleView::setText);

        // Create the LineChart
        final LineChart lineChart = binding.chart;

        // Observe the LiveData from the PhViewModel
        phViewModel.getPh().observe(getViewLifecycleOwner(), ph -> {
            // Create the entries for the LineChart
            List<Entry> entries = new ArrayList<>();
            // For each pH value, create an entry
            for (Map<String, String> map : ph) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    // Get the date of the pH value
                    String dateString = Objects.requireNonNull(map.get("created"));
                    // Create an Instant from the date
                    Instant instantCreated = Instant.parse(dateString);
                    // Get the current date
                    Instant instantNow = Instant.now();
                    // Calculate the difference in seconds between the current date and the date of the temperature
                    long differenceInSeconds = instantNow.getEpochSecond() - instantCreated.getEpochSecond();
                    // Calculate the difference in minutes
                    float differenceInMinutes = differenceInSeconds / 60.0f;
                    // Get the temperature value
                    float yValue = Float.parseFloat(Objects.requireNonNull(map.get("pH")));
                    // Add the entry to the entries list
                    entries.add(new Entry(differenceInMinutes, yValue));
                }
            }

            Log.d("TemperatureFragment", "Temperature entries created: " + entries);

            // Create the LineDataSet and LineData
            LineDataSet lineDataSet = new LineDataSet(entries, "pH");
            LineData lineData = new LineData(lineDataSet);
            // Set the LineData to the LineChart
            lineChart.setData(lineData);
            lineChart.invalidate();

            Log.d("TemperatureFragment", "Chart data set and invalidated");
        });


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