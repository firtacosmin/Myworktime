package com.fcc.myworktime.ui.registration

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
import org.mockito.Mockito
import org.mockito.Mockito.*

/**
 * Created by firta on 9/2/2017.
 *
 *
 * 1. everything empty
 * 2. no email
 * 3. no password
 * 4. no re-password
 * 5. passwords not matching
 * 6. weak password
 * 7. email collision
 * 8. successful registration
 *
 */
class RegistrationPresenterTest {

    /*To be able to pass any() to methods that have null parameter check*/
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T
    private var messages: Messages = MessagesTest()
    private var lifeCycleEvents = PublishSubject.create<EventData>()


    private lateinit var model:RegistrationModel
    private lateinit var view:RegistrationView
    private lateinit var navigator:Navigator

    private lateinit var presenter:RegistrationPresenter


    @Before
    fun setUp() {

        model = mock(RegistrationModel::class.java)
        view = mock(RegistrationView::class.java)
        navigator = mock(Navigator::class.java)

        presenter = RegistrationPresenter(navigator, model, messages)
    }

    @After
    fun tearDown() {
        /*clearing the subscriptions on the presenter*/
        lifeCycleEvents.onNext(EventData("",MainView.EVENT_DESTROYED))

    }


    @Test
    fun test1_no_fields(){
        val email = ""
        val pass = ""
        val rePass = ""

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)

        presenter.bindView(view)

        verify(view).printEmailError(messages.field_empty_error!!)
        verify(view).printPassError(messages.field_empty_error!!)
        verify(view, never()).printLoading()
        verify(model, never()).registerUser(any(), any())

    }


    @Test
    fun test2_no_email(){
        val email = ""
        val pass = "pass"
        val rePass = "pass"

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)

        presenter.bindView(view)

        verify(view).printEmailError(messages.field_empty_error!!)
        verify(view).printPassError("")
        verify(view, never()).printLoading()
        verify(model, never()).registerUser(any(), any())

    }

    @Test
    fun test3_no_password(){
        val email = "email"
        val pass = ""
        val rePass = "pass"

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)

        presenter.bindView(view)

        verify(view).printEmailError("")
        verify(view).printPassError(messages.field_empty_error!!)
        verify(view, never()).printLoading()
        verify(model, never()).registerUser(any(), any())

    }
    @Test
    fun test4_no_repassword(){
        val email = "email"
        val pass = "pass"
        val rePass = ""

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)

        presenter.bindView(view)

        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printRePassError(messages.field_empty_error!!)
        verify(view, never()).printLoading()
        verify(model, never()).registerUser(any(), any())

    }

    @Test
    fun test5_passwords_not_match(){
        val email = "email"
        val pass = "pass"
        val rePass = "repass"

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)

        presenter.bindView(view)

        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printRePassError(messages.passwords_not_thesame!!)
        verify(view, never()).printLoading()
        verify(model, never()).registerUser(any(), any())

    }

    @Test
    fun test6_weak_password(){
        val email = "email"
        val pass = "pass"
        val rePass = "pass"

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)
        `when`(model.getRegistrationEvents()).thenReturn(Observable.just(Resource.error("", RegistrationModel.ErrorMessages.WEAK_PASS)))

        presenter.bindView(view)

        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printRePassError("")
        verify(view).printLoading()
        verify(model).registerUser(email, pass)
        verify(view).hideLoading()
        verify(view).printGeneralError(messages.weak_pass_error!!)

    }

    @Test
    fun test7_email_collision(){
        val email = "email"
        val pass = "pass"
        val rePass = "pass"

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)
        `when`(model.getRegistrationEvents()).thenReturn(Observable.just(Resource.error("", RegistrationModel.ErrorMessages.USER_COLLISION)))

        presenter.bindView(view)

        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printRePassError("")
        verify(view).printLoading()
        verify(model).registerUser(email, pass)
        verify(view).hideLoading()
        verify(view).printGeneralError(messages.user_collision_error!!)

    }

    @Test
    fun test8_successful_registration(){
        val email = "email"
        val pass = "pass"
        val rePass = "pass"

        `when`(view.viewEvent()).thenReturn(lifeCycleEvents)
        `when`(view.registerObservable()).thenReturn(Observable.just(""))

        `when`(view.getEmail()).thenReturn(email)
        `when`(view.getPassword()).thenReturn(pass)
        `when`(view.getRePassword()).thenReturn(rePass)
        `when`(model.getRegistrationEvents()).thenReturn(Observable.just(Resource.success(RegistrationModel.ErrorMessages.SUCCESS)))

        presenter.bindView(view)

        verify(view).printEmailError("")
        verify(view).printPassError("")
        verify(view).printRePassError("")
        verify(view).printLoading()
        verify(model).registerUser(email, pass)
        verify(view).hideLoading()
        verify(navigator).goToProjects()

    }

}