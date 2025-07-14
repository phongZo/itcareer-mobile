package graduate.itdreams.android.di.component;


import graduate.itdreams.android.di.module.FragmentModule;
import graduate.itdreams.android.di.scope.FragmentScope;

import dagger.Component;
import graduate.itdreams.android.ui.main.account.AccountFragment;
import graduate.itdreams.android.ui.main.account.AccountUnLoginFragment;
import graduate.itdreams.android.ui.main.comment.TopCommentFragment;
import graduate.itdreams.android.ui.main.cv.CvProfileFragment;
import graduate.itdreams.android.ui.main.home.HomeFragment;
import graduate.itdreams.android.ui.main.register.QuizJobFragment;
import graduate.itdreams.android.ui.main.register.SignUpFragment;
import graduate.itdreams.android.ui.main.notification.NotificationFragment;
import graduate.itdreams.android.ui.main.register.VerifyOTPFragment;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(HomeFragment fragment);
    void inject(CvProfileFragment fragment);
    void inject(NotificationFragment fragment);
    void inject(TopCommentFragment fragment);
    void inject(AccountFragment fragment);
    void inject(AccountUnLoginFragment fragment);
    void inject(SignUpFragment fragment);
    void inject(VerifyOTPFragment fragment);
    void inject(QuizJobFragment fragment);

}
