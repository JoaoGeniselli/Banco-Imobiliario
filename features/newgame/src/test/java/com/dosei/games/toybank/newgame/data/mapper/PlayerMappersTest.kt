package com.dosei.games.toybank.newgame.data.mapper

import androidx.compose.ui.graphics.toArgb
import com.dosei.games.toybank.core.data.storage.player.Player
import com.dosei.games.toybank.newgame.data.model.LeadPlayer
import com.dosei.games.toybank.ui.theme.Purple
import org.junit.Assert.assertEquals
import org.junit.Test

class PlayerMappersTest {

    @Test
    fun `check map`() {
        val lead = LeadPlayer("John", Purple.toArgb())
        assertEquals(
            Player(
                name = "John",
                colorARGB = Purple.toArgb(),
                balanceInCents = 300_00
            ),
            lead.toNewEntity(300_00)
        )
    }
}