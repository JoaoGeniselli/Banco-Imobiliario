package com.dosei.games.toybank.core.data.mapper

import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.core.data.model.LeadPlayer

fun LeadPlayer.toNewEntity(balanceInCents: Int) = Player(
    name = name,
    colorARGB = colorARGB,
    balanceInCents = balanceInCents
)