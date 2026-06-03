package com.sultanseidov.viewlistdemo2.domain.usecase.pin

import com.sultanseidov.viewlistdemo2.data.local.dao.PinDao
import com.sultanseidov.viewlistdemo2.data.local.entity.PinEntity
import com.sultanseidov.viewlistdemo2.util.CyberLogger
import javax.inject.Inject

class SeedInitialOnboardingGenresUseCase @Inject constructor(
    private val pinDao: PinDao
) {
    suspend operator fun invoke(selectedGenreIds: List<Int>) {
        if (selectedGenreIds.isEmpty()) return

        // Mimarimize uygun olarak "system_core" tipinde ilk tohum cache-index'ini oluşturuyoruz
        val initialPin = PinEntity(
            title = "✦ NEURAL CORE INITIALIZATION",
            semanticType = "system_core",
            dominantGenres = selectedGenreIds,
            movieCount = 0, // Henüz kalıcı watched_movies_table içinde film yok
            clusterStrength = 1.0,
            generatedFromMovies = emptyList(),
            lastRecomputedAt = System.currentTimeMillis()
        )

        CyberLogger.logNeuralSeed(initialPin.title, initialPin.dominantGenres)
        pinDao.upsertPin(initialPin)
    }
}