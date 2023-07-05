package com.gca.mygca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.gca.mygca.base.BaseActivity
import com.gca.mygca.utils.PageConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navigationController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toolBar2:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolBar2 = findViewById(R.id.toolBar2)
        setSupportActionBar(toolBar2)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navigationController = navHost.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        setupActionBarWithNavController(navigationController,appBarConfiguration)
        lifecycleScope.launchWhenResumed {
            navigationController.addOnDestinationChangedListener{_,destination,_->
                onDestinationChange(destination)
            }

        }
        
    }

    fun onDestinationChange(destination: NavDestination){
        val config = PageConfiguration.getConfiguration(destination.id)
        toolBar2.isVisible = config.toolbarVisible

    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()||navigationController.navigateUp()
    }
}