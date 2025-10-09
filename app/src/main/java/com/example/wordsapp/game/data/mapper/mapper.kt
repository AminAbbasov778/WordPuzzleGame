package com.example.wordsapp.game.data.mapper


import com.example.wordsapp.game.data.model.GameOver
import com.example.wordsapp.game.data.model.GameSettingsUpdate
import com.example.wordsapp.game.data.model.GameStarted
import com.example.wordsapp.game.data.model.GameUpdate
import com.example.wordsapp.game.data.model.GuessLetter
import com.example.wordsapp.game.data.model.GuessWord
import com.example.wordsapp.home.data.model.JoinRoom
import com.example.wordsapp.game.data.model.LeaveRoom
import com.example.wordsapp.game.data.model.Player
import com.example.wordsapp.game.data.model.PlayerEliminated
import com.example.wordsapp.game.data.model.PlayerLeft
import com.example.wordsapp.game.data.model.PlayerReadyUpdated
import com.example.wordsapp.game.data.model.Ready
import com.example.wordsapp.game.data.model.HomeState
import com.example.wordsapp.game.data.model.Turn
import com.example.wordsapp.game.data.model.UnreadyUpdate
import com.example.wordsapp.game.domain.model.GameOverModel
import com.example.wordsapp.game.domain.model.GameSettingsUpdateModel
import com.example.wordsapp.game.domain.model.GameStartedModel
import com.example.wordsapp.game.domain.model.GameUpdateModel
import com.example.wordsapp.game.domain.model.GuessLetterModel
import com.example.wordsapp.game.domain.model.GuessWordModel
import com.example.wordsapp.game.domain.model.JoinRoomModel
import com.example.wordsapp.game.domain.model.LeaveRoomModel
import com.example.wordsapp.game.domain.model.PlayerEliminatedModel
import com.example.wordsapp.game.domain.model.PlayerLeftModel
import com.example.wordsapp.game.domain.model.PlayerModel
import com.example.wordsapp.game.domain.model.PlayerReadyUpdatedModel
import com.example.wordsapp.game.domain.model.ReadyModel
import com.example.wordsapp.game.domain.model.HomeStateModel
import com.example.wordsapp.game.domain.model.TurnModel
import com.example.wordsapp.game.domain.model.UnreadyUpdateModel


fun GameOver.toDomain(): GameOverModel {
    return GameOverModel(
        winner = winner,
        word = word,
        hasWinner = hasWinner

    )
}

fun GameSettingsUpdate.toDomain(): GameSettingsUpdateModel{
    return GameSettingsUpdateModel(
        canGuessWord = canGuessWord
    )
}

fun HomeState.toDomain(): HomeStateModel{
    return HomeStateModel(
        roomId = roomId,
        players = players.map { it.toDomain() },
        started = started,
        discovered = discovered,
        wrongGuesses = wrongGuesses,
        currentTurn = currentTurn
    )
}







fun GameStarted.toDomain(): GameStartedModel{
    return GameStartedModel(
        wordLength = wordLength,
        players = players.map { it.toDomain() }

    )

}
fun Player.toDomain(): PlayerModel {
    return PlayerModel(
        id = id,
        name = name,
        ready = ready,
        eliminated = eliminated,
        score = score

    )
}

fun GameUpdate.toDomain(): GameUpdateModel {
    return GameUpdateModel(
        discovered = discovered,
        guessedBy = guessedBy,
        guessedLetter = guessedLetter,
        correct = correct,
        playerScore = playerScore
    )


}

fun GuessLetterModel.toData():  GuessLetter {
    return  GuessLetter(
        roomId = roomId,
        userId = userId,
        letter = letter
    )
}

fun PlayerReadyUpdated.toDomain(): PlayerReadyUpdatedModel {
    return PlayerReadyUpdatedModel(
        userId = userId,
        ready = ready
    )
}


fun  GuessWordModel.toData(): GuessWord {
    return GuessWord(
        roomId = roomId,
        userId = userId,
        guess = guess
    )
}

fun JoinRoomModel.toData(): JoinRoom{
    return JoinRoom(
        roomId = roomId,
        username = username,
        userId = userId,
        difficulty = difficulty,
        language = language
    )

}
fun LeaveRoomModel.toData(): LeaveRoom {
    return LeaveRoom(
        roomId = roomId,
        userId = userId
    )
}

fun PlayerEliminated.toDomain(): PlayerEliminatedModel{
    return PlayerEliminatedModel(
        userId = userId
    )
}

fun ReadyModel.toData(): Ready {
    return Ready(
        userId = userId,
        roomId = roomId,
        difficulty = difficulty,
        language = language
    )
}
fun Turn.toDomain(): TurnModel{
    return TurnModel(
        name = name,
        userId = userId
    )
}

fun UnreadyUpdateModel.toData(): UnreadyUpdate {
    return UnreadyUpdate(
        roomId = roomId,
        userId = userId
    )
}

fun PlayerLeft.toDomain(): PlayerLeftModel{
    return PlayerLeftModel(
        userId = userId
    )

}



