package graduate.itdreams.android.ui.main.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import graduate.itdreams.android.data.model.api.response.simulation.SimulationResponse;
import graduate.itdreams.android.databinding.ItemSimulationBinding;

public class SimulationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<SimulationResponse> simulationList = new ArrayList<>();
    private static OnPostClickListener listener;
    public interface OnPostClickListener {
        void onItemClick(Long postId);
    }
    private HomeViewModel viewModel;
    public SimulationAdapter(HomeViewModel viewModel, OnPostClickListener listener){
        this.viewModel = viewModel;
        this.listener = listener;
    }

    public void setData(List<SimulationResponse> newData){
        simulationList.clear();

        if(newData != null){
            simulationList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSimulationBinding binding = ItemSimulationBinding.inflate(inflater,parent, false);
        return new SimulationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("PostsAdapter", "Binding item at position: " + position);
        if(holder instanceof SimulationViewHolder){
            ((SimulationViewHolder) holder).bind(simulationList.get(position), viewModel);
        }
    }

    @Override
    public int getItemCount() {
        return simulationList.size();
    }


    static class SimulationViewHolder extends RecyclerView.ViewHolder{
        private final ItemSimulationBinding binding;
        public SimulationViewHolder(ItemSimulationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(SimulationResponse item, HomeViewModel viewModel){
            binding.tvJobPosition.setText(item.getTitle());
            binding.tvCompanyName.setText(item.getEducator().getProfileAccountDto().getFullName());
            binding.ratingBar.setRating(item.getAvgRating());
            binding.tvEstimatedTime.setText(item.getTotalEstimatedTime());

            binding.getRoot().setOnClickListener(v -> {
                if (item != null) {
                    listener.onItemClick(item.getId());
                }
            });
        }
    }
}
