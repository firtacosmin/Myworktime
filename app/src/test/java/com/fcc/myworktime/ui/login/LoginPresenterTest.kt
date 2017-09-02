package com.fcc.myworktime.ui.login

import com.fcc.myworktime.ui.navigation.Navigator
import com.fcc.myworktime.ui.utils.EventData
import com.fcc.myworktime.ui.utils.MainView
import com.fcc.myworktime.utils.Messages
import com.fcc.myworktime.utils.MessagesTest
import com.fcc.myworktime.utils.Resource
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by firta on 9/2/2017.
 * testing the login presenter
 *
 * 1. empty email
 * 2. empty password
 * 3. error response from server
 * 4. correct login response
 *
 *
 */
class LoginPresenterTest {

    lateinit var presenter:LoginPresenter

    private var messages: Messages = MessagesTest()
    private lateinit var model:LoginModel
    private lateinit var view:LoginView
    private lateinit var navigator:Navigator
    private var lifeCycleEvents = PublishSubject.create<EventData>()


    @Before
    fun setUp() {

        model = mock(LoginModel::class.java)
        view = mock(LoginView::class.java)
        navigator = mock(Navigator::class.java)

        presenter = LoginPresenter(navigator, model, messages)
    }

    @After
    fun tearDown() {

        /*clearing the subscriptions on the presenter*/

        lifeCycleEvents.onNext(EventData("",MainView.EVENT_DESTROYED))
    }

    @Test
    fun test1_empty_email(){

        val email = ""
        val password = ""


        `when`(view.observerLoginClick()).thenReturn(Observable.just(""))
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observerRegisterClick()).thenReturn(Observable.never())

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(password)

        presenter.bindView(view)

        verify(view).printEmailError(messages.field_empty_error!!)

    }

    @Test
    fun test2_empty_pass(){

        val email = "email"
        val password = ""


        `when`(view.observerLoginClick()).thenReturn(Observable.just(""))
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observerRegisterClick()).thenReturn(Observable.never())

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(password)

        presenter.bindView(view)

        verify(view).printPassError(messages.field_empty_error!!)

    }

    @Test
    fun test3_error_response_from_server(){

        val email = "email"
        val password = "password"


        `when`(view.observerLoginClick()).thenReturn(Observable.just(""))
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observerRegisterClick()).thenReturn(Observable.never())

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(password)
        `when`(model.login(email, password)).thenReturn(Observable.just(Resource.error("", "")))


        presenter.bindView(view)
        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printLoading()
        verify(view).printError(messages.loginErrorMessage)

    }
    @Test
    fun test4_correct_login_response(){

        val email = "email"
        val password = "password"


        `when`(view.observerLoginClick()).thenReturn(Observable.just(""))
        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.observerRegisterClick()).thenReturn(Observable.never())

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(password)
        `when`(model.login(email, password)).thenReturn(Observable.just(Resource.success("")))


        presenter.bindView(view)
        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printLoading()
        verify(navigator).goToProjects()

    }

}