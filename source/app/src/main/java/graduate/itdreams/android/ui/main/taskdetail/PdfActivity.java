package graduate.itdreams.android.ui.main.taskdetail;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import graduate.itdreams.android.R;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import graduate.itdreams.android.data.socket.dto.Message;
import graduate.itdreams.android.databinding.ActivityPdfBinding;
import graduate.itdreams.android.databinding.ActivityTaskDetailBinding;
import graduate.itdreams.android.di.component.ActivityComponent;
import graduate.itdreams.android.ui.base.activity.BaseActivity;

public class PdfActivity extends BaseActivity<ActivityPdfBinding,PdfViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        String pdfUrl = getIntent().getStringExtra("url_pdf");
        if (pdfUrl != null) {
            viewModel.loadDocument(pdfUrl);
        }

        // Quan sát dữ liệu PDF (khi tải xong)
        viewModel.getPdfData().observe(this, bytes -> {
            if (bytes != null && bytes.length > 0) {
                Log.d("PdfActivity", "PDF byte size: " + bytes.length);

                // Tùy chọn: lưu file tạm để test
                try {
                    File tempFile = new File(getCacheDir(), "temp.pdf");
                    FileOutputStream fos = new FileOutputStream(tempFile);
                    fos.write(bytes);
                    fos.close();
                    Log.d("PdfActivity", "Temp PDF path: " + tempFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Load PDF trực tiếp từ bytes
                viewBinding.pdfView.post(() -> {
                    viewBinding.pdfView.fromBytes(bytes)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .load();
                });

            } else {
                Log.w("PdfActivity", "PDF dữ liệu trống hoặc null");
            }
        });

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onMessageReceived(Message message) {

    }

    @Override
    public void onConnectionClosed() {

    }

    @Override
    public void onConnectionClosing() {

    }
}
