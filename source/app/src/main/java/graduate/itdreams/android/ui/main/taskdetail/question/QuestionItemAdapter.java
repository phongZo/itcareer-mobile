package graduate.itdreams.android.ui.main.taskdetail.question;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import graduate.itdreams.android.data.model.api.response.question.TaskQuestionResponse;
import graduate.itdreams.android.databinding.ItemQuestionFileBinding;
import graduate.itdreams.android.databinding.ItemQuestionTextBinding;

public class QuestionItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_FILE = 1;
    private static final int VIEW_TYPE_TEXT = 2;

    private List<TaskQuestionResponse> questionList = new ArrayList<>();
    public interface OnUploadFileClickListener {
        void onUploadFileClick(TaskQuestionResponse item, int position, UploadFileCallback callback);
    }
    public interface UploadFileCallback {
        void onFileSelected(File file);
    }
    private OnUploadFileClickListener listener;
    public interface OnSubmitFileClickListener {
        void onSubmitFileClick(TaskQuestionResponse item, int position, File selectedFile);
    }
    private OnSubmitFileClickListener submitFileListener;
    public interface OnSubmitTextClickListener {
        void onSubmitTextClick(TaskQuestionResponse item, int position, String answer);
    }
    private OnSubmitTextClickListener submitTextListener;
    public QuestionItemAdapter(List<TaskQuestionResponse> questionList, OnUploadFileClickListener listener, OnSubmitFileClickListener submitFileListener, OnSubmitTextClickListener submitTextListener) {
        this.questionList = questionList;
        this.listener = listener;
        this.submitFileListener = submitFileListener;
        this.submitTextListener = submitTextListener;
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
            ItemQuestionTextBinding binding =
                    ItemQuestionTextBinding.inflate(inflater, parent, false);
            return new TextViewHolder(binding, submitTextListener);
        } else {
            ItemQuestionFileBinding binding =
                    ItemQuestionFileBinding.inflate(inflater, parent, false);
            return new FileViewHolder(binding, submitFileListener, listener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskQuestionResponse item = questionList.get(position);

        if (holder instanceof TextViewHolder) {
            ((TextViewHolder) holder).bind(item, position);
        } else if (holder instanceof FileViewHolder) {
            ((FileViewHolder) holder).bind(item, position);
        }
    }

    @Override
    public int getItemCount() {
        return questionList != null ? questionList.size() : 0;
    }
    static class TextViewHolder extends RecyclerView.ViewHolder {
        ItemQuestionTextBinding binding;
        OnSubmitTextClickListener submitClickListener;
        public TextViewHolder(ItemQuestionTextBinding binding, OnSubmitTextClickListener submitClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.submitClickListener = submitClickListener;
        }

        void bind(TaskQuestionResponse item, int position) {
            binding.tvQuestion.setText(item.getQuestion());
            binding.btnSubmit.setOnClickListener(v ->{
                String answer = binding.textAnswer.getText().toString().trim();
                if (submitClickListener != null) submitClickListener.onSubmitTextClick(item, position, answer);
            });
        }
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        ItemQuestionFileBinding binding;
        OnUploadFileClickListener listener;
        OnSubmitFileClickListener submitClickListener;
        File selectedFile = null;
        public FileViewHolder(ItemQuestionFileBinding binding,OnSubmitFileClickListener submitClickListener, OnUploadFileClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.submitClickListener = submitClickListener;
            this.listener = listener;
        }

        void bind(TaskQuestionResponse item, int position) {
            binding.tvQuestion.setText(item.getQuestion());

            binding.btnUploadFile.setOnClickListener(v -> {
                if (listener != null){
                    listener.onUploadFileClick(item, position, new UploadFileCallback() {
                        @Override
                        public void onFileSelected(File file) {
                            selectedFile = file;
                            binding.tvFileName.setText(file.getName());
                        }
                    });
                }
            });

            binding.btnSubmit.setOnClickListener(v ->{
                if (submitClickListener != null) submitClickListener.onSubmitFileClick(item, position, selectedFile);
            });
        }
    }

}

