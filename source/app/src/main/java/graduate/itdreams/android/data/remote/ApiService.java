package graduate.itdreams.android.data.remote;

import graduate.itdreams.android.data.model.api.request.student.ResetOtpRequest;
import graduate.itdreams.android.data.model.api.request.student.VerifyOtpRequest;
import graduate.itdreams.android.data.model.api.response.student.SignUpResponse;
import graduate.itdreams.android.data.model.api.response.file.UploadResponse;
import io.reactivex.rxjava3.core.Observable;
import graduate.itdreams.android.data.model.api.ResponseListObj;
import graduate.itdreams.android.data.model.api.ResponseWrapper;
import graduate.itdreams.android.data.model.api.request.account.CreateAccountAdminRequest;
import graduate.itdreams.android.data.model.api.request.account.ForgetPasswordRequest;
import graduate.itdreams.android.data.model.api.request.account.UpdateAccountAdminRequest;
import graduate.itdreams.android.data.model.api.request.account.UpdateProfileAdminRequest;
import graduate.itdreams.android.data.model.api.request.student.StudentSignUpRequest;
import graduate.itdreams.android.data.model.api.request.student.StudentUpdateProfileRequest;
import graduate.itdreams.android.data.model.api.request.category.CreateCategoryRequest;
import graduate.itdreams.android.data.model.api.request.category.UpdateCategoryRequest;
import graduate.itdreams.android.data.model.api.request.company.CreateCompanyRequest;
import graduate.itdreams.android.data.model.api.request.company.UpdateCompanyRequest;
import graduate.itdreams.android.data.model.api.request.employee.CreateEmployeeRequest;
import graduate.itdreams.android.data.model.api.request.employee.UpdateEmployeeRequest;
import graduate.itdreams.android.data.model.api.request.group.CreateGroupRequest;
import graduate.itdreams.android.data.model.api.request.group.UpdateGroupRequest;
import graduate.itdreams.android.data.model.api.request.login.CandidateLoginRequest;
import graduate.itdreams.android.data.model.api.request.nation.CreateNationRequest;
import graduate.itdreams.android.data.model.api.request.nation.UpdateNationRequest;
import graduate.itdreams.android.data.model.api.request.user.LoginRequest;
import graduate.itdreams.android.data.model.api.request.user.UpdateUserRequest;
import graduate.itdreams.android.data.model.api.response.account.AccountResponse;
import graduate.itdreams.android.data.model.api.response.account.ForgetPasswordResponse;
import graduate.itdreams.android.data.model.api.response.account.ProfileAccountResponse;
import graduate.itdreams.android.data.model.api.response.student.StudentResponse;
import graduate.itdreams.android.data.model.api.response.category.CategoryResponse;
import graduate.itdreams.android.data.model.api.response.company.CompanyResponse;
import graduate.itdreams.android.data.model.api.response.employee.EmployeeResponse;
import graduate.itdreams.android.data.model.api.response.group.GroupResponse;
import graduate.itdreams.android.data.model.api.response.login.AccessTokenResponse;
import graduate.itdreams.android.data.model.api.response.nation.NationAdminDtoResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("/api/token")
    @Headers({"UseBasicAuth: 1"})
    Observable<AccessTokenResponse> candidateLogin(@Body CandidateLoginRequest request);

//    USER
    @POST("/v1/user/login")
    @Headers({"IgnoreAuth: 1"})
    Observable<ResponseWrapper> login(@Body LoginRequest request);

    @POST("/v1/user/signup")
    @Headers({"UseBasicAuth: 1"})
    Observable<ResponseWrapper<SignUpResponse>> signUpCandidate(@Body StudentSignUpRequest request);

    @GET("/v1/user/profile")
    Observable<ResponseWrapper<AccountResponse<ProfileAccountResponse>>> getProfile();

    @PUT("/v1/user/client_update")
    Observable<ResponseWrapper> update(@Body UpdateUserRequest request);

//    ACCOUNT CONTROLLER
    @POST("/v1/account/create_admin")
    Observable<ResponseWrapper> createAdmin(@Body CreateAccountAdminRequest request);
    @DELETE("/v1/account/delete/{id}")
    Observable<ResponseWrapper> deleteAccount(@Path("id") Long id);
    @POST("/v1/account/forget_password")
    Observable<ResponseWrapper> forgetPassword(@Body ForgetPasswordRequest request);
    @GET("/v1/company/get/{id}")
    Observable<ResponseWrapper<AccountResponse>> getAccount(@Path("id") Long id);
    @POST("/v1/account/request_forget_password")
    Observable<ResponseWrapper<ForgetPasswordResponse>> forgetPassword(@Body String email);
    @PUT("/v1/company/update_admin")
    Observable<ResponseWrapper> updateAdmin(@Body UpdateAccountAdminRequest request);
    @PUT("/v1/company/update_profile_admin")
    Observable<ResponseWrapper> updateProfileAdmin(@Body UpdateProfileAdminRequest request);

//    CANDIDATE CONTROLLER
    @DELETE("/v1/candidate/delete/{id}")
    Observable<ResponseWrapper> deleteCandidate(@Path("id") Long id);
    @GET("/v1/candidate/get/{id}")
    Observable<ResponseWrapper<StudentResponse>> getCandidate(@Path("id") Long id);
    @GET("/v1/candidate/list")
    Observable<ResponseWrapper<ResponseListObj<StudentResponse>>> getListCandidate();
    @GET("/v1/candidate/profile")
    Observable<ResponseWrapper<StudentResponse>> getCandidateProfile();

    @PUT("/v1/candidate/signup")
    Observable<ResponseWrapper> updateProfileCandidate(@Body StudentUpdateProfileRequest request);

//    CATEGORY CONTROLLER
    @POST("/v1/category/create")
    Observable<ResponseWrapper> createCategory(@Body CreateCategoryRequest request);
    @DELETE("/v1/category/delete/{id}")
    Observable<ResponseWrapper> deleteCategory(@Path("id") Long id);
    @GET("/v1/category/get/{id}")
    Observable<ResponseWrapper<CategoryResponse>> getCategory(@Path("id") Long id);
    @GET("/v1/category/list")
    Observable<ResponseWrapper<ResponseListObj<CategoryResponse>>> getListCategory();
    @PUT("/v1/category/update")
    Observable<ResponseWrapper> updateCategory(@Body UpdateCategoryRequest request);
//    COMPANY CONTROLLER
    @POST("/v1/company/create")
    Observable<ResponseWrapper> createCompany(@Body CreateCompanyRequest request);
    @DELETE("/v1/company/delete/{id}")
    Observable<ResponseWrapper> deleteCompany(@Path("id") Long id);
    @GET("/v1/company/get/{id}")
    Observable<ResponseWrapper<CompanyResponse>> getCompany(@Path("id") Long id);
    @GET("/v1/company/list")
    Observable<ResponseWrapper<ResponseListObj<CompanyResponse>>> getListCompany();
    @PUT("/v1/company/update")
    Observable<ResponseWrapper> updateCompany(@Body UpdateCompanyRequest request);

//    EMPLOYEE CONTROLLER
    @POST("/v1/employee/create")
    Observable<ResponseWrapper> createEmployee(@Body CreateEmployeeRequest request);
    @DELETE("/v1/employee/delete/{id}")
    Observable<ResponseWrapper> deleteEmployee(@Path("id") Long id);
    @GET("/v1/employee/get/{id}")
    Observable<ResponseWrapper<EmployeeResponse>> getEmployee(@Path("id") Long id);
    @GET("/v1/employee/list")
    Observable<ResponseWrapper<ResponseListObj<EmployeeResponse>>> getListEmployee();
    @PUT("/v1/employee/update")
    Observable<ResponseWrapper> updateEmployee(@Body UpdateEmployeeRequest request);


//    GROUP CONTROLLER
    @POST("/v1/group/create")
    Observable<ResponseWrapper> createGroup(@Body CreateGroupRequest request);
    @GET("/v1/group/get/{id}")
    Observable<ResponseWrapper<GroupResponse>> getGroup(@Path("id") Long id);
    @GET("/v1/group/list")
    Observable<ResponseWrapper<ResponseListObj<GroupResponse>>> getListGroup();
    @PUT("/v1/group/update")
    Observable<ResponseWrapper> updateGroup(@Body UpdateGroupRequest request);

//    NATION CONTROLLER
    @POST("/v1/nation/create")
    Observable<ResponseWrapper> createNation(@Body CreateNationRequest request);
    @DELETE("/v1/nation/delete/{id}")
    Observable<ResponseWrapper> deleteNation(@Path("id") Long id);
    @GET("/v1/nation/get/{id}")
    Observable<ResponseWrapper<NationAdminDtoResponse>> getNation(@Path("id") Long id);
    @GET("/v1/nation/list")
    Observable<ResponseWrapper<ResponseListObj<NationAdminDtoResponse>>> getListNation();
    @PUT("/v1/nation/update")
    Observable<ResponseWrapper> updateNation(@Body UpdateNationRequest request);

//  IMAGE
    @GET("v1/file/download{file}")
    Observable<ResponseBody> loadImage(@Path(value = "file", encoded = true) String file);
    @Multipart
    @POST("v1/file/upload")
    Observable<ResponseWrapper<UploadResponse>> uploadImage(
            @Part("type") RequestBody type,
            @Part MultipartBody.Part file
    );

//  VERIFY OTP
    @POST("/v1/user/verify")
    @Headers({"UseBasicAuth: 1"})
    Observable<ResponseWrapper> verifyOtp(@Body VerifyOtpRequest request);

    @POST("/v1/user/resend-verify")
    @Headers({"UseBasicAuth: 1"})
    Observable<ResponseWrapper<SignUpResponse>> resetOtp(@Body ResetOtpRequest request);
}
