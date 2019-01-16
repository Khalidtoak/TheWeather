package khalid.com.forecastAppmvvm2.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import khalid.com.forecastAppmvvm2.R
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
private const val MY_PERMISSON_ACCESS_COARSE_LOCATION = 1
class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val fusedLocationProviderClient : FusedLocationProviderClient by instance()
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }
    //late init var to ba initialized in onCreate
    private  lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //find host fragment controlling the navigation
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        //set up bottom nav with NavigationController
        bottom_nav.setupWithNavController(navController)
        //setUp action bar with navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        requestPermision()
        if (hasLocationPermission())
            bindLocationManager()
        else
            requestPermision()
    }

    private fun bindLocationManager() {
        LifeCycleAwareLocationManager(this, fusedLocationProviderClient, locationCallback)
    }

    override fun onNavigateUp(): Boolean {
        //set up back button
        return NavigationUI.navigateUp(navController, null)
    }
    private fun requestPermision(){
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION), MY_PERMISSON_ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSON_ACCESS_COARSE_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                bindLocationManager()
        }
        else
            Toast.makeText(this, "please grant permission in settings", Toast.LENGTH_SHORT).show()
    }
    private fun hasLocationPermission() :Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }// 6f41967d7bf14644b01142434181211
}
