package com.sultanseidov.viewlistdemo2.pinengine

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.dao.WatchedMovieDao
import com.sultanseidov.viewlistdemo2.data.local.database.AppDatabase
import com.sultanseidov.viewlistdemo2.domain.model.MovieModel
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import com.sultanseidov.viewlistdemo2.domain.usecase.pin.SeedInitialOnboardingGenresUseCase
import com.sultanseidov.viewlistdemo2.domain.usecase.pin.WatchClassificationPipelineUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class PinEngineIntegrationTest {

    private lateinit var database: AppDatabase
    private lateinit var pinDao: PinDao
    private lateinit var watchedMovieDao: WatchedMovieDao
    private lateinit var repository: IMovieRepository
    
    private lateinit var seedUseCase: SeedInitialOnboardingGenresUseCase
    private lateinit var classificationUseCase: WatchClassificationPipelineUseCase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        
        pinDao = database.pinDao()
        watchedMovieDao = database.watchedMovieDao()
        repository = mockk()

        seedUseCase = SeedInitialOnboardingGenresUseCase(pinDao)
        classificationUseCase = WatchClassificationPipelineUseCase(repository, watchedMovieDao, pinDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `validate cold-start neural initialization seeding creates root cluster`() = runTest {
        // Arrange
        val selectedGenreIds = listOf(878, 16) // Sci-Fi and Animation

        // Act
        seedUseCase(selectedGenreIds)

        // Assert
        val activePins = pinDao.getActivePinsFlow().first()
        assertEquals(1, activePins.size)
        val rootPin = activePins.first()
        
        assertEquals("✦ NEURAL CORE INITIALIZATION", rootPin.title)
        assertEquals("system_core", rootPin.semanticType)
        assertEquals(0, rootPin.movieCount)
        assertEquals(listOf(878, 16), rootPin.dominantGenres)
    }

    @Test
    fun `validate v2 dynamic cluster production engine creates new cluster for unique movie`() = runTest {
        // Arrange
        val movieId = 83533L
        val movieMetadata = createMockMovie(movieId, "Avatar: Fire and Ash", listOf(878, 28))
        val keywords = listOf("witch", "clone", "alien")

        coEvery { repository.getMovieDetails(movieId) } returns movieMetadata
        coEvery { repository.getMovieKeywords(movieId) } returns keywords

        // Act
        classificationUseCase(movieId)

        // Assert
        // 1. Verify movie saved permanently
        val savedMovie = watchedMovieDao.getWatchedMovieById(movieId)
        assertNotNull(savedMovie)
        assertEquals(2, savedMovie?.classificationVersion)
        
        // 2. Verify new dynamic cluster created (threshold 0.50 not met for existing empty pins)
        val activePins = pinDao.getActivePinsFlow().first()
        assertTrue(activePins.any { it.semanticType == "dynamic_cluster" })
        
        val dynamicPin = activePins.first { it.semanticType == "dynamic_cluster" }
        assertEquals(1, dynamicPin.movieCount)
        assertTrue(dynamicPin.title.contains("WITCH") || dynamicPin.title.contains("CLONE"))
        
        // 3. Verify linkage
        assertEquals(dynamicPin.pinId, savedMovie?.assignedPinId)
    }

    @Test
    fun `validate disposable cache linkage increments count for similar movies`() = runTest {
        // Arrange
        val movie1Id = 1L
        val movie1Metadata = createMockMovie(movie1Id, "Alien Romulus", listOf(878))
        val movie1Keywords = listOf("alien", "space", "horror")

        val movie2Id = 2L
        val movie2Metadata = createMockMovie(movie2Id, "Alien Isolation", listOf(878))
        val movie2Keywords = listOf("alien", "survival", "space")

        coEvery { repository.getMovieDetails(movie1Id) } returns movie1Metadata
        coEvery { repository.getMovieKeywords(movie1Id) } returns movie1Keywords
        coEvery { repository.getMovieDetails(movie2Id) } returns movie2Metadata
        coEvery { repository.getMovieKeywords(movie2Id) } returns movie2Keywords

        // Act
        classificationUseCase(movie1Id)
        classificationUseCase(movie2Id)

        // Assert
        val activePins = pinDao.getActivePinsFlow().first()
        // Should only have one dynamic cluster because they share "alien" and "space" 
        // which meets the 0.50 threshold (2/3 match for movie 2 if pin title is "✦ ALIEN SPACE")
        assertEquals(1, activePins.count { it.semanticType == "dynamic_cluster" })
        
        val dynamicPin = activePins.first { it.semanticType == "dynamic_cluster" }
        assertEquals(2, dynamicPin.movieCount)
        assertEquals(listOf(movie1Id, movie2Id), dynamicPin.generatedFromMovies)
        
        val savedMovie2 = watchedMovieDao.getWatchedMovieById(movie2Id)
        assertEquals(dynamicPin.pinId, savedMovie2?.assignedPinId)
    }

    private fun createMockMovie(id: Long, title: String, genres: List<Int>) = MovieModel(
        id = id.toInt(),
        adult = false,
        backdrop_path = null,
        genre_ids = genres,
        original_language = "en",
        original_title = title,
        overview = "Overview",
        popularity = 1.0,
        poster_path = "/path.jpg",
        release_date = null,
        title = title,
        video = false,
        vote_average = 1.0,
        vote_count = 1
    )
}
