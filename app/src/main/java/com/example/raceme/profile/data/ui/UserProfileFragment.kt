package com.example.raceme.profile.data.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import coil.load
import com.example.raceme.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class UserProfileFragment : Fragment() {

    private lateinit var profileImageView: ImageView
    private lateinit var usernameEditText: EditText
    private lateinit var bioEditText: EditText
    private lateinit var goalSeekBar: SeekBar
    private lateinit var goalValueText: TextView
    private lateinit var saveButton: Button
    private lateinit var changePhotoButton: Button
    private var imageUri: Uri? = null

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profileImageView = view.findViewById(R.id.ivProfilePic)
        usernameEditText = view.findViewById(R.id.etUsername)
        bioEditText = view.findViewById(R.id.etBio)
        goalSeekBar = view.findViewById(R.id.sbGoal)
        goalValueText = view.findViewById(R.id.tvGoalValue)
        saveButton = view.findViewById(R.id.btnSave)
        changePhotoButton = view.findViewById(R.id.btnChangePhoto)

        // initial label
        goalValueText.text = "${goalSeekBar.progress} steps"

        goalSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                goalValueText.text = "$progress steps"   // <- (you had "$progress_steps")
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        changePhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        saveButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val bio = bioEditText.text.toString().trim()
            val goal = goalSeekBar.progress

            if (username.isBlank()) {
                Toast.makeText(requireContext(), "Pick a username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = auth.currentUser?.uid ?: run {
                Toast.makeText(requireContext(), "Sign in first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "username" to username,
                "bio" to bio,
                "dailyGoal" to goal
            )

            fun writeDoc(photoUrl: String?) {
                if (!photoUrl.isNullOrBlank()) data["photoUrl"] = photoUrl
                db.collection("users").document(uid)
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Save failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }

            val local = imageUri
            if (local != null) {
                val ref = storage.reference.child("users/$uid/profile.jpg")
                ref.putFile(local)
                    .addOnSuccessListener { ref.downloadUrl.addOnSuccessListener { url -> writeDoc(url.toString()) } }
                    .addOnFailureListener { Toast.makeText(requireContext(), "Upload failed", Toast.LENGTH_SHORT).show() }
            } else {
                writeDoc(null)
            }
        }

        // Optional: load existing profile
        val uid = auth.currentUser?.uid
        if (uid != null) {
            db.collection("users").document(uid).get()
                .addOnSuccessListener { snap ->
                    val username = snap.getString("username") ?: ""
                    val bio = snap.getString("bio") ?: ""
                    val goal = (snap.getLong("dailyGoal") ?: 10000).toInt()
                    val photo = snap.getString("photoUrl") ?: ""

                    usernameEditText.setText(username)
                    bioEditText.setText(bio)
                    goalSeekBar.progress = goal
                    goalValueText.text = "$goal steps"
                    if (photo.isNotBlank()) profileImageView.load(photo) { placeholder(R.drawable.ic_person_24) }
                }
        }
    }

    @Deprecated("startActivityForResult is fine for this simple picker")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            profileImageView.setImageURI(imageUri)
        }
    }
}
