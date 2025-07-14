package graduate.itdreams.android.ui.main.notification;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.ui.base.fragment.BaseFragmentViewModel;

public class NotificationViewModel extends BaseFragmentViewModel {
    public NotificationViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
