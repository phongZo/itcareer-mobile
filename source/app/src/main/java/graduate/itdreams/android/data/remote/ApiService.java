package graduate.itdreams.android.data.remote;

import java.util.List;

import graduate.itdreams.android.data.model.api.ResponseListObj;
import graduate.itdreams.android.data.model.api.request.student.ResetOtpRequest;
import graduate.itdreams.android.data.model.api.request.student.StudentUpdateProfileRequest;
import graduate.itdreams.android.data.model.api.request.student.VerifyOtpRequest;
import graduate.itdreams.android.data.model.api.response.question.TaskQuestionProgressResponse;
import graduate.itdreams.android.data.model.api.response.question.TaskQuestionResponse;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationDetailResponse;
import graduate.itdreams.android.data.model.api.response.simulation.SimulationResponse;
import graduate.itdreams.android.data.model.api.response.student.SignUpResponse;
import graduate.itdreams.android.data.model.api.response.file.UploadResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskProgressResponse;
import graduate.itdreams.android.data.model.api.response.task.SubTaskResponse;
import graduate.itdreams.android.data.model.api.response.task.TaskResponse;
import io.reactivex.rxjava3.core.Observable;
import graduate.itdreams.android.data.model.api.ResponseWrapper;
import graduate.itdreams.android.data.model.api.request.student.StudentSignUpRequest;
import graduate.itdreams.android.data.model.api.request.login.CandidateLoginRequest;
import graduate.itdreams.android.data.model.api.response.account.AccountResponse;
import graduate.itdreams.android.data.model.api.response.account.ProfileAccountResponse;
import graduate.itdreams.android.data.model.api.response.login.AccessTokenResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/token")
    @Headers({"UseBasicAuth: 1"})
    Observable<AccessTokenResponse> candidateLogin(@Body CandidateLoginRequest request);

//  STUDENT
    @POST("/v1/student/signup")
    @Headers({"UseBasicAuth: 1"})
    Observable<ResponseWrapper<SignUpResponse>> signUpCandidate(@Body StudentSignUpRequest request);

    @GET("/v1/student/profile")
    Observable<ResponseWrapper<AccountResponse<ProfileAccountResponse>>> getProfile();

    @PUT("/v1/student/client_update")
    Observable<ResponseWrapper> update(@Body StudentUpdateProfileRequest request);

//  IMAGE
    @GET("v1/file/download{file}")
    Observable<ResponseBody> loadFile(@Path(value = "file", encoded = true) String file);
    @Multipart
    @POST("v1/file/upload")
    Observable<ResponseWrapper<UploadResponse>> uploadImage(
            @Part("type") RequestBody type,
            @Part MultipartBody.Part file
    );

//  VERIFY OTP
    @POST("/v1/student/verify")
    @Headers({"UseBasicAuth: 1"})
    Observable<ResponseWrapper> verifyOtp(@Body VerifyOtpRequest request);

    @POST("/v1/student/resend-verify")
    @Headers({"UseBasicAuth: 1"})
    Observable<ResponseWrapper<SignUpResponse>> resetOtp(@Body ResetOtpRequest request);

//  SIMULATION
    @GET("/v1/simulation/student-list")
    Observable<ResponseWrapper<ResponseListObj<SimulationResponse>>> getSimulationList();

    @GET("/v1/simulation/student-get/{id}")
    Observable<ResponseWrapper<SimulationDetailResponse>> getSimulationDetail(@Path("id") Long id);

//  TASK
    @GET("/v1/task/student-list")
    Observable<ResponseWrapper<ResponseListObj<TaskResponse>>> getTaskList(@Query("simulationId") long simulationId);

//  SUBTASK
    @GET("/v1/task/student-get/{id}")
    Observable<ResponseWrapper<SubTaskResponse>> getSubTaskDetail(@Path("id") Long id);

    @GET("/v1/subtask-progress/student-get/{id}")
    Observable<ResponseWrapper<SubTaskProgressResponse>> createSubTaskProgress(@Path("id") Long id);

//  TASK QUESTION
    @GET("/v1/task-question-progress/student-list")
    Observable<ResponseWrapper<ResponseListObj<TaskQuestionProgressResponse>>> getTaskQuestionProgressList(
            @Query("studentSubTaskProgressId") long studentSubTaskProgressId,
            @Query("taskId") long taskId
    );
    @GET("/v1/task-question/student-list")
    Observable<ResponseWrapper<ResponseListObj<TaskQuestionResponse>>> getTaskQuestionList(
            @Query("simulationId") long simulationId,
            @Query("taskId") long taskId
    );
}
