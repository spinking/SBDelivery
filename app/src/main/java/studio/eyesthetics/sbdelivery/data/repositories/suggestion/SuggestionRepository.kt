package studio.eyesthetics.sbdelivery.data.repositories.suggestion

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.dao.SuggestionsDao
import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity

class SuggestionRepository(
    private val suggestionDao: SuggestionsDao
) : ISuggestionRepository {
    override fun insertSuggestion(suggestion: SuggestionEntity) {
        suggestionDao.insert(suggestion)
    }

    override fun getSuggestions(): LiveData<List<SuggestionEntity>> {
        return suggestionDao.getSuggestions()
    }

    override fun deleteSuggestion(suggestion: SuggestionEntity) {
        suggestionDao.deleteSuggestion(suggestion)
    }
}