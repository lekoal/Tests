package com.geekbrains.tests

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.presenter.search.SearchPresenter
import com.geekbrains.tests.repository.GitHubRepository
import com.geekbrains.tests.view.search.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context
    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
        presenter = SearchPresenter(repository)
    }

    @Test
    fun mainActivity_AssertNotNull() {
        scenario.onActivity {
            assertNotNull(it)
        }
    }

    @Test
    fun mainActivity_IsResumed() {
        assertSame(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun mainActivity_EditText_IsVisible() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            assertEquals(View.VISIBLE, editText.visibility)
        }
    }

    @Test
    fun mainActivity_EditText_HasNoText() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.searchEditText)
            assertEquals("", editText.text.toString())
        }
    }

    @Test
    fun mainActivity_Button_IsVisible() {
        scenario.onActivity {
            val button = it.findViewById<Button>(R.id.toDetailsActivityButton)
            assertSame(View.VISIBLE, button.visibility)
        }
    }

    @Test
    fun mainActivity_Button_Text_IsSame() {
        scenario.onActivity {
            val button = it.findViewById<Button>(R.id.toDetailsActivityButton)
            assertEquals("to details", button.text)
        }
    }

    @Test
    fun mainActivity_RecyclerView_IsVisible() {
        scenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView)
            assertEquals(View.VISIBLE, recyclerView.visibility)
        }
    }

    @Test
    fun mainActivity_RecyclerView_IsEmpty() {
        scenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView)
            assertEquals(0, recyclerView.size)
        }
    }

    @Test
    fun mainActivity_DisplayLoading_IsShow() {
       scenario.onActivity {
           val progressBar = it.findViewById<ProgressBar>(R.id.progressBar)
           it.displayLoading(true)
           assertEquals(true, progressBar.isVisible)
       }
    }

    @Test
    fun mainActivity_DisplayLoading_IsHide() {
        scenario.onActivity {
            val progressBar = it.findViewById<ProgressBar>(R.id.progressBar)
            it.displayLoading(false)
            assertEquals(false, progressBar.isVisible)
        }
    }

    @Test
    fun onAttachView_IsSuccess() {
        scenario.onActivity {
            assertNotSame(it, presenter.getCurrentView())
            presenter.onAttach(it)
            assertSame(it, presenter.getCurrentView())
        }
    }

    @Test
    fun onDetach_IsSuccess() {
        scenario.onActivity {
            presenter.onAttach(it)
            assertSame(it, presenter.getCurrentView())
            presenter.onDetach()
            assertNotSame(it, presenter.getCurrentView())
        }
    }
}