package com.dosei.games.toybank.data.mapper

import com.dosei.games.toybank.data.local.storage.player.PlayerEntity
import com.dosei.games.toybank.data.model.LeadPlayer

fun LeadPlayer.toNewEntity(balanceInCents: Int) = PlayerEntity(
    name = name,
    colorARGB = colorARGB,
    balanceInCents = balanceInCents
)