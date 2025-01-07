import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kot104.baitapbuoi13.room.Entity.SellerEntity
import com.kot104.baitapbuoi13.viewmodel.AuthState
import com.kot104.baitapbuoi13.viewmodel.AuthViewModel
import com.kot104.baitapbuoi13.viewmodel.SharedViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.kot104.baitapbuoi13.ROUTE_NAME_SCREEN
import java.util.regex.Pattern


@Composable
fun Register(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    shareViewModel: SharedViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var address by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val authState by authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // Xử lý trạng thái Authentication
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                // Khi đăng ký thành công, lưu thông tin Seller vào Firebase Database
                val seller = SellerEntity(
                    name = name,
                    phonenumber = phoneNumber,
                    email = email,
                    address = if (address.isEmpty()) null else address,
                    dob = if (dob.isEmpty()) null else dob,
                    nationality = if (nationality.isEmpty()) null else nationality,
                    createdat = java.time.ZonedDateTime.now().toString(),
                    updatedat = java.time.ZonedDateTime.now().toString()
                )
                shareViewModel.sellerViewModel.addSeller(seller)

                navController.navigate(ROUTE_NAME_SCREEN.LoginScreen.name)
            }

            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).message, Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register Seller", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Field
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text(text = "Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; passwordError = null },
            label = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            isError = passwordError != null
        )
        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it; passwordError = null },
            label = { Text(text = "Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError != null
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Address Field
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text(text = "Address (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // DOB Field
        OutlinedTextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text(text = "Date of Birth (yyyy-MM-dd)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nationality Field
        OutlinedTextField(
            value = nationality,
            onValueChange = { nationality = it },
            label = { Text(text = "Nationality (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = {
                if (password.length < 8){
                    passwordError = "Password must have at least 8 characters."
                } else if (!Pattern.compile("[A-Z]").matcher(password).find()){
                    passwordError = "Password must have at least 1 uppercase character."
                }  else if (!Pattern.compile("[^a-zA-Z0-9\\s]").matcher(password).find()){
                    passwordError = "Password must have at least 1 special character."
                }
                else if (password != confirmPassword){
                    passwordError = "Password must be the same with confirm password."
                }else {
                    authViewModel.signup(email, password)
                }
            },
            enabled = authState != AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if(authState == AuthState.Loading){
                androidx.compose.material3.CircularProgressIndicator()
            }else {
                Text(text = "Register")
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        // Navigate to Login
        TextButton(onClick = {
            navController.navigate("LoginScreen")
        }) {
            Text(text = "Already have an account? Login")
        }
    }
}