package graduate.itdreams.android.ui.main.simulation.overview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationDetailResponse;
import graduate.itdreams.android.ui.base.fragment.BaseFragmentViewModel;

public class OverviewViewModel extends BaseFragmentViewModel {

    public OverviewViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
