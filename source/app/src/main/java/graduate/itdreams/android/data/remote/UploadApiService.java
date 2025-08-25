package graduate.itdreams.android.data.remote;

import graduate.itdreams.android.data.model.api.ResponseListObj;
import graduate.itdreams.android.data.model.api.ResponseWrapper;
import graduate.itdreams.android.data.model.api.request.login.CandidateLoginRequest;
import graduate.itdreams.android.data.model.api.request.student.ResetOtpRequest;
import graduate.itdreams.android.data.model.api.request.student.StudentSignUpRequest;
import graduate.itdreams.android.data.model.api.request.student.StudentUpdateProfileRequest;
import graduate.itdreams.android.data.model.api.request.student.VerifyOtpRequest;
import graduate.itdreams.android.data.model.api.response.account.AccountResponse;
import graduate.itdreams.android.data.model.api.response.account.ProfileAccountResponse;
import graduate.itdreams.android.data.model.api.response.file.UploadResponse;
import graduate.itdreams.android.data.model.api.response.login.AccessTokenResponse;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationDetailResponse;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationResponse;
import graduate.itdreams.android.data.model.api.response.student.SignUpResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UploadApiService {

    @GET("v1/file/download{file}")
    Observable<ResponseBody> loadImage(@Path(value = "file", encoded = true) String file);
    @Multipart
    @POST("v1/file/upload")
    Observable<ResponseWrapper<UploadResponse>> uploadImage(
            @Part("type") RequestBody type,
            @Part MultipartBody.Part file
    );
}
