package graduate.itdreams.android.ui.main.taskdetail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import graduate.itdreams.android.data.model.api.response.ItemTitleContentResponse;
import graduate.itdreams.android.data.model.api.response.question.TaskQuestionResponse;
import graduate.itdreams.android.databinding.ItemQuestionBinding;
import graduate.itdreams.android.databinding.ItemTitleContentBinding;

public class QuestionItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_FILE = 1;
    private static final int VIEW_TYPE_TEXT = 2;

    private List<TaskQuestionResponse> questionList = new ArrayList<>();

    public QuestionItemAdapter(List<TaskQuestionResponse> questionList) {
        this.questionList = questionList;
    }
    @Override
    public int getItemViewType(int position) {
        TaskQuestionResponse item = questionList.get(position);
        return item.getQuestionType();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_TEXT) {
            ItemQuestionBinding binding =
                    ItemQuestionBinding.inflate(inflater, parent, false);
            return new TextViewHolder(binding);
        } else {
            ItemQuestionBinding binding =
                    ItemQuestionBinding.inflate(inflater, parent, false);
            return new FileViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskQuestionResponse item = questionList.get(position);

        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).bind(item);
        } else if (holder instanceof FileViewHolder) {
            ((FileViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return questionList != null ? questionList.size() : 0;
    }
    static class TextViewHolder extends RecyclerView.ViewHolder {
        ItemQuestionBinding binding;

        public TextViewHolder(ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TaskQuestionResponse item) {
            binding.tvQuestion.setText(item.getQuestion());
            // xử lý thêm nếu cần
        }
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        ItemQuestionBinding binding;

        public FileViewHolder(ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TaskQuestionResponse item) {
            binding.tvQuestion.setText(item.getQuestion());

//            binding.btnUploadFile.setOnClickListener(v -> {
//                // TODO: mở file picker hoặc callback lên Activity/Fragment
//            });
        }
    }
}

