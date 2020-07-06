package com.ashish.baseproject.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import com.ashish.baseproject.R
import com.ashish.baseproject.network.RepositoryImplementation
import com.ashish.baseproject.ui.baseactivity.BaseActivity
import com.ashish.baseproject.util.Constants.Companion.KEYSTORE_HELPER_ALIES
import com.ashish.baseproject.util.NetworkUtils
import com.ashish.baseproject.util.ToolBarUtil
import com.ashish.baseproject.util.showView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    /*
    1	/employee	GET	JSON	http://dummy.restapiexample.com/api/v1/employees	Get all employee data	Details
    2	/employee/{id}	GET	JSON	http://dummy.restapiexample.com/api/v1/employee/1	Get a single employee data	Details
    3	/create	POST	JSON	http://dummy.restapiexample.com/api/v1/create	Create new record in database	Details
    4	/update/{id}	PUT	JSON	http://dummy.restapiexample.com/api/v1/update/21	Update an employee record	Details
    5	/delete/{id}	DELETE	JSON	http://dummy.restapiexample.com/api/v1/delete/2	Delete an employee record	Details

     */
    private val repositoryImplementation: RepositoryImplementation by inject<RepositoryImplementation>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressDialog()

        ToolBarUtil(this, "Home", false)

        NetworkUtils.getNetworkLiveData(applicationContext).observe(this, Observer { isConnected ->
            if (!isConnected) {
                CoroutineScope(Dispatchers.Main).launch {
                    repositoryImplementation.getSampleData(this@MainActivity)
                        .observe(this@MainActivity, Observer {
                            textView.apply{showView()}
                            stopProgressDialog()
                        })
                }
            }
        })

    }
}
