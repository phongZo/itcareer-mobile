package graduate.itdreams.android.ui.main.register;

import android.os.Bundle;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.request.student.ResetOtpRequest;
import graduate.itdreams.android.data.model.api.request.student.VerifyOtpRequest;
import graduate.itdreams.android.databinding.FragmentVerifyOtpBinding;
import graduate.itdreams.android.di.component.FragmentComponent;
import graduate.itdreams.android.ui.base.fragment.BaseFragment;

public class VerifyOTPFragment extends BaseFragment<FragmentVerifyOtpBinding, VerifyOTPViewModel> {
    String idHash = null;
    String email = null;


    @Override
    protected void performDataBinding() {

    }
    public void onResetOtpClick(){
        ResetOtpRequest request = new ResetOtpRequest();
        request.setEmail(email);
        viewModel.resetOtp(request);
    }

    public void onConfirmClick(){
        String otp = binding.otp.getText().toString().trim();
        VerifyOtpRequest request = new VerifyOtpRequest();
        request.setOtp(otp);
        request.setIdHash(idHash);
        viewModel.conFirmOtp(request);
        viewModel.isSuccess.observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                String idHash = viewModel.idHash.getValue();
                goToQuizJob();
            }
        });
    }

    private void goToQuizJob() {
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify_otp;
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
