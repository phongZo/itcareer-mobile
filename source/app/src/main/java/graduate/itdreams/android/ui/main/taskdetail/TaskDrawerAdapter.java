package graduate.itdreams.android.ui.main.taskdetail;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;

public class TaskDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> displayList = new ArrayList<>();
    private final OnSubTaskClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private final List<TaskResponse> originalList = new ArrayList<>();
    private final Set<String> expandedTaskTitles = new HashSet<>();

    public interface OnSubTaskClickListener {
        void onSubTaskClick(TaskResponse task, SubTaskResponse subTask);
    }


    public TaskDrawerAdapter(OnSubTaskClickListener listener) {
        this.listener = listener;
    }

    public void setTaskItems(List<TaskResponse> taskItems) {
        originalList.clear();
        originalList.addAll(taskItems);
        rebuildDisplayList();
    }

    private void rebuildDisplayList() {
        displayList.clear();
        for (TaskResponse task : originalList) {
            displayList.add(task); // Parent
//            if (expandedTaskTitles.contains(task.getTitle())) {
//                displayList.addAll(task.getSubTasks()); // Sub items
//            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = displayList.get(position);
        return (item instanceof TaskResponse) ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_task_parent, parent, false);
        return (viewType == 0)
                ? new ParentViewHolder(view)
                : new SubTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = displayList.get(position);

        holder.itemView.setBackgroundColor(
                position == selectedPosition ? Color.LTGRAY : Color.TRANSPARENT
        );

        if (holder instanceof ParentViewHolder) {
            ((ParentViewHolder) holder).bind((TaskResponse) item);
        } else {
            ((SubTaskViewHolder) holder).bind((SubTaskResponse) item);
        }
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        ImageView expandIcon;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            expandIcon = itemView.findViewById(R.id.expandIcon);
        }

        void bind(TaskResponse task) {
//            text1.setText(task.getTitle());
//
//            boolean isExpanded = expandedTaskTitles.contains(task.getTitle());
//            expandIcon.setImageResource(isExpanded ? R.drawable.ic_task_open : R.drawable.ic_task_close);
//
//            itemView.setOnClickListener(v -> {
//                String title = task.getTitle();
//                if (isExpanded) {
//                    expandedTaskTitles.remove(title);
//                } else {
//                    expandedTaskTitles.add(title);
//                }
//                rebuildDisplayList();
//            });
        }
    }

    class SubTaskViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        ImageView icon;

        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            icon = itemView.findViewById(R.id.expandIcon);
        }

        void bind(SubTaskResponse subTask) {
            text1.setText(subTask.getName());
            icon.setImageResource(R.drawable.ic_fries_menu); // icon riêng cho subtask

            itemView.setOnClickListener(v -> {
                int oldPos = selectedPosition;
                selectedPosition = getAdapterPosition();
                notifyItemChanged(oldPos);
                notifyItemChanged(selectedPosition);

                // Tìm task cha của subtask
                int position = getAdapterPosition();
                for (int i = position - 1; i >= 0; i--) {
                    Object item = displayList.get(i);
                    if (item instanceof TaskResponse) {
                        listener.onSubTaskClick((TaskResponse) item, subTask);
                        break;
                    }
                }
            });
        }
    }
}
