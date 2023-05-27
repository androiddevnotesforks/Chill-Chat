package top.chilfish.chillchat.ui.debugger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.UserRepository
import top.chilfish.chillchat.provider.AccountProvider
import top.chilfish.chillchat.provider.SettingsProvider
import top.chilfish.chillchat.provider.curCid
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val contactsRepo: ContactsRepository,
    private val userRepo: UserRepository,
) : ViewModel() {
    fun setHost(host: String) = viewModelScope.launch {
        SettingsProvider.setHost(host)
    }

    fun find(cid: String) = viewModelScope.launch {
        val res = contactsRepo.findUser(cid)
        Log.d("Chat", "debug: User: $res")
    }

    fun setCid(cid: String) = viewModelScope.launch {
        val res = contactsRepo.findUser(cid) ?: return@launch
        AccountProvider.setLogin(res.id, cid)
    }

    fun loadContacts() = viewModelScope.launch {
//        contactsRepo.allUsers().collect {
//            Log.d("Chat", "debug: Contacts: $it")
//        }
        val user = contactsRepo.findUser(curCid)
        contactsRepo.loadAll(user?.id!!)
    }

    fun login() = viewModelScope.launch {
        val res = userRepo.auth("chilfish", "12345678")
        Log.d("Chat", "login:$res")
    }
}