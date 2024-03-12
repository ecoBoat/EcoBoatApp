package fr.vannes.ecoboat.ui.nitrate;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
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

import fr.vannes.ecoboat.R;
import fr.vannes.ecoboat.databinding.FragmentNitrateBinding;

/**
 * NitrateFragment class
 */
public class NitrateFragment extends Fragment {

    // Link the NitrateViewModel to the NitrateFragment's layout
    private FragmentNitrateBinding binding;


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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Create a NitrateViewModel
        NitrateViewModel nitrateViewModel =
                new ViewModelProvider(this).get(NitrateViewModel.class);

        // Inflate the layout for this fragment
        binding = FragmentNitrateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TableLayout tableLayout = binding.tableNitrateLevel;

        // Observe the LiveData from the NitrateViewModel
        nitrateViewModel.getNitrateDataList().observe(getViewLifecycleOwner(), nitrateDataList -> {
            // Clear the table
            tableLayout.removeAllViews();

            // Create header row
            final TableRow headerRow = new TableRow(getContext());
            headerRow.setGravity(Gravity.CENTER);
            String[] headers = {"Heure", "Taux"};
            // Loop through the headers
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
            // Add the header row to the table
            tableLayout.addView(headerRow);

            // Loop through the nitrate data list
            for (String nitrateData : nitrateDataList) {
                TableRow row = new TableRow(getContext());
                row.setGravity(Gravity.CENTER);
                // Split the nitrate data
                String[] parts = nitrateData.split(" : ");
                for (String part : parts) {
                    TextView cell = new TextView(getContext());
                    cell.setGravity(Gravity.END);
                    cell.setText(part);
                    cell.setTextSize(16);
                    cell.setTextColor(Color.BLACK);
                    cell.setPadding(100, 8, 100, 8);
                    cell.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.cell_border));

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.setMargins(10, 10, 10, 10);
                    cell.setLayoutParams(params);
                    row.addView(cell);
                }
                // Add the row to the table
                tableLayout.addView(row);
            }
        });
        // Return the root view
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