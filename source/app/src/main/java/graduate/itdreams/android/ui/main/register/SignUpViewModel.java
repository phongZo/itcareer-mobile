package graduate.itdreams.android.ui.main.register;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.File;

import graduate.itdreams.android.data.model.api.response.file.UploadResponse;
import graduate.itdreams.android.ui.base.fragment.BaseFragmentViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.data.model.api.request.student.StudentSignUpRequest;
import graduate.itdreams.android.utils.NetworkUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class SignUpViewModel extends BaseFragmentViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final MutableLiveData<String> avatarUri = new MutableLiveData<>();
    public final MutableLiveData<String> idHash = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();


    public SignUpViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void signUpCandidate(StudentSignUpRequest request, Context context) {
        showLoading();
        compositeDisposable.add(repository.getApiService().signUpCandidate(request)
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
                            String hash = response.getData().getIdHash();
                            idHash.setValue(hash);
                            isSuccess.setValue(true);
                        }, throwable -> {
                            hideLoading();
                            isSuccess.setValue(false);
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                            showNormalMessage(context.getString(R.string.signup_un_success));
                        }));
    }

    public void uploadImage(File imageFile ){
        if (imageFile == null || !imageFile.exists()) {
            Log.e("Upload", "❌ File ảnh không hợp lệ hoặc không tồn tại!");
            return;
        }

        Log.d("Upload", "📤 Bắt đầu upload ảnh: " + imageFile.getAbsolutePath());

        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "AVATAR");

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", imageFile.getName(), requestFile);

        compositeDisposable.add(repository.getApiService().uploadImage(type, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult() && response.getData() != null) {
                        UploadResponse uploadedUrl = response.getData();
                        avatarUri.setValue(uploadedUrl.getFilePath());
                        Log.d("Upload", "✅ Thành công! Ảnh URL: " + uploadedUrl);
                    } else {
                        Log.e("Upload", "❌ Lỗi Upload: " + response.getMessage());
                    }
                }, throwable -> {
                    Log.e("Upload", "❌ Lỗi kết nối: " + throwable.getMessage());
                })
        );
    }
}
