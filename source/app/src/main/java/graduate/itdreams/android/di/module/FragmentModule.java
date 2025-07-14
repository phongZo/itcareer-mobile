package graduate.itdreams.android.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import graduate.itdreams.android.MVVMApplication;
import graduate.itdreams.android.ViewModelProviderFactory;
import graduate.itdreams.android.data.Repository;
import graduate.itdreams.android.di.scope.FragmentScope;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import graduate.itdreams.android.ui.main.account.AccountUnLoginViewModel;
import graduate.itdreams.android.ui.main.account.AccountViewModel;
import graduate.itdreams.android.ui.main.comment.TopCommentViewModel;
import graduate.itdreams.android.ui.main.cv.CvProfileViewModel;
import graduate.itdreams.android.ui.main.home.HomeViewModel;
import graduate.itdreams.android.ui.main.register.QuizJobViewModel;
import graduate.itdreams.android.ui.main.register.SignUpViewModel;
import graduate.itdreams.android.ui.main.notification.NotificationViewModel;
import graduate.itdreams.android.ui.main.register.VerifyOTPViewModel;

@Module
public class FragmentModule {

    private BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Named("access_token")
    @Provides
    @FragmentScope
    String provideToken(Repository repository) {
        return repository.getToken();
    }
    @Provides
    @FragmentScope
    HomeViewModel provideHomeViewModel(Repository repository, Context application) {
        Supplier<HomeViewModel> supplier = () -> new HomeViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<>(HomeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(HomeViewModel.class);
    }

    @Provides
    @FragmentScope
    CvProfileViewModel provideCvProfileViewModel(Repository repository, Context application) {
        Supplier<CvProfileViewModel> supplier = () -> new CvProfileViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<CvProfileViewModel> factory = new ViewModelProviderFactory<>(CvProfileViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CvProfileViewModel.class);
    }

    @Provides
    @FragmentScope
    NotificationViewModel provideNotificationViewModel(Repository repository, Context application) {
        Supplier<NotificationViewModel> supplier = () -> new NotificationViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<NotificationViewModel> factory = new ViewModelProviderFactory<>(NotificationViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(NotificationViewModel.class);
    }

    @Provides
    @FragmentScope
    TopCommentViewModel provideTopCommentViewModel(Repository repository, Context application) {
        Supplier<TopCommentViewModel> supplier = () -> new TopCommentViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<TopCommentViewModel> factory = new ViewModelProviderFactory<>(TopCommentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TopCommentViewModel.class);
    }

    @Provides
    @FragmentScope
    AccountViewModel provideAccountViewModel(Repository repository, Context application) {
        Supplier<AccountViewModel> supplier = () -> new AccountViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AccountViewModel> factory = new ViewModelProviderFactory<>(AccountViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AccountViewModel.class);
    }

    @Provides
    @FragmentScope
    AccountUnLoginViewModel provideAccountUnLoginViewModel(Repository repository, Context application) {
        Supplier<AccountUnLoginViewModel> supplier = () -> new AccountUnLoginViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AccountUnLoginViewModel> factory = new ViewModelProviderFactory<>(AccountUnLoginViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AccountUnLoginViewModel.class);
    }

    @Provides
    @FragmentScope
    SignUpViewModel provideSignUpViewModel(Repository repository, Context application) {
        Supplier<SignUpViewModel> supplier = () -> new SignUpViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<SignUpViewModel> factory = new ViewModelProviderFactory<>(SignUpViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SignUpViewModel.class);
    }

    @Provides
    @FragmentScope
    VerifyOTPViewModel provideVerifyOTPViewModel(Repository repository, Context application) {
        Supplier<VerifyOTPViewModel> supplier = () -> new VerifyOTPViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<VerifyOTPViewModel> factory = new ViewModelProviderFactory<>(VerifyOTPViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(VerifyOTPViewModel.class);
    }

    @Provides
    @FragmentScope
    QuizJobViewModel provideQuizJobViewModel(Repository repository, Context application) {
        Supplier<QuizJobViewModel> supplier = () -> new QuizJobViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<QuizJobViewModel> factory = new ViewModelProviderFactory<>(QuizJobViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(QuizJobViewModel.class);
    }

}
