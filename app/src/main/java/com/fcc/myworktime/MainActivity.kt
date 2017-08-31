package com.fcc.myworktime

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.EventLog
import android.view.Menu
import android.view.MenuItem
import com.fcc.myworktime.data.UserData
import com.fcc.myworktime.ui.mainactivity.MainActivityPresenter
import com.fcc.myworktime.ui.mainactivity.MainActivityView
import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import io.reactivex.subjects.PublishSubject



class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, MainActivityView {



    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


    private var lifeCycleEvents = PublishSubject.create<EventData>()
    private var menuEvents = PublishSubject.create<EventData>()


    @Inject lateinit var navigator:Navigator
    @Inject lateinit var presenter:MainActivityPresenter


    var logoutMenuItem:MenuItem? = null
    var logoutMenuVisible = true;

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter.bindView(this)

        lifeCycleEvents.onNext(EventData(savedInstanceState,MainView.EVENT_CREATED))




//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    override fun onDestroy() {

        lifeCycleEvents.onNext(EventData(null, MainView.EVENT_DESTROYED))

        super.onDestroy()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        logoutMenuItem = menu.findItem(R.id.action_logout)
        logoutMenuItem?.isVisible = logoutMenuVisible

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_logout -> {
                menuEvents.onNext(EventData(null, MainActivityView.LOGOUT_MENU))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun viewEvent(): Observable<EventData> {
        return lifeCycleEvents
    }

    override fun menuEvent(): Observable<EventData> {
        return menuEvents
    }

    override fun displayMenu() {
        logoutMenuItem?.isVisible = true
        logoutMenuVisible = true
    }
    override fun hideMenu() {
        logoutMenuItem?.isVisible = false
        logoutMenuVisible = false
    }
}
