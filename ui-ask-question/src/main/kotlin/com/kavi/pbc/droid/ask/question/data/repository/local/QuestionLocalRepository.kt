package com.kavi.pbc.droid.ask.question.data.repository.local

import com.kavi.pbc.droid.data.dto.question.Question
import com.kavi.pbc.droid.lib.datastore.AppInMemoryStore
import java.util.UUID
import javax.inject.Inject

class QuestionLocalRepository @Inject constructor(
    val inMemoryStore: AppInMemoryStore
) {
    fun setModifyingQuestion(question: Question): String {
        val randomQuestionKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomQuestionKey, question)
        return randomQuestionKey
    }

    fun getModifyingQuestion(tempQuestionKey: String): Result<Question> {
        val question = inMemoryStore.retrieveValue<Question>(key = tempQuestionKey)
        return question
    }

    fun setSelectedQuestion(question: Question): String {
        val randomQuestinKey = UUID.randomUUID().toString()
        inMemoryStore.storeValue(randomQuestinKey, question)
        return randomQuestinKey
    }

    fun getSelectedQuestion(tempQuestionKey: String): Result<Question> {
        val question = inMemoryStore.retrieveValue<Question>(key = tempQuestionKey)
        return question
    }

    fun storeQuestionModifyStatus(storingStatus: Boolean) {
        inMemoryStore.storeValue(DataKey.IS_QUESTION_UPDATED, storingStatus)
    }

    fun isQuestionModified(): Result<Boolean> {
        return inMemoryStore.retrieveValue<Boolean>(DataKey.IS_QUESTION_UPDATED)
    }
}