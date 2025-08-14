package com.dosei.games.toybank.newgame.data.mapper

import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.newgame.data.model.LeadPlayer

internal fun LeadPlayer.toNewEntity(balanceInCents: Int) = Player(
    name = name,
    colorARGB = colorARGB,
    balanceInCents = balanceInCents
)