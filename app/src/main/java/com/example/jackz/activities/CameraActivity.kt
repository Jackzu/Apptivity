package com.example.jackz.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jackz.R
import com.example.jackz.adapters.SaveSettings
import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import com.example.jackz.showToast
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

    /* Class to take and share picture */
class CameraActivity : AppCompatActivity() {

    private lateinit var saveSetting: SaveSettings
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //SharedPreferences state to load a theme
        saveSetting = SaveSettings(this)
        if (saveSetting.loadThemeState() == true) {
            setTheme(R.style.darkTheme)
        } else
            setTheme(R.style.AppTheme)

        //set content to xml file
        setContentView(R.layout.activity_camera)

        //set the image to default as long as no picture has been taken
        camera_image_view.setImageResource(R.drawable.notfound)

        // toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        //OnClickListener for the button to share the picture
        camera_share_btn.setOnClickListener {

            //cant share as long as no picture has been taken
            if (image_uri == null) {
                val toast = R.string.camera_toast
                Toast.makeText(this, toast, Toast.LENGTH_SHORT ).show()
            }

            //get the picture that has been taken and send it through an intent with additional text and a subject
            else {
                val image = image_uri
                val intent = Intent(Intent.ACTION_SEND)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                intent.putExtra(Intent.EXTRA_STREAM, image)
                intent.type = "ïmage/png"
                intent.putExtra(Intent.EXTRA_SUBJECT, "This is where Apptivity brought us! Download now!")
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "This is where Apptivity brought us! Download now!")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share with your friends"))
            }
        }

        //OnClickListener for button to take the picture
        camera_capture_btn.setOnClickListener {

            //get the Request to take a picture first
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){

                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    //call function to request the permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    //permission granted open the camera
                    openCamera()
                }
            }
            else{
                //system needs no permission since its oler than marshmallow
                openCamera()
            }
        }
    }

    //function to open the camera
    private fun openCamera() {

        //collect values to start the camera intent
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        //start the camera intent with the given values in form of image_uri
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user allows or denies permission request
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    openCamera()
                }
                else{
                    //permission was denied
                    Toast.makeText(this, "Camera usage denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //when an activity is successful display the image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            camera_image_view.setImageURI(image_uri)
        }
    }

}

