package com.jgeniselli.banco.game.transaction

import androidx.lifecycle.*
import com.jgeniselli.banco.game.common.domain.GameRepository
import com.jgeniselli.banco.game.common.domain.Player
import com.jgeniselli.banco.game.common.domain.PlayerRepository
import com.jgeniselli.banco.game.common.domain.TransactionRepository

class TransactionViewModel(
    private val playerId: Int,
    private val playerRepository: PlayerRepository,
    private val gameRepository: GameRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel(), LifecycleObserver {

    private lateinit var selectedPlayer: Player
    private lateinit var otherPlayers: List<Player>
    private val viewStateEvent = MutableLiveData<TransactionViewState>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun start() {
        retrieveSelectedPlayer(
            thenExecute = this::retrieveOtherPlayers
        )
    }

    private fun retrieveSelectedPlayer(thenExecute: () -> Unit) {
        playerRepository.findById(playerId) { foundPlayer ->
            foundPlayer?.let {
                this.selectedPlayer = it
            } ?: throw IllegalStateException("Player is unknown")
            thenExecute()
        }
    }

    private fun retrieveOtherPlayers() {
        val otherPlayers = gameRepository.getActiveGame()
            ?.players
            ?.filter { it != selectedPlayer } ?: listOf()
        viewStateEvent.value = TransactionViewState.Content(selectedPlayer.name, otherPlayers)
    }

    fun observeViewState(owner: LifecycleOwner, observer: Observer<TransactionViewState>) {
        viewStateEvent.observe(owner, observer)
    }

    fun applyDebit(value: Double) {
        addCashToPlayer(value * -1.0, selectedPlayer)
    }

    private fun addCashToPlayer(value: Double, player: Player) {
        player.currentValue += value
        transactionRepository.saveTransaction(player, value)
        viewStateEvent.postValue(TransactionViewState.TransactionComplete)
    }

    fun applyCredit(value: Double) {
        addCashToPlayer(value, selectedPlayer)
    }

    fun applyTransfer(value: Double, otherPlayer: Player) {
        addCashToPlayer(value * -1.0, selectedPlayer)
        addCashToPlayer(value, otherPlayer)
    }
}