package fr.vannes.ecoboat.ui.nitrate;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import fr.vannes.ecoboat.R;
import fr.vannes.ecoboat.databinding.FragmentNitrateBinding;

public class NitrateFragment extends Fragment {

    private FragmentNitrateBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        NitrateViewModel nitrateViewModel =
                new ViewModelProvider(this).get(NitrateViewModel.class);

        binding = FragmentNitrateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView titleView = binding.titleNitrate;
        final TableLayout tableLayout = binding.tableNitrateLevel;

        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(20);
        titleView.setTextColor(Color.BLACK);
        titleView.setPadding(0, 0, 0, 30);
        titleView.setTypeface(titleView.getTypeface(), Typeface.BOLD);

        nitrateViewModel.getNitrateDataList().observe(getViewLifecycleOwner(), nitrateDataList -> {
            // Clear the table
            tableLayout.removeAllViews();

            // Create header row
            final TableRow headerRow = new TableRow(getContext());
            headerRow.setGravity(Gravity.CENTER);
            String[] headers = {"Heure", "Taux"};
            for (String header : headers) {
                TextView headerCell = new TextView(getContext());
                headerCell.setText(header);
                headerCell.setTextSize(16);
                headerCell.setTextColor(Color.BLACK);
                headerCell.setGravity(Gravity.CENTER);
                headerCell.setPadding(100, 8, 100, 8);
                headerCell.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.cell_border));
                headerRow.addView(headerCell);

                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.setMargins(10, 10, 10, 30); // marge de 10dp
                headerCell.setLayoutParams(params);
            }
            tableLayout.addView(headerRow);
            // Add rows to the table
            for (String nitrateData : nitrateDataList) {
                TableRow row = new TableRow(getContext());
                row.setGravity(Gravity.CENTER);

                String[] parts = nitrateData.split(" : ");
                for (String part : parts) {
                    TextView cell = new TextView(getContext());
                    cell.setGravity(Gravity.END);
                    cell.setText(part);
                    cell.setTextSize(16); // taille du texte
                    cell.setTextColor(Color.BLACK); // couleur du texte
//                    cell.setBackgroundColor(Color.WHITE); // couleur d'arrière-plan
                    cell.setPadding(100, 8, 100, 8); // padding
                    cell.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.cell_border)); // bordure

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.setMargins(10, 10, 10, 10); // marge de 10dp
                    cell.setLayoutParams(params);
                    row.addView(cell);
                }

                tableLayout.addView(row);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(NitrateViewModel.class);
//        // TODO: Use the ViewModel
//    }

}