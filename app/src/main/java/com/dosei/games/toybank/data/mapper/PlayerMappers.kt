package com.dosei.games.toybank.data.mapper

import com.dosei.games.toybank.data.local.storage.player.Player
import com.dosei.games.toybank.data.model.LeadPlayer

fun LeadPlayer.toNewEntity(balanceInCents: Int) = Player(
    name = name,
    colorARGB = colorARGB,
    balanceInCents = balanceInCents
)