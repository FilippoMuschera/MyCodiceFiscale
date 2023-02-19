package com.muschera.mycodicefiscale.ui.theme

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.muschera.mycodicefiscale.MainActivity
import com.muschera.mycodicefiscale.R
import com.muschera.mycodicefiscale.codicefiscale.CodiceFiscaleController
import com.muschera.mycodicefiscale.database.RepositoryCity
import com.muschera.mycodicefiscale.database.SavedCF
import com.muschera.mycodicefiscale.model.CFModel

val buttonColor = Color(0xFF022B3A)
val lightBlueColor = Color(0xFF1F7A8C)
val lightPurpleColor = Color(0xFFDABFFF)
val textColor = Color(0xFFFFFEF2)

@Composable
fun CFLayout(
    cfController: CodiceFiscaleController,
    repositoryCity: RepositoryCity,
    context: Context,
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = context.getString(R.string.yourCFis),
            fontSize = 28.sp,
            color = Color(0xFF3D426B),
            modifier = Modifier.padding(4.dp)
        )

        SelectionContainer(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        ) {
            Text(
                CFModel.res.value + "\n",
                fontSize = 28.sp,
                color = Color(0xFF3D426B)
            )
        }

        TextBox(s = stringResource(id = R.string.lastName), 1f, "lastName", cfController)

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            TextBox(s = stringResource(id = R.string.firstName), 0.6f, "firstName", cfController)
            TextBox(s = stringResource(id = R.string.sex), 1f, "sex", cfController)
        }

        TextBox(s = stringResource(id = R.string.birthPlace), 1f, "birthPlace", cfController)
        TextBox(s = stringResource(id = R.string.city), 1f, "distr", cfController)


        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            TextBox(s = stringResource(id = R.string.day), 0.3f, "day", cfController)
            TextBox(s = stringResource(id = R.string.month), 0.5f, "month", cfController)
            TextBox(s = stringResource(id = R.string.year), 1f, "year", cfController)


        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {


            Button(
                onClick = {
                    if (CFModel.res.value != "" && CFModel.firstName != "" && CFModel.lastName != "") {
                        val newCF = SavedCF()
                        newCF.cf = CFModel.res.value
                        newCF.firstName =
                            CFModel.firstName[0].uppercase() + CFModel.firstName.substring(1)
                                .lowercase()
                        newCF.lastName =
                            CFModel.lastName[0].uppercase() + CFModel.lastName.substring(1)
                                .lowercase()
                        try {
                            repositoryCity.insertSavedCF(newCF)
                            Toast.makeText(
                                context,
                                context.getString(R.string.savedSuccess),
                                Toast.LENGTH_LONG
                            ).show()

                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.alreadySaved),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp, 35.dp),
                shape = RoundedCornerShape(35),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor
                )
            ) {


                Text(
                    text = stringResource(R.string.saveBtn),
                    color = Color.White
                )

            }

            Button(
                onClick = {
                    MainActivity.onMainScreen = false
                    navController.navigate("SavedCFLayout")

                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp, 35.dp),
                shape = RoundedCornerShape(35),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor
                )
            ) {

                Text(
                    text = stringResource(R.string.goToSaved),
                    color = Color.White
                )
            }
        }


    }
}


@Composable
fun TextBox(s: String, f: Float, fieldName: String, cfController: CodiceFiscaleController) {

    var text by rememberSaveable { mutableStateOf("") }

    TextField(

        value = text,

        singleLine = true,
        onValueChange = {
            if (fieldName == "lastName") {
                text = it
                CFModel.lastName = it
                cfController.getCF()
            } else if (fieldName == "firstName") {
                text = it
                CFModel.firstName = it
                cfController.getCF()
            } else if (fieldName == "sex") {
                if (it != "" && it != "M" && it != "m" && it != "F" && it != "f") {
                    text = ""
                } else {
                    text = it
                    CFModel.sex = it
                    cfController.getCF()
                }
            } else if (fieldName == "birthPlace") {
                text = it
                CFModel.birthPlace = it
                cfController.getCF()
            } else if (fieldName == "distr") {
                if (it.length <= 2) {
                    text = it
                    CFModel.distr = it
                    cfController.getCF()
                }

            } else if (fieldName == "day") {
                if (it.length <= 2) {
                    text = it
                    CFModel.day = it
                    cfController.getCF()
                }
            } else if (fieldName == "month") {
                if (it.length <= 2) {
                    text = it
                    CFModel.month = it
                    cfController.getCF()
                }
            } else if (fieldName == "year") {
                if (it.length == 4) {
                    text = it
                    CFModel.year = it
                    cfController.getCF()
                } else if (it.length < 4) text = it
            }
        },
        label = {
            Text(
                s,
                modifier = Modifier.padding(8.dp),
                color = textColor
            )
        },
        modifier = Modifier
            .fillMaxWidth(f)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = lightBlueColor,
            focusedIndicatorColor = lightPurpleColor,
            unfocusedIndicatorColor = Color(0xFF8FCACA),
            textColor = textColor
        )


    )
}


@Composable
fun SavedCFLayout(list: List<SavedCF>, context: Context, repositoryCity: RepositoryCity) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val listOfCF = list
        if (listOfCF.isEmpty()) Toast.makeText(
            context,
            context.getString(R.string.NoCF),
            Toast.LENGTH_LONG
        ).show()
        items(listOfCF) { savedCF -> //items ha due parametri, cosa mostrare e come mostrarlo (con delle cards in questo caso)
            Card(modifier = Modifier.padding(8.dp), elevation = 16.dp) {
                Column(
                    modifier = Modifier
                        .background(lightBlueColor)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    SelectionContainer(
                        modifier = Modifier.align(Alignment.Start),
                    ) {
                        Text(
                            text = savedCF.cf,
                            fontSize = 29.sp,
                            modifier = Modifier.background(lightBlueColor),
                            color = textColor
                        )
                    }

                    Text(
                        text = "\n " + savedCF.firstName + " " + savedCF.lastName,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.background(lightBlueColor),
                        color = textColor

                    )

                    Button(
                        onClick = {
                            repositoryCity.deleteSavedCF(savedCF.cf)
                            Toast.makeText(
                                context,
                                context.getString(R.string.CfDeleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(100.dp, 35.dp)
                            .align(Alignment.End),
                        shape = RoundedCornerShape(35),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = buttonColor
                        )
                    ) {

                        Text(
                            text = stringResource(R.string.delete),
                            color = Color.White
                        )
                    }


                }

            }
        }
    }

}




















