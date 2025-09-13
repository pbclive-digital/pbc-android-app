package com.kavi.pbc.droid.event.ui.selected

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import javax.inject.Inject

class EventPotluckSheet @Inject constructor() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PotluckSheetUI(sheetState: SheetState, showSheet: MutableState<Boolean>) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showSheet.value = false
            }
        ) {
            //val staffDataString = Gson().toJson(repository.staffData)
            //uiModuleRegistry.getUIModule("STAFF")?.GetEntryUIWithData(data = staffDataString)
        }
    }
}