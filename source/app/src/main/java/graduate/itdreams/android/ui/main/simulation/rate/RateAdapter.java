package graduate.itdreams.android.ui.main.simulation.rate;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import graduate.itdreams.android.databinding.ItemRateBinding;

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private final int itemCount = 5;

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRateBinding binding = ItemRateBinding.inflate(inflater, parent, false);
        return new RateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    static class RateViewHolder extends RecyclerView.ViewHolder {
        public RateViewHolder(ItemRateBinding binding) {
            super(binding.getRoot());
        }
    }
}

