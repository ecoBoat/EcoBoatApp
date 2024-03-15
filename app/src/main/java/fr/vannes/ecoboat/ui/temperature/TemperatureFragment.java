package fr.vannes.ecoboat.ui.temperature;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.vannes.ecoboat.R;
import fr.vannes.ecoboat.databinding.FragmentTemperatureBinding;

public class TemperatureFragment extends Fragment {

    private FragmentTemperatureBinding binding;
    private LineChart chart;

    public static TemperatureFragment newInstance() {
        return new TemperatureFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TemperatureViewModel temperatureViewModel =
                new ViewModelProvider(this).get(TemperatureViewModel.class);

        binding = FragmentTemperatureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTemperature;
        temperatureViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        chart = binding.chart;

        // Créez une liste d'entrées avec des valeurs fictives
        List<Entry> entries = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            float yValue = random.nextFloat() * 100; // Génère une valeur y aléatoire entre 0 et 100
            entries.add(new Entry(i, yValue));
        }

        // Créez un LineDataSet à partir des entrées
        LineDataSet dataSet = new LineDataSet(entries, "Temperature");

        // Créez un LineData à partir du LineDataSet et définissez-le comme les données du graphique
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // Rafraîchissez le graphique
        chart.invalidate();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}