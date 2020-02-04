package com.rechargeweb.rechargeweb.Network;

import com.rechargeweb.rechargeweb.AepsReport;
import com.rechargeweb.rechargeweb.ForgetPassword;
import com.rechargeweb.rechargeweb.Model.Coupon;
import com.rechargeweb.rechargeweb.Model.Credential;
import com.rechargeweb.rechargeweb.Model.Details;
import com.rechargeweb.rechargeweb.Model.FundResponse;
import com.rechargeweb.rechargeweb.Model.Otp;
import com.rechargeweb.rechargeweb.Model.Password;
import com.rechargeweb.rechargeweb.Model.Post;
import com.rechargeweb.rechargeweb.Model.SignUp;
import com.rechargeweb.rechargeweb.Profile;
import com.rechargeweb.rechargeweb.Settlement;
import com.rechargeweb.rechargeweb.UpdateProfie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {


    @POST("login")
    @FormUrlEncoded
    Observable<Post> savePost(@Field("login_id") String login_id,
                              @Field("password") String password);

    @POST("profile?")
    @FormUrlEncoded
    Observable<Details> setDetailsPost(@Field("session_id") String session_id,
                                       @Field("auth_key") String auth_key);

    @POST("forgot_password?")
    @FormUrlEncoded
    Call<Password> resetPassword(@Field("user_id") String user_id);

    @POST("profile")
    @FormUrlEncoded
    Observable<Profile> getProfileDetails(@Field("session_id") String session_id,
                                          @Field("auth_key") String auth_key);

    @GET("fund_request?")
    Call<FundResponse> getFundResponse(@Query("session_id") String id,
                                       @Query("auth_key") String auth,
                                       @Query("amount") String amount,
                                       @Query("bank") String bank,
                                       @Query("payment_mode") String paymentMode,
                                       @Query("payment_date") String paymentDate,
                                       @Query("transaction_id") String transaction_id,
                                       @Query("wallet_type") String walletType);

    @POST("psa_registration?")
    @FormUrlEncoded
    Call<FundResponse> getPsaResgistration(@Field("session_id") String id,
                                           @Field("auth_key") String auth,
                                           @Field("name") String name,
                                           @Field("shop_name") String shop,
                                           @Field("location") String location,
                                           @Field("pincode") String pincode,
                                           @Field("state") String state,
                                           @Field("mobile") String mobile,
                                           @Field("email") String email,
                                           @Field("pan_no") String pan);

    @POST("view_credentials")
    @FormUrlEncoded
    Call<Credential> viewCredential(@Field("session_id") String session_id,
                                    @Field("auth_key") String auth);

    @POST("buy_coupon")
    @FormUrlEncoded
    Call<Coupon> buyCoupon(@Field("auth_key") String auth,
                           @Field("session_id") String id,
                           @Field("vle_name") String name,
                           @Field("quantity") String quantity);

    @POST("change_password")
    @FormUrlEncoded
    Observable<Password> changePassword(@Field("session_id") String session_id,
                                        @Field("auth_key") String auth_key,
                                        @Field("current_password") String cPassword,
                                        @Field("new_password") String nPassword,
                                        @Field("confrim_new_password") String conNewPassword);

    @POST("aeps_report")
    @FormUrlEncoded
    Call<List<AepsReport>> getAepsReport(@Field("user_id") String session_id,
                                         @Field("auth_key") String auth_key);

    @POST("aeps_report")
    @FormUrlEncoded
    Call<List<AepsReport>> getAepsReportByDate(@Field("user_id") String session_id,
                                               @Field("auth_key") String auth_key,
                                               @Field("from_date") String from_date,
                                               @Field("to_date") String to_date);

    @POST("otp_verification")
    @FormUrlEncoded
    Call<Otp> getOtpDetails(@Field("auth_key") String auth,
                            @Field("mobile") String mobile,
                            @Field("email") String email);

    @POST("signup")
    @FormUrlEncoded
    Call<SignUp> signUpUser(@Field("business_name") String shopName,
                            @Field("name") String userName,
                            @Field("email") String email,
                            @Field("mobile") String mobile,
                            @Field("password") String password);

    @POST("profile_update")
    @FormUrlEncoded
    Call<UpdateProfie> updateProfile(@Field("session_id") String session_id,
                                     @Field("business_name") String business_name,
                                     @Field("name") String name,
                                     @Field("address") String address,
                                     @Field("state") String state,
                                     @Field("location") String location,
                                     @Field("pincode") String pincode,
                                     @Field("auth_key") String authKey,
                                     @Field("pan_no") String panNo,
                                     @Field("gstin") String gstNo,
                                     @Field("aadhar_no") String aadharNo);

    @POST("move_to_bank")
    @FormUrlEncoded
    Call<Settlement>moveToBank(@Field("user_id")String session_id,
                               @Field("auth_key")String auth_key,
                               @Field("transfer_mode")String transferMode,
                               @Field("amount")String amount);

    @POST("forgot_password_otp")
    @FormUrlEncoded
    Call<ForgetPassword>forgetPassword(@Field("auth_key") String authKey,
                                       @Field("mobile") String mobileNo);
}
