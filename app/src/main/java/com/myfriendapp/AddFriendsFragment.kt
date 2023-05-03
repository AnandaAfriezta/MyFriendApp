package com.myfriendapp

import android.app.DatePickerDialog
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.ULocale
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AddFriendsFragment : Fragment() {

    private var namaInput: String = ""
    private var emailInput: String = ""
    private var telpInput: String = ""
    private var alamatInput: String = ""
    private var genderInput: String = ""
    private var ttlInput: String = ""
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendsDao? = null
    private var calendar = Calendar.getInstance()


    private var edtName: EditText? = null
    private var edtEmail: EditText? = null
    private var edtTelp: EditText? = null
    private var edtAddress: EditText? = null
    private var btn_save: Button? = null
    private var spinnerGender: Spinner? = null
    private var edtTTL: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.addfriends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendDao = db?.friendDao()
        setDataSpinnerGender()
    }

    private fun setDataSpinnerGender() {
        val adapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.gender_list,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerGender?.adapter = adapter
    }

    private fun validasiInput() {
        namaInput = edtName?.text.toString()
        emailInput = edtEmail?.text.toString()
        ttlInput = edtTTL?.text.toString()
        telpInput = edtTelp?.text.toString()
        alamatInput = edtAddress?.text.toString()
        genderInput = spinnerGender?.selectedItem.toString()
        when {
            namaInput.isEmpty() -> edtName?.error = "Nama tidak boleh kosong"

            genderInput.equals("Pilih kelamin") -> tampilToast("Kelamin harus dipilih")

            emailInput.isEmpty() -> edtEmail?.error = "Email tidak boleh kosong"

            telpInput.isEmpty() -> edtTelp?.error = "Telp tidak boleh kosong"

            alamatInput.isEmpty() -> edtAddress?.error = "Alamat tidak boleh kosong"
            ttlInput.isEmpty() -> edtTTL?.error = "Tanggal lahir tidak boleh kosong"
            else -> {
                val teman = MyFriend(
                    nama = namaInput, kelamin =
                    genderInput, email = emailInput, telp = telpInput, alamat =
                    alamatInput
                )
                tambahDataTeman(teman)
            }
        }
    }

    private fun tampilToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun tambahDataTeman(teman: MyFriend): Job {
        return GlobalScope.launch {
            myFriendDao?.tambahTeman(teman)
            (activity as MainActivity).tampilMyFriendsFragment()
        }
    }

    private fun initView() {
        spinnerGender = activity?.findViewById(R.id.spinnerGender)

        edtName = activity?.findViewById(R.id.edtName)
        edtEmail = activity?.findViewById(R.id.edtEmail)
        edtTelp = activity?.findViewById(R.id.edtTelp)
        edtAddress = activity?.findViewById(R.id.edtAddress)

        btn_save = activity?.findViewById(R.id.btnSave)
        btn_save?.setOnClickListener {
            validasiInput()
            //(activity as MainActivity).tampilMyFriendsFragment()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        db?.close()
        edtName = null
        edtEmail = null
        edtTelp = null
        edtAddress = null
        btn_save = null
        spinnerGender = null
    }

    companion object {
        fun newInstance() = AddFriendsFragment()
    }
}