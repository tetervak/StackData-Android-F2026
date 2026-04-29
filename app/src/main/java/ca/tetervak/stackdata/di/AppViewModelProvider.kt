package ca.tetervak.stackdata.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ca.tetervak.stackdata.StackDataApplication
import ca.tetervak.stackdata.ui.stack.StackViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            StackViewModel(
                stackDataApplication().container.stackItemRepository
            )
        }
    }
}

fun CreationExtras.stackDataApplication(): StackDataApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StackDataApplication)