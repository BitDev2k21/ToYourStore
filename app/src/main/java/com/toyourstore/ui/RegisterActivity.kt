package com.toyourstore.ui

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.ApiException
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.preference.SessionData
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import java.util.HashMap
import android.R.attr.password
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : BaseActivity() {

    private lateinit var rlLoginSignUp: RelativeLayout
    private lateinit var txtCreate: TextView
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var edtContactNumber: EditText
    private lateinit var pd: MyProgressDialog
    private lateinit var lnr_google_sign_in:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        try {
            init()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun init() {
        rlLoginSignUp = findViewById(R.id.rlLoginSignUp)
        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        edtContactNumber = findViewById(R.id.edtContactNumber)
        pd = MyProgressDialog(this, R.drawable.icons8_loader)
        lnr_google_sign_in = findViewById(R.id.lnr_google_sign_in)
        rlLoginSignUp.setOnClickListener {
            Log.e("Click", "=========")
            if (isValidateData()) {
                val deviceTokenFB = SessionData.getInstance(applicationContext).getFirebaseToken()?:""
                apiCallingForRegister(
                        edtName.text.toString().trim(),
                        edtEmail.text.toString().trim(),
                        edtPassword.text.toString().trim(),
                    edtContactNumber.text.toString().trim(),
                    deviceTokenFB
                )
            }
        }

        lnr_google_sign_in.setOnClickListener {
            GoogleSignIn()
        }
        txtCreate.setOnClickListener {
        }
    }

    private fun isValidPassword(password:String):Boolean{
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun isValidateData(): Boolean {
        val name = edtName.text.toString().trim()
        val user = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()
        val contactNumber = edtContactNumber.text.toString().trim()

        if (TextUtils.isEmpty(name)) {
            PopupUtils.alertMessage(this, "Please enter name")
            return false
        } else if (TextUtils.isEmpty(user)) {
            PopupUtils.alertMessage(this, "Please enter valid email")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            PopupUtils.alertMessage(this, "Please enter valid email")
            return false
        } else if (TextUtils.isEmpty(password)) {
            PopupUtils.alertMessage(this, "Please enter password")
            return false
        }
        else if (!isValidPassword(password)) {
            PopupUtils.alertMessage(this, "Password must contain one capital letter on number and one symbol (@,$,%,&,#,) ")
            return false
        }
        else if (TextUtils.isEmpty(confirmPassword)) {
            PopupUtils.alertMessage(this, "Please enter confirm password")
            return false
        } else if (!password.equals(confirmPassword)) {
            PopupUtils.alertMessage(this, "Password and confirm password not match")
            return false
        }
        else if (TextUtils.isEmpty(contactNumber)) {
            PopupUtils.alertMessage(this, "Contact Number can not be empty")
            return false
        }
        else {
            return true
        }
    }

    private fun apiCallingForRegister(name: String, email: String, password: String, contact: String, tokenFB: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["name"] = name
            params["email"] = email
            params["password"] = password
            params["user_type"] = "distributor"
            params["contact"] = contact
            params["social_id"] = ""
            params["device_token"] = tokenFB
            //contact
            try {
                val responseOfRegister = apiCallingRequest.apiCallingRegister(params)
                Log.v("@@@"," resp : "+responseOfRegister.message)
                if(responseOfRegister.user!=null && responseOfRegister.user!!.email!=null){
                    withContext(Dispatchers.Main) {
                        pd.cancel()
                        PopupUtils.alertMessageWithCallBack(this@RegisterActivity, "successful user registration", {
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finishAffinity()
                        })
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        pd.cancel()
                        Toast.makeText(this@RegisterActivity,""+responseOfRegister.message,Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (apiEx: Exception) {
                Log.v("@@@"," exception : "+apiEx.message)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                    pd.cancel()
                }
            }
        }
    }

    private fun apiCallingForSocialLogin(email: String, socialName: String,socialID: String, deviceToken: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["email"] = email
            params["user_type"] = "distributor"
            params["email"] = email
            params["social_id"] = socialID
            params["device_token"] = deviceToken
            params["name"] = socialName
            params["contact"] = ""
            //contact
            try {
                val responseOfRegister = apiCallingRequest.apiCallingRegister(params)
                Log.v("@@@"," resp : "+responseOfRegister.message)
                if(responseOfRegister.user!=null && responseOfRegister.user!!.email!=null){
                    withContext(Dispatchers.Main) {
                        pd.cancel()
                        PopupUtils.alertMessageWithCallBack(this@RegisterActivity, "successful user registration", {
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            finishAffinity()
                        })
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        pd.cancel()
                        Toast.makeText(this@RegisterActivity,""+responseOfRegister.message,Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (apiEx: Exception) {
                Log.v("@@@"," exception : "+apiEx.message)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                    pd.cancel()
                }
            }
        }
    }

    private fun isUserSignedIn(): Boolean {
        val account = com.google.android.gms.auth.api.signin.GoogleSignIn.getLastSignedInAccount(this)
        return account != null
    }

    fun getGoogleSinginClient() : GoogleSignInClient {
        /**
         * Configure sign-in to request the user's ID, email address, and basic
         * profile. ID and basic profile are included in DEFAULT_SIGN_IN.
         */
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        /**
         * Build a GoogleSignInClient with the options specified by gso.
         */
        return com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);
    }

    private fun GoogleSignIn(){
        val signInIntent = getGoogleSinginClient().signInIntent
        startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN)
//        if (!isUserSignedIn()){
//            val signInIntent = getGoogleSinginClient().signInIntent
//            startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN)
//        } else {
//            Toast.makeText(this, " User already signed-in ", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun signout() {
        if (isUserSignedIn()){
            getGoogleSinginClient().signOut().addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, " Signed out ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, " Error ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var socialID = ""
    var deviceToken = ""
    var socialName = ""
    var socialemail = ""
    private fun handleSignData(data: Intent?) {
        // The Task returned from this call is always completed, no need to attach
        // a listener.
        com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            .addOnCompleteListener {
                "isSuccessful ${it.isSuccessful}".print()
                if (it.isSuccessful){
                    // user successfully logged-in
                    "account ${it.result?.account}".print()
                    "displayName ${it.result?.displayName}".print()
                    "Email ${it.result?.email}".print()
                    try {
                        socialID = it.result?.id.toString()
                        deviceToken = it.result?.idToken.toString()
                        socialName = it.result?.displayName.toString()
                        socialemail = it.result?.email.toString()
                        val deviceTokenFB = SessionData.getInstance(applicationContext).getFirebaseToken()?:""
                        apiCallingForSocialLogin(socialemail,socialName,socialID,deviceTokenFB)
                    }catch (ex:Exception){
                        Log.v(LoginActivity.TAG_KOTLIN," Exception ${ex.message}")
                    }
                } else {
                    // authentication failed
                    "exception ${it.exception}".print()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginActivity.RC_SIGN_IN) {
            handleSignData(data)
        }
    }

    companion object{
        const val RC_SIGN_IN = 0
        const val TAG_KOTLIN = "@@@TAG_KOTLIN"
    }

    fun Any.print(){
        Log.v(TAG_KOTLIN, " $this")
    }

}