package graduate.itdreams.android.ui.main;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.ui.base.activity.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public MainViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
//    public void doLogin(){
//        LoginRequest request = new LoginRequest();
//        request.setPosId(deviceId);
//        showLoading();
//        compositeDisposable.add(repository.getApiService().login(request)
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .retryWhen(throwable ->
//                                                           throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
//                                                               if (NetworkUtils.checkNetworkError(throwable1)) {
//                                                                   hideLoading();
//                                                                   return application.showDialogNoInternetAccess();
//                                                               }else{
//                                                                   return Observable.error(throwable1);
//                                                               }
//                                                           })
//                                        )
//                                        .subscribe(
//                                                response -> {
//                                                    hideLoading();
//                                                    repository.getSharedPreferences().setToken(response.getData().getAccess_token());
//                                                    showSuccessMessage("Login success");
//                                                }, throwable -> {
//                                                    hideLoading();
//                                                    Timber.e(throwable);
//                                                    if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400){
//                                                        HttpException httpException = (HttpException) throwable;
//                                                        if (httpException.code() == 400) {
//                                                        }
//                                                        showErrorMessage("Login failed");
//                                                    } else{
//                                                        showErrorMessage("Login failed");
//                                                    }
//                                                }));
//    }

}
