package graduate.itdreams.android.ui.main.account;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.InputStream;

import graduate.itdreams.android.utils.ImageUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.ui.base.fragment.BaseFragmentViewModel;
import graduate.itdreams.android.utils.NetworkUtils;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class AccountViewModel extends BaseFragmentViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<Bitmap> avatarLiveData = new MutableLiveData<>();

    public final MutableLiveData<String> fullName = new MutableLiveData<>("");
    public final MutableLiveData<String> email = new MutableLiveData<>("");

    public AccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        fetchProfile();
    }
    public void fetchProfile() {
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
                            fullName.setValue(response.getData().getProfileAccountDto().getFullName());
                            email.setValue(response.getData().getProfileAccountDto().getEmail());
                            loadAvatar(response.getData().getProfileAccountDto().getAvatar());
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

    public void loadAvatar(String url){
        compositeDisposable.add(repository.getUploadApiService().loadImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( responseBody ->  {
                    InputStream inputStream = responseBody.byteStream();
                    Bitmap bitmap = ImageUtils.getBitmap(inputStream);
                    if (bitmap != null) {
                        avatarLiveData.setValue(bitmap);
                    } else {
                        Log.e("ProfileViewModel", "Lỗi: Bitmap rỗng");
                    }
                }, throwable -> {
                    Log.e("ProfileViewModel", "Lỗi khi tải ảnh: " + throwable.getMessage());
                })
        );

    }
    public void logout(){
        repository.getSharedPreferences().setToken(null);
        repository.getSharedPreferences().saveAccessTokenObject(null);
    }
}
