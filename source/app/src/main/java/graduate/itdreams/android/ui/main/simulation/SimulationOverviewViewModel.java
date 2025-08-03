package graduate.itdreams.android.ui.main.simulation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationDetailResponse;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationResponse;
import graduate.itdreams.android.ui.base.activity.BaseViewModel;
import graduate.itdreams.android.utils.NetworkUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import timber.log.Timber;

public class SimulationOverviewViewModel extends BaseViewModel {
    public final MutableLiveData<String> title = new MutableLiveData<>("");
    public final MutableLiveData<String> author = new MutableLiveData<>("");
    public final MutableLiveData<String> estimatedTime = new MutableLiveData<>("");
    public final MutableLiveData<String> rating = new MutableLiveData<>("");
    private final MutableLiveData<SimulationDetailResponse> simulationDetail = new MutableLiveData<>();
    public LiveData<SimulationDetailResponse> getSimulationDetail() {
        return simulationDetail;
    }

    private final MutableLiveData<Long> simulationId = new MutableLiveData<>();
    public LiveData<Long> getSimulationId() {
        return simulationId;
    }
    public SimulationOverviewViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void fetchSimulationDetail(Long id) {
        showLoading();
        compositeDisposable.add(repository.getApiService().getSimulationDetail(id)
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
                            title.setValue(response.getData().getTitle());
                            author.setValue(response.getData().getEducator().getProfileAccountDto().getFullName());
                            estimatedTime.setValue(response.getData().getTotalEstimatedTime());
                            rating.setValue(response.getData().getAvgRating().toString().trim());

                            simulationDetail.setValue(response.getData());
                            simulationId.setValue(id);
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
