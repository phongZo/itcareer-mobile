package graduate.itdreams.android.ui.main.taskdetail;

import static kotlin.io.ByteStreamsKt.readBytes;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.data.model.api.request.student.StudentUpdateProfileRequest;
import graduate.itdreams.android.data.model.api.request.task.CompleteTaskRequest;
import graduate.itdreams.android.data.model.api.request.task.TaskQuestionProgressRequest;
import graduate.itdreams.android.data.model.api.response.file.UploadResponse;
import graduate.itdreams.android.data.model.api.response.question.TaskQuestionProgressResponse;
import graduate.itdreams.android.data.model.api.response.question.TaskQuestionResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskProgressResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import graduate.itdreams.android.ui.base.fragment.BaseFragmentViewModel;
import graduate.itdreams.android.utils.ImageUtils;
import graduate.itdreams.android.utils.NetworkUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class SubTaskViewModel extends BaseFragmentViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<SubTaskResponse> subTaskDetailLiveData = new MutableLiveData<>();
    public LiveData<SubTaskResponse> getSubTaskDetail() { return subTaskDetailLiveData; }
    private final MutableLiveData<SubTaskProgressResponse> subTaskProgressLiveData = new MutableLiveData<>();
    public LiveData<SubTaskProgressResponse> getSubTaskProgress() { return subTaskProgressLiveData; }
    private final MutableLiveData<List<TaskQuestionProgressResponse>> taskQuestionProgressLiveData = new MutableLiveData<>();
    public LiveData<List<TaskQuestionProgressResponse>> getTaskQuestionProgress() { return taskQuestionProgressLiveData; }
    private final MutableLiveData<List<TaskQuestionResponse>> taskQuestionLiveData = new MutableLiveData<>();
    public LiveData<List<TaskQuestionResponse>> getTaskQuestion() { return taskQuestionLiveData; }
    private final MutableLiveData<String> fileLiveData = new MutableLiveData<>();
    public LiveData<String> getFilePath() { return fileLiveData; }
    public SubTaskViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void fetchSubtaskDetail(Long taskId) {
        showLoading();
        compositeDisposable.add(repository.getApiService().getSubTaskDetail(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
                            if (NetworkUtils.checkNetworkError(throwable1)) {
                                hideLoading();
                                return application.showDialogNoInternetAccess();
                            } else {
                                return Observable.error(throwable1);
                            }
                        })
                )
                .subscribe(
                        response -> {
                            hideLoading();
                            subTaskDetailLiveData.setValue(response.getData());
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                        }));

    }
    public void createSubtaskProgress(Long taskId) {
        showLoading();
        compositeDisposable.add(repository.getApiService().createSubTaskProgress(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
                            if (NetworkUtils.checkNetworkError(throwable1)) {
                                hideLoading();
                                return application.showDialogNoInternetAccess();
                            } else {
                                return Observable.error(throwable1);
                            }
                        })
                )
                .subscribe(
                        response -> {
                            hideLoading();
                            subTaskProgressLiveData.setValue(response.getData());
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                        }));

    }
//    public void fetchListTaskQuestionProgress(Long studentSubTaskProgressId,Long taskId) {
//        showLoading();
//        compositeDisposable.add(repository.getApiService().getTaskQuestionProgressList(studentSubTaskProgressId, taskId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .retryWhen(throwable ->
//                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
//                            if (NetworkUtils.checkNetworkError(throwable1)) {
//                                hideLoading();
//                                return application.showDialogNoInternetAccess();
//                            } else {
//                                return Observable.error(throwable1);
//                            }
//                        })
//                )
//                .subscribe(
//                        response -> {
//                            hideLoading();
//                            taskQuestionProgressLiveData.setValue(response.getData().getContent());
//                        }, throwable -> {
//                            hideLoading();
//                            Timber.e(throwable);
//                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
//                                HttpException httpException = (HttpException) throwable;
//                                if (httpException.code() == 400) {
//                                }
//                            }
//                        }));
//
//    }
    public void fetchListTaskQuestion(Long simulationId,Long taskId) {
        showLoading();
        compositeDisposable.add(repository.getApiService().getTaskQuestionList(simulationId, taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
                            if (NetworkUtils.checkNetworkError(throwable1)) {
                                hideLoading();
                                return application.showDialogNoInternetAccess();
                            } else {
                                return Observable.error(throwable1);
                            }
                        })
                )
                .subscribe(
                        response -> {
                            hideLoading();
                            taskQuestionLiveData.setValue(response.getData().getContent());
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                        }));

    }

    public void completeTask(CompleteTaskRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().completeTask(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
                            if (NetworkUtils.checkNetworkError(throwable1)) {
                                hideLoading();
                                return application.showDialogNoInternetAccess();
                            } else {
                                return Observable.error(throwable1);
                            }
                        })
                )
                .subscribe(
                        response -> {
                            hideLoading();
                            //taskQuestionLiveData.setValue(response.getData().getContent());
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                        }));

    }

    public void submitQuestion(TaskQuestionProgressRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().submitQuestion(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
                            if (NetworkUtils.checkNetworkError(throwable1)) {
                                hideLoading();
                                return application.showDialogNoInternetAccess();
                            } else {
                                return Observable.error(throwable1);
                            }
                        })
                )
                .subscribe(
                        response -> {
                            hideLoading();
                            //taskQuestionLiveData.setValue(response.getData().getContent());
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                        }));

    }
    public void uploadFile(File file) {
        if (file == null || !file.exists()) {
            showNormalMessage("File không hợp lệ");
            return;
        }

        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "DOCUMENT");

        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        showLoading();
        compositeDisposable.add(repository.getUploadApiService().uploadFile(type, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideLoading();
                    if (response.isResult() && response.getData() != null) {
                        UploadResponse uploadedUrl = response.getData();
                        String filePath = uploadedUrl.getFilePath();
                        String fixedPath = filePath.replace("\\", "/");
                        fileLiveData.setValue(fixedPath);
                    }
                }, throwable -> {
                    hideLoading();
                    Timber.e(throwable);
                    if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                        HttpException httpException = (HttpException) throwable;
                        if (httpException.code() == 400) {
                        }
                    }
                })
        );
    }
}
