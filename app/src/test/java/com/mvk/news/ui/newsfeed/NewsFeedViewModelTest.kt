package com.mvk.news.ui.newsfeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mvk.news.R
import com.mvk.news.data.repository.NewsRepository
import com.mvk.news.utils.common.Resource
import com.mvk.news.utils.network.NetworkHelper
import com.mvk.news.utils.rx.TestSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsFeedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var newsRepository: NewsRepository

    private lateinit var testScheduler: TestScheduler

    private lateinit var newsFeedViewModel: NewsFeedViewModel

    private lateinit var paginator: PublishProcessor<Int>

    @Before
    fun setUp() {
        val compositeDisposable = CompositeDisposable()
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        paginator = PublishProcessor.create()
        newsFeedViewModel = NewsFeedViewModel(
            testSchedulerProvider,
            compositeDisposable,
            networkHelper,
            newsRepository,
            ArrayList(),
            paginator
        )
        newsFeedViewModel.country = "in"
    }

    @Test
    fun givenNoInternet_whenFetchingNews_shouldShowNetworkError() {
        doReturn(false)
            .`when`(networkHelper)
            .isNetworkConnected()
        newsFeedViewModel.loadMorePosts()
        assert(newsFeedViewModel.messageStringId.value == Resource.error(R.string.network_connection_error))
    }

    @Test
    fun givenCategory_whenFetchHeadlines_shouldUpdateFeed() {
        val category = "business"
        newsFeedViewModel.fetchHeadlinesWithCategory(category)
        assertEquals(newsFeedViewModel.category, category)
    }

    @Test
    fun givenSearchQuery_whenFetchSearch_shouldUpdateFeed() {
        val query = "school"
        newsFeedViewModel.fetchSearchQuery(query)
        assertEquals(newsFeedViewModel.searchQuery, query)
    }
}