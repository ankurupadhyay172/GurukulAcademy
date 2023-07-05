package com.gca.mygca.ui

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.gca.mygca.BR
import com.gca.mygca.R
import com.gca.mygca.base.BaseFragment
import com.gca.mygca.databinding.FragmentEarlyDepartureFormBinding
import com.gca.mygca.model.request.DepartureRequestModel
import com.gca.mygca.utils.ImagePickerUtils
import com.gca.mygca.utils.Loader
import com.gca.mygca.utils.setImage
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt


@AndroidEntryPoint
class EarlyDepartureFormFragment: BaseFragment<FragmentEarlyDepartureFormBinding,HomeViewModel>() {

    lateinit var date:String
    lateinit var time:String
    val homeViewModel: HomeViewModel by activityViewModels()
    var image: String? = null
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var auth:FirebaseAuth
    lateinit var verificationId: String
    lateinit var loader:Dialog
    lateinit var currentPath:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loader = Loader.getLoader(requireActivity())

        auth = FirebaseAuth.getInstance()
        //registering fragment for camera listener
        val takePhoto = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {

                var bitmap = BitmapFactory.decodeFile(currentPath)
                //val imageBitmap = it.data?.extras?.get("data") as Bitmap
                // do your thing with the obtained bitmap
                //getViewDataBinding().image.setImageBitmap(imageBitmap)
                bitmap = ImagePickerUtils.modifyOrientation(context, bitmap, currentPath, true);
                image = homeViewModel.BitMapToString(homeViewModel.getResizedBitmap(bitmap,500)!!)

                getViewDataBinding().capture.setImageBitmap(bitmap)
            }
        }


        date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        getViewDataBinding().edtDate.setText(date)


        time = SimpleDateFormat("hh:mm aaa", Locale.getDefault()).format(Date())
        getViewDataBinding().edtTime.setText(time)


        getViewDataBinding().capture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    requireActivity().requestPermissions(arrayOf(Manifest.permission.CAMERA),101)
                }
            }else{

                val filename = "photo"
                val storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                try {
                    val imageFile = File.createTempFile(filename,".jpg",storageDirectory)
                    currentPath = imageFile.absolutePath

                    val imageUri = FileProvider.getUriForFile(requireContext(),"com.gca.mygca",imageFile)
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                    takePhoto.launch(intent)
                }catch (e:Exception){

                }

            }

        }


        getViewDataBinding().submitOtp.setOnClickListener {
            verifyCode(getViewDataBinding().edtOtp.text.toString())
        }




        getViewDataBinding().submit.setOnClickListener {

            if(getViewDataBinding().edtStudentName.text.isEmpty()||
                getViewDataBinding().edtStudentClass.text.isEmpty()||
                getViewDataBinding().edtReceiverName.text.isEmpty()||
                getViewDataBinding().edtRelationWithStudent.text.isEmpty()||
                getViewDataBinding().edtMobileNo.text.isEmpty()||
                getViewDataBinding().edtInchargeName.text.isEmpty()){
                if(getViewDataBinding().edtStudentName.text.isEmpty()||
                        image==null)
                    getViewDataBinding().edtStudentName.error = "Enter student name"

                if(getViewDataBinding().edtStudentClass.text.isEmpty())
                    getViewDataBinding().edtStudentClass.error = "Enter student class"

                if(getViewDataBinding().edtReceiverName.text.isEmpty())
                    getViewDataBinding().edtReceiverName.error = "Enter receiver name"

                if(getViewDataBinding().edtRelationWithStudent.text.isEmpty())
                    getViewDataBinding().edtRelationWithStudent.error = "Enter relation with student"

                if(getViewDataBinding().edtMobileNo.text.isEmpty())
                    getViewDataBinding().edtMobileNo.error = "Enter student mobile no"

                if(getViewDataBinding().edtInchargeName.text.isEmpty())
                    getViewDataBinding().edtInchargeName.error = "Enter incharge name"

                if (image==null){
                    showToast("Please capture the image")
                }


            }else{
                loader.show()
                //mobileNoAuthenticator()
                authenticateViaSms()
                //addDeparture()
            }
        }
    }

    private fun authenticateViaSms() {


        val queue = Volley.newRequestQueue(requireContext())

        val one = nextInt(0,9)
        val two = nextInt(0,9)
        val three = nextInt(0,9)
        val four = nextInt(0,9)
        val five = nextInt(0,9)
        val six = nextInt(0,9)
        val random = "$one$two$three$four$five$six"
        val mobile = "91"+getViewDataBinding().edtMobileNo.text.toString()
        val url = "https://www.txtguru.in/imobile/api.php?username=satishdpr&password=33422993&source=GURUAC&dmobile=$mobile&dltentityid=1507162616050773746&dltheaderid=1505161225516297041&dlttempid=1507162616050773746&message=YOU%20HAVE%20DEPOSITED%20$random%20FOR%20Early%20Departure%20THANKS%20,%20GURUKUL%20GURUAC"


        Log.d("mylogotp", "authenticateViaSms: "+random)

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                //showToast("Response is: ${response.substring(0, 500)}")
                loader.cancel()
                showToast("Otp send successfully")
                getViewDataBinding().layoutOtp.visibility = View.VISIBLE
                getViewDataBinding().submitOtp.visibility = View.VISIBLE
            },
            { showToast("That didn't work!") })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

        getViewDataBinding().submitOtp.setOnClickListener {
            if(getViewDataBinding().edtOtp.text.toString() == random){
                loader.show()
                addDeparture()
            }else{
                showToast("Please enter a valid otp")
            }
        }



    }

    private fun addDeparture() {
        homeViewModel.addDeparture(
            DepartureRequestModel(getViewDataBinding().edtStudentName.text.toString(),getViewDataBinding().edtStudentClass.text.toString(),getViewDataBinding().edtStudentSection.text.toString()
                ,getViewDataBinding().edtRelationWithStudent.text.toString(),homeViewModel.selectedBranch,getViewDataBinding().edtReceiverName.text.toString(),image,getViewDataBinding().edtMobileNo.text.toString(),getViewDataBinding().edtInchargeName.text.toString(),date,time,
            getViewDataBinding().edtVehicleNo.text.toString())).observe(viewLifecycleOwner){
            Log.d("mylogdata", "onViewCreated: "+it.getErrorIfExists()?.message)
            it.getErrorIfExists()?.let {
                loader.cancel()
                showToast(""+it.message)
            }
            it.getValueOrNull()?.let {
                //showToast("${it.status}")
                loader.cancel()
                showToast("Departure added successfully")
                findNavController().popBackStack()

            }
        }
    }


    fun mobileNoAuthenticator(){

        callbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                loader.cancel()
               val code = phoneAuthCredential.smsCode
                verifyCode(code)
                showToast("Code sent successfully")
                getViewDataBinding().edtOtp.visibility = View.VISIBLE
                getViewDataBinding().submit.visibility = View.VISIBLE
            }

            override fun onVerificationFailed(e: FirebaseException) {
                loader.cancel()
                showToast(""+e.message)
                Log.d("mylogerror", "onVerificationFailed: "+e.message)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    showToast("Invalid credential")

                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    showToast("Too many request")
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                    showToast("Null activity")
                }

            }

            override fun onCodeSent(s: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, p1)
                verificationId = s
            }


        }



        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91"+getViewDataBinding().edtMobileNo.text.toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code:String?){

        val credential = PhoneAuthProvider.getCredential(verificationId,code!!)
        auth.signInWithCredential(credential).addOnSuccessListener {

        }.addOnFailureListener {
            showToast("Please enter valid otp")
        }
    }

    override fun getLayoutId() = R.layout.fragment_early_departure_form
    override fun getBindingVariable() = BR.model
    override fun getViewModel() = homeViewModel
}