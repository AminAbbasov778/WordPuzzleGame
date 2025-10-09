package com.example.wordsapp.game.presentation.mapper

import com.example.wordsapp.game.domain.model.GameOverModel
import com.example.wordsapp.game.domain.model.GameSettingsUpdateModel
import com.example.wordsapp.game.domain.model.GameStartedModel
import com.example.wordsapp.game.domain.model.GameUpdateModel
import com.example.wordsapp.game.domain.model.GuessLetterModel
import com.example.wordsapp.game.domain.model.GuessWordModel
import com.example.wordsapp.game.domain.model.HomeStateModel
import com.example.wordsapp.game.domain.model.JoinRoomModel
import com.example.wordsapp.game.domain.model.LeaveRoomModel
import com.example.wordsapp.game.domain.model.PlayerEliminatedModel
import com.example.wordsapp.game.domain.model.PlayerLeftModel
import com.example.wordsapp.game.domain.model.PlayerModel
import com.example.wordsapp.game.domain.model.PlayerReadyUpdatedModel
import com.example.wordsapp.game.domain.model.ReadyModel
import com.example.wordsapp.game.domain.model.TurnModel
import com.example.wordsapp.game.domain.model.UnreadyUpdateModel
import com.example.wordsapp.game.presentation.model.GameOverUi
import com.example.wordsapp.game.presentation.model.GameSettingsUpdateUi
import com.example.wordsapp.game.presentation.model.GameStartedUi
import com.example.wordsapp.game.presentation.model.GameUpdateUi
import com.example.wordsapp.game.presentation.model.GuessLetterUi
import com.example.wordsapp.game.presentation.model.GuessWordUi
import com.example.wordsapp.game.presentation.model.HomeStateUi
import com.example.wordsapp.game.presentation.model.JoinRoomUi
import com.example.wordsapp.game.presentation.model.LeaveRoomUi
import com.example.wordsapp.game.presentation.model.PlayerEliminatedUi
import com.example.wordsapp.game.presentation.model.PlayerLeftUi
import com.example.wordsapp.game.presentation.model.PlayerReadyUpdatedUi
import com.example.wordsapp.game.presentation.model.PlayerUi
import com.example.wordsapp.game.presentation.model.ReadyUi
import com.example.wordsapp.game.presentation.model.TurnUi
import com.example.wordsapp.game.presentation.model.UnreadyUpdateUi


fun GameOverModel.toUi(): GameOverUi {
   return GameOverUi(
       winner = winner,
       word = word,
       hasWinner = hasWinner

   )
}

fun HomeStateModel.toUi(): HomeStateUi{
    return HomeStateUi(
        roomId = roomId,
        players = players.map { it.toUi() },
        started = started,
        discovered = discovered,
        wrongGuesses = wrongGuesses,
        currentTurn = currentTurn
    )
}

fun GameSettingsUpdateModel.toUi(): GameSettingsUpdateUi{
    return GameSettingsUpdateUi(
        canGuessWord = canGuessWord
    )
}

fun GameStartedModel.toUi(): GameStartedUi{
    return GameStartedUi(
        wordLength = wordLength,
        players = players.map { it.toUi() }

    )

}
fun PlayerModel.toUi(): PlayerUi {
    return PlayerUi(
        id = id,
        name = name,
        ready = ready,
        eliminated = eliminated,
        score = score

    )
}

fun GameUpdateModel.toUi(): GameUpdateUi {
   return GameUpdateUi(
       discovered = discovered,
       guessedBy = guessedBy,
       guessedLetter = guessedLetter,
       correct = correct,
       playerScore = playerScore
   )


}

fun GuessLetterUi.toDomain():  GuessLetterModel {
    return  GuessLetterModel(
        roomId = roomId,
        userId = userId,
        letter = letter
    )
}

fun  GuessWordUi.toDomain(): GuessWordModel {
    return GuessWordModel(
        roomId = roomId,
        userId = userId,
        guess = guess
    )
}

fun JoinRoomUi.toDomain(): JoinRoomModel{
    return JoinRoomModel(
        roomId = roomId,
        username = username,
        userId = userId,
        difficulty =difficulty,
        language = language
    )

}
fun LeaveRoomUi.toDomain(): LeaveRoomModel {
    return LeaveRoomModel(
        roomId = roomId,
        userId = userId
    )
}

fun PlayerEliminatedModel.toUi(): PlayerEliminatedUi{
    return PlayerEliminatedUi(
        userId = userId
    )
}

fun ReadyUi.toDomain(): ReadyModel {
    return ReadyModel(
        userId = userId,
        roomId = roomId,
        difficulty = difficulty,
        language = language
    )
}
fun TurnModel.toUI(): TurnUi{
    return TurnUi(
        name = name,
        userId = userId
    )
}

fun UnreadyUpdateUi.toDomain(): UnreadyUpdateModel {
    return UnreadyUpdateModel(
        roomId = roomId,
        userId = userId
    )
}

fun PlayerReadyUpdatedModel.toUi(): PlayerReadyUpdatedUi {
    return PlayerReadyUpdatedUi(
        userId = userId,
        ready = ready
    )
}

fun PlayerLeftModel.toUi(): PlayerLeftUi{
    return PlayerLeftUi(
        userId = userId
    )

}


