package com.fastnews.home

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fastnews.repository.PostRepository
import com.fastnews.service.model.NewPost
import com.fastnews.service.model.PostData
import com.fastnews.util.TestCoroutineRule
import com.fastnews.viewmodel.PostViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostViewModelTest {

    var after: String = "teste"
    var limit: Int = 20

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: PostRepository

    @Mock
    private lateinit var apiRepositoriesObserver: Observer<NewPost>

    @Before
    fun setUp() {
    }

    @Test
    fun givenServerResponse200_whenPostData_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<PostData>())
                .`when`(apiHelper)
                .getPosts(after, limit)
            val viewModel = PostViewModel(Application())
            viewModel.getPosts(after, limit).observeForever(apiRepositoriesObserver)
            Mockito.verify(apiHelper).getPosts(after, limit)
            viewModel.getPosts(after, limit).removeObserver(apiRepositoriesObserver)
        }
    }

    @After
    fun tearDown() {
    }
}