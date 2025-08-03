package graduate.itdreams.android.ui.main.account;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.request.student.StudentUpdateProfileRequest;
import graduate.itdreams.android.databinding.ActivityEditProfileBinding;
import graduate.itdreams.android.di.component.ActivityComponent;
import graduate.itdreams.android.ui.base.activity.BaseActivity;

public class EditProfileActivity extends BaseActivity<ActivityEditProfileBinding, EditProfileViewModel> {
    private Calendar selectedBirthDate = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.setLifecycleOwner(this);
        viewModel.loadProfile();
        viewBinding.toolbar.setNavigationOnClickListener(v -> finish());
        setUpBirthDate();
    }

    public void onUpdateProfile() {
        String fullname = viewBinding.name.getText().toString().trim();
        String email = viewBinding.email.getText().toString().trim();
        String username = viewBinding.username.getText().toString().trim();
        StudentUpdateProfileRequest request = new StudentUpdateProfileRequest();
        request.setFullName(fullname);
        request.setEmail(email);
        request.setUsername(username);
        if (selectedBirthDate != null) {
            SimpleDateFormat apiFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String formattedBirthDate = apiFormat.format(selectedBirthDate.getTime());
            request.setBirthday(formattedBirthDate);
        }
        viewModel.updateProfile(request);
    }

    private void setUpBirthDate() {
        viewBinding.etBirthdate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        selectedBirthDate = Calendar.getInstance();
                        selectedBirthDate.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        viewBinding.etBirthdate.setText(sdf.format(selectedBirthDate.getTime()));
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });
    }
    @Override
    public int getLayoutId () {
        return R.layout.activity_edit_profile;
    }

    @Override
    public int getBindingVariable () {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection (ActivityComponent buildComponent){
        buildComponent.inject(this);
    }

}

