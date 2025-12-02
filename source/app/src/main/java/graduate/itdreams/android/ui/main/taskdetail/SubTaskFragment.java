package graduate.itdreams.android.ui.main.taskdetail;

import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.request.task.CompleteTaskRequest;
import graduate.itdreams.android.data.model.api.request.task.TaskQuestionProgressRequest;
import graduate.itdreams.android.data.model.api.response.ItemTitleContentResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskProgressResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.databinding.FragmentSubTaskBinding;
import graduate.itdreams.android.di.component.FragmentComponent;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;
import graduate.itdreams.android.ui.main.simulation.overview.OverviewAdapter;
import graduate.itdreams.android.ui.main.taskdetail.quiz.QuestionQuizAdapter;

public class SubTaskFragment extends BaseFragment<FragmentSubTaskBinding,SubTaskViewModel> {
    private int currentIndex = 0;
    private SubTaskProgressResponse subTaskProgress;
    private File file;
    private QuestionItemAdapter.UploadFileCallback currentUploadCallback;
    public void loadSubTask(Long simulationId,SubTaskResponse subTask) {
        viewModel.fetchSubtaskDetail(subTask.getId());
        viewModel.getSubTaskDetail().observe(getViewLifecycleOwner(), responseSubtaskDetail -> {
            if (responseSubtaskDetail != null) {
                String introductionJson = responseSubtaskDetail.getIntroduction();
                Gson gson = new Gson();

                Type listType = new TypeToken<List<ItemTitleContentResponse>>(){}.getType();
                List<ItemTitleContentResponse> overviewList = gson.fromJson(introductionJson, listType);

                OverviewAdapter overviewAdapter = new OverviewAdapter(overviewList);
                binding.rcvContent.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.rcvContent.setAdapter(overviewAdapter);
                if(responseSubtaskDetail.getFilePath() == null){
                    binding.documentButton.setVisibility(View.GONE);
                }else {
                    binding.documentButton.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.createSubtaskProgress(subTask.getId());
        viewModel.getSubTaskProgress().observe(getViewLifecycleOwner(), responseSubtaskProgress -> {
            subTaskProgress = responseSubtaskProgress;
        });
        viewModel.fetchListTaskQuestion(simulationId, subTask.getId());
        viewModel.getTaskQuestion().observe(getViewLifecycleOwner(), responseTaskQuestion -> {
            if(responseTaskQuestion != null){
                if(responseTaskQuestion.get(0).getQuestionType() != 3){
                    binding.layoutQuestionFileAndText.setVisibility(View.VISIBLE);
                    binding.btnComplete.setVisibility(View.VISIBLE);
                    binding.layoutQuestionQuiz.setVisibility(View.GONE);
                    QuestionItemAdapter questionItemAdapter = new QuestionItemAdapter(responseTaskQuestion, (item, position, callback) -> {
                        currentUploadCallback = callback;
                        // Mở file picker ở đây
                        filePickerLauncher.launch("application/pdf");
                    }, (item, position, file) -> {
                        if(item.getQuestionType() == 1){
                            submitFileClick(responseTaskQuestion.get(position).getId(), file);
                        }
                    }, (item, position, answer) -> {
                        if(item.getQuestionType() == 2){
                            submitTextClick(responseTaskQuestion.get(position).getId(), answer);
                        }
                    });
                    binding.rcvQuestionFileAndText.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.rcvQuestionFileAndText.setAdapter(questionItemAdapter);
                }else {
                    binding.layoutQuestionQuiz.setVisibility(View.VISIBLE);
                    binding.btnComplete.setVisibility(View.VISIBLE);
                    binding.layoutQuestionFileAndText.setVisibility(View.GONE);
                    QuestionQuizAdapter adapter = new QuestionQuizAdapter(responseTaskQuestion, currentIndex, isCorrect -> {
                        binding.btnNext.setEnabled(true);
                        submitQuizClick(responseTaskQuestion.get(currentIndex).getId(), isCorrect);
                    });
                    binding.rcvQuestionQuiz.setAdapter(adapter);
                    binding.rcvQuestionQuiz.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.btnNext.setOnClickListener(v -> {
                        if (currentIndex < responseTaskQuestion.size() - 1) {
                            currentIndex++;
                            adapter.setCurrentIndex(currentIndex);
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
        binding.btnComplete.setOnClickListener(v -> {
            CompleteTaskRequest request = new CompleteTaskRequest();
            request.setTaskId(subTask.getId());
            viewModel.completeTask(request);
        });

    }
    private void submitQuizClick(Long taskQuestionId, Boolean isCorrect){
        TaskQuestionProgressRequest request = new TaskQuestionProgressRequest();
        request.setAnswer("câu trả lời trắc nghiệm");
        request.setStudentSubTaskProgressId(subTaskProgress.getId());
        request.setTaskQuestionId(taskQuestionId);
        request.setIsCorrect(isCorrect);
        viewModel.submitQuestion(request);
    }
    private void submitTextClick(Long taskQuestionId, String answer){
        TaskQuestionProgressRequest request = new TaskQuestionProgressRequest();
        request.setAnswer(answer);
        request.setStudentSubTaskProgressId(subTaskProgress.getId());
        request.setTaskQuestionId(taskQuestionId);
        request.setIsCorrect(true);
        viewModel.submitQuestion(request);
    }
    private void submitFileClick(Long taskQuestionId, File file){
        TaskQuestionProgressRequest request = new TaskQuestionProgressRequest();
        viewModel.uploadFile(file);
        viewModel.getFilePath().observe(getViewLifecycleOwner(), filePath -> {
            request.setAnswer(filePath);
            request.setStudentSubTaskProgressId(subTaskProgress.getId());
            request.setTaskQuestionId(taskQuestionId);
            request.setIsCorrect(true);
            viewModel.submitQuestion(request);
        });

    }
    private final ActivityResultLauncher<String> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    file = uriToFile(uri);
                    currentUploadCallback.onFileSelected(file); // ← gọi callback ở đây
                    currentUploadCallback = null;
                }
            });
    private File uriToFile(Uri uri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
            File tempFile = new File(requireContext().getCacheDir(), "upload_" + System.currentTimeMillis());

            FileOutputStream out = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            out.close();
            inputStream.close();

            return tempFile; // File đã convert thành công
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void viewFileClick(){
        String url = viewModel.getSubTaskDetail().getValue().getFilePath();
        Intent intent = new Intent(getContext(), PdfActivity.class);
        intent.putExtra("url_pdf", url );
        startActivity(intent);
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_task;
    }

    @Override
    protected void performDataBinding() {
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}