package graduate.itdreams.android.ui.main.account;



import androidx.lifecycle.MutableLiveData;

import graduate.itdreams.android.data.model.api.request.student.StudentUpdateProfileRequest;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.ui.base.activity.BaseViewModel;
import graduate.itdreams.android.utils.NetworkUtils;
import retrofit2.HttpException;
import timber.log.Timber;

public class EditProfileViewModel extends BaseViewModel {
    public final MutableLiveData<String> fullname = new MutableLiveData<>("");
    public final MutableLiveData<String> email = new MutableLiveData<>("");
    public final MutableLiveData<String> birthday = new MutableLiveData<>("");
    public final MutableLiveData<String> username = new MutableLiveData<>("");
    public EditProfileViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void updateProfile(StudentUpdateProfileRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().update(request)
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
                            showNormalMessage(getApplication().getString(R.string.update_success));
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                            showNormalMessage(getApplication().getString(R.string.update_un_success));
                        }));
    }
    public void loadProfile() {
        showLoading();
        compositeDisposable.add(repository.getApiService().getProfile()
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
                            fullname.setValue(response.getData().getProfileAccountDto().getFullName());
                            email.setValue(response.getData().getProfileAccountDto().getEmail());
                            username.setValue(response.getData().getProfileAccountDto().getUsername());
                            birthday.setValue(response.getData().getBirthday());

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
}

