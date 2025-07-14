package graduate.itdreams.android.ui.main.comment;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.ui.base.fragment.BaseFragmentViewModel;

public class TopCommentViewModel extends BaseFragmentViewModel {
    public TopCommentViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
