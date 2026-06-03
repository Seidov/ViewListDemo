package com.sultanseidov.viewlistdemo2.domain.usecase.pin

import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.dao.WatchedMovieDao
import com.sultanseidov.viewlistdemo2.data.local.entity.PinEntity
import com.sultanseidov.viewlistdemo2.data.local.entity.WatchedMovieEntity
import com.sultanseidov.viewlistdemo2.domain.repository.IMovieRepository
import com.sultanseidov.viewlistdemo2.util.CyberLogger
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Advanced Behavior Evolution System - Movie Pin Classification Pipeline.
 * 
 * This pipeline implements:
 * 1. Noise Filtering (Metadata/Empty genre drops)
 * 2. Semantic Clustering (Universe identification)
 * 3. Vector Similarity Matching (Jaccard-based PIN association)
 * 4. Adaptive PIN Evolution (Dynamic title mutation)
 */
class WatchClassificationPipelineUseCase @Inject constructor(
    private val repository: IMovieRepository,
    private val watchedMovieDao: WatchedMovieDao,
    private val pinDao: PinDao
) {

    companion object {
        private const val SIMILARITY_THRESHOLD = 0.70
        
        // Essential content genres for clustering
        private val CONTENT_GENRE_WHITELIST = setOf(
            28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10752, 37, 53
        )
    }

    suspend operator fun invoke(movieId: Long) {
        val movieDetails = repository.getMovieDetails(movieId)
        val keywords = repository.getMovieKeywords(movieId)
        val rawGenres = movieDetails.genre_ids ?: emptyList()

        // Prepare Watched Movie Entity
        val movie = WatchedMovieEntity(
            movieId = movieId,
            title = movieDetails.title ?: "Unknown",
            posterPath = movieDetails.poster_path,
            watchedAt = System.currentTimeMillis(),
            classificationVersion = 3,
            genres = rawGenres,
            keywords = keywords,
            weightedGenreProfile = rawGenres.associateWith { id -> if (id == 878 || id == 16) 1.5 else 1.0 },
            genreImportanceScore = rawGenres.size.toDouble(),
            keywordVector = keywords.associateWith { 1.0 },
            semanticIdentity = emptyList(), // Will be updated by classification
            assignedPinId = null
        )

        classifyAndSavePin(movie)
    }

    /**
     * CORE PIPELINE: Classifies a watched movie into a PIN or creates a new one.
     */
    suspend fun classifyAndSavePin(movie: WatchedMovieEntity) {
        val genres = movie.genres

        // 1. Noise Filtering Layer
        // If genres are empty or only metadata (non-content), drop from pipeline
        if (genres.isEmpty() || genres.none { it in CONTENT_GENRE_WHITELIST }) {
            CyberLogger.d("PIPELINE", "Classification Aborted: Movie ${movie.title} marked as noise/metadata.")
            watchedMovieDao.insertWatchedMovie(movie) // Still save as watched, but no PIN
            return
        }

        // 2. Semantic Clustering
        val semanticUniverse = determineSemanticUniverse(genres)
        val updatedMovie = movie.copy(semanticIdentity = listOf(semanticUniverse))

        // 3. Vector Similarity Matching
        val activePins = pinDao.getActivePinsFlow().first()
        var bestMatch: PinEntity? = null
        var maxSim = 0.0

        for (pin in activePins) {
            val sim = calculateSimilarity(genres, pin.dominantGenres)
            if (sim > maxSim) {
                maxSim = sim
                bestMatch = pin
            }
        }

        val finalPinId: Long
        if (maxSim >= SIMILARITY_THRESHOLD && bestMatch != null) {
            // 4. Adaptive PIN Evolution (Update existing)
            val evolvedGenres = (bestMatch.dominantGenres + genres).distinct()
            val mutatedLabel = determineEvolvedLabel(evolvedGenres)
            
            val updatedPin = bestMatch.copy(
                title = mutatedLabel,
                dominantGenres = evolvedGenres,
                movieCount = bestMatch.movieCount + 1,
                generatedFromMovies = (bestMatch.generatedFromMovies + movie.movieId).distinct(),
                lastRecomputedAt = System.currentTimeMillis()
            )
            pinDao.upsertPin(updatedPin)
            finalPinId = updatedPin.pinId
            CyberLogger.logRebuildCache(finalPinId, mutatedLabel, isNew = false)
        } else {
            // 4. Create New Adaptive PIN
            val initialLabel = determineEvolvedLabel(genres)
            val newPin = PinEntity(
                title = initialLabel,
                semanticType = semanticUniverse,
                dominantGenres = genres,
                movieCount = 1,
                clusterStrength = 1.0,
                generatedFromMovies = listOf(movie.movieId),
                lastRecomputedAt = System.currentTimeMillis()
            )
            finalPinId = pinDao.upsertPin(newPin)
            CyberLogger.logRebuildCache(finalPinId, initialLabel, isNew = true)
        }

        // 5. Data Integration & Linkage
        watchedMovieDao.insertWatchedMovie(updatedMovie.copy(assignedPinId = finalPinId))
    }

    private fun calculateSimilarity(movieGenres: List<Int>, pinGenres: List<Int>): Double {
        if (movieGenres.isEmpty() || pinGenres.isEmpty()) return 0.0
        val intersection = movieGenres.intersect(pinGenres.toSet()).size
        val union = movieGenres.union(pinGenres.toSet()).size
        return intersection.toDouble() / union.toDouble()
    }

    private fun determineSemanticUniverse(genres: List<Int>): String {
        return when {
            genres.contains(16) && (genres.contains(878) || genres.contains(14)) -> "MULTIVERSE_COSMIC"
            genres.containsAll(listOf(28, 12)) && (genres.contains(878) || genres.contains(14)) -> "COMBAT_FANTASY"
            genres.contains(27) -> "HORROR_DEPTH"
            genres.contains(80) -> "NOIR_CRIME"
            else -> "GENERAL_CINEMA"
        }
    }

    private fun determineEvolvedLabel(genres: List<Int>): String {
        return when {
            // Priority 1: Multiverse/Cosmic (Animation + Sci-Fi/Fantasy)
            genres.contains(16) && (genres.contains(878) || genres.contains(14)) -> "✦ MULTIVERSE COSMIC WONDER"
            
            // Priority 2: Combat Fantasy (Action/Adventure + Sci-Fi/Fantasy)
            genres.containsAll(listOf(28, 12)) && (genres.contains(878) || genres.contains(14)) -> "✦ EPIC COMBAT FANTASY"
            
            // Genre specific labels
            genres.contains(16) -> "✦ ANIMATED ODYSSEY"
            genres.contains(878) -> "✦ SCI-FI FRONTIER"
            genres.contains(14) -> "✦ FANTASY REALM"
            genres.contains(28) && genres.contains(12) -> "✦ ACTION ADVENTURE SAGA"
            genres.contains(27) -> "✦ DARK HORROR DEPTHS"
            genres.contains(80) -> "✦ CRIME & NOIR"
            genres.contains(18) -> "✦ DRAMATIC CINEMA"
            genres.contains(99) -> "✦ DOCUMENTARY EXPLORATION"
            else -> "✦ NEURAL COLLECTION"
        }
    }
}
