package com.rechargeweb.rechargeweb;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rechargeweb.rechargeweb.Model.AddBeneficiary;
import com.rechargeweb.rechargeweb.Model.AepsLogIn;
import com.rechargeweb.rechargeweb.Model.Bank;
import com.rechargeweb.rechargeweb.Model.Beneficiary;
import com.rechargeweb.rechargeweb.Model.BillPay;
import com.rechargeweb.rechargeweb.Model.Coupon;
import com.rechargeweb.rechargeweb.Model.CouponReport;
import com.rechargeweb.rechargeweb.Model.Credential;
import com.rechargeweb.rechargeweb.Model.ElectricStatus;
import com.rechargeweb.rechargeweb.Model.FetchOperator;
import com.rechargeweb.rechargeweb.Model.FundResponse;
import com.rechargeweb.rechargeweb.Model.NumberDetect;
import com.rechargeweb.rechargeweb.Model.Otp;
import com.rechargeweb.rechargeweb.Model.Passbook;
import com.rechargeweb.rechargeweb.Model.Password;
import com.rechargeweb.rechargeweb.Model.PlanDetails;
import com.rechargeweb.rechargeweb.Model.Prepaid;
import com.rechargeweb.rechargeweb.Model.Recharge;
import com.rechargeweb.rechargeweb.Model.RechargeDetails;
import com.rechargeweb.rechargeweb.Model.Register;
import com.rechargeweb.rechargeweb.Model.Remitter;
import com.rechargeweb.rechargeweb.Model.SignUp;
import com.rechargeweb.rechargeweb.Model.Support;
import com.rechargeweb.rechargeweb.Model.Validate;
import com.rechargeweb.rechargeweb.Network.ApiService;
import com.rechargeweb.rechargeweb.Network.ApiUtills;
import com.rechargeweb.rechargeweb.Network.Operator;
import com.rechargeweb.rechargeweb.Network.OperatorServices;
import com.rechargeweb.rechargeweb.Network.OperatorUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private ApiService apiService = ApiUtills.getApiService();

    private OperatorServices operatorServices = OperatorUtil.getOperatorServices();

    //Store Password LiveData
    private MutableLiveData<String> passwordMutableLiveData = new MutableLiveData<>();

    //Store Operator list
    private MutableLiveData<List<Prepaid>> operatorListLiveData;

    //Store a single recharge status
    private MutableLiveData<Recharge> rechargeLiveData = new MutableLiveData<>();

    //Store a list of recharge status
    private MutableLiveData<List<RechargeDetails>> rechargeListLiveData = new MutableLiveData<>();

    //Store support details
    private MutableLiveData<Support> supportMutableLiveData = new MutableLiveData<>();

    //Store report by date
    private MutableLiveData<List<RechargeDetails>> dateRechargeListLiveData = new MutableLiveData<>();

    //Store list of operator by state
    private MutableLiveData<List<Prepaid>> operatorByStateLiveData;

    //Store response of electric bill status
    private MutableLiveData<ElectricStatus> electricStatusMutableLiveData = new MutableLiveData<>();

    //Store ElectricBillPayment details
    private MutableLiveData<BillPay> billPayMutableLiveData = new MutableLiveData<>();

    //Store passbook details
    private MutableLiveData<List<Passbook>> passbookDetailsLiveData = new MutableLiveData<>();

    //Store list of bank details
    private MutableLiveData<List<Bank>> bankDetailsListLiveData;

    //Store response of fund request
    private MutableLiveData<String> fundResponseMutableLiveData = new MutableLiveData<>();

    //Store credential details
    private MutableLiveData<Credential> credentialMutableLiveData;

    //Store psa registration response
    private MutableLiveData<String> psaRegistrationMutableLiveData = new MutableLiveData<>();

    //Store coupon details
    private MutableLiveData<Coupon> couponMutableLiveData = new MutableLiveData<>();

    //Store credit summary list
    private MutableLiveData<List<Passbook>> creditSummaryMutableLiveData = new MutableLiveData<>();

    //Store credit summary list by date
    private MutableLiveData<List<Passbook>> creditSummaryByDateLiveData = new MutableLiveData<>();

    //Store credit summary list
    private MutableLiveData<List<Passbook>> debiitSummaryMutableLiveData = new MutableLiveData<>();

    //Store debit summary list by date
    private MutableLiveData<List<Passbook>> debiitSummaryByDateLiveData = new MutableLiveData<>();

    //Store list of coupon report
    private MutableLiveData<List<CouponReport>> couponReportMutableLiveData = new MutableLiveData<>();

    //Store list of coupon report by date
    private MutableLiveData<List<CouponReport>> couponReportMutableLiveDataByDate = new MutableLiveData<>();

    //Store number details
    private MutableLiveData<NumberDetect> numberDetectMutableLiveData = new MutableLiveData<>();

    //Store list of recharge plans
    private MutableLiveData<List<PlanDetails>> plansDetailsMutableLiveData = new MutableLiveData<>();

    //Store remitter details
    private MutableLiveData<Remitter> remitterMutableLiveData = new MutableLiveData<>();

    //Store List of beneficiary
    private MutableLiveData<List<Beneficiary>> beneficiaryListMutableLiveData = new MutableLiveData<>();

    //Store remitter message
    private MutableLiveData<String> messageMutableLiveData = new MutableLiveData<>();

    //Store id send for dmt registration
    private MutableLiveData<String> idMutableLiveData = new MutableLiveData<>();

    //Store validate message
    private MutableLiveData<Validate> validateMessageMutableLiveData = new MutableLiveData<>();

    //Store register message
    private MutableLiveData<Register> registerMutableLiveData = new MutableLiveData<>();

    //Store message when adding new beneficiary
    private MutableLiveData<AddBeneficiary> addBeneficiaryMutableLiveData = new MutableLiveData<>();

    //Store name and message from account validate
    private MutableLiveData<AddBeneficiary> validateAccountMutableLiveData = new MutableLiveData<>();

    //Store delete beneficiary message
    private MutableLiveData<AddBeneficiary> deleteBenMutableLiveData = new MutableLiveData<>();

    //Store delete beneficiary validation message
    private MutableLiveData<AddBeneficiary> deleteBenValidationMutableLiveData = new MutableLiveData<>();

    //Store transfer money message
    private MutableLiveData<String> transferMutableLiveData = new MutableLiveData<>();

    //Store aeps log in details
    private MutableLiveData<AepsLogIn> aepsLogInMutableLiveData = new MutableLiveData<>();

    //Store list of aeps report data
    private MutableLiveData<List<AepsReport>> aepsReportListMutableLiveData = new MutableLiveData<>();

    //Store list of aeps report by date
    private MutableLiveData<List<AepsReport>> aepsReportListMutableLiveDataByDate = new MutableLiveData<>();

    //Store kyc submit response
    private MutableLiveData<String> kycresponseMutableLiveData = new MutableLiveData<>();

    //Store response after sending aeps details
    private MutableLiveData<String> aepssendResponseMutableLiveData = new MutableLiveData<>();

    //Store Otp details
    private MutableLiveData<Otp> otpMutableLiveData = new MutableLiveData<>();

    //Store sign-up message
    private MutableLiveData<String> signUpMutableLiveData = new MutableLiveData<>();

    //Store update profile message
    private MutableLiveData<String> updateProfileMutableLiveData = new MutableLiveData<>();

    //Store mobile number details fetch from api
    private MutableLiveData<FetchOperator>fetchOperatorMutableLiveData = new MutableLiveData<>();

    //Store List of mobile recharge plans
    private MutableLiveData<List<Plans>>planListMutableLiveData = new MutableLiveData<>();

    //Store list of special offer
    private MutableLiveData<List<Roffer>>specialOfferListLiveData = new MutableLiveData<>();

    //Store list of DTH plans
    private MutableLiveData<List<DTH>>dthListMutableLiveData = new MutableLiveData<>();

    //Store DTH Customer information
    private MutableLiveData<DthCustomerInfo>dthCustomerInfoMutableLiveData = new MutableLiveData<>();

    //Store response from transaction report send
    private MutableLiveData<String>transactionReportMutableLiveData = new MutableLiveData<>();

    public static Repository getInstance() {
        return new Repository();
    }

    public LiveData<String>sendTranReport(String session_id, String authKey, String mmp_txn, String mer_txn, String amount, String prob, String date, String bank_txn,
                                          String f_code, String clientCode, String bank_name, String authCode, String ipg_txn_id, String merchant_id, String desc,
                                          String discriminator, String udf9, String surcharge, String cardNumber, String udf1, String udf2, String udf3, String udf4, String udf5,
                                          String signature){
            sendAddMoneyReport(session_id,authKey,mmp_txn,mer_txn,amount,prob,date,bank_txn,f_code,clientCode,bank_name,
                    authCode,ipg_txn_id,merchant_id,desc,discriminator,udf9,surcharge,cardNumber,udf1,udf2, udf3, udf4, udf5, signature);
            return transactionReportMutableLiveData;
    }

    //Get Dth Customer Information
    public LiveData<DthCustomerInfo>getDthCusomerInfo(String number, String operator){

        getDthCusInfo(number,operator);
        return dthCustomerInfoMutableLiveData;
    }

    //Get List of Dth plans
    public LiveData<List<DTH>>getDthPlansList(String apiKey, String operator){

        getDthPlans(apiKey,operator);
        return dthListMutableLiveData;
    }

    //Get list of special offer for a mobile number
    public LiveData<List<Roffer>>getSpecialOfferList(String mobile,String operatorName){

        getRofferList(mobile,operatorName);
        return specialOfferListLiveData;
    }

    //Get Mobile Recharge plans
    public LiveData<List<Plans>>getMobileRechargePlans(String circleId, String operatorId, String type){

        getMobileNumberRechargePlans(circleId,operatorId,type);
        return planListMutableLiveData;
    }

    //Get Mobile Number Details
    public LiveData<FetchOperator>fectchMobileDetails(String mobileNumber){

        getMobileNumberDetails(mobileNumber);
        return fetchOperatorMutableLiveData;
    }

    //Update profile
    public LiveData<String> updateProfile(String session_id, String businessName, String name, String address, String state, String location, String pincode,
                                          String authKey, String panNo, String gstNo, String aadharNo) {

        updateUserProfile(session_id, businessName, name, address, state, location, pincode, authKey, panNo, gstNo, aadharNo);
        return updateProfileMutableLiveData;
    }

    //Sign up user
    public LiveData<String> signUpUser(String shopName, String userName, String email, String mobile, String password) {

        getSignUpMessage(shopName, userName, email, mobile, password);
        return signUpMutableLiveData;
    }

    //Get Otp details
    public LiveData<Otp> getOtpDetails(String authKey, String mobile, String email) {

        getOtp(authKey, mobile, email);
        return otpMutableLiveData;
    }

    //Send aeps transaction details
    public LiveData<String> sendAepsDetails(String session_id, String auth, String service, String amount, String order_id, String mobile) {
        sendAepsTrans(session_id, auth, service, amount, order_id, mobile);
        return aepssendResponseMutableLiveData;
    }

    //Submit Kyc
    public LiveData<String> submitKyc(String session_id, String auth, String name, String shopName, String dob, String email, String address, String pincode,
                                      String state, String mobile, String city, String aadhaarNo, String panNo, RequestBody aadharImageUrl, RequestBody panImageUrl, String service) {

        submitKycDetails(session_id, auth, name, shopName, dob, email, address, pincode, state, mobile, city, aadhaarNo, panNo, aadharImageUrl, panImageUrl, service);
        return kycresponseMutableLiveData;
    }

    //Get Aeps report by date
    public LiveData<List<AepsReport>> getAepsReportListByDate(String session_id, String auth, String from, String to) {

        getAepsReportByDate(session_id, auth, from, to);
        return aepsReportListMutableLiveDataByDate;
    }

    //Get Aeps report
    public LiveData<List<AepsReport>> getAepsReportList(String session_id, String auth) {

        getAepsReport(session_id, auth);
        return aepsReportListMutableLiveData;
    }

    //Aeps Log in
    public LiveData<AepsLogIn> aepsLogIn(String session_id, String serviceType, String auth) {

        logInAeps(session_id, serviceType, auth);
        return aepsLogInMutableLiveData;
    }


    //Transfer money to bank
    public LiveData<String> transferMoney(String session_id, String auth, String mobile, String remitter_id, String name, String ifsc, String account, String ben_id, String amount) {

        sendMoney(session_id, auth, mobile, remitter_id, name, ifsc, account, ben_id, amount);
        return transferMutableLiveData;
    }

    //delete beneficiary
    public LiveData<AddBeneficiary> deleteBeneficiary(String auth, String beneficiary_id, String remitter_id) {

        deleteBenData(auth, beneficiary_id, remitter_id);
        return deleteBenMutableLiveData;
    }

    //delete beneficiary validate
    public LiveData<AddBeneficiary> deleteBenValidate(String auth, String benId, String remId, String otp) {

        deleteBenValidation(auth, benId, remId, otp);
        return deleteBenValidationMutableLiveData;
    }

    //Account validate
    public LiveData<AddBeneficiary> validateAccount(String session_id, String auth, String account, String ifsc, String mobile) {

        validateAccountDetails(session_id, auth, account, ifsc, mobile);
        return validateAccountMutableLiveData;
    }

    //Get message for add beneficiary
    public LiveData<AddBeneficiary> getAddBeneficiaryMessage(String auth, String mobile, String remitter_id, String name, String ifscCode, String account) {

        addBeneficary(auth, mobile, remitter_id, name, ifscCode, account);
        return addBeneficiaryMutableLiveData;
    }

    //Get validate Message
    public LiveData<Validate> getValidateMessage(String auth, String mobile, String remitter_id, String otp) {

        getValidate(auth, mobile, remitter_id, otp);
        return validateMessageMutableLiveData;
    }

    //Get register message
    public LiveData<Register> getRegisterMessage(String auth, String mobile, String firstName, String lastName, String pincode) {

        getRegister(auth, mobile, firstName, lastName, pincode);
        return registerMutableLiveData;
    }

    //Get beneficiary list
    public LiveData<List<Beneficiary>> getBenefiaciaryList(String auth, String mobile) {

        getBeneficiary(auth, mobile);
        return beneficiaryListMutableLiveData;
    }

    //Get message of remitter
    public LiveData<String> getRemitterMessage(String auth, String mobile) {

        getMessage(auth, mobile);
        return messageMutableLiveData;
    }

    //Get remitter details
    public LiveData<Remitter> getRemitterDetails(String auth, String mobile) {

        getRemitter(auth, mobile);
        return remitterMutableLiveData;
    }

    //Get id remitter
    public LiveData<String> getIdLiveData(String auth, String mobile) {
        getId(auth, mobile);
        return idMutableLiveData;
    }

    //Get list of recharge plans
    public LiveData<List<PlanDetails>> getPlanDetails(String token, String type, String circleId, String optId) {

        getPlans(token, type, circleId, optId);
        return plansDetailsMutableLiveData;
    }

    //Get Number details
    public LiveData<NumberDetect> getNumbeDetails(String token, int number) {
        getNumberDetails(token, number);

        return numberDetectMutableLiveData;
    }

    //Get List of Coupon report
    public LiveData<List<CouponReport>> getCouponReportList(String id, String auth) {

        getCouponReport(id, auth);
        return couponReportMutableLiveData;
    }

    //Get list of coupon report by date
    public LiveData<List<CouponReport>> getCouponReportByDate(String id, String auth, String from, String to) {

        getCouponReportListByDate(id, auth, from, to);
        return couponReportMutableLiveDataByDate;
    }

    //Get list of debit summary by date
    public LiveData<List<Passbook>> getCreditListByDate(String id, String auth, String from, String to) {

        getCreditByDate(id, auth, from, to);

        return creditSummaryByDateLiveData;
    }

    //Get credit summary
    public LiveData<List<Passbook>> getCreditSummaryList(String id, String auth) {

        getCreditSummary(id, auth);
        return creditSummaryMutableLiveData;
    }

    //Get list of debit summary by date
    public LiveData<List<Passbook>> getDebitListByDate(String id, String auth, String from, String to) {

        getDebitByDate(id, auth, from, to);

        return debiitSummaryByDateLiveData;
    }

    //Get debit summary
    public LiveData<List<Passbook>> getDebitSummaryList(String id, String auth) {

        getDebitSummary(id, auth);
        return debiitSummaryMutableLiveData;
    }

    //Get coupon purchase details
    public LiveData<Coupon> viewCouponPurchaseDetails(String auth, String id, String name, String quantity) {

        getCouponPurchaseDetails(auth, id, name, quantity);
        return couponMutableLiveData;
    }

    //Get Psa reg response
    public LiveData<String> getPsaRegResponse(String id, String auth, String name, String shop, String locality, String pincode, String state, String mobile, String email, String panNo) {

        getPsaResponse(id, auth, name, shop, locality, pincode, state, mobile, email, panNo);
        return psaRegistrationMutableLiveData;
    }


    //Get Credential details
    public LiveData<Credential> getCredentialDetails(String id, String auth) {

        if (credentialMutableLiveData == null) {
            credentialMutableLiveData = new MutableLiveData<>();
            getCredential(id, auth);
        }

        return credentialMutableLiveData;
    }

    //Get fund response
    public LiveData<String> getFundRequestResponse(String id, String auth, String amount, String bank, String pmode, String pdate, String tranId, String walletType) {

        getFundResponse(id, auth, amount, bank, pmode, pdate, tranId, walletType);
        return fundResponseMutableLiveData;
    }

    //Get list of bank details
    public LiveData<List<Bank>> getBankDetialsList() {

        if (bankDetailsListLiveData == null) {

            bankDetailsListLiveData = new MutableLiveData();
            getBankDetails();
        }

        return bankDetailsListLiveData;
    }

    //Get Passbook details by date
    public LiveData<List<Passbook>> getPassbookdetailsByDate(String id, String auth, String fromDate, String toDate) {

        getPassbookByDate(id, auth, fromDate, toDate);
        return passbookDetailsLiveData;
    }

    //Get Passbook details
    public LiveData<List<Passbook>> getPassBookDetails(String id, String auth) {

        getPassbook(id, auth);
        return passbookDetailsLiveData;
    }

    //Get bill payment details
    public LiveData<BillPay> getBillPaymentDetails(String auth, String session_id, String counsumer_id, String code, int amount, String ref_id) {

        getBillPayDetails(auth, session_id, counsumer_id, code, amount, ref_id);
        return billPayMutableLiveData;
    }

    //Get response of electric bill
    public LiveData<ElectricStatus> getElectricBillStatus(String auth, String customer_id, String code) {

        getElectricStatus(auth, customer_id, code);
        return electricStatusMutableLiveData;
    }

    // Get response of Electric bill with two parameter
    public LiveData<ElectricStatus> getElectricBillStatusWithP(String auth, String customer_id, String code, String parameter2) {
        getElectricBillStatusTwo(auth, customer_id, code, parameter2);
        return electricStatusMutableLiveData;
    }

    //Get list of operator by state
    public LiveData<List<Prepaid>> getOperatorByState(String operatorType, String state) {

        if (operatorByStateLiveData == null) {
            operatorByStateLiveData = new MutableLiveData<>();
        }

        getOperatorListByState(operatorType, state);
        return operatorByStateLiveData;
    }

    //Get a single recharge status
    public LiveData<Recharge> getRechargeStatus(String userId, String auth, String number, String operatorId, String amount) {

        getStatus(userId, auth, number, operatorId, amount);
        return rechargeLiveData;
    }

    //Get a list of recharge status
    public LiveData<List<RechargeDetails>> getRechargeList(String session_id, String auth) {

        getRechargeListLiveData(session_id, auth);
        return rechargeListLiveData;
    }

    //Get list of recharge status by date
    public LiveData<List<RechargeDetails>> getRechargeStatusByDate(String id, String auth, String from, String to) {
        getRechargeListByDate(id, auth, from, to);
        return dateRechargeListLiveData;
    }


    //Get Password
    public LiveData<String> getPassword(String name) {

        resetPassword(name);
        return passwordMutableLiveData;
    }

    //Get Prepaid Operator List
    public LiveData<List<Prepaid>> getPrepaidOperator(String operator) {

        if (operatorListLiveData == null) {
            operatorListLiveData = new MutableLiveData<>();
        }
        getOperatorList(operator);
        return operatorListLiveData;
    }

    //Get support details
    public LiveData<Support> getSupportDetails(String key) {

        getSupport(key);
        return supportMutableLiveData;
    }

    //Network call to get recharge plans
    private void getPlans(String token, final String type, String circleId, final String optId) {

        final List<PlanDetails> planDetailsList = new ArrayList<>();

        Call<String> call = operatorServices.getPlans("json", token, type, circleId, optId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Recharge Plans response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject dataObject = jsonObject.optJSONObject("data");

                        JSONArray jsonArray = dataObject.getJSONArray(type);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String amount = object.getString("amount");
                            String details = object.getString("detail");
                            String validity = object.getString("validity");
                            String talktime = object.getString("talktime");

                            PlanDetails planDetails = new PlanDetails(amount, details, validity, talktime);
                            planDetailsList.add(planDetails);
                            plansDetailsMutableLiveData.setValue(planDetailsList);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Recharge Plans Failure : " + t.getMessage());

                PlanDetails planDetails = new PlanDetails(t.getMessage());
                planDetailsList.add(planDetails);
                plansDetailsMutableLiveData.setValue(planDetailsList);
            }
        });
    }

    //Network call to get a number details
    private void getNumberDetails(String token, int number) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.RECH_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getNumberDetails("json", token, number);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Number Detect response success: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject data = jsonObject.getJSONObject("data");

                        String services = data.optString("service");
                        String location = data.optString("location");
                        String circleId = data.optString("circleId");
                        String optId = data.optString("opId");

                        NumberDetect numberDetect = new NumberDetect(services, location, circleId, optId);
                        numberDetectMutableLiveData.setValue(numberDetect);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Number Detect response failure: " + t.getMessage());
            }
        });
    }

    //Network call to get support details
    private void getSupport(String key) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getSupport(key);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Support response is full: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String company = jsonObject.getString("company_name");
                        String mobile1 = jsonObject.getString("mobile");
                        String mobile2 = jsonObject.getString("phone");
                        String mobile3 = jsonObject.getString("phone_1");
                        String email_1 = jsonObject.getString("email");
                        String email_2 = jsonObject.getString("email_2");
                        String billEmail = jsonObject.getString("email_2");
                        String add = jsonObject.getString("address");
                        String website = jsonObject.getString("website_link");
                        String about = jsonObject.getString("about_us");

                        Support support = new Support(company, mobile1, mobile2, mobile3, email_1, email_2, billEmail, add, website, about);
                        supportMutableLiveData.setValue(support);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Support Response failure: " + t.getMessage());
                Support support = new Support(t.getMessage());
                supportMutableLiveData.setValue(support);

            }
        });
    }

    //Network call to get list of bank
    private void getBankDetails() {

        final List<Bank> bankList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getBankDetails();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        Log.e(TAG, "Bank details response is successful: " + response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String account = object.getString("name_in_bank");
                            String bankName = object.getString("bank_name");
                            String accountNo = object.getString("account_no");
                            String ifsc = object.getString("ifsc_code");
                            String status = object.getString("status");

                            Bank bank = new Bank(account, bankName, accountNo, ifsc, status);
                            bankList.add(bank);
                            bankDetailsListLiveData.setValue(bankList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, t.getMessage());
                Bank bank = new Bank(t.getMessage());
                bankList.add(bank);
                bankDetailsListLiveData.setValue(bankList);
            }
        });

    }

    //Network call to get list of recharge status by Date
    private void getRechargeListByDate(String id, String auth, String from, String to) {

        final List<RechargeDetails> rechargeDetailsList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);

        Call<String> call = operator.getReportByDate(id, auth, from, to);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "By Date response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String number = jsonObject.optString("number");
                            String amount = jsonObject.optString("amount");
                            String status = jsonObject.optString("status");
                            String operator_name = jsonObject.optString("operator_name");
                            String txn_id = jsonObject.optString("txn_id");
                            String opt_txn_id = jsonObject.optString("opt_txn_id");
                            String time = jsonObject.optString("created_on");
                            String message = jsonObject.optString("api_response");
                            String logo = jsonObject.optString("operator_logo");
                            String operator_logo = "http://rechargewebs.com/web/templete/dist/img/" + logo;
                            String recharge_by = jsonObject.optString("recharge_by");
                            String closing_balance = jsonObject.optString("closing_bal");

                            RechargeDetails details = new RechargeDetails(number, amount, status, operator_name, txn_id, opt_txn_id, time, operator_logo, closing_balance, recharge_by, message);
                            rechargeDetailsList.add(details);

                            dateRechargeListLiveData.setValue(rechargeDetailsList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Faliure on Date by: " + t.getMessage());
                RechargeDetails details = new RechargeDetails(t.getMessage());
                rechargeDetailsList.add(details);
                dateRechargeListLiveData.setValue(rechargeDetailsList);
            }
        });
    }

    //Network request for list of recharge
    private void getRechargeListLiveData(String session_id, String auth) {

        final List<RechargeDetails> rechargeList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRechargeList(session_id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null && response.isSuccessful()) {
                    Log.e(TAG, "Response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String number = jsonObject.optString("number");
                            String amount = jsonObject.optString("amount");
                            String status = jsonObject.optString("status");
                            String operator_name = jsonObject.optString("operator_name");
                            String txn_id = jsonObject.optString("txn_id");
                            String opt_txn_id = jsonObject.optString("opt_txn_id");
                            String time = jsonObject.optString("created_on");
                            String message = jsonObject.optString("api_response");
                            String logo = jsonObject.optString("operator_logo");
                            String operator_logo = "http://rechargewebs.com/web/templete/dist/img/" + logo;
                            String recharge_by = jsonObject.optString("recharge_by");
                            String closing_balance = jsonObject.optString("closing_bal");
                            RechargeDetails details = new RechargeDetails(number, amount, status, operator_name, txn_id, opt_txn_id, time, operator_logo, closing_balance, recharge_by, message);
                            rechargeList.add(details);

                            rechargeListLiveData.setValue(rechargeList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Support response failure: " + t.getMessage());
                RechargeDetails details = new RechargeDetails(t.getMessage());
                rechargeList.add(details);
                dateRechargeListLiveData.setValue(rechargeList);
            }
        });
    }

    //Network call to get list of debit summary
    private void getDebitSummary(String id, String auth) {

        final List<Passbook> debitList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getDebititSummary(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Debit response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            debitList.add(passbook);
                            debiitSummaryMutableLiveData.setValue(debitList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Passbook passbook = new Passbook(t.getMessage());
                debitList.add(passbook);
                debiitSummaryMutableLiveData.setValue(debitList);
            }
        });
    }

    //Network call to get list list of credit summary
    private void getCreditSummary(String id, String auth) {

        final List<Passbook> creditList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getCreditSummary(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "CreditReportFragment response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            creditList.add(passbook);
                            creditSummaryMutableLiveData.setValue(creditList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Passbook passbook = new Passbook(t.getMessage());
                creditList.add(passbook);
                creditSummaryMutableLiveData.setValue(creditList);
            }
        });
    }


    //Get recharge status
    private void getStatus(String userId, String auth, String number, String operatorId, String amount) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRechargeStatus(userId, auth, number, operatorId, amount);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        String number = jsonObject.getString("number");
                        String amount = jsonObject.getString("amount");
                        String status = jsonObject.getString("status");
                        String operator_name = jsonObject.getString("operator_name");
                        String txn_id = jsonObject.getString("txn_id");
                        String opt_txn_id = jsonObject.optString("opt_txn_id");
                        String time = jsonObject.getString("created_on");
                        String message = jsonObject.getString("message");
                        String logo = jsonObject.getString("operator_logo");
                        String operator_logo = "http://rechargewebs.com/web/templete/dist/img/" + logo;

                        Recharge recharge = new Recharge(number, amount, status, operator_name, txn_id, opt_txn_id, time, message, operator_logo);
                        rechargeLiveData.setValue(recharge);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "No response " + response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Recharge response: " + t.getMessage());
                Recharge recharge = new Recharge(t.getMessage());
                rechargeLiveData.setValue(recharge);
            }
        });
    }

    //Network request to get list of electric operator by state
    private void getOperatorListByState(String operatorType, String state) {

        final List<Prepaid> operatorBySList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getOperatorListByState(operatorType, state);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Operator by state is successful" + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String operator = jsonObject.getString("operator_name");
                            String code = jsonObject.getString("operator_code");
                            String logo = jsonObject.getString("logo");

                            Prepaid operatorByS = new Prepaid(logo, code, operator);
                            operatorBySList.add(operatorByS);
                            operatorByStateLiveData.setValue(operatorBySList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Operator by state is failed" + t.getMessage());
                Prepaid prepaid = new Prepaid(t.getMessage());
                operatorBySList.add(prepaid);
                operatorByStateLiveData.setValue(operatorBySList);
            }
        });
    }

    //Network call to get electric bill pay
    private void getBillPayDetails(String auth, String session_id, String counsumer_id, String code, int amount, final String ref_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getElectricBillPayDetails(auth, session_id, counsumer_id, code, amount, ref_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Bill pay Response success: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String number = jsonObject.getString("number");
                        String amount = jsonObject.getString("amount");
                        String status = jsonObject.getString("status");
                        String operator = jsonObject.getString("operator_name");
                        String txn = jsonObject.optString("txn_id");
                        String opt_txn = jsonObject.optString("opt_txn_id");
                        String created = jsonObject.getString("created_on");
                        String resp = jsonObject.getString("response");

                        BillPay billPay = new BillPay(number, amount, status, operator, txn, opt_txn, created, resp);
                        billPayMutableLiveData.setValue(billPay);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                BillPay billPay = new BillPay(t.getMessage());
                billPayMutableLiveData.setValue(billPay);
                Log.e(TAG, "Bill pay response failure");
            }
        });
    }

    //Network request to get coupon purchase details
    private void getCouponPurchaseDetails(String auth, String id, String name, String quantity) {

        Call<Coupon> couponCall = apiService.buyCoupon(auth, id, name, quantity);
        couponCall.enqueue(new Callback<Coupon>() {
            @Override
            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                Log.e(TAG, "Coupon REsponse: " + response.body().getMessage());
                if (response.isSuccessful() && response.body() != null) {
                    Coupon coupon = new Coupon(response.body().getMessage(), response.body().getTxn_id(), response.body().getStatus(), response.body().getPrice(),
                            response.body().getTotoal_price(), response.body().getCreated_on());
                    couponMutableLiveData.setValue(coupon);
                }
            }

            @Override
            public void onFailure(Call<Coupon> call, Throwable t) {

                Log.e(TAG, "coupon purchase: " + t.getMessage());
                Coupon coupon = new Coupon(t.getMessage());
                couponMutableLiveData.setValue(coupon);
            }
        });
    }

    //Network request to get electric bill status
    private void getElectricStatus(String auth, String customer_id, String code) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);

        Call<ElectricStatus> call = operator.getElectricBillStatus(auth, customer_id, code);
        call.enqueue(new Callback<ElectricStatus>() {
            @Override
            public void onResponse(Call<ElectricStatus> call, Response<ElectricStatus> response) {

                Log.e(TAG,"Electric Bill status response: " + response.body().toString());
                if (response.isSuccessful() && response.body() != null) {
                        Log.e(TAG, "Electric response success: " + response.body().toString());
                        ElectricStatus electricStatus = new ElectricStatus(response.body().getStatus(), response.body().getCustomerId(),
                                response.body().getCustomerName(), response.body().getBillNumber(), response.body().getBillDate(), response.body().getBillDueDate(),
                                response.body().getBillPeriod(), response.body().getBillAmount(), response.body().getRefId(), response.body().getMessage());

                        electricStatusMutableLiveData.setValue(electricStatus);
                    }
            }

            @Override
            public void onFailure(Call<ElectricStatus> call, Throwable t) {
                Log.e(TAG, "Electric response Failure: " + t.getMessage());
                ElectricStatus electricStatus = new ElectricStatus("Unable To Fetch Details");
                electricStatusMutableLiveData.setValue(electricStatus);
            }
        });

    }

    //Network request to get electric bill status with more than one parameter
    private void getElectricBillStatusTwo(String auth, String customer_id, String code, String parameter2) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<ElectricStatus> call = operator.getElectricBillStatus(auth, customer_id, code, parameter2);
        call.enqueue(new Callback<ElectricStatus>() {
            @Override
            public void onResponse(Call<ElectricStatus> call, Response<ElectricStatus> response) {
                Log.e(TAG, "Electric response success");
                if (response.isSuccessful() && response.body() != null) {

                    ElectricStatus electricStatus = new ElectricStatus(response.body().getStatus(), response.body().getCustomerId(),
                            response.body().getCustomerName(), response.body().getBillNumber(), response.body().getBillDate(), response.body().getBillDueDate(),
                            response.body().getBillPeriod(), response.body().getBillAmount(), response.body().getRefId(), response.body().getMessage());

                    electricStatusMutableLiveData.setValue(electricStatus);
                }
            }

            @Override
            public void onFailure(Call<ElectricStatus> call, Throwable t) {
                Log.e(TAG, "Electric response success: " + t.getMessage());
                ElectricStatus electricStatus = new ElectricStatus(t.getMessage());
                electricStatusMutableLiveData.setValue(electricStatus);
            }
        });
    }

    //Network Call to get Operator List
    private void getOperatorList(String operator) {

        final List<Prepaid> list = new ArrayList<>();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator1 = retrofit.create(Operator.class);

        Call<String> call = operator1.getOperatorList(operator);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Operator response successful");
                    Log.e(TAG, response.body());
                    try {
                        JSONArray array = new JSONArray(response.body());
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String image = object.getString("logo");
                            String logo = "http://rechargewebs.com/web/templete/dist/img/" + image;
                            String name = object.getString("operator_name");
                            Log.e(TAG, "opertor: " + id + " : " + name);
                            list.add(new Prepaid(logo, id, name));
                            operatorListLiveData.setValue(list);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Prepaid prepaid = new Prepaid(t.getMessage());
                list.add(prepaid);
                operatorListLiveData.setValue(list);

            }
        });
    }

    //Network call to get Fund request response
    private void getFundResponse(String id, String auth, String amount, String bank, String paymentMode, String paymentDate, String transaction_id, String walletType) {

        Call<FundResponse> call = apiService.getFundResponse(id, auth, amount, bank, paymentMode, paymentDate, transaction_id, walletType);
        call.enqueue(new Callback<FundResponse>() {
            @Override
            public void onResponse(Call<FundResponse> call, Response<FundResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Fund requrest response is successful");
                    String message = response.body().getMessage();

                    fundResponseMutableLiveData.setValue(message);
                } else {
                    Log.e(TAG, "Fund request response is null");
                }
            }

            @Override
            public void onFailure(Call<FundResponse> call, Throwable t) {

                Log.e(TAG, "Fund Request response is failed : " + t.getMessage());
            }
        });
    }

    //Network call to get Psa registration response
    private void getPsaResponse(String id, String auth, String name, String shop, String locality, String pincode, String state, String mobile, String email, String panNo) {

        Call<FundResponse> call = apiService.getPsaResgistration(id, auth, name, shop, locality, pincode, state, mobile, email, panNo);
        call.enqueue(new Callback<FundResponse>() {
            @Override
            public void onResponse(Call<FundResponse> call, Response<FundResponse> response) {
                Log.e(TAG,"Psa Response: " + response.body().getMessage());
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG,"Psa Response: " + response.body().getMessage());
                    String message = response.body().getMessage();
                    psaRegistrationMutableLiveData.setValue(message);
                }
            }

            @Override
            public void onFailure(Call<FundResponse> call, Throwable t) {

                Log.e(TAG,"Psa response failure: " + t.getMessage());
            }
        });
    }

    //Network call to reset password
    private void resetPassword(String name) {
        String message;

        Call<Password> call = apiService.resetPassword(name);
        call.enqueue(new Callback<Password>() {
            @Override
            public void onResponse(Call<Password> call, Response<Password> response) {

                if (response.isSuccessful() && response.body() != null) {

                    passwordMutableLiveData.setValue(response.body().getMessage());
                    Log.e(TAG, "Message: " + response.body().getMessage());
                } else {
                    Log.e(TAG, "empty body");
                }
            }

            @Override
            public void onFailure(Call<Password> call, Throwable t) {

                Log.e(TAG, t.getMessage());
                passwordMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to get credential details
    private void getCredential(String id, String auth) {

        Call<Credential> call = apiService.viewCredential(id, auth);
        call.enqueue(new Callback<Credential>() {
            @Override
            public void onResponse(Call<Credential> call, Response<Credential> response) {
                Log.e(TAG, "cred response: " + response.body());
                if (response.isSuccessful() && response.body() != null) {

                    String status = response.body().getStatus();
                    if (status == null) {
                        status = "empty status";
                    }
                    Log.e(TAG, "Status: " + status);
                    String remark = response.body().getRemark();
                    if (remark == null) {
                        remark = "empty remark";
                    }
                    Log.e(TAG, "remark: " + remark);
                    String price = response.body().getPrice();

                    Credential credential = new Credential(response.body().getPsaId(), response.body().getVleName(), response.body().getMessage(), status, remark, price);
                    credentialMutableLiveData.setValue(credential);
                }
            }

            @Override
            public void onFailure(Call<Credential> call, Throwable t) {

                Credential credential = new Credential(t.getMessage());
                credentialMutableLiveData.setValue(credential);
            }
        });
    }


    //Network call to get list of passbook details
    private void getPassbook(String id, String auth) {

        final List<Passbook> passbookList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getPassbookDetails(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Passbook response successful: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            passbookList.add(passbook);
                            passbookDetailsLiveData.setValue(passbookList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Passbook passbook = new Passbook(t.getMessage());
                passbookList.add(passbook);
                passbookDetailsLiveData.setValue(passbookList);
            }
        });

    }

    //Network call to get passbook details by date
    private void getPassbookByDate(String id, String auth, String fromDate, String toDate) {

        final List<Passbook> passbookList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getPassbookDetails(id, auth, fromDate, toDate);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Passbook response: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            passbookList.add(passbook);
                            passbookDetailsLiveData.setValue(passbookList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {


                Passbook passbook = new Passbook(t.getMessage());
                passbookList.add(passbook);
                passbookDetailsLiveData.setValue(passbookList);
            }
        });
    }

    //Network call to get credit list by date
    private void getCreditByDate(String id, String auth, String from, String to) {

        final List<Passbook> creditList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getCreditSummaryByDate(id, auth, from, to);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "CreditReportFragment response by date: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            creditList.add(passbook);
                            creditSummaryMutableLiveData.setValue(creditList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Passbook passbook = new Passbook(t.getMessage());
                creditList.add(passbook);
                creditSummaryMutableLiveData.setValue(creditList);
            }
        });

    }

    //Network call to get debit summary by date
    private void getDebitByDate(String id, String auth, String from, String to) {

        final List<Passbook> debitList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getDebitSummaryByDate(id, auth, from, to);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Debit response by date: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String credit = jsonObject.optString("credit_amount");
                            String debit = jsonObject.optString("debit_amount");
                            String closing = jsonObject.optString("closing_bal");
                            String opening = jsonObject.optString("opening_bal");
                            String tran_id = jsonObject.optString("transaction_id");
                            String narration = jsonObject.optString("narration");
                            String wallet_type = jsonObject.optString("wallet_type");
                            String created_on = jsonObject.optString("created_on");

                            Passbook passbook = new Passbook(credit, debit, closing, opening, tran_id, narration, wallet_type, created_on);
                            debitList.add(passbook);
                            debiitSummaryByDateLiveData.setValue(debitList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Passbook passbook = new Passbook(t.getMessage());
                debitList.add(passbook);
                debiitSummaryByDateLiveData.setValue(debitList);
            }
        });

    }

    //Network call to get list of coupon report
    private void getCouponReport(String id, String auth) {

        final List<CouponReport> couponReportList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getCouponPurchaseReport(id, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Coupon transfer report : " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String psaid = jsonObject.optString("psa_id");
                            Log.e(TAG, psaid);
                            String name = jsonObject.optString("vle_name");
                            String quantity = jsonObject.optString("quantity");
                            String couponprice = jsonObject.optString("coupon_price");
                            String total_price = jsonObject.optString("total_price");
                            String txn_id = jsonObject.optString("txn_id");
                            String status = jsonObject.optString("status");
                            String created_on = jsonObject.optString("created_on");
                            String remark = jsonObject.optString("remark");

                            CouponReport report = new CouponReport(psaid, name, quantity, couponprice, total_price, txn_id, status, created_on, remark);
                            couponReportList.add(report);
                            couponReportMutableLiveData.setValue(couponReportList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                CouponReport couponReport = new CouponReport(t.getMessage());
                couponReportList.add(couponReport);
                couponReportMutableLiveData.setValue(couponReportList);
            }
        });
    }

    //Network call to get coupon report by date
    private void getCouponReportListByDate(String id, String auth, String from, String to) {

        Log.e(TAG, "Parameters: " + id + auth + from + to);

        final List<CouponReport> couponReportList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getCouponPurchaseReportByDate(id, auth, from, to);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Coupon response by date: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String psaid = jsonObject.optString("psa_id");
                            Log.e(TAG, psaid);
                            String name = jsonObject.optString("vle_name");
                            String quantity = jsonObject.optString("quantity");
                            String couponprice = jsonObject.optString("coupon_price");
                            String total_price = jsonObject.optString("total_price");
                            String txn_id = jsonObject.optString("txn_id");
                            String status = jsonObject.optString("status");
                            String created_on = jsonObject.optString("created_on");
                            String remark = jsonObject.optString("remark");

                            CouponReport report = new CouponReport(psaid, name, quantity, couponprice, total_price, txn_id, status, created_on, remark);
                            couponReportList.add(report);
                            couponReportMutableLiveData.setValue(couponReportList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                CouponReport couponReport = new CouponReport(t.getMessage());
                couponReportList.add(couponReport);
                couponReportMutableLiveData.setValue(couponReportList);

            }
        });
    }

    //Network call to get remitter message
    private void getMessage(String auth, String mobile) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Operator.BASE_URL)
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRemitterDetails(auth, mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Message response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String message = jsonObject.getString("message");

                        messageMutableLiveData.setValue(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Remitter response failure: " + t.getMessage());
                messageMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to get the id
    private void getId(String auth, String mobile) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRemitterDetails(auth, mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Response for id is successful: " + response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        JSONObject object = dataObj.getJSONObject("remitter");

                        String id = object.getString("id");
                        idMutableLiveData.setValue(id);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Id response failure: " + t.getMessage());
                idMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to get remitter details
    private void getRemitter(String auth, String mobile) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator formaxServices = retrofit.create(Operator.class);
        Call<String> call = formaxServices.getRemitterDetails(auth, mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Remitter response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject dataObject = jsonObject.optJSONObject("data");

                        JSONObject remitterObject = dataObject.optJSONObject("remitter");

                        String id = remitterObject.getString("beneficiary_id");
                        String name = remitterObject.getString("name");
                        String mobile = remitterObject.getString("mobile");
                        String address = remitterObject.getString("address");
                        String pincode = remitterObject.getString("pincode");
                        String city = remitterObject.getString("city");
                        String state = remitterObject.getString("state");
                        String kyc_stat = remitterObject.getString("kyc_status");
                        int consumed_limit = remitterObject.optInt("consumed_limit");
                        int remaining = remitterObject.getInt("remaining_limit");
                        String kyc_docs = remitterObject.getString("kyc_docs");
                        int is_verified = remitterObject.getInt("is_verified");
                        int txnLimit = remitterObject.getInt("perm_txn_limit");

                        Remitter remitter = new Remitter(id, name, mobile, address, pincode, city, state, kyc_stat, consumed_limit, remaining, kyc_docs
                                , is_verified, txnLimit);

                        remitterMutableLiveData.setValue(remitter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Remitter response failure: " + t.getMessage());
                remitterMutableLiveData.setValue(new Remitter(t.getMessage()));
            }
        });
    }

    private void getBeneficiary(String auth, String mobile) {

        final List<Beneficiary> beneficiaryList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.getRemitterDetails(auth, mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Beneficiary response Successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject dataObject = jsonObject.optJSONObject("data");
                        JSONArray beneficiaryArray = dataObject.getJSONArray("beneficiary");

                        if (!beneficiaryArray.isNull(0)) {
                            Log.e(TAG, "beneficiary array is not empty");
                            for (int i = 0; i < beneficiaryArray.length(); i++) {

                                JSONObject object = beneficiaryArray.optJSONObject(i);
                                String id = object.optString("id");
                                String name = object.optString("name");
                                String mobile = object.optString("mobile");
                                String account = object.optString("account");
                                String bank = object.optString("bank");
                                String stat = object.getString("status");
                                String last_suc_date = object.optString("last_success_date");
                                String last_suc_name = object.optString("last_success_name");
                                String last_suc_imps = object.optString("last_success_imps");
                                String ifsc = object.getString("ifsc");
                                String imps = object.getString("imps");

                                Beneficiary beneficiary = new Beneficiary(id, name, mobile, account, bank, stat, last_suc_date, last_suc_name, last_suc_imps, ifsc, imps);
                                beneficiaryList.add(beneficiary);
                                beneficiaryListMutableLiveData.setValue(beneficiaryList);

                            }
                        } else {
                            Log.e(TAG, "beneficiary array is empty");
                            String message = "Add Beneficiary";
                            beneficiaryList.add(new Beneficiary(message));
                            beneficiaryListMutableLiveData.setValue(beneficiaryList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Beneficiary response failed: " + t.getMessage());
                beneficiaryList.add(new Beneficiary(t.getMessage()));
                beneficiaryListMutableLiveData.setValue(beneficiaryList);
            }
        });
    }

    private void getValidate(String auth, String mobile, final String remitter_id, String otp) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.validateRemitter(auth, mobile, remitter_id, otp);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Validate response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String message = jsonObject.getString("message");

                        if (message.equals("Transaction Successful")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject remitter = data.getJSONObject("remitter");

                            int isVerified = remitter.getInt("is_verified");
                            Validate validate = new Validate(message, isVerified);
                            validateMessageMutableLiveData.setValue(validate);
                        } else {
                            validateMessageMutableLiveData.setValue(new Validate(message));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Validate Response Failure: " + t.getMessage());
                validateMessageMutableLiveData.setValue(new Validate(t.getMessage()));
            }
        });
    }


    private void getRegister(String auth, String mobile, String firstName, String lastName, String pincode) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.registerRemitter(auth, mobile, firstName, lastName, pincode);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Register response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        String message = jsonObject.getString("message");
                        if (message.equals("OTP sent successfully")) {
                            JSONObject dataObj = jsonObject.getJSONObject("data");
                            JSONObject object = dataObj.getJSONObject("remitter");

                            String id = object.getString("id");
                            int isVerified = object.getInt("is_verified");

                            Register register = new Register(message, id, isVerified);
                            registerMutableLiveData.setValue(register);
                        } else {
                            registerMutableLiveData.setValue(new Register(message));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Register response failure: " + t.getMessage());
                registerMutableLiveData.setValue(new Register(t.getMessage()));
            }
        });
    }

    //Network call to add beneficiary
    private void addBeneficary(String auth, String mobile, final String remitter_id, String name, String ifscCode, String account) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.addBeneficiary(auth, mobile, remitter_id, name, ifscCode, account);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Add beneficiary response is successful: " + response.body());

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        addBeneficiaryMutableLiveData.setValue(new AddBeneficiary(status, message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Add beneficiary response failure: " + t.getMessage());
                addBeneficiaryMutableLiveData.setValue(new AddBeneficiary(t.getMessage()));
            }
        });
    }

    //Network call to validate account
    private void validateAccountDetails(String session_id, String auth, String account, String ifsc, String mobile) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.validateAccount(session_id, auth, account, ifsc, mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Account Validate Response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String message = jsonObject.getString("message");
                        Log.e(TAG, "Message: " + message);
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        String name = dataObject.optString("benename");

                        validateAccountMutableLiveData.setValue(new AddBeneficiary(name, message));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Validate Account response Failure: " + t.getMessage());
                validateAccountMutableLiveData.setValue(new AddBeneficiary(t.getMessage()));
            }
        });
    }

    //Network call to delete beneficiary
    private void deleteBenData(String auth, String beneficiary_id, final String remitter_id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.deleteBeneficiary(auth, beneficiary_id, remitter_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Delete beneficiary response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        deleteBenMutableLiveData.setValue(new AddBeneficiary(status, message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Delete beneficiary response failure: " + t.getMessage());
                deleteBenMutableLiveData.setValue(new AddBeneficiary(t.getMessage()));
            }
        });
    }

    //Network call to validate delete
    private void deleteBenValidation(String auth, String benId, String remId, String otp) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.deleteBenValidate(auth, benId, remId, otp);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Delete validation response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        String data = jsonObject.getString("data");
                        String message = jsonObject.getString("message");

                        deleteBenValidationMutableLiveData.setValue(new AddBeneficiary(data, message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Delete validation response failure: " + t.getMessage());
                deleteBenValidationMutableLiveData.setValue(new AddBeneficiary(t.getMessage()));
            }
        });
    }

    // Network call to send money to beneficiary
    private void sendMoney(String session_id, String auth, String mobile, String remitter_id, String name,
                           String ifsc, String account, String ben_id, String amount) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.transferMoney(session_id, auth, mobile, remitter_id, name, ifsc, account, ben_id, amount);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Transfer money response Successful" + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String message = jsonObject.getString("message");
                        transferMutableLiveData.setValue(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Transfer money response Failure: " + t.getMessage());
                transferMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to aeps log in
    private void logInAeps(String session_id, String serviceType, String auth) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.aepsLogIn(session_id, serviceType, auth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "Aeps login response Successful: " + response.body());
                if (response.isSuccessful() && response.body() != null) {

                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String agent_id = object.optString("agent_id");
                            String message = object.optString("Success");
                            String status = object.optString("status");
                            String remark = object.optString("remark");

                            AepsLogIn logIn = new AepsLogIn(agent_id, message, status, remark);
                            aepsLogInMutableLiveData.setValue(logIn);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Aeps log in failed: " + t.getMessage());
                aepsLogInMutableLiveData.setValue(new AepsLogIn(t.getMessage()));
            }
        });
    }

    //Network request to get aeps report
    private void getAepsReport(String session_id, String auth) {

        Call<List<AepsReport>> listCall = apiService.getAepsReport(session_id, auth);
        listCall.enqueue(new Callback<List<AepsReport>>() {
            @Override
            public void onResponse(Call<List<AepsReport>> call, Response<List<AepsReport>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "Aeps report response successful");
                    aepsReportListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<AepsReport>> call, Throwable t) {

                Log.e(TAG, "Aeps report response failure:  " + t.getMessage());
            }
        });
    }


    //Network call to get aeps report list by date
    private void getAepsReportByDate(String session_id, String auth, String from, String to) {

        Call<List<AepsReport>> listCall = apiService.getAepsReportByDate(session_id, auth, from, to);
        listCall.enqueue(new Callback<List<AepsReport>>() {
            @Override
            public void onResponse(Call<List<AepsReport>> call, Response<List<AepsReport>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Aeps report by date response is successful");
                    aepsReportListMutableLiveDataByDate.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<AepsReport>> call, Throwable t) {

                Log.e(TAG, "Aeps report by date response is failure: " + t.getMessage());
            }
        });
    }

    //Network request to get kyc submit response
    private void submitKycDetails(String session_id, String auth, String name, String shopName, String dob, String email, String address, String pincode,
                                  String state, String mobile, String city, String aadhaarNo, String panNo, RequestBody adharImageUrl,
                                  RequestBody panImageUrl, String service) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);

        Call<String> call = operator.submitKyc(session_id, auth, name, shopName, dob, email, address, pincode, state, mobile, city, aadhaarNo, panNo, adharImageUrl, panImageUrl, service);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG, "Submit kyc Response is: " + response.body());
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Submit kyc response successful: " + response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        String message = jsonObject.optString("message");
                        kycresponseMutableLiveData.setValue(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (response.body() == null) {
                    kycresponseMutableLiveData.setValue("No response");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Submit Kyc response is failure : " + t.getMessage());
                kycresponseMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to send aeps transaction details

    private void sendAepsTrans(String session_id, String auth, String service, String amount, String order_id, String mobile) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String> call = operator.sendAepsDetails(session_id, auth, service, amount, order_id, mobile);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG, "Aeps send response is : " + response.body());
                try {
                    JSONArray jsonArray = new JSONArray(response.body());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String status = jsonObject.getString("status");
                        aepssendResponseMutableLiveData.setValue(status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG, "Aeps send response failure: " + t.getMessage());
                aepssendResponseMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network request to get Otp and details
    private void getOtp(String authKey, String mobile, String email) {

        Call<Otp> otpCall = apiService.getOtpDetails(authKey, mobile, email);
        otpCall.enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

                Log.e(TAG, "Otp response successfull");
                if (response.isSuccessful() && response.body() != null) {

                    Log.e(TAG, "Otp is : " + response.body().getMessage() + "," + "Otp is: " + response.body().getOtp());
                    otpMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {

                Log.e(TAG, "Error getting Otp details: " + t.getMessage());
            }
        });
    }

    //Network call to sign up user
    private void getSignUpMessage(String shopName, String userName, String email, String mobile, String password) {

        Call<SignUp> call = apiService.signUpUser(shopName, userName, email, mobile, password);
        call.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {

                Log.e(TAG, "Sign up response is : " + response.body());
                if (response.isSuccessful() && response.body() != null) {

                    signUpMutableLiveData.setValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {

                Log.e(TAG, "Sing Up response is failed : " + t.getMessage());
                signUpMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to update user profile
    private void updateUserProfile(String session_id, String businessName, String name, String address, String state, String location,
                                   String pincode, String authKey, String panNo, String gstNo, String aadharNo) {

        Call<UpdateProfie> call = apiService.updateProfile(session_id, businessName, name, address, state, location, pincode, authKey, panNo, gstNo, aadharNo);

        call.enqueue(new Callback<UpdateProfie>() {
            @Override
            public void onResponse(Call<UpdateProfie> call, Response<UpdateProfie> response) {

                String message = response.body().getMessage();
                Log.e(TAG, "Update profile response is :" + message);
                updateProfileMutableLiveData.setValue(message);
            }

            @Override
            public void onFailure(Call<UpdateProfie> call, Throwable t) {

                Log.e(TAG, "Update profile response failure: " + t.getMessage());
                updateProfileMutableLiveData.setValue(t.getMessage());
            }
        });
    }

    //Network call to get Mobile Number Details
    private void getMobileNumberDetails(String mobileNumber) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.PLAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<FetchOperator>call = operator.fetchOperator("3368","12345",mobileNumber);
        call.enqueue(new Callback<FetchOperator>() {
            @Override
            public void onResponse(Call<FetchOperator> call, Response<FetchOperator> response) {

                if (response.body() != null && response.isSuccessful()){

                    Log.e(TAG,"Mobile operator fetch successful: " +response.body().getCircle());
                    fetchOperatorMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<FetchOperator> call, Throwable t) {

                Log.e(TAG,"Mobile operator fetch failure: " + t.getMessage());
                fetchOperatorMutableLiveData.setValue(new FetchOperator(t.getMessage()));
            }
        });
    }

    //Network Request to get Mobile Recharge Plans
    private void getMobileNumberRechargePlans(String circleId, String operatorId, String type) {

        final List<Plans>fullTtList = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.PLAN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator api = retrofit.create(Operator.class);
        Call<String>call = api.getPlanDetails("3368","12345",circleId,operatorId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){

                    Log.e(TAG,"Response is not null");
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONObject recordObject = jsonObject.optJSONObject("RDATA");
                        if (recordObject != null) {
                            JSONArray fttArray = recordObject.optJSONArray(type);

                            if (fttArray != null) {
                                for (int i = 0; i < fttArray.length(); i++) {

                                    JSONObject object = fttArray.getJSONObject(i);
                                    String rs = object.optString("rs");
                                    String desc = object.getString("desc");
                                    String validity = object.optString("validity");
                                    String last_updated = object.getString("last_update");

                                    Plans fullTt = new Plans(rs, desc, validity, last_updated);
                                    fullTtList.add(fullTt);
                                    planListMutableLiveData.setValue(fullTtList);
                                }
                            } else {
                                fullTtList.add(new Plans("No Plans Available"));
                                planListMutableLiveData.setValue(fullTtList);
                            }
                        }else {
                            fullTtList.add(new Plans("No Plans Available"));
                            planListMutableLiveData.setValue(fullTtList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Failure response: " + t.getMessage());
                fullTtList.add(new Plans(t.getMessage()));
                planListMutableLiveData.setValue(fullTtList);
            }
        });
    }

    //Network request to get list of special offer for mobile numbers
    private void getRofferList(String mobile, String operatorName) {

        final List<Roffer>rofferList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.PLAN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);

        Call<String>call = operator.getSpecialOffer("3368","12345",mobile,operatorName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"Special offer response : " + response.body());
                if (response.isSuccessful() && response.body() != null){

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JSONArray recordArray = jsonObject.optJSONArray("RDATA");

                        if (recordArray != null) {
                            for (int i = 0; i < recordArray.length(); i++) {

                                JSONObject object = recordArray.getJSONObject(i);
                                String rs = object.optString("price");
                                String desc = object.optString("ofrtext");

                                Roffer roffer = new Roffer(rs, desc);
                                rofferList.add(roffer);
                                specialOfferListLiveData.setValue(rofferList);
                            }
                        }else {
                            Roffer roffer = new Roffer("No Plans Available");
                            rofferList.add(roffer);
                            specialOfferListLiveData.setValue(rofferList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Special offer response is failure: " + t.getMessage());
                rofferList.add(new Roffer(t.getMessage()));

            }
        });
    }

    //Network call the Dth plans list
    private void getDthPlans(String apiKey, String operatorName) {

        final List<DTH>dthList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.PLAN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String>call = operator.getDthPlans(apiKey,operatorName);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"Dth response is : " + response.body());
                if (response.isSuccessful() && response.body() != null){

                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        JSONObject recordObject = jsonObject.getJSONObject("records");
                        JSONArray planArray = recordObject.optJSONArray("Plan");

                        if (planArray != null) {
                            for (int i = 0; i < planArray.length(); i++) {

                                JSONObject plan = planArray.getJSONObject(i);
                                JSONObject rsObject = plan.getJSONObject("rs");
                                String description = plan.optString("desc");
                                String amount = rsObject.optString("1 MONTHS");

                                DTH dth = new DTH(amount, description);
                                dthList.add(dth);
                                dthListMutableLiveData.setValue(dthList);
                            }
                        }else {
                            String desc = recordObject.optString("desc");
                            dthList.add(new DTH(desc));
                            dthListMutableLiveData.setValue(dthList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Dth response is failure: " + t.getMessage());
                dthList.add(new DTH(t.getMessage()));
                dthListMutableLiveData.setValue(dthList);
            }
        });
    }

    private void getDthCusInfo(String number, String operatorName) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.PLAN_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        Operator operator = retrofit.create(Operator.class);
        Call<String>call = operator.getDthCustomerInfo("3368","12345",operatorName,number);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.e(TAG,"Dth Customer info resposne is: " + response.body());
                if (response.body()!= null && response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());

                        JSONObject object = jsonObject.optJSONObject("DATA");
                        if (object!=null) {
                            String balance = object.optString("Balance");
                            String cusotmerName = object.optString("Name");
                            String nextRechargeDate = object.optString("Next Recharge Date");
                            String Status = object.optString("Address");
                            String planname = object.optString("Plan");
                            String monthlyRecharge = object.optString("Monthly");

                            DthCustomerInfo dthCustomerInfo = new DthCustomerInfo(balance, cusotmerName, nextRechargeDate, Status, planname, monthlyRecharge);
                            dthCustomerInfoMutableLiveData.setValue(dthCustomerInfo);
                        }else {
                            dthCustomerInfoMutableLiveData.setValue(new DthCustomerInfo("Service For This operator is Unavailable"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG,"Dth customer response failure: " + e.getMessage());
                        dthCustomerInfoMutableLiveData.setValue(new DthCustomerInfo(e.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //Network request to get message after sending transaction report
    private void sendAddMoneyReport(String session_id, String authKey, String mmp_txn, String mer_txn,
                                    String amount, String prob, String date, String bank_txn, String f_code,
                                    String clientCode, String bank_name, String authCode, String ipg_txn_id,
                                    String merchant_id, String desc, String discriminator, String udf9, String surcharge,
                                    String cardNumber, String udf1, String udf2, String udf3, String udf4, String udf5, String signature) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Operator.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Operator operator = retrofit.create(Operator.class);
        Call<String>call = operator.sendTransactionDetails(session_id,authKey,mmp_txn,mer_txn,amount,prob,date,bank_txn,f_code,clientCode,bank_name,
                authCode,ipg_txn_id,merchant_id,desc,discriminator,udf9,surcharge,cardNumber,udf1,udf2, udf3, udf4, udf5, signature);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG,"Response is: " + response.body());
                if (response.body() != null && response.isSuccessful()){
                    transactionReportMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.e(TAG,"Error sending details: " + t.getMessage());
                transactionReportMutableLiveData.setValue(t.getMessage());
            }
        });
    }
}
